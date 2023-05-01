//
// TestZelle - Zelle's original sample program.
//

import Graphics.*;

public class TestZelle {
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("My Circle", 100, 100);
        Circle c = new Circle(new Point(50, 50), 10);
        c.draw(win);
        win.getMouse(); // Pause to view result
        win.close();    // Close window when done
    }
}
