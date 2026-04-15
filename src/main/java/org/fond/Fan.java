package org.fond;// Trame du TP de synthese d'images : reperes locaux et tache de fond
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.GL2;


/** Hollow cone solid primitive */
public class Fan extends SolidPrimitive
{
    private Box blade = new Box(1.5f, 0.2f, 0.02f);
    private float[] fanMat = {0.4f, 0.4f, 0.4f, 1.0f};

  /** Constructs a hollow cone with given parameters.
    * @param baseRadius Cone base radius
    * @param topRadius Cone top radius
    * @param height Cone height
    * @param thickness Cone base thickness
    * @param resolution Cone resolution
    * @param ref Cone refence system (REF_CENTER or REF_BASE)
    */


  /** Constructs a hollow cone with given parameters
    * and the reference linked to the base
    * @param baseRadius Cone base radius
    * @param topRadius Cone top radius
    * @param height Cone height
    * @param thickness Cone base thickness
    * @param resolution Cone resolution
    */

  /** Constructs a hollow cone with given parameters, default resolution
    * and the reference linked to the base
    * @param baseRadius Cone base radius
    * @param topRadius Cone top radius
    * @param thickness Cone base thickness
    * @param height Cone height
    */

  /** Constructs a unit hollow cone
    */
  public Fan()
  {
  }

  /** Sets the cone reference system (REF_CENTER or REF_BASE)
    * @param ref Reference system position wrt the cone.
    */
  protected void setReference (int ref)
  {
    switch (ref)
    {
      case REF_CENTER :
        refMatrix[11] = 0.0f;
        break;
      default :
        break;
    }
  }

  /** Renders this hollow cone primitive.
    * @param gl GL2 context. 
    */ 
  public void draw (GL2 gl)
  {
      gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, fanMat, 0);
      for (int i = 0; i < 3; i++) {
          gl.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);

          gl.glTranslatef(0.75f, 0.0f, 0.0f);

          blade.draw(gl);


          gl.glTranslatef(-0.75f, 0.0f, 0.0f);
          gl.glRotatef(-20.0f, 1.0f, 0.0f, 0.0f);

          gl.glRotatef(120.0f, 0.0f, 0.0f, 1.0f);
      }
  }
}
