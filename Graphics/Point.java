package Graphics;

import java.awt.Graphics;

// This serves a dual purpose as both a geometric point
// and a displayable graphics point.

public class Point extends GraphicsObject {
    
    private double x;
    private double y;
    private ColorRGB color;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        color = new ColorRGB(0, 0, 0);
    }

    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
        this.color = other.color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public void doDraw(Graphics g) {
        java.awt.Point pt = win.pointXYtoScreen(this);
        g.setColor(color.getColor());
        g.drawRect((int)pt.x, (int)pt.y, 1, 1);
    }

    @Override
    public void doMove(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public Object clone() {
        return new Point(this);
    }
}
