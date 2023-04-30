import Graphics.GraphWin;
import Graphics.Point;

// Test the checkMouse() and checkKey() methods.

public class TestInput {
    
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("Test Input", 600, 600);

        while (!win.isClosed()) {
            Point pt = win.checkMouse();
            if (pt != null) {
                System.out.printf("Mouse: %.2f %.2f\n", pt.getX(), pt.getY());
            }

            String s = win.checkKey();
            if (s != null) {
                System.out.printf("Key: %s\n", s);
            }
        }

        System.out.println("Closed now.");
    }
}
