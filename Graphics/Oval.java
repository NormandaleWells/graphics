package Graphics;

import java.awt.Graphics;

/**
 * Oval is a drawable oval object.
 */
public class Oval extends GraphicsObject {
    
    private Point p1;
    private Point p2;
    private ColorRGB color;
    private ColorRGB fillColor;

    /**
     * Initializes a new <code>Oval</code> object given
     * two points.
     * <p>
     * The Point objects are not stored directly; they are
     * copied instead.  The original objects may be changed
     * without affecting the <code>Oval</code>.
     * 
     * @param p1    One point defining the oval
     * @param p2    The other point defining the oval
     */
    public Oval(Point p1, Point p2) {
        this.p1 = new Point(p1);
        this.p2 = new Point(p2);
        this.color = new ColorRGB(0, 0, 0);
        this.fillColor = null;
    }

    /**
     * Creates a copy of the given <code>Oval</code>.
     * <p>
     * The new <code>Oval</code> object does not get the drawn
     * status of the original oval.
     * 
     * @param other     The Oval object to copy
     */
    public Oval(Oval other) {
        this.p1 = new Point(other.p1);
        this.p2 = new Point(other.p2);
        this.color = other.color;
        this.fillColor = other.fillColor;
    }

    @Override
    public void setOutline(ColorRGB color) {
        this.color = color;
    }

    @Override
    public void setFill(ColorRGB color) {
        this.fillColor = color;
    }

    /**
     * Returns the center of the oval.
     * 
     * @return      A <code>Point</code> object representing
     *              the center of the circle.
     */
    public Point getCenter() {
        return Point.midPoint(p1, p2);
    }

    /**
     * Returns a clone of the first Point used to construct
     * the oval.
     * 
     * @return      A clone of p1 as given to the constructor.
     */
    public Point getP1() {
        return new Point(p1);
    }

    /**
     * Returns a clone of the second Point used to construct
     * the oval.
     * 
     * @return      A clone of p2 as given to the constructor.
     */
    public Point getP2() {
        return new Point(p2);
    }

    @Override
    protected void doDraw(Graphics g) {
        java.awt.Point pi1 = win.pointXYtoScreen(this.p1);
        java.awt.Point pi2 = win.pointXYtoScreen(this.p2);
        int width  = pi2.x - pi1.x;
        int height = pi2.y - pi1.y;

        // AWT requires that the width and height be
        // positive.  We could do that in the ctor, but
        // we need to keep the original p1 and p2 around
        // so we can return them via the getP1() and
        // getP2() methods.
        if (width < 0) {
            width = -width;
            int t = pi1.x;
            pi1.x = pi2.x;
            pi2.x = t;
        }

        if (height < 0) {
            height = -height;
            int t = pi1.y;
            pi1.y = pi2.y;
            pi2.y = t;
        }

        // Draw the interior, if necessary.
        if (this.fillColor != null) {
            g.setColor(fillColor.getColor());
            g.fillOval(pi1.x, pi1.y, width, height);
        }

        // Draw the outline.
        g.setColor(color.getColor());
        g.drawOval(pi1.x, pi1.y, width, height);
    }

    @Override
    protected void doMove(double dx, double dy) {
        p1.doMove(dx, dy);
        p2.doMove(dx, dy);
    }
    
    @Override
    public Object clone() {
        return new Oval(this);
    }
}
