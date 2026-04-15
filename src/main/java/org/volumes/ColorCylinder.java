package org.volumes;// Trame du TP de modelisation 3D : primitives solides
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.GL2;


/** Colored cylinder primitive */
public class ColorCylinder extends ColorShape
{

  /** Renders the cylinder primitive.
    * @param gl GL2 context. 
    */


  public void draw (GL2 gl)
  {

      float radius = 1.0f;
      float halfLength = 1.5f;
      int slices = 24;

      gl.glBegin(GL2.GL_TRIANGLE_STRIP);

      for (int i = 0; i <= slices; i++) {

          float theta = (float)(i * 2.0 * Math.PI / slices);

          float x = (float)(radius * Math.cos(theta));
          float z = (float)(radius * Math.sin(theta));
          gl.glVertex3f(x,  halfLength, z);
          gl.glVertex3f(x, -halfLength, z);
      }

      gl.glEnd();

  }
}
