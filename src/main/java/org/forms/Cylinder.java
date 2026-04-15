package org.forms;// Trame du TP de modelisation 3D : primitives solides
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.GL2;


/** Cylinder shape */
public class Cylinder extends ComplientShape
{
  /** Radius */
  private float radius = DEFAULT_VALUE;
  /** Half height (Z) */
  private float height_2 = DEFAULT_VALUE;
  /** Circular resolution. */
  private int res = DEFAULT_RESOLUTION;


  /** Creates a cylinder with given parameters.
    * @param diameter Cylinder diameter
    * @param height Cylinder height
    * @param resolution Circular resolution
    */ 
  public Cylinder (float diameter, float height, int resolution)
  {
    radius = diameter / 2; 
    height_2 = height / 2; 
    setResolution (resolution);
  }

  /** Creates a cylinder with given parameters.
    * @param diameter Cylinder diameter
    * @param height Cylinder height
    */ 
  public Cylinder (float diameter, float height)
  {
    this (diameter, height, DEFAULT_RESOLUTION);
  }

  /** Creates a unit cylinder
    */ 
  public Cylinder ()
  {
    this (DEFAULT_VALUE, DEFAULT_VALUE);
  }

  /** Renders the cylinder shape centered.
    * @param gl GL2 context. 
    */
  protected void drawShape(GL2 gl) {
      //tube
      gl.glBegin(GL2.GL_TRIANGLE_STRIP);
      for (int i = 0; i <= res; i++) {
          float theta = (float) (i * 2.0 * Math.PI / res);
          float x = (float) (radius * Math.cos(theta));
          float y = (float) (radius * Math.sin(theta));
          gl.glNormal3f(x / radius, y / radius, 0.0f);
          gl.glVertex3f(x, y,  height_2);
          gl.glVertex3f(x, y, -height_2);
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

      //base of the cone
      gl.glBegin(GL2.GL_TRIANGLE_FAN);
      gl.glNormal3f(0.0f, -1.0f, 0.0f);
      gl.glVertex3f(0, -height_2, 0);
      for (int i = 0; i <= res; i++) {
          float theta = (float)(i * -2.0 * Math.PI / res);

          gl.glVertex3f((float)(radius * Math.cos(theta)), -height_2, (float)(radius * Math.sin(theta)));
      }

      gl.glEnd();
  }


  public void setReference (int ref)
  {
    switch (ref)
    {
      case REF_CENTER :
      case REF_BASE :
      case REF_TOP :
      case REF_CORNER :
        refMatrix[12] = 0.0f;
        refMatrix[13] = 0.0f;
        refMatrix[14] = 0.0f;
        break;
      default :
        break;
    }
  }

  /** Sets a new circular resolution for the cylinder.
   * @param resolution new resolution value.
   */
  public void setResolution (int resolution)
  {
    res = resolution;
  }
}
