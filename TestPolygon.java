import java.util.ArrayList;

import Graphics.ColorRGB;
import Graphics.GraphWin;
import Graphics.Point;
import Graphics.Polygon;

public class TestPolygon {
    
    public static void main(String[] args) throws InterruptedException {

        GraphWin win = new GraphWin("Test Polygon", 600, 600);
        win.setBackground(new ColorRGB("light blue"));

        win.setCoords(-0.1, -0.1, 1.1, 1.1);
        Polygon lastPolygon = null;

        Point p00 = new Point(0, 0);
        Point p10 = new Point(1, 0);
        Point p01 = new Point(0, 1);
        Point p11 = new Point(1, 1);

        Polygon p1 = new Polygon(p00, p10, p11, p01);
        p1.setOutline("black");
        p1.setFill("orchid");
        p1.draw(win);

        ArrayList<Point> pts = new ArrayList<Point>();
        while (!win.isClosed()) {
            Point pt = win.checkMouse();
            if (pt != null) {
                if (lastPolygon != null)
                    lastPolygon.undraw();
                pts.add(pt);
                lastPolygon = new Polygon(pts);
                lastPolygon.setOutline("black");
                lastPolygon.setFill("dark green");
                lastPolygon.draw(win);
            }

            String s = win.checkKey();
            if (s != null && lastPolygon != null) {
                switch (s) {
                    case "uparrow":
                        lastPolygon.move(0, 0.1);
                        break;
                    case "downarrow":
                        lastPolygon.move(0, -0.1);
                        break;
                    case "leftarrow":
                        lastPolygon.move(-0.1, 0);
                        break;
                    case "rightarrow":
                        lastPolygon.move(0.1, 0);
                        break;
                }
            }
        }
    }
}
