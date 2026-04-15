package org.volumes;// Trame du TP de modelisation 3D : primitives solides
// BUT Info 3 - 2025/2026
// Source: Ph. Even

import com.jogamp.opengl.GL2;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


/** Colored cone primitive */
public class ColorCone extends ColorShape
{
  /** Renders the cone primitive.
    * @param gl GL2 context. 
    */ 
  public void draw (GL2 gl)
  {
      float radius = 10f;
      float halfLength = 5f;
      int slices = 50;
      for(int i=0; i<slices; i++) {
          float nextTheta = (float) ((i + 1) * 2.0 * Math.PI / slices);
          float theta = (float) (i * 2.0 * Math.PI / slices);
          gl.glBegin(GL2.GL_TRIANGLE_STRIP);
          gl.glVertex3f(0.0F, halfLength, 0.0f);
          gl.glVertex3f((float) (radius * cos(theta)), halfLength, (float) (radius * sin(theta)));
          gl.glVertex3f((float) (radius * cos(nextTheta)), halfLength, (float) (radius * sin(nextTheta)));
          gl.glVertex3f(0.0f, -halfLength, 0.0f);
          gl.glEnd();
      }
  }
}
