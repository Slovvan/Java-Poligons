package org.polygone;// Trame du TP sur le traitement des polygones
// BUT Info 3 - 2025/2026
// Source: Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



/** Fenetre principale de l'exercice */
public class Exo
{
  private static final long serialVersionUID = 1L;

  /** Cree une fenetre Swing. */
  public static void main (String[] args)
  {
    if (args.length == 0)
    {
      System.out.println ("Usage : java Exo <num>");
      System.exit (0);
    }

    // Creation de la vue de test
    JFrame frame = null;
    ExoView myView = null;
    if (args[0].equals ("1"))
    {
      frame = new JFrame ("Test d'intersections : jaune.intersection (bleu)");
      myView = new ExoInterSeg ();
    }
    else if (args[0].equals ("2"))
    {
      frame = new JFrame ("Test de convexite");
      myView = new ExoConvex ();
    }
    else if (args[0].equals ("3"))
    {
      frame = new JFrame ("Test de l'interieur");
      myView = new ExoPointIn ();
    }
    else if (args[0].equals ("4"))
    {
      frame = new JFrame ("Test de diagonales");
      myView = new ExoDiag ();
    }
    else if (args[0].equals ("5"))
    {
      frame = new JFrame ("Test de caracterisation");
      myView = new ExoType (frame);
    }
    else if (args[0].equals ("6"))
    {
      frame = new JFrame ("Test des oreilles");
      myView = new ExoOreille ();
    }
    else if (args[0].equals ("7"))
    {
      frame = new JFrame ("Test de convexification");
      myView = new ExoCvxPol ();
    }
    else
    {
      System.out.println ("Entrer un numero de 1 a 7");
      System.exit (0);
    }

    // Ajout d'un controleur
    ExoController myCtrl = new ExoController (myView);
    frame.addKeyListener (myCtrl);
    myView.addMouseListener (myCtrl);
    myView.addMouseMotionListener (myCtrl);

    // Comportement de ferbmeture de la fenetre
    frame.addWindowListener (
      new WindowAdapter ()
      {
        public void windowClosing (WindowEvent e)
        {
          System.exit (0);
        }
      });

    // Fin de la specification de la fenetre
    frame.add (myView);
    frame.setSize (600, 600);
    frame.setLocation (0, 0);
    frame.setBackground (Color.white);
    frame.setVisible (true);
  }
}
