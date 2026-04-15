package org.fond;// Trame du TP de synthese d'images : reperes locaux et tache de fond
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.GL2;


/** Hollow cone solid primitive */
public class HCone extends SolidPrimitive
{
  /** Base radius */
  private float baseR = 1.0f;
  /** Top radius */
  private float topR = 1.0f;
  /** Half height (Z) */
  private float height_2 = 1.0f;
  /** Base thickness */
  private float th = 0.02f;
  /** Resolution */
  private int resol = 17;

  /** Constructs a hollow cone with given parameters.
    * @param baseRadius Cone base radius
    * @param topRadius Cone top radius
    * @param height Cone height
    * @param thickness Cone base thickness
    * @param resolution Cone resolution
    * @param ref Cone refence system (REF_CENTER or REF_BASE) 
    */ 
  public HCone (float baseRadius, float topRadius,
                float height, float thickness, int resolution, int ref)
  {
    baseR = baseRadius; 
    topR = topRadius; 
    height_2 = height / 2; 
    th = thickness; 
    resol = resolution;
    setReference (ref);
  }

  /** Constructs a hollow cone with given parameters
    * and the reference linked to the base
    * @param baseRadius Cone base radius
    * @param topRadius Cone top radius
    * @param height Cone height
    * @param thickness Cone base thickness
    * @param resolution Cone resolution
    */ 
  public HCone (float topRadius, float baseRadius,
                float height, float thickness, int resolution)
  {
    this (topRadius, baseRadius, height, thickness, resolution, REF_BASE);
  }

  /** Constructs a hollow cone with given parameters, default resolution
    * and the reference linked to the base
    * @param baseRadius Cone base radius
    * @param topRadius Cone top radius
    * @param thickness Cone base thickness
    * @param height Cone height
    */ 
  public HCone (float topRadius, float baseRadius,
                float height, float thickness)
  {
    this (topRadius, baseRadius, height, thickness, 8, REF_BASE);
  }

  /** Constructs a unit hollow cone
    */ 
  public HCone ()
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
      case REF_BASE :
        refMatrix[14] = height_2;
        break;
      case REF_TOP :
        refMatrix[14] = - height_2;
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
    gl.glPushMatrix ();
      gl.glMultMatrixf (refMatrix, 0);

      gl.glBegin (GL2.GL_TRIANGLE_STRIP);
        gl.glNormal3f (0.0f, 0.0f, 1.0f);
        gl.glVertex3f (topR - th, 0.0f, height_2);
        gl.glVertex3f (topR, 0.0f, height_2);
	for (int i = 1; i < resol; i ++)
        {
          gl.glVertex3f (
            (topR - th) * (float) Math.cos (2 * i * Math.PI / resol),
            (topR - th) * (float) Math.sin (2 * i * Math.PI / resol),
            height_2);
          gl.glVertex3f (
            topR * (float) Math.cos (2 * i * Math.PI / resol),
            topR * (float) Math.sin (2 * i * Math.PI / resol),
            height_2);
        }
        gl.glVertex3f (topR - th, 0.0f, height_2);
        gl.glVertex3f (topR, 0.0f, height_2);
      gl.glEnd ();

      gl.glBegin (GL2.GL_TRIANGLE_STRIP);
        gl.glNormal3f (0.0f, 0.0f, -1.0f);
        gl.glVertex3f (baseR, 0.0f, - height_2);
        gl.glVertex3f (baseR - th, 0.0f, - height_2);
	for (int i = 1; i < resol; i ++)
        {
          gl.glVertex3f (
            baseR * (float) Math.cos (2 * i * Math.PI / resol),
            baseR * (float) Math.sin (2 * i * Math.PI / resol),
            - height_2);
          gl.glVertex3f (
            (baseR - th) * (float) Math.cos (2 * i * Math.PI / resol),
            (baseR - th) * (float) Math.sin (2 * i * Math.PI / resol),
            - height_2);
        }
        gl.glVertex3f (baseR, 0.0f, - height_2);
        gl.glVertex3f (baseR - th, 0.0f, - height_2);
      gl.glEnd ();

      gl.glBegin (GL2.GL_TRIANGLE_STRIP);
        float nz = baseR - topR;
        float norm = (float) Math.sqrt (nz * nz + height_2 * height_2 * 4);
        float nr = height_2 * 2 / norm;
	nz /= norm;

        gl.glNormal3f (nr, 0.0f, nz);
        gl.glVertex3f (topR, 0.0f, height_2);
        gl.glVertex3f (baseR, 0.0f, - height_2);
	for (int i = 1; i < resol; i ++)
	{
          float cost = (float) Math.cos (2 * i * Math.PI / resol);
          float sint = (float) Math.sin (2 * i * Math.PI / resol);
          gl.glNormal3f (nr * cost, nr * sint, nz);
          gl.glVertex3f (topR * cost, topR * sint, height_2);
          gl.glVertex3f (baseR * cost, baseR * sint, - height_2);
	}
        gl.glNormal3f (nr, 0.0f, nz);
        gl.glVertex3f (topR, 0.0f, height_2);
        gl.glVertex3f (baseR, 0.0f, - height_2);
      gl.glEnd ();

      gl.glBegin (GL2.GL_TRIANGLE_STRIP);
        nz = - baseR - topR;
        norm = (float) Math.sqrt (nz * nz + height_2 * height_2 * 4);
        nr = - height_2 * 2 / norm;
	nz /= norm;

        gl.glNormal3f (nr, 0.0f, nz);
        gl.glVertex3f (baseR - th, 0.0f, - height_2);
        gl.glVertex3f (topR - th, 0.0f, height_2);
	for (int i = 1; i < resol; i ++)
	{
          float cost = (float) Math.cos (2 * i * Math.PI / resol);
          float sint = (float) Math.sin (2 * i * Math.PI / resol);
          gl.glNormal3f (nr * cost, nr * sint, nz);
          gl.glVertex3f ((baseR - th) * cost, (baseR - th) * sint, - height_2);
          gl.glVertex3f ((topR - th) * cost, (topR - th) * sint, height_2);
	}
        gl.glNormal3f (nr, 0.0f, nz);
        gl.glVertex3f (baseR - th, 0.0f, - height_2);
        gl.glVertex3f (topR - th, 0.0f, height_2);
      gl.glEnd ();

    gl.glPopMatrix ();
  }
}
