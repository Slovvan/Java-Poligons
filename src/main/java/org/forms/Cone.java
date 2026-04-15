package org.forms;// Trame du TP de modelisation 3D : primitives solides
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.GL2;


/** Cone shape */
public class Cone extends ComplientShape
{
  /** Radius */
  private float radius = DEFAULT_VALUE;
  /** Half height (Z) */
  private float height_2 = DEFAULT_VALUE;
  /** Circular resolution. */
  private int res = DEFAULT_RESOLUTION;


  /** Creates a cone with given parameters.
    * @param diameter Cone base diameter
    * @param height Cone height
    * @param resolution Circular resolution
    */ 
  public Cone (float diameter, float height, int resolution)
  {
    radius = diameter / 2; 
    height_2 = height / 2; 
    setResolution (resolution);
  }

  /** Creates a cone with given parameters.
    * @param diameter Cone diameter
    * @param height Cone height
    */ 
  public Cone (float diameter, float height)
  {
    this (diameter, height, DEFAULT_RESOLUTION);
  }

  /** Creates a unit cone
    */ 
  public Cone ()
  {
    this (DEFAULT_VALUE, DEFAULT_VALUE);
  }

  /** Renders the cone shape centered.
    * @param gl GL2 context. 
    */ 
  protected void drawShape (GL2 gl)
  {
      gl.glBegin(GL2.GL_TRIANGLE_FAN);
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      gl.glVertex3f(0, height_2, 0);
      for (int i = 0; i <= res; i++) {
          float theta = (float)(i * 2.0 * Math.PI / res);
          float x = (float)(radius * Math.cos(theta));
          float z = (float)(radius * Math.sin(theta));

          gl.glNormal3f(x / radius, 0.5f, z / radius);
          gl.glVertex3f(x, -height_2, z);
      }
      gl.glEnd();

      //base of the cone
      gl.glBegin(GL2.GL_TRIANGLE_FAN);
      gl.glNormal3f(0.0f, -1.0f, 0.0f);
      gl.glVertex3f(0, -height_2, 0);
      for (int i = 0; i <= res; i++) {
          float theta = (float)(i * 2.0 * Math.PI / res);

          gl.glVertex3f((float)(radius * Math.cos(theta)), -height_2, (float)(radius * Math.sin(theta)));
      }

      gl.glEnd();
  }

  /** Sets the cone reference system.
    * @param ref Reference system position wrt the shape.
    */
  public void setReference (int ref)
  {
      switch (ref) {
          case REF_CENTER :
              refMatrix[14] = 0.0f;
          case REF_BASE :
              refMatrix[14] = height_2;
          case REF_TOP :
              refMatrix[14] = -height_2;
      case REF_CORNER :
               refMatrix[14] = 0.0f;
        break;
      default :
        break;
    }
  }

  /** Sets a new circular resolution for the cone.
   * @param resolution new resolution value.
   */
  public void setResolution (int resolution)
  {
    res = resolution;
  }
}
