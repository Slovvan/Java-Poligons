package org.fond;// Trame du TP de synthese d'images : reperes locaux et tache de fond
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.awt.GLCanvas;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//Deprec import javax.media.opengl.GLCapabilities;


/** Exercice main window */
public class Exo
{
  /** Creates AWT Frame with OpenGL context */
  public static void main (String[] args)
  {
    // AWT window creation
    Frame frame = new Frame ("Au theatre ce soir");

    // OpenGL display area creation
//Deprec     GLCapabilities capabilities = new GLCapabilities ();
//Deprec     capabilities.setDoubleBuffered (true); 
    GLCanvas canvas = new GLCanvas ();

    // Adding a OpenGL context (view)
    ExoView myView = new ExoView();
    canvas.addGLEventListener (myView);
    // Adding a user event handler (controller)
    ExoController myController = new ExoController(canvas, myView);
    canvas.addKeyListener (myController);
    // canvas.addMouseListener (myController);
    // canvas.addMouseMotionListener (myController);

    // Window closing behavior
    frame.addWindowListener (
      new WindowAdapter ()
      {
        public void windowClosing (WindowEvent e)
        {
          System.exit (0);
        }
      });

    // End of window specification
    frame.add (canvas);
    frame.setSize (600, 600);
    frame.setLocation (0, 0);
    frame.setBackground (Color.white);
    frame.setVisible (true);

    // Start of OpenGL loop
    canvas.requestFocus ();
  }
}
