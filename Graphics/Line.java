package Graphics;

import java.awt.Graphics;

public class Line extends GraphicsObject {

    private Point pt1;
    private Point pt2;
    private ColorRGB color;

    public Line(Point p1, Point p2) {
        pt1 = new Point(p1);
        pt2 = new Point(p2);
        color = new ColorRGB(0, 0, 0);
    }

    public Line(Line other) {
        this.pt1 = new Point(other.pt2);
        this.pt2 = new Point(other.pt2);
        this.color = other.color;
    }

    public void setOutline(ColorRGB color) {
        this.color = color;
    }

    public void setArrow() {
        // TODO: figure out how to support this
    }

    public Point getCenter() {
        double x = (pt1.getX() + pt2.getX()) / 2.0;
        double y = (pt1.getY() + pt2.getY()) / 2.0;
        return new Point(x, y);
    }

    public Point getP1() {
        return new Point(pt1);
    }

    public Point getP2() {
        return new Point(pt2);
    }

    @Override
    public void doDraw(Graphics g) {
        java.awt.Point p1 = win.pointXYtoScreen(pt1);
        java.awt.Point p2 = win.pointXYtoScreen(pt2);
        g.setColor(color.getColor());
        g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
    }

    @Override
    public void doMove(double dx, double dy) {
        pt1.doMove(dx, dy);
        pt2.doMove(dx, dy);
    }
    
    @Override
    public Object clone() {
        return new Line(this);
    }
}
