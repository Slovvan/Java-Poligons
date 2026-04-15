// Trame du TP sur le traitement des polygones
// BUT Info 3 - 2025/2026
// Source: Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)

package org.polygone;

import javax.swing.*;


/** Surface de trace pour le test de segment ou de polygone. */
public abstract class ExoView extends JPanel
{
  /** Change de sommet de depart. */
  public void next () { }

  /** Selectionne un sommet. */
  public abstract boolean select (int x, int y);

  /** Deplace une extremite. */
  public abstract void moveSelection (int x, int y);
}
