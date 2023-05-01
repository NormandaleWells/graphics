package Graphics;

import java.awt.Graphics;
/**
 * Line is a drawable line object.
 */
public class Line extends GraphicsObject {

    private Point pt1;
    private Point pt2;
    private ColorRGB color;

    /**
     * Initializes a new Line object given two points.
     * <p>
     * The Point objects are not stored directly; they are
     * copied instead.  The original objects may be changed
     * without affecting the <code>Line</code>.
     * 
     * @param p1        The first endpoint
     * @param p2        The other endpoint
     */
    public Line(Point p1, Point p2) {
        pt1 = new Point(p1);
        pt2 = new Point(p2);
        color = new ColorRGB(0, 0, 0);
    }

    /**
     * Creates a copy of the given <code>Line</code>.
     * <p>
     * The new <code>Line</code> object does not get the drawn
     * status of the original line.
     * 
     * @param other     The Line object to copy
     */
    public Line(Line other) {
        this.pt1 = new Point(other.pt2);
        this.pt2 = new Point(other.pt2);
        this.color = other.color;
    }

    @Override
    public void setOutline(ColorRGB color) {
        this.color = color;
    }

    /**
     * Determines which ends of the line have arrows.
     * <p>
     * This is currently unsupported.
     * <p>
     * This is NOT inherited from <code>GraphicsObject</code>;
     * it is specific to <code>Line</code> objects.
     * 
     * @param arrowType     Describes which end(s) have arrows.
     */
    public void setArrow(String arrowType) {
    }

    /**
     * Returns a point representing the midpoint of the
     * <code>line</code>.
     * 
     * @return      A <code>Point</code> object representing
     *              the midpoint of the line.
     */
    public Point getCenter() {
        double x = (pt1.getX() + pt2.getX()) / 2.0;
        double y = (pt1.getY() + pt2.getY()) / 2.0;
        return new Point(x, y);
    }

    /**
     * Returns the first endpoint of the line.
     * 
     * @return      The first endpoint of the line.
     */
    public Point getP1() {
        return new Point(pt1);
    }

    /**
     * Returns the second endpoint of the line.
     * 
     * @return      The second endpoint of the line.
     */
    public Point getP2() {
        return new Point(pt2);
    }

    @Override
    protected void doDraw(Graphics g) {
        java.awt.Point p1 = win.pointXYtoScreen(pt1);
        java.awt.Point p2 = win.pointXYtoScreen(pt2);
        g.setColor(color.getColor());
        g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
    }

    @Override
    protected void doMove(double dx, double dy) {
        pt1.doMove(dx, dy);
        pt2.doMove(dx, dy);
    }
    
    @Override
    public Object clone() {
        return new Line(this);
    }
}
