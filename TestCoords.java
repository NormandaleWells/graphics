import Graphics.GraphWin;
import Graphics.Point;

public class TestCoords {
    
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("My Window", 600, 600);

        win.setCoords(-0.1, -0.1, 1.1, 1.1);
        while (!win.isClosed()) {
            Point pt = win.checkMouse();
            if (pt != null) {
                System.out.printf("Mouse: %.2f %.2f\n", pt.getX(), pt.getY());
                java.awt.Point screen = win.pointXYtoScreen(pt);
                System.out.printf("Screen: %d %d\n", (int)screen.getX(), (int)screen.getY());
            }
        }
    }
}
