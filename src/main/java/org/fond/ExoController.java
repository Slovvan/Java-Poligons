package org.fond;// Trame du TP de synthese d'images : reperes locaux et tache de fond
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



/** Event handler for OpenGL exercices */
public class ExoController
           implements KeyListener//, MouseListener, MouseMotionListener
{
  /** Controlled display area. */
  private GLCanvas canvas;
  /** Controlled OpenGL context. */
  private ExoView myView;
  /** Tache de fond. */
  private FPSAnimator animator = null;

  /** Constructs a event handler.
    * @param canvas display area.
    * @param myView OpenGL context.
    */
  public ExoController (GLCanvas canvas, ExoView myView)
  {
    this.myView = myView;
    this.canvas = canvas;
    animator = new FPSAnimator (canvas, myView.backgroundTaskFrequency (), true);
//Deprec     animator.setRunAsFastAsPossible (false);
    animator.start ();
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

      case KeyEvent.VK_HOME :
        if (! pressed) myView.observer().resetProjection ();
        break;

      case KeyEvent.VK_UP :
        if (pressed)
          if (e.isControlDown ())
            myView.observer().move (Observer.GO_FRONTWARDS);
          else myView.observer().move (Observer.TURN_UP);
        else
        {
          myView.observer().move (Observer.NO_FRONT_BACK_GO);
          myView.observer().move (Observer.NO_UP_DOWN_TURN);
        }
        break;

      case KeyEvent.VK_DOWN :
        if (pressed)
          if (e.isControlDown ())
            myView.observer().move (Observer.GO_BACKWARDS);
          else myView.observer().move (Observer.TURN_DOWN);
        else
        {
          myView.observer().move (Observer.NO_FRONT_BACK_GO);
          myView.observer().move (Observer.NO_UP_DOWN_TURN);
        }
        break;

      case KeyEvent.VK_LEFT :
        if (pressed)
          if (e.isControlDown ())
            myView.observer().move (Observer.GO_LEFTWARDS);
          else myView.observer().move (Observer.TURN_LEFT);
        else
        {
          myView.observer().move (Observer.NO_RIGHT_LEFT_GO);
          myView.observer().move (Observer.NO_RIGHT_LEFT_TURN);
        }
        break;

      case KeyEvent.VK_RIGHT :
        if (pressed)
          if (e.isControlDown ())
            myView.observer().move (Observer.GO_RIGHTWARDS);
          else myView.observer().move (Observer.TURN_RIGHT);
        else
        {
          myView.observer().move (Observer.NO_RIGHT_LEFT_GO);
          myView.observer().move (Observer.NO_RIGHT_LEFT_TURN);
        }
        break;

      case KeyEvent.VK_PAGE_UP :
        if (pressed) myView.observer().move (Observer.GO_UPWARDS);
        else myView.observer().move (Observer.NO_UP_DOWN_GO);
        break;

      case KeyEvent.VK_PAGE_DOWN :
        if (pressed) myView.observer().move (Observer.GO_DOWNWARDS);
        else myView.observer().move (Observer.NO_UP_DOWN_GO);
        break;
    }
  }

  /** Invoked when a key has been typed.
    * Implementation from KeyListener.
    * @param e detected key event.
    */
  public void keyTyped (KeyEvent e)
  {
    switch (e.getKeyChar ())
    {
      case 'z' :
        myView.observer().zoomIn ();
        break;

      case 'Z' :
	myView.observer().zoomOut ();
        break;
      default :
        myView.scene().processKey (e);
        break;
    }
  }
}
