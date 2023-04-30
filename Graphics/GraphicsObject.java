///////////////////////////////////////////////////////////
//
// GraphicsObject - base class for graphics objects
//
// Every derived class must override doDraw() and doMove().
//
// `clone()` requires a cast to the proper type, which is
// rather annoying.  Therefore, every derived class should
// have a copy constructor.  However, `clone()` is still
// useful in cases where one may have a collection of
// `GraphicsObject`s that need to be cloned.
//

package Graphics;

import java.awt.Graphics;

public abstract class GraphicsObject implements Cloneable {

    // `win` is non-null iff this object is currently drawn
    // in a window.
    protected GraphWin win = null;

    // Derived classes should override this as needed.
    public void setFill(ColorRGB color) {
    }

    public final void setFill(String color) {
        setFill(new ColorRGB(color));
    }

    // Derived classes should override this as needed.
    public void setOutline(ColorRGB color) {
    }

    public final void setOutline(String color) {
        setFill(new ColorRGB(color));
    }

    // Derived classes should override this as needed.
    public void setWidth(int pixels) {
    }

    // Calling `draw()` or `move()` requires some extra
    // work that shouldn't be done in each derived class.
    // Instead, we'll mark those fuctions as final here,
    // and call the abstract `doDraw()` and `doMove()`
    // as necessary.
    // `doDraw()` is also used when traversing the display
    // list to redraw the screen.
    public abstract void doDraw(Graphics g);
    public abstract void doMove();

    public final void draw(GraphWin win) {
        if (this.win != null) return; // Throw an exception?
        this.win = win;
        // Add to display
        doDraw(this.win.getGraphicsPanel().getGraphics());
    }

    public final void undraw() {
        if (win == null) return;
        // Remove this from the display list.
    }
}
