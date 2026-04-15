// Trame des exercices sur le traitement des polygones
// BUT Info 3 - 2025/2026
// Source: Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)
// Complete par X
package org.polygone;


/** Polygones dans le plan */
public class Polygone
{
  /** Caracteristiques possibles du polygone. */
  public final static int TRIANGLE = 0;
  public final static int CONVEXE = 1;
  public final static int MONOTONE_XY = 2;
  public final static int MONOTONE_X = 3;
  public final static int MONOTONE_Y = 4;
  public final static int SIMPLE = 5;      // sans auto-intersections
  public final static int INCOHERENT = 6;  // avec auto-intersections
  public final static int INCONNU = 7;
  /** Noms des caracteristiques. */
  public final static String[] NOM = {"TRIANGLE", "CONVEXE", "MONOTONE_XY",
                                      "MONOTONE_X", "MONOTONE_Y",
                                      "SIMPLE", "INCOHERENT", "INCONNU"};
  /** Sommets du polygone. */
  private Point[] pts = null;
  /** Type du polygone. */
  private int type = INCONNU;


  /** Cree un polygone a partir de sommets.
   * @param pts Tableau de sommets
   */
  public Polygone (Point[] pts)
  {
    this.pts = pts;
    classifier ();
  }

  /** Convertit le polygone en chaine de caracteres.
   */
  public String toString ()
  {
    String s = "[" + pts[0];
    for (int i = 1; i < pts.length; i++) s += "," + pts[i];
    return (s + "]");
  }

  /** Retourne les sommets du polygone.
   */
  public Point[] sommets ()
  {
    return (pts);
  }

  /** Retourne le sommet suivant le ieme sommet.
   */
  public Point suiv (int i)
  {
    return (pts[i == pts.length - 1 ? 0 : i + 1]);
  }

  /** Retourne le sommet precedent le ieme sommet.
   */
  public Point prec (int i)
  {
    return (pts[i == 0 ? pts.length - 1 : i - 1]);
  }

  /** Retourne le barycentre du polygone.
   */
  public Point barycentre ()
  {
    double x = 0., y = 0.;
    for (int i = 0; i < pts.length; i++)
    {
      x += pts[i].x ();
      y += pts[i].y ();
    }
    return (new Point (x / pts.length, y / pts.length));
  }

  /** Retourne les aretes du polygone.
   */
  public Segment[] aretes ()
  {
    Segment[] aretes = new Segment[pts.length];
    for (int i = 0; i < pts.length - 1; i ++)
      aretes[i] = new Segment (pts[i], pts[i + 1]);
    aretes[pts.length - 1] = new Segment (pts[pts.length - 1], pts[0]);
    return (aretes);
  }

  /** Retourne le type du polygone.
   */
  public int type ()
  {
    return (type);
  }

  /** Classifie le polygone.
   */
  public void classifier ()
  {
    if (triangle ()) type = TRIANGLE;
    else if (autosecant ()) type = INCOHERENT;
    else if (convexe ()) type = CONVEXE;
    else if (monotoneX ())
    {
      if (monotoneY ()) type = MONOTONE_XY;
      else type = MONOTONE_X;
    }
    else if (monotoneY ()) type = MONOTONE_Y;
    else type = SIMPLE;
  }

  /** Verifie si le polygone est un triangle.
   */
  public boolean triangle ()
  {
    return (false);
  }

  /** Verifie si le polygone comporte des auto-intersections.
   */
  public boolean autosecant ()
  {
    return (false);
  }

  /** Verifie si le polygone est horizontalement monotone.
   */
  public boolean monotoneX ()
  {
    return (false);
  }

  /** Verifie si le polygone est verticalement monotone.
   */
  public boolean monotoneY ()
  {
    return (false);
  }

  /** Verifie si le polygone est convexe.
   */
  public boolean convexe ()
  {
    return (false);
  }

  /** Verifie si un point appartient au polygone.
   * @param pt Point a tester
   */
  public boolean contient (Point pt)
  {
    return (false);
  }

  /** Verifie si le segment joignant deux sommets est une diagonale.
   * @param n1 Indice du sommet de depart
   * @param n2 Indice du sommet d'arrivee
   */
  public boolean diagonale (int n1, int n2)
  {
    return (true);
  }

  /** Verifie si un sommet est convexe.
   * @param n Indice du sommet a tester
   */
  public boolean convexe (int n)
  {
    return (true);
  }

  /** Retourne une triangulation a partir des oreilles du polygone.
   * @param depart Indice du sommet de depart de la recherche d'oreilles.
   */
  public Polygone[] oreilles (int depart)
  {
    return (new Polygone[0]);
  }

  /** Scinde le polygone en polygones convexes.
   */
  public Polygone[] convexes ()
  {
    return (new Polygone[0]);
  }
}
