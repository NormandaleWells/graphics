import Graphics.ColorRGB;
import Graphics.GraphWin;
import Graphics.Point;

public class TestGraphcs1 {
    
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("My Window", 600, 600);
        win.setBackground(new ColorRGB("hotpink"));

        while (!win.isClosed()) {
            Point pt = win.checkMouse();
            if (pt != null) {
                System.out.printf("Mouse: %.2f %.2f\n", pt.getX(), pt.getY());
            }

            String s = win.checkKey();
            if (s != null) {
                System.out.printf("Key: %s\n", s);
                if (s.equals("x"))
                    win.close();
            }

            // Thread.sleep(100);
        }

        System.out.println("Closed now.");
    }
}
