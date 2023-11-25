package Graphics;

import java.awt.Graphics;

// This serves a dual purpose as both a geometric point
// and a displayable graphics point.

/**
 * <code>Point</code> is a drawable point object.
 * <p>
 * This class serves two purposes; it is both a drawable
 * object, and is also used extensively throughout the
 * <code>Graphics</code> package to represent points.
 */
public class Point extends GraphicsObject {
    
    private double x;
    private double y;
    private ColorRGB color;

    /**
     * Initializes a new <code>Point</code> object.
     * 
     * @param x     The x coordinate of the point.
     * @param y     The y coordinate of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        color = new ColorRGB(0, 0, 0);
    }

    /**
     * Creates a copy of the given <code>Point</code>.
     * 
     * @param other     The <code>Point</code> to copy.
     */
    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
        this.color = other.color;
    }

    @Override
    public void setOutline(ColorRGB color) {
        this.color = color;
    }

    /**
     * Returns the x coordinate of the point.
     * 
     * @return      The x coordinate of the point.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the point.
     * 
     * @return      The y coordinate of the point.
     */
    public double getY() {
        return y;
    }

    @Override
    protected void doDraw(Graphics g) {
        java.awt.Point pt = win.pointXYtoScreen(this);
        g.setColor(color.getColor());
        g.drawRect((int)pt.x, (int)pt.y, 1, 1);
    }

    @Override
    protected void doMove(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public Object clone() {
        return new Point(this);
    }

    /**
     * Computes the midpoint of two given points.
     * 
     * @param p1    - the first point
     * @param p2    - the second point
     * @return      - the midpoint of the two points
     */
    public static Point midPoint(Point p1, Point p2) {
        double x = (p1.x + p2.x) / 2.0;
        double y = (p1.y + p2.y) / 2.0;
        return new Point(x, y);
    }
}
