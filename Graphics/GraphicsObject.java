package Graphics;

import java.awt.Graphics;

/**
 * This is the base class for all graphics objects.
 * 
 * Implementation notes:
 * <p>
 * Zelle's library defines a <code>clone()</code> method for creating a copy
 * of an existing object.  Unfortunately, Java already defines a
 * <code>Object.clone()</code> method which does the same thing, but is not
 * type-safe; the result needs to be explicitly cast to the proper type.
 * I've chosen instead to just have every class define a copy constructor
 * that creates a copy of the object in an undrawn state.
 * <p>
 * Every derived class must, at a minimum, provide its own implementation
 * of the <code>doDraw()</code> and <code>doMove()</code> methods.  The
 * <code>doDraw()</code> is responsible for just drawing the object in its
 * current state.  It is called when the object is first created, and when
 * the entire display list is redrawn.
 * <p>
 * <code>doMove()</code> only needs to modify the coordinates of the object;
 * the redrawing is done by the base class.
 * <p>
 * Derived classes should also optionally override <code>setFill</code>,
 * <code>setOutline</code>, and <code>setWidth</code> if they can support
 * those operations.  The versions of <code>setFill</code> and
 * <code>setOutline</code> that take color names are handled in this
 * class, and result in a call to the corresponding function that takes
 * a <code>ColorRGB</code> object.
 * <p>
 * The <code>undraw()</code> function is handled entirely within the
 * <code>GraphicsObject</code> class.
 */
public abstract class GraphicsObject implements Cloneable {

    /**
     * The <code>GraphWin</code> this object is drawn on.
     * <p>
     * An object may only be drawn in one window at a time.  If
     * <code>win</code> is <code>null</code>, the object is not
     * currently drawn in any window.
     */
    protected GraphWin win = null;

    /**
     * Create a GraphicsObject.
     * <p>
     * This is protected because a GraphicsObject should never be
     * created.  Only derived classes are at all useful.
     */
    protected GraphicsObject() {
    }

    /**
     * Set the fill color for this object.
     * <p>
     * Derived classes override this if and only if they support filled
     * objects.
     * 
     * @param color     A <code>ColorRGB</code> object specifying the fill
     *                  color.
     */
    public void setFill(ColorRGB color) {
    }

    /**
     * Set the fill color for this object, using an X11 color name.
     * <p>
     * This translates the name to an <code>ColorRGB</code> and calls
     * the <code>setFill()</code> with that.
     * 
     * @param color     An X11 color name specifying the fill color.
     */
    public final void setFill(String color) {
        setFill(new ColorRGB(color));
    }

    /**
     * Set the outline color for this object.
     * <p>
     * Every derived class should probably override this.
     * 
     * @param color     A <code>ColorRGB</code> object specifying the
     *                  outline color.
     */
    public void setOutline(ColorRGB color) {
    }

    /**
     * Set the outline color for this object, using an X11 color name.
     * <p>
     * This translates the name to an <code>ColorRGB</code> and calls
     * the <code>setOutline()</code> with that.
     * 
     * @param color     An X11 color name specifying the outline color.
     */
    public final void setOutline(String color) {
        setOutline(new ColorRGB(color));
    }

    // Derived classes should override this as needed.
    /**
     * Set the line width to use when drawing this object.
     * <p>
     * Derived classes override this if and only if they support an outline
     * width.
     * 
     * @param pixels        The width, in pixels, of the outline.
     */
    public void setWidth(int pixels) {
    }

    /**
     * Draw the object.
     * <p>
     * This must be overridden by every derived class.  It is called
     * when the object is first drawn, and every time the display list
     * is redrawn.
     * 
     * @param g         The <code>graphics</code> object to draw
     *                  into.
     */
    public abstract void doDraw(Graphics g);

    /**
     * Draw the object.
     * <p>
     * The object is also added to a list of objects (the displayh list)
     * that are redrawn whenever the window is redrawn.
     * <p>
     * Derived classes cannot override this function.
     * @param win       The <code>GraphWin</code> object to draw this
     *                  object on.
     */
    public final void draw(GraphWin win) {
        if (this.win != null) return; // Throw an exception?
        this.win = win;
        GraphWin.GraphicsPanel panel = this.win.getGraphicsPanel();
        panel.addObject(this);
        doDraw(panel.getGraphics());
    }

    /**
     * Move the object.
     * <p>
     * Note that the move is specified as a delta, not an absolute
     * position.
     * <p>
     * This must be overridden by every derived class.  It is called
     * when <code>move()</code> is called for the object.  The derived
     * class should only modify the object position; the actual drawing
     * is done by the base class.
     * 
     * @param dx        The x distance to move.
     * @param dy        The y distance to move.
     */
    public abstract void doMove(double dx, double dy);

    /**
     * Move the object.
     * <p>
     * Note that the move is specified as a delta, not an absolute
     * position.  Calling this function will always force a redraw
     * of the entire window in order to maintain the z-ordering of
     * objects.
     * 
     * @param dx        The x distance to move.
     * @param dy        The y distance to move.
     */
    public final void move(double dx, double dy) {
        doMove(dx, dy);
        win.checkUpdate();
    }

    /**
     * Undraw the object.
     * <p>
     * Calling this function will always force a redraw of the
     * entire window in order to maintain the z-ordering of
     * objects.
     */
    public final void undraw() {
        if (win == null) return;
        win.getGraphicsPanel().removeObject(this);
        win.checkUpdate();
        win = null;
    }
}
