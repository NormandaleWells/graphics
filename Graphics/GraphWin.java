package Graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphWin
    implements MouseListener, KeyListener, WindowListener {
    
    private JFrame frame;
    private GraphicsPanel panel;
    private boolean closed;

    private int windowWidth;
    private int windowHeight;

    private double xOffset;
    private double yOffset;
    private double xScale;
    private double yScale;

    private int lastX = 0;
    private int lastY = 0;
    private boolean hasPoint = false;

    // Invariant:
    //      if hasChar is true, exactly one of lastChar or lastCode is non-zero.
    //
    // a non-zero lastChar indicates that a normal key was pressed
    // a non-zero lastCode indicates that a special key (e.g. an arrow key)
    //      was pressed
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

    // This class intentionally has package visibiilty.
    class GraphicsPanel extends JPanel {

        public GraphicsPanel() {
            super(false);
        }

        public void paintComponent(Graphics g) {
            // Redraw display list
        }
    }

    // This method intentionally has package visibility.
    GraphicsPanel getGraphicsPanel() {
        return panel;
    }

    // Exactly one of `code` or `c` must be non-zero.
    // If `code` is non-zero, that code must have an
    // entry i `specialKeys`.  If either of these
    // conditions is not true, this will return null.
    private String keyCodeToString(int code, char c) {
        if (c != 0)
            return ((Character)c).toString();
        if (code != 0)
            return specialKeys.get(code);
        return null;
    }

    public GraphWin(String title, int width, int height) {

        windowWidth = width;
        windowHeight = height;

        frame = new JFrame();
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.getContentPane().setFocusable(true);
        frame.getContentPane().addMouseListener(this);
        frame.getContentPane().addKeyListener(this);
        frame.addWindowListener(this);

        panel = new GraphicsPanel(); // set to true later for double buffering
        frame.getContentPane().add(panel);
        panel.setPreferredSize(new Dimension(width, height));
        frame.pack();

        frame.setVisible(true);
        closed = false;

        // The default coordinate system as (0,0) at the
        // top left corner.
        setCoords(0, height, width, 0);
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

    public Point screenXYtoPoint(int x, int y) {
        y = (windowHeight-1) - y;
        double userX = x / xScale + xOffset;
        double userY = y / yScale + yOffset;
        return new Point(userX, userY);
    }

    public java.awt.Point pointXYtoScreen(Point pt) {
        int screenX = (int)((pt.getX() - xOffset) * xScale);
        int screenY = (int)((pt.getY() - yOffset) * yScale);
        screenY = (windowHeight-1) - screenY;
        return new java.awt.Point(screenX, screenY);
    }

    public void setBackground(ColorRGB color) {
        panel.setBackground(color.getColor());
    }

    public void setBackground(String color) {
        setBackground(new ColorRGB(color));
    }

    public void close() {
        // See https://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        closed = true;
    }

    public boolean isOpen() {
        return !closed;
    }

    public boolean isClosed() {
        return closed;
    }

    // TODO: See if there's a better way of doing this other than sleeping.
    public Point getMouse() throws InterruptedException {
        while (!hasPoint) {
            Thread.sleep(1);
        }

        hasPoint = false;
        return screenXYtoPoint(lastX, lastY);
    }

    public Point checkMouse() throws InterruptedException {
        // Minor kludge: since we're running this all in a
        // single thread, we need to provide some time for
        // the event handling to kick in.
        Thread.sleep(0);
        if (!hasPoint) {
            return null;
        } else {
            hasPoint = false;
            return screenXYtoPoint(lastX, lastY);
        }
    }

    public String getKey() throws InterruptedException {
        while (!hasChar) {
            Thread.sleep(1);
        }

        hasChar = false;
        return keyCodeToString(lastCode, lastChar);
    }

    public String checkKey() throws InterruptedException {
        // Minor kludge: since we're running this all in a
        // single thread, we need to provide some time for
        // the event handling to kick in.
        Thread.sleep(0);
        if (!hasChar) {
            return null;
        } else {
            hasChar = false;
            return keyCodeToString(lastCode, lastChar);
        }
    }

    public void setCoords(double xmin, double ymin, double xmax, double ymax) {
        double dx = xmax - xmin;
        double dy = ymax - ymin;
        xScale = windowWidth / dx;
        yScale = windowHeight / dy;
        xOffset = xmin;
        yOffset = ymin;
    }
}
