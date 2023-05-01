package Graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Represents a window on the screen where graphical images may be drawn.
 * <p>
 * Creating a <code>GraphWin</code> should generally be the first step
 * taken by any program using this library.  For example:
 * <pre>
 * {@code
 *      GraphWin win = new GraphWin("My Window", 600, 600);
 * }
 * </pre>
 * This will create a 600 pixel by 600 pixel window
 */

public class GraphWin
    implements MouseListener, KeyListener, WindowListener {
    
    private JFrame frame;
    private GraphicsPanel panel;
    private boolean closed;

    private boolean autoFlush;

    private int windowWidth;
    private int windowHeight;

    // A really good argument could be made for having these
    // in the GraphicsPanel class instead of here, but since
    // setCoords is a GraphWin method, they're here for now.
    private double xOffset;
    private double yOffset;
    private double xScale;
    private double yScale;

    private int lastX = 0;
    private int lastY = 0;
    private boolean hasPoint = false;

    // Invariant:
    //      if hasChar is true, exactly one of lastChar or lastCode
    //      is non-zero.
    //
    // a non-zero lastChar indicates that a normal key was pressed
    // a non-zero lastCode indicates that a special key (e.g. an
    // arrow key) was pressed
    private char lastChar = 0;
    private int lastCode = 0;
    private boolean hasChar = false;

    private static final HashMap<Integer,String> specialKeys;
    static {
        specialKeys = new HashMap<>();
        specialKeys.put(KeyEvent.VK_LEFT, "leftarrow");
        specialKeys.put(KeyEvent.VK_RIGHT, "rightarrow");
        specialKeys.put(KeyEvent.VK_UP, "uparrow");
        specialKeys.put(KeyEvent.VK_DOWN, "downarrow");
        specialKeys.put(KeyEvent.VK_HOME, "home");
        specialKeys.put(KeyEvent.VK_END, "end");
        specialKeys.put(KeyEvent.VK_F1, "F1");
    }

    /**
     * Supports drawing into a <code>GraphWin`.
     * <p>
     * In order to display graphics in Java, we need to create a subclass of
     * <code>JPanel</code>, and override the <code>paintComponent</code> method.
     * We're using <code>JPanel</code>'s double-buffer capability to support
     * smooth animation by having <code>paintComponent()</code> draw into an
     * off-screen bitmap which is then bit-bltted to the screen (or it may use
     * screen-flipping; I'm not sure).
     * <p>
     * This class intentionally has package visibiilty, since some methods in
     * <code>GraphicsObject</code> need to add and remove items from the display
     * list.
     */
    class GraphicsPanel extends JPanel {

        /**
         * The list of objects to be redrawn on every call to
         * <code>paintComponent</code>.
         */
        private ArrayList<GraphicsObject> displayList;

        /**
         * Constructs a <code>>GraphicsPanel</code> with support for double
         * buffering.
         */
        public GraphicsPanel() {
            super(true);
            displayList = new ArrayList<>();
        }

        /**
         * Repaints the window by called <code>doDraw()</code> for every object
         * in the display list.  Objects are drawn in the order in which they
         * are added.
         */
        @Override
        public void paintComponent(Graphics g) {
            for (GraphicsObject obj : displayList) {
                obj.doDraw(g);
            }
        }

        /**
         * Adds a new object to the display list.
         * 
         * @param obj       The <code>GraphicsObject</code>
         *                  to be added.
         */
        public void addObject(GraphicsObject obj) {
            displayList.add(obj);
        }

        /**
         * Removes the specified object from the display list.
         * 
         * @param obj
         */
        public void removeObject(GraphicsObject obj) {
            // We don't want to use indexOf() because we're really looking
            // for this exact object rather than using .equals().
            for (int i = 0; i < displayList.size(); i++) {
                if (displayList.get(i) == obj) {
                    displayList.remove(i);
                    return;
                }
            }
        }
    }

    /**
     * Retrieves the <code>GraphicsPanel</code> object for this window.
     * 
     * @return      The <code>GraphicsPanel</code> for this window.
     */
    // This method intentionally has package visibility.
    GraphicsPanel getGraphicsPanel() {
        return panel;
    }

    /**
     * Updates the screen if we are using autoflush mode.
     * <p>
     * This method intentionally has package visibility.
     */
    void checkUpdate() {
        if (autoFlush)
            frame.repaint();
    }

    /**
     * Returns a string representing the given key code or character.
     * <p>
     * Normal Unicode characters will return a one-character string.
     * For special keyboard characters, a descriptive string (e.g.
     * "uparrwo") is returned.
     * <p>
     * Exactly one of <code>code</code> or <code>c</code> must be non-zero.
     * If <code>code</code> is non-zero, that code must have an entry in
     * <code>specialKeys`. If either of these conditions is not met, this
     * will return <code>null</code>.
     * 
     * @param code      The key code to translate (or 0 to use <code>c</code>
     *                  instead).
     * @param c         The character to return (or 0 to use <code>code</code>
     *                  instead.)
     * @return          A one-character string indicating that a "normal" key
     *                  was pressed, or a string indicating the special key
     *                  that was pressed.
     */
    private String keyCodeToString(int code, char c) {
        if (c != 0)
            return ((Character)c).toString();
        if (code != 0)
            return specialKeys.get(code);
        return null;
    }

    /**
     * Creates a window for drawing into.
     * 
     * @param title         The window title.
     * @param width         The width of the window in pixels.
     * @param height        The height of the window in pixels.
     * @param autoFlush     If <code>true</code>, the window is redrawn
     *                      with every <code>move()</code> or
     *                      <code>undraw()</code> operation.
     */
    public GraphWin(String title, int width, int height, boolean autoFlush) {

        windowWidth = width;
        windowHeight = height;
        this.autoFlush = autoFlush;

        frame = new JFrame();
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.getContentPane().setFocusable(true);
        frame.getContentPane().addMouseListener(this);
        frame.getContentPane().addKeyListener(this);
        frame.addWindowListener(this);

        panel = new GraphicsPanel();
        frame.getContentPane().add(panel);
        panel.setPreferredSize(new Dimension(width, height));
        frame.pack();

        frame.setVisible(true);
        closed = false;

        // The default coordinate system as (0,0) at the
        // top left corner.
        setCoords(0, height, width, 0);
    }

    /**
     * Creates a window for drawing into, with autoflush set
     * to <code>true</code>.
     * 
     * @param title         The window title.
     * @param width         The width of the window in pixels.
     * @param height        The height of the window in pixels.
     */
    public GraphWin(String title, int width, int height) {
        this(title, width, height, true);
    }

    ///////////////////////////////////////////////////////
    //
    // MouseListener methods
    //
    // TODO: Figure out if we should be using mouseReleased
    // instead of mouseClicked.  Right now, if the mouse is
    // moved while pressed, we get no click.

    @Override
    public void mouseClicked(MouseEvent e) {
        this.lastX = e.getX();
        this.lastY = e.getY();
        this.hasPoint = true;
        // System.out.printf("Mouse click at (%d,%d)\n", lastX, lastY);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    ///////////////////////////////////////////////////////
    //
    // KeyListener methods

    @Override
    public void keyTyped(KeyEvent e) {
        lastCode = 0;
        lastChar = e.getKeyChar();
        hasChar = true;
        // System.out.printf("   keyTyped: Code %d, char %c\n", lastCode, lastChar);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.printf(" keyPressed: Code %d, char %c\n", e.getKeyCode(), e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (specialKeys.containsKey(e.getKeyCode())) {
            lastCode = e.getKeyCode();
            lastChar = 0;
            hasChar = true;
        }
        // System.out.printf("keyReleased: Code %d, char %c\n", e.getKeyCode(), e.getKeyChar());
    }

    ///////////////////////////////////////////////////////
    //
    // WindowListener methods

    @Override
    public void windowOpened(WindowEvent e) {
        closed = false;
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        closed = true;
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    ///////////////////////////////////////////////////////
    //
    // instance methods

    /**
     * Translates a screen point to user coordinates.
     * <p>
     * The transform is set up by calling <code>setCoords</code>.
     * 
     * @param x         The x screen coordinate
     * @param y         The y scrren coordinate
     * @return          A <code>Point</code> object representing
     *                  the corresponding point in user coordinates.
     */
    public Point screenXYtoPoint(int x, int y) {
        y = (windowHeight-1) - y;
        double userX = x / xScale + xOffset;
        double userY = y / yScale + yOffset;
        return new Point(userX, userY);
    }

    /**
     * Translates a user coordinate point to screen coordinates.
     * <p>
     * The transform is set up by calling <code>setCoords</code>.
     * 
     * @param pt        The Point object to convert to scrren
     *                  coordinates.
     * @return          A <code>java.awt.Point</code> object
     *                  describing the corresponding screen
     *                  coordinate point.
     */
    public java.awt.Point pointXYtoScreen(Point pt) {
        int screenX = (int)((pt.getX() - xOffset) * xScale);
        int screenY = (int)((pt.getY() - yOffset) * yScale);
        screenY = (windowHeight-1) - screenY;
        return new java.awt.Point(screenX, screenY);
    }

    /**
     * Translates an x distance in user coordinates to a
     * distance in screen coordinates.
     * 
     * @param dx        The user coordinate distance
     * @return          The corresponding screen coorindate
     *                  distance.
     */
    public double xDistToScreen(double dx) {
        return (int)(dx * xScale);
    }

    /**
     * Translates a y distance in user coordinates to a
     * distance in screen coordinates.
     * 
     * @param dy        The user coordinate distance
     * @return          The corresponding screen coorindate
     *                  distance.
     */
    public double yDistToScreen(double dy) {
        return (int)(dy * yScale);
    }

    /**
     * Sets the background color of the window using a ColorRGB object.
     * 
     * @param color     The ColorRGB object containing the color to use.
     */
    public void setBackground(ColorRGB color) {
        panel.setBackground(color.getColor());
    }

    /**
     * Sets the background color of the window using an X11 color name.
     * 
     * @param color     The X11 color name to use.
     */
    public void setBackground(String color) {
        setBackground(new ColorRGB(color));
    }

    /**
     * Closes the window.
     */
    public void close() {
        // See https://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        closed = true;
    }

    /**
     * Returns an indicator of whether the window is open.
     * 
     * @return          <code>true</code> if the window is open
     *                  and <code>false</code> otherwise.
     */
    public boolean isOpen() {
        return !closed;
    }

    /**
     * Returns an indicator of whether the window is closed.
     * 
     * @return          <code>true</code> if the window is closed
     *                  and <code>false</code> otherwise.
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Waits for a mouse click and returns the user-coordinate point where
     * the mouse was clicked.
     * <p>
     * I suspect the "proper" way to do this requires using threads, but since
     * this not intended for production use, we should be okay here.
     * 
     * @return      A Point object representing the user coordinate point
     *              where the mouse was clicked, or <code>null</code> if the
     *              window is closed.
     * @throws InterruptedException     Thrown if the program is interrupted.
     */
    public Point getMouse() throws InterruptedException {
        while (!hasPoint && !closed) {
            Thread.sleep(1);
        }

        if (closed) return null;

        hasPoint = false;
        return screenXYtoPoint(lastX, lastY);
    }

    /**
     * Returns the most recent user-coordinate point clicked, or
     * <code>null</code> if no click has occurred since the last
     * call to <code>getMouse()</code> or <code>checkMouse</code>.
     * <p>
     * I suspect the "proper" way to do this requires using threads,
     * but since this not intended for production use, we should be
     * okay here.
     * 
     * @return           A Point object representing the most recent
     *                  point clicked, or <code>null</code> if no click
     *                  has happened since the last call to either
     *                  <code>getMouse()</code> or <code>checkMouse</code>.
     * @throws InterruptedException     Thrown if the program is interrupted.
     */
    public Point checkMouse() throws InterruptedException {
        // Minor kludge: since we're running this all in a
        // single thread, we need to provide some time for
        // the event handling to kick in.
        Thread.sleep(0);
        if (!hasPoint || closed) {
            return null;
        } else {
            hasPoint = false;
            return screenXYtoPoint(lastX, lastY);
        }
    }

    /**
     * Waits for a key press and returns a <code>String</code> representing
     * the key that was pressed.
     * <p>
     * Normal Unicode characters are returned as a single-character
     * <code>String</code>.  Special keys (for example, arrow keys) are
     * returned as a descriptive <code>String</code>, such as "uparrow"
     * for the up arrow.
     * 
     * @return              A <code>String</code> representing the last
     *                      key pressed, or <code>null</code> if the
     *                      window was closed.
     * @throws InterruptedException     Thrown if the program is interrupted.
     */
    public String getKey() throws InterruptedException {
        while (!hasChar && !closed) {
            Thread.sleep(1);
        }

        if (closed) return null;

        hasChar = false;
        return keyCodeToString(lastCode, lastChar);
    }

    /**
     * Returns the most recent key pressed, or <code>null</code> if no key
     * press has occurred since the last call to <code>getKey()</code> or
     * <code>checkKey</code>.
     * <p>
     * I suspect the "proper" way to do this requires using threads,
     * but since this not intended for production use, we should be
     * okay here.
     * 
     * @return           A <code>String</code> representing the last
     *                  key pressed, or <code>null</code> if no key was
     *                  pressed since the last call to either
     *                  <code>getKey()</code> or <code>checkKey</code>.
     * @throws InterruptedException     Thrown if the program is interrupted.
     */
    public String checkKey() throws InterruptedException {
        // Minor kludge: since we're running this all in a
        // single thread, we need to provide some time for
        // the event handling to kick in.
        Thread.sleep(0);
        if (!hasChar || closed) {
            return null;
        } else {
            hasChar = false;
            return keyCodeToString(lastCode, lastChar);
        }
    }

    /**
     * Sets up the coordiantes for the window.
     * <p>
     * The point <code>(xmin,ymin)</code> is mappeed to the lower-left corner
     * of the window, and the point <code>(xmax,ymax)</code> is mapped to the
     * upper-right corner.
     * <p>
     * For example, this call to <code>setCoords()</code>:
     * 
     *      <code>win.setCoords(-0.1, -0.1, 1.1, 1.1)</code>
     * 
     * will set up a window that provides a 1.0x1.0 area with a 0.1 margin
     * all around.
     * 
     * @param xmin      The x coordinate of the point to be mapped to the
     *                  lower-left corner of the window.
     * @param ymin      The y coordinate of the point to be mapped to the
     *                  lower-left corner of the window.
     * @param xmax      The x coordinate of the point to be mapped to the
     *                  upper-right corner of the window.
     * @param ymax      The y coordinate of the point to be mapped to the
     *                  upper-right corner of the window.
     */
    public void setCoords(double xmin, double ymin, double xmax, double ymax) {
        double dx = xmax - xmin;
        double dy = ymax - ymin;
        xScale = windowWidth / dx;
        yScale = windowHeight / dy;
        xOffset = xmin;
        yOffset = ymin;
    }
}
