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
s licensed under the terms of the GPL,
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

# Implementation Notes
Since this is intended for use with novice programmers,
I kept it single-threaded.
This means there is some potentially nasty code
in the `checkMouse()`, `getMouse()`, `checkKey()`, and `getKey()`
functions that would be made less awkward - and
more acceptable to "real" Java programmers - by using
threads.

For colors, I'm using my own ColorRGB class which wraps
the java.awt.Color class, but also has a constructor
for defining a color by name,
using the [X11 color names](https://en.wikipedia.org/wiki/X11_color_names).

# Known issues
If the mouse is moved while the button is pressed,
`getMouse()` and `checkMouse()` return nothing.
I'll fix this after I have a chance to see what
Zelle's library.
