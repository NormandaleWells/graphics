# graphics
Java version of John Zelle's graphics.py.

# Background
For his textbook "Python Programming: An Introduction to Computer Science"
(Franklin, Beedle & Associations), ISBN 978-1590282755)
John Zelle created a simple graphics library.
Since Java is used by so many CS2 courses,
I thought by now someone would have translated this library
into Java, but a quick web search turned up nothing.
So I figured I'd try my hand at it.

I'm trying to retain the same behavior as Zelle's library.
If the behavior of this libary differs from that of Zelle's,
let me know and I'll fix it.

# License
[Dr. Zelle's Python version](http://mcsp.wartburg.edu/zelle/python)
is licensed under the terms of the GPL,
but I prefer the MIT license for my projects.
It is not clear to me from reading the GPL whether a derivative
work like this is also required to be GPL-licensed.
If that is the case, let me know, and I'll change it.

# Platforms
This is written in Java using Swing and AWT,
so it should run on any machine with a JVM.
I'm using Java 17 for development,
but I'm not knowingly using any features of Java
that are new in Java 17.

This library is for educational purposes only;
it is **not** intended for production use!

# Implementation Notes
Since this is intended for use with novice programmers,
I'm keeping it single-threaded.
This means there is some potentially nasty code
in the `checkMouse()`, `getMouse()`, `checkKey()`, and `getKey()`
functions that would be made less awkward - and
more acceptable to "real" Java programmers - by using
threads.

For colors, I'm using my own ColorRGB class which wraps
the java.awt.Color class, but also has a constructor
for defining a color by name
using the [X11 color names](https://en.wikipedia.org/wiki/X11_color_names).

# Possible additions
It may be useful to have a `moveTo()` function that moves an
object to an absolute location.

It may be useful to have a `Vector` class representing the
difference between two points, and have a version of `move()`
that takes a vector.  There would also be functions that add
and subtract `Vector`s.  This would **not** be drawable, and
therefore would not derive from GraphicsObject.

# Known issues
If the mouse is moved while the button is pressed,
`getMouse()` and `checkMouse()` return nothing.
I'll fix this after I have a chance to see what
Zelle's library.

Many, many more special keys need to be added
to the `specialKeys` list in `GraphWin`.
I need to research the full list supported by
Zelle's library; I suspect this is built into
TKinter.

GraphWin.setBackground() has stopped working.

Still unimplemented:
- `update()`
- `setWidth` (for any object type)
- `Line.setArrow()`
- `Circle`
- `Rectangle`
- `Oval`
- `Polygon`
- `Text`
- `Images`

To be checked:
- What do Circle and Oval do if the window mapping is anisotropic?
- What does Zelle's code do if an object is drawn into another window?
- Should we call undraw() for all display list objects when the window closes?
- Does Point support setFill()?
- What does setWidth() do with non-lines?  Does it just affect the outline?
- Should we use mouseClicked or mouseReleased()?  We currently get no input
  if the mouse is moved while the button is pressed.
- Can the fill color be removed by calling setFill(null)?
