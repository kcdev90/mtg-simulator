package mtgSimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HandPanel extends JPanel implements MouseListener, Constants {
  
  // Constrants
  private static final int HORIZONTAL_MARGIN = 30;
  private static final int VERTICAL_MARGIN   = (HAND_PANEL_HEIGHT - CARD_HEIGHT)/2;
  
  // Fields
  private MagicTheGathering mtg;
  private BufferedImage backgroundImage;
  private ArrayList<Card> cards;
  
  // Constructor method
  public HandPanel(MagicTheGathering mtg) {
    this.mtg = mtg;
    this.cards = new ArrayList<Card>();
    setPreferredSize(new Dimension(HAND_PANEL_WIDTH, HAND_PANEL_HEIGHT));
    setBackground(DEFAULT_BACKGROUND_URL);
    addMouseListener(this);
  }
  
  // Sets the image of the background.
  public void setBackground(String backgroundURL) {
    try {
      URL url = new URL(backgroundURL);
      backgroundImage = ImageIO.read(url);
    }
    catch (MalformedURLException mue) {
      JOptionPane.showMessageDialog(this,"Could not load image --- Bad URL!","Error",JOptionPane.ERROR_MESSAGE);
    }
    catch (IOException ioe) {
      JOptionPane.showMessageDialog(this,"Could not load image --- Unable to connect to the internet!"
                                      ,"Error",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  // Returns the cards in hand.
  public ArrayList<Card> getHand() {
    return this.cards;
  }
  
  // The painComponent method in the Graphics class. Necessary in order to render.
  public void paintComponent(Graphics g) {
    g.drawImage(backgroundImage,0,0,null); // Needs to be (0,0) to fill the entirety.
    int index = 0;
    for(Card c: this.cards) {
      c.setX(HORIZONTAL_MARGIN + index*mtg.getWidth()/cards.size()); // Allow risizing.
      c.setY(VERTICAL_MARGIN);
      renderCard(c,g);
      index++;
    }
  }
  
  // Renders the cards in hand.
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
    g.fillRect(c.getX(),c.getY(),CARD_WIDTH,CARD_HEIGHT);
    if (c.getColor() != COLOR.BLACK) { // The card is non-black.
      g.setColor(Color.BLACK); 
    }
    else { // The card is black.
      g.setColor(Color.WHITE);
    }
    g.drawString(c.getCardName(), c.getX()+10,c. getY()+20);
  }
  
  // Checks whether a card in hand has been selected. If so, remove it and add it to the battlefied.
  public void mouseClicked(MouseEvent me) {
    if (mtg.handKept()) {
      if (me.getClickCount() == 2) {
        Iterator<Card> it = cards.iterator();
        while (it.hasNext()){
          Card c = it.next();
          boolean condition1 = me.getX() >= c.getX();
          boolean condition2 = me.getX() <= c.getX() + CARD_WIDTH;
          boolean condition3 = me.getY() >= c.getY();
          boolean condition4 = me.getY() <= c.getY() + CARD_HEIGHT;
          if (condition1 && condition2 && condition3 && condition4) {
            it.remove();
            c.setX((mtg.getMainPanel().getWidth()  - CARD_WIDTH)/2);
            c.setY((mtg.getMainPanel().getHeight() - CARD_HEIGHT)/2);
            mtg.getMainPanel().getBattlefield().add(c);
            repaint();
            mtg.getMainPanel().repaint();
            mtg.notify(mtg.getPlayerName()+" played "+c.getCardName()+".");
          }
        }
      }
    }
  }
  
  // Unused methods
  public void mousePressed(MouseEvent me) {}
  public void mouseReleased(MouseEvent me) {}
  public void mouseEntered(MouseEvent me) {}
  public void mouseExited(MouseEvent me) {}
  
  
  
}