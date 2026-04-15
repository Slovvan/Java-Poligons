package org.ogl;// Trame du TP de synthese d'images : primitives OpenGL
// BUT Info 3 - 2025/2026
// Source: Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;


/** GL2 context to render 2D primitives */
public class ExoView implements GLEventListener
{
  /** Rendered 2D primitive */
  private Drawing myDrawing = new Drawing ();
  private boolean optimized = false;
  private boolean optModif = false;
  private float ang = 0.0f;
  private int num;
  private double cx = 0., cy = 0.;
  private double zoom = 100.;
  private float[][] ccol = {{1.0f, 1.0f, 1.0f, 0.0f},
                            {1.0f, 0.0f, 0.0f, 0.0f},
                            {0.0f, 1.0f, 0.0f, 0.0f},
                            {0.0f, 0.0f, 1.0f, 0.0f},
                            {0.0f, 0.0f, 0.0f, 0.0f}};
  private int cnum = 4;
  private boolean switchCol = false;
  private int rescur = 0;
  private final static int[] resols = {16, 8, 15, 7};


  /** Creates a view for the appropriate detail. */
  public ExoView (int num)
  {
    this.num = num;
    if (num >= 1 && num <= 4) 
    {
      zoom = 35.;
      cy = (num <= 2 ? 75. : 25.);
      cx = (num % 2 != 0 ? -75. : - 25.);
    }
    else if (num > 4)
    {
      zoom = 70.;
      cy = (num > 5 ? -50 : 50);
      cx = (num % 2 != 0 ? 50. : -50);
    }
  }

  /** Called by the drawable immediately after the OpenGL2 context is
    * initialized for the first time.
    * Implementation from GLEventListener. 
    * Used to perform one-time OpenGL2 initializations.
    * @param glDrawable GLAutoDrawable object.
    */
  public void init (GLAutoDrawable glDrawable)
  {
    final GL2 gl = glDrawable.getGL().getGL2 ();
    gl.glShadeModel (GL2.GL_FLAT);
    gl.glClearColor (ccol[cnum][0], ccol[cnum][1], ccol[cnum][2], 0.0f);
    gl.glPointSize (num == 0 ? 2.0f : 5.0f);
    gl.glLineWidth (num == 0 ? 1.0f : 3.0f);
    if (optimized) gl.glEnable (GL2.GL_CULL_FACE);
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
    if (switchCol)
    {
      if (++cnum == ccol.length) cnum = 0;
      gl.glClearColor (ccol[cnum][0], ccol[cnum][1], ccol[cnum][2], 0.0f);
      switchCol = false;
    }
    if (optModif)
    {
      if (optimized) gl.glEnable (GL2.GL_CULL_FACE);
      else gl.glDisable (GL2.GL_CULL_FACE);
      optModif = false;
    }
    gl.glClear (GL2.GL_COLOR_BUFFER_BIT);
    gl.glLoadIdentity ();
    gl.glTranslated (cx, cy, 0.);
    gl.glRotatef (ang, 0.0f, 0.0f, 1.0f);
    gl.glTranslated (- cx, - cy, 0.);

    float fz = 100.0f;
    float[] mycol = null;
    if (cnum == 0) mycol = ccol[4];
    else if (cnum == 1) mycol = ccol[2];
    else if (cnum == 2) mycol = ccol[3];
    else if (cnum == 3) mycol = ccol[1];
    else if (cnum == 4) mycol = ccol[0];
    gl.glColor3f (mycol[0], mycol[1], mycol[2]);
    gl.glBegin (GL2.GL_LINES);
      gl.glVertex2f (- fz, 0.0f);
      gl.glVertex2f (fz, 0.0f);
      gl.glVertex2f (0.0f, - fz);
      gl.glVertex2f (0.0f, fz);
      gl.glVertex2f (- fz, fz / 2);
      gl.glVertex2f (0.0f, fz / 2);
      gl.glVertex2f (- fz / 2, 0.0f);
      gl.glVertex2f (- fz / 2, fz);
    gl.glEnd ();

    gl.glShadeModel (GL2.GL_FLAT);
    for (int i = 0; i < 10; i++)
    {
      if (myDrawing.CASE[i] != 0)
      {
        if (num == 0 || num == myDrawing.CASE[i])
        {
          if (myDrawing.LISS[i] != 0) gl.glShadeModel (GL2.GL_SMOOTH);
          if (i == 0) myDrawing.displayPoints (gl);
          else if (i == 1) myDrawing.displayLines (gl);
          else if (i == 2) myDrawing.displayLineStrip (gl);
          else if (i == 3) myDrawing.displayLineLoop (gl);
          else if (i == 4) myDrawing.displayTriangles (gl);
          else if (i == 5) myDrawing.displayTriangleStrip (gl);
          else if (i == 6) myDrawing.displayTriangleFan (gl);
          else if (i == 7) myDrawing.displayQuads (gl);
          else if (i == 8) myDrawing.displayQuadStrip (gl);
          else if (i == 9) myDrawing.displayPolygon (gl);
          if (myDrawing.LISS[i] != 0) gl.glShadeModel (GL2.GL_FLAT);
        }
      }
    }
  }

  /** Called by the drawable during the first repaint after the component
    * has been resized.
    * Implementation from GLEventListener. 
    * Used to update the viewport and view volume appropriately,
    * for example by a call to GL2.glViewport(int, int, int, int).
    * Note that for convenience the component has already called
    * GL2.glViewport(int, int, int, int)(x, y, width, height) when this
    * method is called, so the client may not have to do anything.
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

    if (height <= 0) // avoid a divide by zero error!
      height = 1;
    final float h = (float) width / (float) height;
    gl.glViewport (0, 0, width, height);
    gl.glMatrixMode (GL2.GL_PROJECTION);
      gl.glLoadIdentity ();
      gl.glOrtho (cx - zoom, cx + zoom, cy - zoom, cy + zoom, -1.0, 1.0);
    gl.glMatrixMode (GL2.GL_MODELVIEW);
      gl.glLoadIdentity ();
  }

  /** Called when the display mode has been changed.
    * Implementation from GLEventListener. 
    * @param glDrawable GLAutoDrawable object.
    * @param modeChanged Indicates if the video mode has changed.
    * @param deviceChanged Indicates if the video device has changed.
    */
  public void dispose (GLAutoDrawable glDrawable)
  {
  }

  public void toggleOptimize ()
  {
    optimized = ! optimized;
    optModif = true;
  }

  public void rotate ()
  {
    ang += 2.0f;
  }

  public void rotateBack ()
  {
    ang -= 2.0f;
  }

  public void background ()
  {
    switchCol = true;
  }

  public void switchResol ()
  {
    if (++rescur == resols.length) rescur = 0;
    myDrawing.cres = resols[rescur];
  }
}
