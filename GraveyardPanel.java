package mtgSimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraveyardPanel extends JPanel implements MouseListener, Constants {
  
  private MagicTheGathering mtg;
  private ArrayList<Card> graveyard;
  private Card topCard;
  private JFrame graveyardFrame;
  private boolean graveyardFrameInDisplay;
  
  public GraveyardPanel(MagicTheGathering mtg) {
    this.mtg = mtg;
    this.graveyard = new ArrayList<Card>();
    this.topCard = null;
    this.graveyardFrameInDisplay = false;
    setPreferredSize(new Dimension(ZONE_PANEL_WIDTH,ZONE_PANEL_HEIGHT/2));
    addMouseListener(this);
    repaint();
  }
  
  // Returns all the cards in the graveyard.
  public ArrayList<Card> getGraveyard() {
    return this.graveyard;
  }
  
  // Sets the cards in the graveyard to the specified array list of cards.
  public void setGraveyard(ArrayList<Card> graveyard) {
    this.graveyard = graveyard;
  }
  
  // Updates the most recent card to the specified card.
  public void setTopCard(Card card) {
    this.topCard = card;
  }
  
  // The the paint component method defined in the Graphics class.
  public void paintComponent(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0,0,ZONE_PANEL_WIDTH, this.getHeight()); // Allow size change.
    if (this.topCard != null) {
      renderCard(topCard,g);
    }
  }
  
  // Renders a card.
  public void renderCard(Card c, Graphics g) {
    Color color = null;
    if (c.getColor() == COLOR.WHITE) color = Color.WHITE;
    if (c.getColor() == COLOR.BLUE)  color = Color.BLUE;
    if (c.getColor() == COLOR.BLACK) color = new Color(48,48,48);
    if (c.getColor() == COLOR.RED)   color = Color.RED;
    if (c.getColor() == COLOR.GREEN) color = new Color(0,192,0);
    if (c.getColor() == COLOR.GOLD)  color = Color.ORANGE;
    if (c.getColor() == null) {
      if (c.getType() == TYPE.ARTIFACT) {
        color = Color.GRAY;
      }
      if (c.getType() == TYPE.LAND) {
        color = new Color(255,176,112);
      }
    }
    g.setColor(color);
    g.fillRect((ZONE_PANEL_WIDTH - CARD_WIDTH)/2, (this.getHeight() - CARD_HEIGHT)/2, CARD_WIDTH, CARD_HEIGHT);
    if (c.getColor() != COLOR.BLACK) { // The card is non-black.
      g.setColor(Color.BLACK); 
    }
    else { // The card is black.
      g.setColor(Color.WHITE);
    }
    g.drawString(c.getCardName(),(ZONE_PANEL_WIDTH - CARD_WIDTH)/2 + 10, (this.getHeight() - CARD_HEIGHT)/2 + 20);
    int fontSize = 28;
    g.setFont(new Font("Serif",1,fontSize));
    if (topCard.getColor() != COLOR.WHITE) {
      g.setColor(Color.white);
    }
    else {
      g.setColor(Color.black);
    }
    Integer cardsInGraveyard = graveyard.size();
    int shift = 0;
    if (cardsInGraveyard < 10) {
      shift = fontSize/4;
    }
    g.drawString(cardsInGraveyard.toString(),(ZONE_PANEL_WIDTH/2) - (fontSize/2) + shift,
                 (this.getHeight()/2) + (fontSize/4));
  }
  
  public void mouseClicked(MouseEvent me) {
    if (graveyard.size() > 0) {
      if (me.isMetaDown()) {
        boolean condition1 = me.getX() > (ZONE_PANEL_WIDTH - CARD_WIDTH)/2;
        boolean condition2 = me.getX() < (ZONE_PANEL_WIDTH + CARD_WIDTH)/2;
        boolean condition3 = me.getY() > (this.getHeight() - CARD_HEIGHT)/2;
        boolean condition4 = me.getY() < (this.getHeight() + CARD_HEIGHT)/2;
        if (condition1 && condition2 && condition3 && condition4) {
          displayGraveyard();
        }
      }
    }
  }
  
  public void displayGraveyard() {
    if (!graveyardFrameInDisplay) {
      graveyardFrameInDisplay = true;
      graveyardFrame = new JFrame();
      graveyardFrame.add(new DisplayPanel());
      graveyardFrame.addWindowListener(new WindowAdaptor());
      graveyardFrame.setSize(150,500);
      graveyardFrame.setLocationRelativeTo(this);
      graveyardFrame.setResizable(false);
      graveyardFrame.setVisible(true);
    }
  }
  
  private class DisplayPanel extends JPanel {
    
    public void paintComponent(Graphics g) {
      g.setColor(Color.black);
      g.fillRect(0, 0, getWidth(), getHeight());
      int index = 0;
      for (Card c: graveyard) {
        Color color = null;
        if (c.getColor() == COLOR.WHITE) color = Color.WHITE;
        if (c.getColor() == COLOR.BLUE)  color = Color.BLUE;
        if (c.getColor() == COLOR.BLACK) color = new Color(48,48,48);
        if (c.getColor() == COLOR.RED)   color = Color.RED;
        if (c.getColor() == COLOR.GREEN) color = new Color(0,192,0);
        if (c.getColor() == COLOR.GOLD)  color = Color.ORANGE;
        if (c.getColor() == null) {
          if (c.getType() == TYPE.ARTIFACT) {
            color = Color.GRAY;
          }
          if (c.getType() == TYPE.LAND) {
            color = new Color(255,176,112);
          }
        }
        g.setColor(color);
        g.fillRect((getWidth() - CARD_WIDTH)/2, 10 + 25*index, CARD_WIDTH, CARD_HEIGHT);
        if (c.getColor() != COLOR.BLACK) { // The card is non-black.
          g.setColor(Color.BLACK); 
        }
        else { // The card is black.
          g.setColor(Color.WHITE);
        }
        g.drawString(c.getCardName(),(getWidth() - CARD_WIDTH)/2 + 10, 30 + 25*index);
        index++;
      }
    }
    
  }
  
  private class WindowAdaptor implements WindowListener {
      public void windowClosing(WindowEvent we)     { graveyardFrameInDisplay = false; }
      public void windowActivated(WindowEvent we)   {}
      public void windowClosed(WindowEvent we)      {}
      public void windowDeactivated(WindowEvent we) {}
      public void windowDeiconified(WindowEvent we) {}
      public void windowIconified(WindowEvent we)   {}
      public void windowOpened(WindowEvent we)      {}
  }
  
  // Unused methods
  public void mousePressed(MouseEvent me) {}
  public void mouseReleased(MouseEvent me) {}
  public void mouseEntered(MouseEvent me) {}
  public void mouseExited(MouseEvent me) {}
  
  
  
}