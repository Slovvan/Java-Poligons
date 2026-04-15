package org.forms;// Trame du TP de modelisation 3D : primitives solides
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.GL2;


/** Milestone shape */
public class Milestone extends ComplientShape
{
  /** Half depth (Y) */
  private float depth_2 = DEFAULT_VALUE;
  /** Half width (X) */
  private float width_2 = DEFAULT_VALUE;
  /** Half height (Z) */
  private float height_2 = DEFAULT_VALUE;
  /** Circular resolution. */
  private int res = DEFAULT_RESOLUTION;


  /** Creates a milestone with given parameters.
    * @param depth Milestone depth
    * @param width Milestone width
    * @param height Milestone height
    * @param resolution Circular resolution
    */ 
  public Milestone (float depth, float width, float height, int resolution)
  {
    depth_2 = depth / 2; 
    width_2 = width / 2; 
    height_2 = height / 2; 
    setResolution (resolution);
  }

  /** Creates a milestone with given parameters.
    * @param depth Milestone depth
    * @param width Milestone width
    * @param height Milestone height
    */ 
  public Milestone (float depth, float width, float height)
  {
    this (depth, width, height, DEFAULT_RESOLUTION);
  }

  /** Creates a unit milestone
    */ 
  public Milestone ()
  {
    this (DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE);
  }

  /** Renders the milestone shape centered.
    * @param gl GL2 context. 
    */ 
  protected void drawShape (GL2 gl)
  {
      gl.glBegin(GL2.GL_QUADS);
      gl.glNormal3f(0.0f, 0.0f, -1.0f);
      gl.glVertex3f(-width_2, -depth_2, -height_2);
      gl.glVertex3f( width_2, -depth_2, -height_2);
      gl.glVertex3f( width_2,  depth_2, -height_2);
      gl.glVertex3f(-width_2,  depth_2, -height_2);

      gl.glNormal3f(0, -1, 0); // Front
      gl.glVertex3f(-width_2, -depth_2, -height_2);
      gl.glVertex3f( width_2, -depth_2, -height_2);
      gl.glVertex3f( width_2, -depth_2, 0);
      gl.glVertex3f(-width_2, -depth_2, 0);

      gl.glNormal3f(0, 1, 0); // Back
      gl.glVertex3f(-width_2,  depth_2, -height_2);
      gl.glVertex3f( width_2,  depth_2, -height_2);
      gl.glVertex3f( width_2,  depth_2, 0);
      gl.glVertex3f(-width_2,  depth_2, 0);

      gl.glNormal3f(-1, 0, 0); // Left
      gl.glVertex3f(-width_2, -depth_2, -height_2);
      gl.glVertex3f(-width_2,  depth_2, -height_2);
      gl.glVertex3f(-width_2,  depth_2, 0);
      gl.glVertex3f(-width_2, -depth_2, 0);

      gl.glNormal3f(1, 0, 0); // Right
      gl.glVertex3f( width_2, -depth_2, -height_2);
      gl.glVertex3f( width_2,  depth_2, -height_2);
      gl.glVertex3f( width_2,  depth_2, 0);
      gl.glVertex3f( width_2, -depth_2, 0);
      gl.glEnd();

        //curved arch
      gl.glBegin(GL2.GL_TRIANGLE_STRIP);
      for (int i = 0; i <= res; i++) {
          float theta = (float) (i * Math.PI / res);
          float x = (float) (width_2 * Math.cos(theta));
          float z = (float) (height_2 * Math.sin(theta));

          gl.glNormal3f(x / width_2, 0, z / height_2);
          gl.glVertex3f(x, -depth_2, z);
          gl.glVertex3f(x,  depth_2, z);
      }
      gl.glEnd();


      //the back and front faces
      gl.glBegin(GL2.GL_TRIANGLE_FAN);
      gl.glNormal3f(0, -1, 0);
      gl.glVertex3f(0, -depth_2, 0);
      for (int i = 0; i <= res; i++) {
          float theta = (float) (i * Math.PI / res);
          gl.glVertex3f((float)(width_2 * Math.cos(theta)), -depth_2, (float)(height_2 * Math.sin(theta)));
      }
      gl.glEnd();
      gl.glBegin(GL2.GL_TRIANGLE_FAN);
      gl.glNormal3f(0, 1, 0);
      gl.glVertex3f(0, depth_2, 0);
      for (int i = res; i >= 0; i--) {
          float theta = (float) (i * Math.PI / res);
          gl.glVertex3f((float)(width_2 * Math.cos(theta)), depth_2, (float)(height_2 * Math.sin(theta)));
      }
      gl.glEnd();
  }

  /** Sets the milestone reference system.
    * @param ref Reference system position wrt the shape.
    */
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

  /** Sets a new circular resolution for the milestone.
   * @param resolution new resolution value.
   */
  public void setResolution (int resolution)
  {
    res = resolution;
  }
}
