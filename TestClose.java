import Graphics.GraphWin;

// Test the win.close() function.
// Just display a window and wait for an "x" to be entered.

public class TestClose {
    
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("Test Close", 600, 600);

        while (!win.isClosed()) {
            String s = win.checkKey();
            if ((s != null) && (s.equals("x"))) {
                win.close();
            }

            // Thread.sleep(100);
        }

        System.out.println("Closed now.");
    }
}
