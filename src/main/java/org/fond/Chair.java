package org.fond;// Trame du TP de synthese d'images : reperes locaux et tache de fond
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.GL2;


/** Chair model */
public class Chair
{
  /** Plate height */
  private float height = 0.65f;
  /** Plate size */
  private float size = 0.6f;
  /** Plate thickness */
  private float thick = 0.06f;

  /** Chair seat */
  private Box plate = null;
  /** Chair back plate */
  private Box plate2 = null;
  /** Chair feet */
  private Box foot = null;

  /* Plates diffuse material */
  private float[] plateMat = {0.2f, 0.2f, 0.3f, 1.0f};
  /* Red plates diffuse material */
  private float[] redMat = {0.4f, 0.2f, 0.1f, 1.0f};
  /* Plates specular material */
  private float[] plateSpec = {0.2f, 0.2f, 0.3f, 1.0f};
  /* Plates shininess */
  private float[] plateShininess = {1.0f};
  /* Feet diffuse material */
  private float[] footMat = {0.5f, 0.6f, 0.2f, 1.0f};
  /* Feet specular material */
  private float[] footSpec = {0.2f, 0.2f, 0.3f, 1.0f};
  /* Feet shininess */
  private float[] footShininess = {1.0f};


  /** Creates a chair */
  public Chair (float height, float size, float thickness)
  {
    this.height = height;
    this.size = size;
    this.thick = thickness;
    plate = new Box (size, size, thick);
    plate2 = new Box (size / 2, size, thick);
    foot = new Box (thick, thick, height);
  }

  /** Creates a red chair */
  public Chair (boolean isRed)
  {
    this ();
    if (isRed) plateMat = redMat;
  }

  /** Creates a default chair */
  public Chair ()
  {
    this (0.65f, 0.6f, 0.06f);
  }

  /** Returns the chair size */
  public float size ()
  {
    return (size);
  }

  /** Returns the plate height */
  public float height ()
  {
    return (height);
  }

  /** Returns the chair thickness */
  public float thick ()
  {
    return (height);
  }

  /** Renders the chair.
    * @param gl GL2 context. 
    */ 
  public void draw (GL2 gl)
  {
    // Plate
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, plateMat, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, plateSpec, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, plateShininess, 0);
    gl.glPushMatrix ();
      gl.glTranslatef (0.f, 0.f, height);
      plate.draw (gl);
      gl.glTranslatef (3 * thick / 2 - size / 2, 0.0f,
                       height - size / 4 + 2 * thick);
      gl.glRotatef (90.0f, 0.0f, 1.0f, 0.0f);
      plate2.draw (gl);
    gl.glPopMatrix ();

    // Feet
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, footMat, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, footSpec, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, footShininess, 0);
    gl.glPushMatrix ();
      gl.glTranslatef (size / 2 - thick, thick - size / 2, 0.0f);
      foot.draw (gl);
      gl.glTranslatef (0.0f , size - 2 * thick, 0.0f);
      foot.draw (gl);
      gl.glTranslatef (thick * 2 - size, 0.0f, 0.0f);
      foot.draw (gl);
      gl.glTranslatef (0.0f , 2 * thick - size, 0.0f);
      foot.draw (gl);
      gl.glTranslatef (0.0f, 2 *thick, height + thick);
      foot.draw (gl);
      gl.glTranslatef (0.0f, size - 6 * thick, 0.0f);
      foot.draw (gl);
    gl.glPopMatrix ();
  }
}
