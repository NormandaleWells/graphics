package Graphics;

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
    public void doDraw() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doDraw'");
    }

    @Override
    public void doMove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doMove'");
    }

    @Override
    public Object clone() {
        return new Point(this);
    }
}
