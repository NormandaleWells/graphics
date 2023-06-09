// TestPoint - first real graphics test
//
// This program places a point at each mouse click,
// erasing the previous one.  It also uses the arrow
// keys to move the point around.

import Graphics.ColorRGB;
import Graphics.GraphWin;
import Graphics.Point;

public class TestPoint {
    
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("Test Point", 600, 600);
        win.setBackground(new ColorRGB("gray"));

        win.setCoords(0.1, 0.1, 1.2, 1.2);
        Point lastPt = null;

        while (!win.isClosed()) {
            Point pt = win.checkMouse();
            if (pt != null) {
                if (lastPt != null)
                    lastPt.undraw();
                lastPt = pt;
                lastPt.setOutline("red");
                lastPt.draw(win);
            }

            String s = win.checkKey();
            if (s != null && lastPt != null) {
                switch (s) {
                    case "uparrow":
                        lastPt.move(0, 0.1);
                        break;
                    case "downarrow":
                        lastPt.move(0, -0.1);
                        break;
                    case "leftarrow":
                        lastPt.move(-0.1, 0);
                        break;
                    case "rightarrow":
                        lastPt.move(0.1, 0);
                        break;
                }
            }
        }
    }
}
