package org.ogl;// Trame du TP de synthese d'images : primitives OpenGL
// BUT Info 3 - 2025/2026
// Source: Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.jogamp.opengl.awt.GLCanvas;


/** Event handler for OpenGL exercices */
public class ExoController implements KeyListener
{
  /** Controlled display area. */
  private GLCanvas canvas;
  /** Controlled OpenGL context. */
  private ExoView myView;

  /** Constructs a event handler.
    * @param canvas display area.
    * @param myView OpenGL context.
    */
  public ExoController (GLCanvas canvas, ExoView myView)
  {
    this.myView = myView;
    this.canvas = canvas;
  }

  /** Invoked when a key has been pressed.
    * Implementation from KeyListener.
    * @param e detected key event.
    */
  public void keyPressed (KeyEvent e)
  {
    processKeyEvent (e, true);
  }

  /** Invoked when a key has been released.
    * Implementation from KeyListener.
    * @param e detected key event.
    */
  public void keyReleased (KeyEvent e)
  {
    processKeyEvent (e, false);
  }

  /** Invoked when a key has been pressed or released.
    * Local implementation from KeyListener.
    * @param e detected key event.
    * @param pressed pressed or released key status.
    */
  private void processKeyEvent (KeyEvent e, boolean pressed)
  {
    switch (e.getKeyCode ())
    {
      case KeyEvent.VK_Q :
      case KeyEvent.VK_ESCAPE :
        if (! pressed) System.exit (0);
        break;
    }
  }

  /** Invoked when a key has been typed.
    * Implementation from KeyListener.
    * @param e detected key event.
    */
  public void keyTyped (KeyEvent e)
  {
    char c = e.getKeyChar ();
    if (c == 'r') myView.rotate ();
    else if (c == 'R') myView.rotateBack ();
    else if (c == 'b') myView.toggleOptimize ();
    else if (c == 'f') myView.background ();
    else if (c == 's') myView.switchResol ();
    else if (c == 'q') System.exit (0);
    canvas.display ();
  }
}
