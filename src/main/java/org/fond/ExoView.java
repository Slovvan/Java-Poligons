package org.fond;// Trame du TP de synthese d'images : reperes locaux et tache de fond
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;


/** GL2 context to render a movable 3D primitive */
public class ExoView implements GLEventListener
{
  /** Rendered scene */
  private ShowHall hall = new ShowHall();

  /** Light position */
  private float lightPos[] = {0.5f, 1.0f, 2.0f, 1.0f};

  /** Scene viewing handler */
  private Observer obs = new Observer();


  /** Called by the drawable immediately after the OpenGL2 context is
    * initialized for the first time.
    * Implementation from GLEventListener. 
    * Used to perform one-time OpenGL2 initializations.
    * @param glDrawable GLAutoDrawable object.
    */
  public void init (GLAutoDrawable glDrawable)
  {
    // Light parameters
    float _lightPos[] = {0.0f, 0.0f, 2.0f, 1.0f};
    float _lightAmbient[] = {0.1f, 0.1f, 0.2f, 1.0f};
    float _lightDiffuse[] = {0.6f, 0.6f, 0.6f, 1.0f};
    float _lightSpecular[] = {0.6f, 0.6f, 0.6f, 1.0f};

    final GL2 gl = glDrawable.getGL().getGL2 ();
    gl.glShadeModel (GL2.GL_SMOOTH);                // Smooth Shading
    gl.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);      // Black Background
    gl.glClearDepth (1.0f);                        // Depth Buffer Setup
    gl.glEnable (GL2.GL_DEPTH_TEST);                // Enables Depth Testing
    gl.glDepthFunc (GL2.GL_LEQUAL);                 // Type Of Depth Testing
    gl.glEnable (GL2.GL_CULL_FACE);                 // Face culling

    // Light model
    gl.glShadeModel (GL2.GL_SMOOTH);
    gl.glLightfv (GL2.GL_LIGHT0, GL2.GL_AMBIENT, _lightAmbient, 0);
    gl.glLightfv (GL2.GL_LIGHT0, GL2.GL_POSITION, _lightPos, 0);
    gl.glLightfv (GL2.GL_LIGHT0, GL2.GL_DIFFUSE, _lightDiffuse, 0);
    gl.glLightfv (GL2.GL_LIGHT0, GL2.GL_SPECULAR, _lightSpecular, 0);
    gl.glEnable (GL2.GL_LIGHTING);
    gl.glEnable (GL2.GL_LIGHT0);

    // Fits the observer to the scene to display
    obs.setViewingVolume (hall.defaultVisibleVolume ());
    obs.setAccessibleVolume (hall.accessibleVolume ());
    obs.setDefaultPosition (hall.observerPosition ());

    // Initializes the room
    hall.initBackgroundTask ();
  }

  /** Called by the drawable to initiate OpenGL2 rendering by the client.
    * Implementation from GLEventListener. 
    * After all GLEventListeners have been notified of a display event,
    * the drawable will swap its buffers if necessary.
    * @param glDrawable GLAutoDrawable object.
    */
  public void display (GLAutoDrawable glDrawable)
  {
    final GL2 gl = glDrawable.getGL().getGL2 ();

    gl.glClearColor (0.0f, 0.0f, 0.0f, 0.5f); // Black Background
    gl.glClear (GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

    // Set the viewer
    obs.apply (gl);

    // Call background task
    hall.runBackgroundTask ();

    // Light positionning
    gl.glLightfv (GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);

    // Display the room
    hall.draw (gl);
  }

  /** Called by the drawable during the first repaint after the component
    * has been resized.
    * Implementation from GLEventListener. 
    * @param glDrawable GLAutoDrawable object.
    * @param x X Coordinate of the viewport area.
    * @param y Y coordinate of the viewport area.
    * @param width new width of the window.
    * @param height new height of the window.
    */
  public void reshape (GLAutoDrawable glDrawable,
                       int x, int y, int width, int height)
  {
    final GL2 gl = glDrawable.getGL().getGL2 ();

    if (height <= 0) height = 1; // avoid a divide by zero error!
    gl.glViewport (0, 0, width, height);
    obs.setProjection (gl, width, height);
    gl.glLoadIdentity ();
  }

  /** Called when the display mode has been changed.
    * Implementation from GLEventListener. 
    * @param glDrawable GLAutoDrawable object.
    */
  public void dispose (GLAutoDrawable glDrawable)
  {
  }

  /** Returns the scene to be rendered.
    */
  public ShowHall scene ()
  {
    return (hall);
  }

  /** Returns the frequency of the display refresh.
    */
  public int backgroundTaskFrequency ()
  {
    return (hall.backgroundTaskFrequency ());
  }

  /** Returns the scene viewing controller.
    */
  public Observer observer ()
  {
    return (obs);
  }
}
