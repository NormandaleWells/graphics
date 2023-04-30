import Graphics.ColorRGB;
import Graphics.GraphWin;
import Graphics.Point;

public class TestGraphcs1 {
    
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("My Window", 600, 600);
        win.setBackground(new ColorRGB("gray"));

        while (!win.isClosed()) {
            Point pt = win.checkMouse();
            if (pt != null) {
                pt.draw(win);
            }
        }
    }
}
