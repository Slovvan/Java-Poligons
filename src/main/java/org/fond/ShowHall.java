package org.fond;// Trame du TP de synthese d'images : reperes locaux et tache de fond
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.GL2;

import java.awt.event.KeyEvent;


/** Show hall 3D model */
public class ShowHall
{
  /** Hall dimensions : half the depth */
  private final float depth_2 = 4.0f;
  /** Hall dimensions : half the width */
  private final float width_2 = 3.0f;
  /** Hall dimensions : the height */
  private final float height = 4.0f;

  /** Hall table */
  private Table table = new Table();
  /** Table position */
  private float[] tabpos = {-2.0f, 0.0f};
  /** Table rotation angle */
  private float tabrot = 0.0f;
  /** Table rotation increment */
  private final static float ANG_INCREMENT = 0.5f;
  /** Display frequency (number of frames per second) */
  private int animationFrequency = 60;


  /** Table clothe */
  private Box tableClothe = new Box (0.2f, 0.2f, 0.01f);
  /** Clothe position */
  private float[] clothepos = {table.width () / 2 - 0.2f,
                               table.length () / 2 - 0.2f};
  /** Clothe rotation angle */
  private float clotherot = 14.0f;

  /** Shelf size */
  private float[] shelfSize = {0.8f, 0.05f};
  /** Shelf */
  private Box shelf = new Box (shelfSize[0], width_2 * 2, shelfSize[1],
                               SolidPrimitive.REF_TOP);
  /** Shelf height */
  private float shelfHeight = 2.4f;

  /** Small shelf size */
  private float[] smallShelfSize = {0.6f, 0.03f};
  /** Small shelf */
  private Box smallShelf = new Box (smallShelfSize[0], width_2 / 8,
                                    smallShelfSize[1], SolidPrimitive.REF_TOP);
  /** Small shelf height */
  private float smallShelfHeight = 1.8f;
  /** Pot */
  private HCone pot = new HCone (0.1f, 0.15f, 0.2f, 0.01f);
  private Fan fan = new Fan();
  private float fanrot = 0.0f;
  private final static float FAN_SPEED = 5.0f;

  /** Hall chairs */
  private Chair[] chairs = {new Chair (), new Chair (true)};
  /** Chairs type */
  private int[] chcol = {1,0,1,0,0,1,1,0,0,0,1,0,0,1,0,1,1,0,0};
  /** Chairs position */
  private final float[][] chpos = {
        {0.0f, -2.4f}, {-3.5f, -2.4f}, {-2.7f, -2.5f}, {-1.9f, -2.3f},
        {-1.2f, -2.6f}, {-0.4f, -2.3f}, {0.5f, -2.3f}, {1.4f, -2.3f},
        {2.2f, -2.1f}, {3.1f, -2.5f}, {3.3f, 2.5f}, {2.25f, 2.5f},
        {1.5f, 2.5f}, {0.5f, 2.4f}, {-0.3f, 2.6f}, {-1.05f, 2.1f},
        {-2.0f, 2.2f}, {-2.75f, 2.3f}, {-3.55f, 2.0f}};
  /** Chairs rotation angle */
  private final float[][] chrot = {
        {78.0f, 0.0f}, {80.0f, 0.0f}, {100.0f, 0.0f}, {70.0f, -20.0f},
        {-20.0f, 0.0f}, {30.0f, 0.0f}, {85.0f, 0.0f}, {-20.0f, 0.0f},
        {170.0f, 0.0f}, {-10.0f, 0.0f}, {180.0f, -20.0f}, {-145.0f, 0.0f},
        {-175.0f, 0.0f}, {-93.0f, 0.0f}, {-88.0f, 0.0f}, {-114.0f, 0.0f},
        {-124.0f, 0.0f}, {-94.0f, 0.0f}, {-78.0f, 0.0f}};

