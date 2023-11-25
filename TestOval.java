import Graphics.Oval;
import Graphics.ColorRGB;
import Graphics.GraphWin;
import Graphics.Point;

public class TestOval {
    
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("Test Oval", 600, 600);
        win.setBackground(new ColorRGB("light blue"));

        win.setCoords(-0.1, -0.1, 1.1, 1.1);
        Oval lastOval = null;
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
                if (lastOval != null)
                    lastOval.undraw();
                lastOval = new Oval(lastPt1, lastPt2);
                lastOval.setOutline("black");
                lastOval.setFill("dark green");
                lastOval.draw(win);
                lastPt1 = null;
            }

            String s = win.checkKey();
            if (s != null && lastOval != null) {
                switch (s) {
                    case "uparrow":
                        lastOval.move(0, 0.1);
                        break;
                    case "downarrow":
                        lastOval.move(0, -0.1);
                        break;
                    case "leftarrow":
                        lastOval.move(-0.1, 0);
                        break;
                    case "rightarrow":
                        lastOval.move(0.1, 0);
                        break;
                }
            }
        }
    }
}
