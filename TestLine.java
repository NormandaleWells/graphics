// TestLine - test the Line class
//
// This program draws a line using every pair
// of points clicked, getting rid of the old
// line every time a new one is created.  The
// arrow keys may be used to move the line
// around.

import Graphics.ColorRGB;
import Graphics.GraphWin;
import Graphics.Point;
import Graphics.Line;

public class TestLine {
    
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("My Window", 600, 600);
        win.setBackground(new ColorRGB("gray"));

        win.setCoords(0.1, 0.1, 1.2, 1.2);
        Point lastPt = null;
        Line lastLine = null;

        while (!win.isClosed()) {
            Point pt = win.checkMouse();
            if (pt != null) {
                if (lastPt == null) {
                    lastPt = pt;
                } else {
                    if (lastLine != null)
                        lastLine.undraw();
                    lastLine = new Line(lastPt, pt);
                    lastPt = null;
                    lastLine.setOutline("maroon");
                    lastLine.draw(win);
                }
            }

            String s = win.checkKey();
            if (s != null && lastLine != null) {
                switch (s) {
                    case "uparrow":
                        lastLine.move(0, 0.1);
                        break;
                    case "downarrow":
                        lastLine.move(0, -0.1);
                        break;
                    case "leftarrow":
                        lastLine.move(-0.1, 0);
                        break;
                    case "rightarrow":
                        lastLine.move(0.1, 0);
                        break;
                }
            }
        }
    }
}