  /** Hall posts */
  private HCone post = new HCone (0.04f, 0.04f, 1.0f, 0.005f);
  /** Posts position */
  private final float[][] postpos = {
        {0.3f, -2.7f}, {0.0f, 0.7f}, {0.0f, 0.2f}, {0.0f, 0.4f},
        {0.0f, 0.12f}, {0.0f, 0.17f}, {0.0f, 0.23f}, {0.0f, 0.1f},
        {0.0f, 0.28f}, {0.0f, 0.12f}, {0.0f, 0.21f}, {0.0f, 0.17f},
        {0.0f, 0.16f}, {0.0f, 0.1f}, {0.0f, 0.21f}, {0.0f, 0.25f},
        {0.0f, 0.16f}, {0.0f, 0.14f}, {0.0f, 0.17f}, {0.0f, 0.11f},
        {0.0f, 0.23f}, {0.0f, 0.21f}, {0.0f, 0.18f}, {0.0f, 0.14f},
        {0.0f, 0.11f}, {0.0f, 0.21f}, {0.0f, 0.13f}, {0.0f, 0.16f},
        {0.0f, 0.14f}};

  /** Floor diffuse material */
  private float[] floorMat = {0.4f, 0.6f, 0.5f, 1.0f};
  /** Floor specular material */
  private float[] floorSpec = {0.5f, 0.3f, 0.1f, 1.0f};
  /** Floor shininess */
  private float[] floorShininess = {2.0f};
  /** Ceiling diffuse material */
  private float[] ceilingMat = {0.5f, 0.7f, 0.6f, 1.0f};
  /** Ceiling specular material */
  private float[] ceilingSpec = {0.5f, 0.3f, 0.1f, 1.0f};
  /** Ceiling shininess */
  private float[] ceilingShininess = {2.0f};
  /** North wall diffuse material */
  private float[] northMat = {0.2f, 0.2f, 0.1f, 1.0f};
  /** North wall specular material */
  private float[] northSpec = {0.5f, 0.3f, 0.1f, 1.0f};
  /** North wall shininess */
  private float[] northShininess = {2.0f};
  /** South wall diffuse material */
  private float[] southMat = {0.2f, 0.2f, 0.1f, 1.0f};
  /** South wall specular material */
  private float[] southSpec = {0.5f, 0.3f, 0.1f, 1.0f};
  /** South wall shininess */
  private float[] southShininess = {2.0f};
  /** East wall diffuse material */
  private float[] eastMat = {0.2f, 0.2f, 0.1f, 1.0f};
  /** East wall specular material */
  private float[] eastSpec = {0.5f, 0.3f, 0.1f, 1.0f};
  /** East wall shininess */
  private float[] eastShininess = {2.0f};
  /** West wall diffuse material */
  private float[] westMat = {0.2f, 0.2f, 0.1f, 1.0f};
  /** West wall specular material */
  private float[] westSpec = {0.5f, 0.3f, 0.1f, 1.0f};
  /** West wall shininess */
  private float[] westShininess = {2.0f};

  /** Table clothe diffuse material */
  private float[] clotheMat = {0.2f, 0.2f, 0.5f, 1.0f};
  /** Table clothe specular material */
  private float[] clotheSpec = {0.0f, 0.0f, 0.0f, 1.0f};
  /** Table clothe shininess */
  private float[] clotheShininess = {1.0f};

  /** Shelf diffuse material */
  private float[] shelfMat = {0.7f, 0.4f, 0.2f, 1.0f};
  /** Shelf specular material */
  private float[] shelfSpec = {0.7f, 0.4f, 0.6f, 1.0f};
  /** Shelf shininess */
  private float[] shelfShininess = {1.0f};

  /** Posts diffuse material */
  private float[] postMat = {0.8f, 0.7f, 0.4f, 1.0f};
  /** Posts specular material */
  private float[] postSpec = {0.7f, 0.8f, 0.4f, 1.0f};
  /** Posts shininess */
  private float[] postShininess = {20.0f};


  /** Renders the hall.
    * @param gl GL2 context. 
    */ 
  public void draw (GL2 gl)
  {
    // Floor
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, floorMat, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, floorSpec, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, floorShininess, 0);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glNormal3f (0.0f, 0.0f, 1.0f);
      gl.glVertex3f (-depth_2, -width_2, 0.0f);
      gl.glVertex3f (depth_2, -width_2, 0.0f);
      gl.glVertex3f (-depth_2, width_2, 0.0f);
      gl.glVertex3f (depth_2, width_2, 0.0f);
    gl.glEnd ();

