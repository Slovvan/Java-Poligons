package org.fond;// Trame du TP de synthese d'images : reperes locaux et tache de fond
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.GL2;


/** Table model */
public class Table
{
  /** Table dimensions : table height */
  private float height = 1.0f;
  /** Table dimensions : table length */
  private float length = 2.4f;
  /** Table dimensions : table width */
  private float width = 0.9f;
  /** Table dimensions : table thickness */
  private float thick = 0.06f;

  /** Table plate */
  private Box plate = null;
  /** Table feet */
  private Box foot = null;

  /** Diffuse material */
  private float[] tableMat = {0.6f, 0.3f, 0.4f, 1.0f};
  /** Specular material */
  private float[] tableSpec = {0.8f, 0.7f, 0.3f, 1.0f};
  /** Shininess */
  private float[] tableShininess = {8.0f};


  /** Creates a table */
  public Table (float height, float length, float width, float thickness)
  {
    this.height = height;
    this.length = length;
    this.width = width;
    this.thick = thickness;
    plate = new Box (width, length, thickness);
    foot = new Box (thickness, thick, height);
  }

  /** Creates a default table */
  public Table ()
  {
    this (1.0f, 2.4f, 0.9f, 0.06f); 
  }

  /** Returns the table length */
  public float length ()
  {
    return (length);
  }

  /** Returns the table width */
  public float width ()
  {
    return (width);
  }

  /** Returns the table height */
  public float height ()
  {
    return (height);
  }

  /** Renders the table.
    * @param gl GL2 context. 
    */ 
  public void draw (GL2 gl)
  {
    // Table display
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, tableMat, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, tableSpec, 0);
    gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, tableShininess, 0);

    gl.glPushMatrix ();
      gl.glTranslatef (width / 2 - thick, thick - length / 2, 0.0f);
      foot.draw (gl);
      gl.glTranslatef (0.0f , length - 2 * thick, 0.0f);
      foot.draw (gl);
      gl.glTranslatef (thick * 2 - width, 0.0f, 0.0f);
      foot.draw (gl);
      gl.glTranslatef (0.0f , 2 * thick - length, 0.0f);
      foot.draw (gl);
    gl.glPopMatrix ();

    gl.glPushMatrix ();
      gl.glTranslatef (0.0f , 0.0f, height - thick);
      plate.draw (gl);
    gl.glPopMatrix ();
  }
}
