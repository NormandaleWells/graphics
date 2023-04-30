/**
 * Graphics is a Java port of John Zelle's graphics.py module.
 * 
 * John Zelle created the graphics.py module for his textbook
 * "Python Programming: An Introduction to Computer Science"
 * (Franklin, Beedle and Associations), ISBN 978-1590282755).
 * It appears that no one has created a Java version, so I
 * thought it sounded like a fun project to tackle.
 * <p>
 * You can find Zelle's original documentation here:
 * <a href="https://mcsp.wartburg.edu/zelle/python/graphics/graphics/graphref.html">graphics.py</a>
 * <p>
 * Do not use this library as a model for using Java's Swing
 * library.  For simplicity, I'm keeping everything single-
 * threaded, and use <code>Thread.sleep()</code> whenever I
 * need to allow the user interface to check for mouse clicks
 * and key presses.
 * <p>
 * This turns the more modern (since 1983) concept of a user
 * interface on its head.  Instead of using an event loop and
 * reacting to mouse and keyboard events, we use here a more
 * old-fashioned "go get input when you need it" model.  While
 * this is very useful for the sorts of things Zelle (and I)
 * want to use this for - simple student-written projects like
 * games and simulations - it should not (indeed, cannot) be
 * used for anything that looks like a modern user interface.
 */
package Graphics;
