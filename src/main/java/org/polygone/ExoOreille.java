// Trame du TP sur le traitement des polygones
// BUT Info 3 - 2025/2026
// Source: Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)
package org.polygone;

import java.awt.*;


/** Surface de trace pour tester la recherche d'oreilles. */
public class ExoOreille extends ExoView
{
  /** Nombre initial de sommets du polygone. */
  private final static int NB = 16;
  /** Distance d'interaction. */
  private final static int DIST2 = 100;
  /** Distance d'interaction. */
  private final static int RAD = 6;
  /** Frontieres de l'espace visible. */
  private final static int SIZE = 600;
  /** Couleur du segment en instance du service intersection. */
  private final static Color COL_INST = Color.YELLOW;
  /** Couleur des sommets. */
  private final static Color COL_PARAM = Color.GREEN;
  /** Couleur du point d'intersection. */
  private final static Color COL_PTINTER = Color.BLUE;
  /** Couleur du fond d'ecran. */
  private final static Color COL_BACK = Color.BLACK;

  /** Polygone */
  private Polygone pol = null;
  /** Sommet de depart de la recherche des orteils */
  private int depart = 0;
  /** Selection */
  private int sel = -1;


  /** Cree une surface de trace.
   */
  public ExoOreille ()
  {
    Point[] pts = new Point[NB];
    for (int i = 0; i < NB; i ++)
      pts[i] = new Point (
                 (int) (200. * Math.cos (2 * i * Math.PI / NB) + 300.5),
                 (int) (200. * Math.sin (2 * i * Math.PI / NB) + 300.5));
    pol = new Polygone (pts);
  }

  /** Change de sommet de depart.
   */
  public void next ()
  {
    if (depart == pol.sommets().length - 1) depart = 0;
    else depart ++;
  }

  /** Trace les segments.
   */
  public void paint (Graphics g)
  {
    g.setColor (COL_BACK);
    g.fillRect (0, 0, SIZE, SIZE);
    g.setColor (COL_PTINTER);
    Polygone[] oreil = pol.oreilles (depart);
    for (int i = 0; i < oreil.length; i++)
    {
      Segment[] aretes = oreil[i].aretes ();
      for (int j = 0; j < aretes.length; j++)
      {
        double[] coords = aretes[j].coordonnees ();
        g.drawLine ((int) (coords[0] + 0.5),
                    SIZE - 1 - (int) (coords[1] + 0.5),
                    (int) (coords[2] + 0.5),
                    SIZE - 1 - (int) (coords[3] + 0.5));
      }
    }
    g.setColor (COL_INST);
    Point[] pts = pol.sommets ();
    for (int i = 1; i < pts.length; i ++)
      g.drawLine ((int) (pts[i-1].x () + 0.5),
                  SIZE - 1 - (int) (pts[i-1].y () + 0.5),
                  (int) (pts[i].x () + 0.5),
                  SIZE - 1 - (int) (pts[i].y () + 0.5));
    g.drawLine ((int) (pts[pts.length-1].x () + 0.5),
                SIZE - 1 - (int) (pts[pts.length-1].y () + 0.5),
                (int) (pts[0].x () + 0.5),
                SIZE - 1 - (int) (pts[0].y () + 0.5));
    g.setColor (COL_PARAM);
    for (int i = 0; i < pts.length; i ++)
      g.fillOval ((int) (pts[i].x () + 0.5) - RAD / 2,
                  SIZE - 1 - (int) (pts[i].y () + 0.5) - RAD / 2, RAD, RAD);
    g.setColor (COL_PTINTER);
    g.fillOval ((int) (pts[depart].x () + 0.5) - RAD,
                SIZE - 1 - (int) (pts[depart].y () + 0.5) - RAD,
                RAD * 2, RAD * 2);
    g.setColor (COL_PTINTER);
    for (int i = 0; i < oreil.length; i++)
    {
      Point centre = oreil[i].barycentre ();
      g.fillOval ((int) (centre.x () + 0.5) - RAD / 2,
                  SIZE - 1 - (int) (centre.y () + 0.5) - RAD / 2, RAD, RAD);
    }
  }

  /** Selectionne un sommet.
   */
  public boolean select (int x, int y)
  {
    Point impact = new Point (x, SIZE - 1 - y);
    sel = -1;
    for (int i = 0; i < pol.sommets().length; i++)
      if (impact.distance2 (pol.sommets()[i]) < DIST2)
      {
        sel = i;
        return (true);
      }
    return (false);
  }

  /** Deplace une extremite.
   */
  public void moveSelection (int x, int y)
  {
    if (sel != -1)
    {
      Point sauve = new Point (pol.sommets()[sel]);
      Point impact = new Point (x, SIZE - 1 - y);
      for (int i = 0; i < pol.sommets().length; i++)
        if (i != sel && impact.distance2 (pol.sommets()[i]) < DIST2) return;
      Segment seg = new Segment (pol.prec (sel), pol.suiv (sel));
      if (seg.distance2 (impact) < DIST2)
      {
        impact = seg.projete (impact);
        if (impact.distance2 (pol.prec (sel)) < DIST2
            || impact.distance2 (pol.suiv (sel)) < DIST2) return;
        else pol.sommets()[sel].set (impact);
      }
      else pol.sommets()[sel].set (impact);
      if (pol.autosecant ()) pol.sommets()[sel].set (sauve);
    }
  }
}
