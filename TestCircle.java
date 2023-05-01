import Graphics.Circle;
import Graphics.ColorRGB;
import Graphics.GraphWin;
import Graphics.Point;

public class TestCircle {
    
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("Test Point", 600, 600);
        win.setBackground(new ColorRGB("green"));

        win.setCoords(-0.1, -0.1, 1.1, 1.1);
        Circle lastCircle = null;

        while (!win.isClosed()) {
            Point pt = win.checkMouse();
            if (pt != null) {
                if (lastCircle != null)
                    lastCircle.undraw();
                lastCircle = new Circle(pt, 0.1);
                lastCircle.setOutline("black");
                lastCircle.setFill("hotpink");
                lastCircle.draw(win);
            }

            String s = win.checkKey();
            if (s != null && lastCircle != null) {
                switch (s) {
                    case "uparrow":
                        lastCircle.move(0, 0.1);
                        break;
                    case "downarrow":
                        lastCircle.move(0, -0.1);
                        break;
                    case "leftarrow":
                        lastCircle.move(-0.1, 0);
                        break;
                    case "rightarrow":
                        lastCircle.move(0.1, 0);
                        break;
                }
            }
        }
    }
}
