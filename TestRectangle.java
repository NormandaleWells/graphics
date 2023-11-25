import Graphics.Rectangle;
import Graphics.ColorRGB;
import Graphics.GraphWin;
import Graphics.Point;

public class TestRectangle {
    
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("Test Rectangle", 600, 600);
        win.setBackground(new ColorRGB("light blue"));

        win.setCoords(-0.1, -0.1, 1.1, 1.1);
        Rectangle lastRectangle = null;
        Point lastPt1 = null;
        Point lastPt2 = null;

        while (!win.isClosed()) {
            Point pt = win.checkMouse();
            if (pt == null) {
                ;
            } else if (lastPt1 == null) {
                lastPt1 = pt;
            } else {
                lastPt2 = pt;
                if (lastRectangle != null)
                    lastRectangle.undraw();
                lastRectangle = new Rectangle(lastPt1, lastPt2);
                lastRectangle.setOutline("black");
                lastRectangle.setFill("dark green");
                lastRectangle.draw(win);
                lastPt1 = null;
            }

            String s = win.checkKey();
            if (s != null && lastRectangle != null) {
                switch (s) {
                    case "uparrow":
                        lastRectangle.move(0, 0.1);
                        break;
                    case "downarrow":
                        lastRectangle.move(0, -0.1);
                        break;
                    case "leftarrow":
                        lastRectangle.move(-0.1, 0);
                        break;
                    case "rightarrow":
                        lastRectangle.move(0.1, 0);
                        break;
                }
            }
        }
    }
}
