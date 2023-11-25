package Graphics;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Polygon is a drawable polygon object.
 */
public class Polygon extends GraphicsObject{

    private Point[] pts;
    private ColorRGB color = new ColorRGB(0, 0, 0);
    private ColorRGB fillColor = null;

     /**
      * Initializes a new <code>Polygon</code> object given an
      * array of points.  The points may be in an array, or may
      * be simply listed as separate arguments.
      * <p>
      * The <code>Point</code> objects in the array are not stored directly;
      * they are copied instead.  The original objects may be
      * changed without affecting the <code>Polygon</code>.
      * 
      * @param pts  - array of <code>Point</code>s defining the polygon
      */
     public Polygon(Point ... pts) {
        this.pts = new Point[pts.length];
        for (int i = 0; i < pts.length; i++) {
            this.pts[i] = new Point(pts[i]);
        }
     }

     /**
      * Initializes a new <code>Polygon</code> object given an
      * ArrayList of points.
      * <p>
      * The <code>Point</code> objects in the ArrayList are not stored directly;
      * they are copied instead.  The original objects may be
      * changed without affecting the <code>Polygon</code>.
      * 
      * @param pts  - <code>ArrayList</code> of <code>Point</code>s defining the polygon
      */
     public Polygon(ArrayList<Point> pts) {
        this.pts = new Point[pts.size()];
        for (int i = 0; i < pts.size(); i++) {
            this.pts[i] = new Point(pts.get(i));
        }
     }

    /**
     * Creates a copy of the given <code>Polygon</code>.
     * <p>
     * The new <code>Polygon</code> object does not get the drawn
     * status of the original oval.
     * 
     * @param other     The Polygon object to copy
     */
    public Polygon(Polygon other) {
        this.pts = new Point[other.pts.length];
        for (int i = 0; i < pts.length; i++) {
            this.pts[i] = new Point(other.pts[i]);
        }
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
     * Return clones of the points that define this polygon.
     * 
     * @return  The points defining the Polygon.
     */
    public Point[] getPoints() {
        Point[] pts = new Point[this.pts.length];
        for (int i = 0; i < this.pts.length; i++) {
            pts[i] = new Point(this.pts[i]);
        }
        return pts;
    }

    @Override
    protected void doDraw(Graphics g) {
        int[] xCoords = new int[this.pts.length];
        int[] yCoords = new int[this.pts.length];
        for (int i = 0; i < pts.length; i++) {
            java.awt.Point ipt = win.pointXYtoScreen(this.pts[i]);
            xCoords[i] = ipt.x;
            yCoords[i] = ipt.y;
        }

        // Draw the interior, if necessary.
        if (this.fillColor != null) {
            g.setColor(fillColor.getColor());
            g.fillPolygon(xCoords, yCoords, this.pts.length);
        }

        // Draw the outline.
        g.setColor(color.getColor());
        g.drawPolygon(xCoords, yCoords, this.pts.length);
    }

    @Override
    protected void doMove(double dx, double dy) {
        for (int i = 0; i < this.pts.length; i++) {
            pts[i].doMove(dx, dy);
        }
    }
    
    @Override
    public Object clone() {
        return new Polygon(this);
    }
}
