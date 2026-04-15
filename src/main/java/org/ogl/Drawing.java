package org.ogl;

// Trame du TP de synthese d'images : primitives OpenGL
// BUT Info 3 - 2025/2026
// Source: Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)
// Complete par :

import com.jogamp.opengl.GL2;

/** 2D drawings. */
public class Drawing
{
    /** Figure position on the panel. */
    public final static int[] CASE = {1, 6, 5, 0, 2, 3, 4, 7, 0, 0};
    /** Figure smoothing. */
    public final static int[] LISS = {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
    /** Plain circle resolution. */
    int cres = 16;

    /** Displays points.
     * Space 1: ]-100,-50[ in X and ]50,100[ in Y.
     */
    void displayPoints (GL2 gl)
    {
        gl.glColor3f (0.6f, 0.6f, 0.2f);
        gl.glBegin (GL2.GL_POINTS);
        for (int i = 0; i < 45; i++)
        {
            double angle = Math.random() * 2 * Math.PI;
            // Center (-75, 75), Radius 22
            float x = (float) (-75 + 22 * Math.cos(angle));
            float y = (float) (75 + 22 * Math.sin(angle));
            gl.glVertex2f(x, y);
        }
        gl.glEnd ();
    }

    /** Displays lines using the Gardener's Method for an ellipse.
     * Space 6: ]-100,-50[ in X and ]-100,-50[ in Y.
     */
    void displayLines (GL2 gl)
    {
        gl.glColor3f (0.6f, 0.6f, 0.3f);
        gl.glBegin (GL2.GL_LINES);
        for (int i = 0; i < 45; i++)
        {
            // Point 1 on ellipse
            double a1 = Math.random() * 2 * Math.PI;
            gl.glVertex2f((float)(-75 + 23 * Math.cos(a1)), (float)(-75 + 17 * Math.sin(a1)));
            // Point 2 on ellipse
            double a2 = Math.random() * 2 * Math.PI;
            gl.glVertex2f((float)(-75 + 23 * Math.cos(a2)), (float)(-75 + 17 * Math.sin(a2)));
        }
        gl.glEnd ();
    }

    /** Displays a shadok with glasses.
     * Space 5: ]0,100[ in X and ]0,100[ in Y.
     */
    void displayLineStrip (GL2 gl)
    {
        // Body (Blue dominant)
        gl.glColor3f (0.3f, 0.4f, 0.9f);
        gl.glBegin (GL2.GL_LINE_STRIP);
        gl.glVertex2f (5f, 35f);
        gl.glVertex2f (5f, 95f);
        gl.glVertex2f (95f, 95f);
        gl.glVertex2f (95f, 35f);
        gl.glVertex2f (50f, 5f);
        gl.glVertex2f (28f, 55f);
        gl.glEnd ();

        // Glasses (Red dominant)
        gl.glColor3f (0.8f, 0.3f, 0.5f);
        gl.glBegin (GL2.GL_LINE_STRIP);
        gl.glVertex2f (15f, 75f); gl.glVertex2f (30f, 85f); gl.glVertex2f (45f, 75f);
        gl.glVertex2f (30f, 65f); gl.glVertex2f (15f, 75f); // Left lens
        gl.glVertex2f (55f, 75f); // Bridge
        gl.glVertex2f (70f, 85f); gl.glVertex2f (85f, 75f); gl.glVertex2f (70f, 65f);
        gl.glVertex2f (55f, 75f); // Right lens
        gl.glEnd ();
    }

    /** Displays a line loop. */
    void displayLineLoop (GL2 gl)
    {
    }

    /** Displays 4 interlaced triangles.
     * Space 2: ]-50,0[ in X and ]50,100[ in Y.
     */
    void displayTriangles (GL2 gl)
    {
        gl.glBegin (GL2.GL_TRIANGLES);
        // Dark Blue
        gl.glColor3f (0.1f, 0.1f, 0.5f);
        gl.glVertex2f (-48f, 75f); gl.glVertex2f (-2f, 75f); gl.glVertex2f (-48f, 98f);
        // Green
        gl.glColor3f (0.1f, 0.6f, 0.1f);
        gl.glVertex2f (-45f, 55f); gl.glVertex2f (-25f, 55f); gl.glVertex2f (-35f, 98f);
        // Yellow
        gl.glColor3f (0.7f, 0.7f, 0.2f);
        gl.glVertex2f (-25f, 55f); gl.glVertex2f (-5f, 55f); gl.glVertex2f (-15f, 98f);
        // Magenta/Red
        gl.glColor3f (0.8f, 0.2f, 0.4f);
        gl.glVertex2f (-48f, 65f); gl.glVertex2f (-2f, 65f); gl.glVertex2f (-2f, 85f);
        gl.glEnd ();
    }

    /** Displays a thick 'W' with Harlequin shading.
     * Space 3: ]-100,-50[ in X and ]0,50[ in Y.
     */
    void displayTriangleStrip (GL2 gl)
    {
        gl.glBegin (GL2.GL_TRIANGLE_STRIP);
        gl.glColor3f (0.5f, 0.8f, 0.2f); gl.glVertex2f (-98f, 45f);
        gl.glColor3f (0.2f, 0.5f, 0.8f); gl.glVertex2f (-88f, 45f);
        gl.glColor3f (0.8f, 0.2f, 0.5f); gl.glVertex2f (-92f, 5f);
        gl.glColor3f (0.5f, 0.2f, 0.8f); gl.glVertex2f (-82f, 5f);
        gl.glColor3f (0.2f, 0.8f, 0.5f); gl.glVertex2f (-78f, 35f);
        gl.glColor3f (0.8f, 0.5f, 0.2f); gl.glVertex2f (-72f, 35f);
        gl.glColor3f (0.5f, 0.5f, 0.8f); gl.glVertex2f (-68f, 5f);
        gl.glColor3f (0.2f, 0.5f, 0.2f); gl.glVertex2f (-58f, 5f);
        gl.glColor3f (0.8f, 0.2f, 0.2f); gl.glVertex2f (-62f, 45f);
        gl.glColor3f (0.2f, 0.2f, 0.8f); gl.glVertex2f (-52f, 45f);
        gl.glEnd ();
    }

    /** Displays a thick green arrow.
     * Space 4: ]-50,0[ in X and ]0,50[ in Y.
     */
    void displayTriangleFan (GL2 gl)
    {
        gl.glColor3f (0.3f, 0.8f, 0.3f);
        gl.glBegin (GL2.GL_TRIANGLE_FAN);
        gl.glVertex2f (-5f, 25f);   // Tip (Fan Center)
        gl.glVertex2f (-25f, 48f);  // Top Wing
        gl.glVertex2f (-25f, 35f);  // Top Shoulder
        gl.glVertex2f (-48f, 35f);  // Top Back
        gl.glVertex2f (-38f, 25f);  // Notch
        gl.glVertex2f (-48f, 15f);  // Bottom Back
        gl.glVertex2f (-25f, 15f);  // Bottom Shoulder
        gl.glVertex2f (-25f, 2f);   // Bottom Wing
        gl.glEnd ();
    }

    /** Displays a thick 'U' shape using 3 Quads.
     * Space 7: ]0,100[ in X and ]-100,0[ in Y.
     */
    void displayQuads (GL2 gl)
    {
        gl.glColor3f (0.8f, 0.4f, 0.1f);
        gl.glBegin (GL2.GL_QUADS);
        // Left vertical bar
        gl.glVertex2f (5f, -95f); gl.glVertex2f (35f, -95f);
        gl.glVertex2f (35f, -5f); gl.glVertex2f (5f, -5f);
        // Right vertical bar
        gl.glVertex2f (65f, -95f); gl.glVertex2f (95f, -95f);
        gl.glVertex2f (95f, -5f); gl.glVertex2f (65f, -5f);
        // Bottom horizontal connector
        gl.glVertex2f (35f, -95f); gl.glVertex2f (65f, -95f);
        gl.glVertex2f (65f, -70f); gl.glVertex2f (35f, -70f);
        gl.glEnd ();
    }

    /** Displays a quadrilater strip. */
    void displayQuadStrip (GL2 gl)
    {
    }

    /** Displays a polygon. */
    void displayPolygon (GL2 gl)
    {
    }
}