import Graphics.Circle;
import Graphics.ColorRGB;
import Graphics.GraphWin;
import Graphics.Point;

public class TestZOrder {
    
    public static void main(String[] args) throws InterruptedException {
        GraphWin win = new GraphWin("Test Point", 600, 600);
        win.setBackground(new ColorRGB("gray"));

        win.setCoords(-0.1, -0.1, 1.1, 1.1);

        Circle circle1 = new Circle(new Point(0.5, 0.5), 0.2);
        Circle circle2 = new Circle(new Point(0.25, 0.25), 0.2);
        Circle circle3 = new Circle(new Point(0.75, 0.25), 0.2);
        Circle circle4 = new Circle(new Point(0.75, 0.75), 0.2);
        Circle circle5 = new Circle(new Point(0.25, 0.75), 0.2);

        circle1.setFill("red");
        circle2.setFill("green");
        circle3.setFill("green");
        circle4.setFill("green");
        circle5.setFill("green");

        circle1.draw(win);
        circle2.draw(win);
        circle3.draw(win);
        circle4.draw(win);
        circle5.draw(win);

        while (!win.isClosed()) {

            String s = win.checkKey();
            if (s != null && circle1 != null) {
                switch (s) {
                    case "uparrow":
                        circle1.move(0, 0.1);
                        break;
                    case "downarrow":
                        circle1.move(0, -0.1);
                        break;
                    case "leftarrow":
                        circle1.move(-0.1, 0);
                        break;
                    case "rightarrow":
                        circle1.move(0.1, 0);
                        break;
                }
            }
        }
    }
}
