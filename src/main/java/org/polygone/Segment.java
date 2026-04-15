// Trame du TP sur le traitement des polygones
// BUT Info 3 - 2025/2026
// Source: Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)
// Complete par X
package org.polygone;


/** Segment dans le plan */
public class Segment
{
  private Point[] pts = new Point[2];


  /** Cree un segment a patrir de ses extremites.
   * @param pt1 Extremite de depart
   * @param pt2 Extremite d'arrivee
   */
  public Segment (Point pt1, Point pt2)
  {
    pts[0] = pt1;
    pts[1] = pt2;
  }

  /** Convertit le segment en chaine de caracteres.
   */
  public String toString ()
  {
    return ("<" + pts[0] + "," + pts[1] + ">");
  }

  /** Retourne les coordonnees des extremites.
   */
  public double[] coordonnees ()
  {
    return (new double[] { pts[0].x (), pts[0].y (),
                           pts[1].x (), pts[1].y () });
  } 

  /** Retourne une des extremites du segment.
   */
  public Point extremite (int num)
  {
    return (pts[num]);
  } 

  /** Verifie si le point fourni est sur le segment.
   */
  public boolean contient (Point p)
  {
    double eps = Math.sqrt (pts[0].distance2 (pts[1])) * Point.EPS;
    return (((pts[1].x () - pts[0].x ()) * (p.x () - pts[0].x ())
             + (pts[1].y () - pts[0].y ()) * (p.y () - pts[0].y ()) > - eps)
            && ((pts[0].x () - pts[1].x ()) * (p.x () - pts[1].x ())
                + (pts[0].y () - pts[1].y ()) * (p.y () - pts[1].y ()) > - eps)
            && distance2 (p) < Point.EPS * Point.EPS);
  }

  /** Retourne le carre de la distance du point fourni a la droite support.
   */
  public double distance2 (Point p)
  {
    double pv = (pts[1].x () - pts[0].x ()) * (p.y () - pts[0].y ())
                - (pts[1].y () - pts[0].y ()) * (p.x () - pts[0].x ());
    return (pv * pv / pts[0].distance2 (pts[1]));
  }

  /** Retourne le projete du point fourni sur la droite support.
   */
  public Point projete (Point p)
  {
    double h = ((pts[1].x () - pts[0].x ()) * (p.x () - pts[0].x ())
                + (pts[1].y () - pts[0].y ()) * (p.y () - pts[0].y ()))
               / pts[0].distance2 (pts[1]);
    return (new Point (pts[0].x () + (pts[1].x () - pts[0].x ()) * h,
                       pts[0].y () + (pts[1].y () - pts[0].y ()) * h));
  } 

  /** Verifie si le segment en coupe un autre.
   * Teste une coupure stricte (extremites de this non comprises)
   * @param seg Autre segment.
   */
  public boolean coupe (Segment seg)
  {
      // Vecteur du segment courant (this)
      double dx1 = pts[1].x () - pts[0].x ();
      double dy1 = pts[1].y () - pts[0].y ();

      // Vecteur de l'autre segment (seg)
      double dx2 = seg.pts[1].x () - seg.pts[0].x ();
      double dy2 = seg.pts[1].y () - seg.pts[0].y ();

      // Determinant (produit en croix 2D)
      double det = dx1 * dy2 - dy1 * dx2;

      // Si le determinant est nul, les segments sont paralleles
      if (Math.abs (det) < Point.EPS) return (false);

      // Calcul des parametres t (pour this) et u (pour seg)
      // Equation: P = P1 + t * V1 = P2 + u * V2
      double dx3 = seg.pts[0].x () - pts[0].x ();
      double dy3 = seg.pts[0].y () - pts[0].y ();

      double t = (dx3 * dy2 - dy3 * dx2) / det;
      double u = (dx3 * dy1 - dy3 * dx1) / det;

      // Coupure stricte: t doit etre strictement entre 0 et 1 (extremites exclues)
      // On verifie egalement u pour s'assurer que l'intersection se trouve
      // a l'interieur du deuxieme segment.
      return (t > Point.EPS && t < 1.0 - Point.EPS &&
              u > Point.EPS && u < 1.0 - Point.EPS);
  }


  /** Retourne l'intersection du segment avec un autre segment.
   * Retourne null s'il n'y a pas d'intersection.
   * @param seg L'autre segment
   */



  public Point intersection (Segment seg)
  {
          double dx1 = pts[1].x () - pts[0].x ();
          double dy1 = pts[1].y () - pts[0].y ();

          double dx2 = seg.pts[1].x () - seg.pts[0].x ();
          double dy2 = seg.pts[1].y () - seg.pts[0].y ();

          double det = dx1 * dy2 - dy1 * dx2;

          // Segments paralleles : pas de point d'intersection unique
          if (Math.abs (det) < Point.EPS) return (null);

          double dx3 = seg.pts[0].x () - pts[0].x ();
          double dy3 = seg.pts[0].y () - pts[0].y ();

          double t = (dx3 * dy2 - dy3 * dx2) / det;
          double u = (dx3 * dy1 - dy3 * dx1) / det;

          // Verification si l'intersection a lieu DANS les segments (extremites comprises)
          // On utilise une marge EPS pour gerer les erreurs d'arrondi
          if (t >= -Point.EPS && t <= 1.0 + Point.EPS &&
                  u >= -Point.EPS && u <= 1.0 + Point.EPS)
          {
              // Calcul des coordonnees du point d'intersection
              return (new Point (pts[0].x () + t * dx1,
                      pts[0].y () + t * dy1));
          }

          return (null);
      }
  }

