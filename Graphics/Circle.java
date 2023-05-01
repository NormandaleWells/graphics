package Graphics;

import java.awt.Graphics;
/**
 * Circle is a drawable circle object.
 */
public class Circle extends GraphicsObject {

    private Point center;
    double radius;
    private ColorRGB color;
    private ColorRGB fillColor;

    /**
     * Initializes a new <code>Circle</code> object given two points.
     * <p>
     * The Point object is not stored directly; it is
     * copied instead.  The original object may be changed
     * without affecting the <code>Circle</code>.
     * 
     * @param center    The center of the circle
     * @param radius    The radius of the circle
     */
    public Circle(Point center, double radius) {
        this.center = new Point(center);
        this.radius = radius;
        this.color = new ColorRGB(0, 0, 0);
        this.fillColor = null;
    }

    /**
     * Creates a copy of the given <code>Circle</code>.
     * <p>
     * The new <code>Circle</code> object does not get the drawn
     * status of the original circle.
     * 
     * @param other     The Circle object to copy
     */
    public Circle(Circle other) {
        this.center = new Point(other.center);
        this.radius = other.radius;
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
     * Returns the center of the circle.
     * 
     * @return      A <code>Point</code> object representing
     *              the center of the circle.
     */
    public Point getCenter() {
        return new Point(center);
    }

    /**
     * Returns the lower-left corner of the circle's
     * bounding box.
     * 
     * @return      The lower-left corner of the circle's
     *              bounding box
     */
    public Point getP1() {
        double x = center.getX() - radius;
        double y = center.getY() - radius;
        return new Point(x, y);
    }

    /**
     * Returns the upper-right corner of the circle's
     * bounding box.
     * 
     * @return      The upper-right corner of the circle's
     *              bounding box
     */
    public Point getP2() {
        double x = center.getX() + radius;
        double y = center.getY() + radius;
        return new Point(x, y);
    }

    @Override
    protected void doDraw(Graphics g) {
        java.awt.Point center = win.pointXYtoScreen(this.center);
        double halfWidth = win.xDistToScreen(radius);
        double halfHeight = win.yDistToScreen(radius);
        double x = center.getX() - halfWidth;
        double y = center.getY() - halfHeight;

        // Draw the interior, if necessary.
        if (this.fillColor != null) {
            g.setColor(fillColor.getColor());
            g.fillOval((int)x, (int)y, (int)(halfWidth*2), (int)(halfHeight*2));
        }

        // Draw the outline.
        g.setColor(color.getColor());
        g.drawOval((int)x, (int)y, (int)(halfWidth*2), (int)(halfHeight*2));
    }

    @Override
    protected void doMove(double dx, double dy) {
        center.doMove(dx, dy);
    }
    
    @Override
    public Object clone() {
        return new Circle(this);
    }
}