    // Table
    gl.glPushMatrix ();
      gl.glTranslatef (tabpos[0], tabpos[1], 0.0f);
      gl.glRotatef (tabrot, 0.0f, 0.0f, 1.0f);
      table.draw (gl);

      // Go to table plate
      gl.glPushMatrix ();
        gl.glTranslatef (0.0f, 0.0f, table.height ());

        gl.glPushMatrix ();
          gl.glTranslatef (clothepos[0], clothepos[1], 0.0f);
          gl.glRotatef (clotherot, 0.0f, 0.0f, 1.0f);
          gl.glMaterialfv (GL2.GL_FRONT,
                           GL2.GL_AMBIENT_AND_DIFFUSE, clotheMat, 0);
          gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, clotheSpec, 0);
          gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, clotheShininess, 0);
          tableClothe.draw (gl);

          //Vase avec 3 cones
          gl.glPushMatrix();
          gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, new float[]{0.9f, 0.9f, 0.0f, 1.0f}, 0);
          HCone baseVase = new HCone(0.05f, 0.03f, 0.05f, 0.005f);
          baseVase.draw(gl);

          gl.glTranslatef(0.0f, 0.0f, 0.05f);
          gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, new float[]{0.2f, 0.5f, 0.8f, 1.0f}, 0);
          HCone corpsVase = new HCone(0.03f, 0.06f, 0.15f, 0.005f);
          corpsVase.draw(gl);

          gl.glTranslatef(0.0f, 0.0f, 0.15f);
          gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, new float[]{0.3f, 0.8f, 0.2f, 1.0f}, 0);
          HCone colVase = new HCone(0.06f, 0.04f, 0.05f, 0.005f);
          colVase.draw(gl);
          gl.glPopMatrix();
      gl.glPopMatrix();
      gl.glPopMatrix ();
    gl.glPopMatrix ();

    //fan
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, 0.0f, height - 0.1f);
    // Rotation avec v
      gl.glRotatef(fanrot, 0.0f, 0.0f, 1.0f);
      fan.draw(gl);
      gl.glPopMatrix ();

      // Chairs
    for (int i = 1; i < chcol.length; i++)
    {
      gl.glPushMatrix ();
        gl.glTranslatef (chpos[i][0], chpos[i][1], 0.0f);
        gl.glRotatef (chrot[i][0], 0.0f, 0.0f, 1.0f);
        gl.glRotatef (chrot[i][1], 0.0f, 1.0f, 0.0f);
        chairs[chcol[i]].draw (gl);
      gl.glPopMatrix ();
    }
 
    // North wall (-X : initially frontwards)
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, northMat, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, northSpec, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, northShininess, 0);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glNormal3f (1.0f, 0.0f, 0.0f);
      gl.glVertex3f (-depth_2, -width_2, 0.0f);
      gl.glVertex3f (-depth_2, width_2, 0.0f);
      gl.glVertex3f (-depth_2, -width_2, height);
      gl.glVertex3f (-depth_2, width_2, height);
    gl.glEnd ();

    // Go to north wall base
    gl.glPushMatrix ();
      gl.glTranslatef (- depth_2, 0.0f, 0.0f);

      // Shelf
      gl.glPushMatrix ();
        gl.glTranslatef (shelfSize[0] / 2, 0.0f, shelfHeight);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, shelfMat, 0);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, shelfSpec, 0);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, shelfShininess, 0);
        shelf.draw (gl);

        // Up chair
        gl.glPushMatrix ();
          gl.glTranslatef (chpos[0][0], chpos[0][1], 0.0f);
          gl.glRotatef (chrot[0][0], 0.0f, 0.0f, 1.0f);
          gl.glRotatef (chrot[0][1], 0.0f, 1.0f, 0.0f);
          chairs[chcol[0]].draw (gl);
        gl.glPopMatrix ();

        // Posts
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, postMat, 0);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, postSpec, 0);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, postShininess, 0);
        for (int i = 0; i < postpos.length; i++)
        {
          gl.glTranslatef (postpos[i][0], postpos[i][1], 0.0f);
          post.draw (gl);
        }
      gl.glPopMatrix ();

      // Small shelf
      gl.glPushMatrix ();
        gl.glTranslatef (smallShelfSize[0] / 2, 0.0f, smallShelfHeight);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, shelfMat, 0);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, shelfSpec, 0);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, shelfShininess, 0);
        smallShelf.draw (gl);

        // Pot
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, postMat, 0);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, postSpec, 0);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, postShininess, 0);
        pot.draw (gl);
      gl.glPopMatrix ();
    gl.glPopMatrix ();

    // West wall (-Y : initially leftwards)
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, westMat, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, westSpec, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, westShininess, 0);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glNormal3f (0.0f, 1.0f, 0.0f);
      gl.glVertex3f (depth_2, -width_2, 0.0f);
      gl.glVertex3f (-depth_2, -width_2, 0.0f);
      gl.glVertex3f (depth_2, -width_2, height);
      gl.glVertex3f (-depth_2, -width_2, height);
    gl.glEnd ();

    // Ceiling
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, ceilingMat, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, ceilingSpec, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, ceilingShininess, 0);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glNormal3f (0.0f, 0.0f, -1.0f);
      gl.glVertex3f (depth_2, width_2, height);
      gl.glVertex3f (depth_2, -width_2, height);
      gl.glVertex3f (-depth_2, width_2, height);
      gl.glVertex3f (-depth_2, -width_2, height);
    gl.glEnd ();

    // South wall (+X : initially backwards)
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, southMat, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, southSpec, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, southShininess, 0);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glNormal3f (-1.0f, 0.0f, 0.0f);
      gl.glVertex3f (depth_2, width_2, height);
      gl.glVertex3f (depth_2, width_2, 0.0f);
      gl.glVertex3f (depth_2, -width_2, height);
      gl.glVertex3f (depth_2, -width_2, 0.0f);
    gl.glEnd ();

    // East wall (+Y : initially rightwards)
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, eastMat, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, eastSpec, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, eastShininess, 0);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glNormal3f (0.0f, -1.0f, 0.0f);
      gl.glVertex3f (depth_2, width_2, height);
      gl.glVertex3f (-depth_2, width_2, height);
      gl.glVertex3f (depth_2, width_2, 0.0f);
      gl.glVertex3f (-depth_2, width_2, 0.0f);
    gl.glEnd ();
  }

  /** Returns the visible volume.
    */
  public float[] defaultVisibleVolume ()
  {
    float[] volume = {- depth_2 * 1.1f, depth_2 * 1.1f,
                      - width_2 * 1.1f, width_2 * 1.1f,
                      - 0.1f, height};
    return (volume);
  }

  /** Returns the volume the observer is allowed to walk through
    */
  public float[] accessibleVolume ()
  {
    float[] volume = {- depth_2 * 0.9f, depth_2 * 0.9f,
                      - width_2 * 0.9f, width_2 * 0.9f,
                      0.1f, height - 0.1f};
    return (volume);
  }

  /** Returns a relevant position of the observer in the hall.
    */
  public float[] observerPosition ()
  {
    float[] pos = {2.0f, 0.0f, 2.0f};
    return (pos);
  }

  /** Processes key events.
    */
  public void processKey (KeyEvent e)
  {
    switch (e.getKeyChar ())
    {case 'v' :
            fanrot += FAN_SPEED;
            break;
      case 't' :
        tabrot += ANG_INCREMENT;
      break;
      case 'h' :
        animationFrequency = (animationFrequency == 60 ? 1 : 60);
        initBackgroundTask ();
      break;
    }
  }

  /** Returns the number of frames per second of the display refresh.
    */
  public int backgroundTaskFrequency ()
  {
    return animationFrequency;
  }

  /** Initializes the backround task (nothing at present).
    */
  public void initBackgroundTask ()
  {
  }

  /** Runs the backround task (nothing at present).
    */
  public void runBackgroundTask ()
  {
  }
}
