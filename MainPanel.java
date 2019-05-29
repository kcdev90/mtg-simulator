package mtgSimulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;

// The battlefield allows dragging of cards.
public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, Constants {
    
  // Fields
  private MagicTheGathering mtg;
  private JFrame actionMenuFrame;
  private BufferedImage playMatImage;
  private ArrayList<Card> battlefield;
  private Card cardSelected;
  private boolean dragging;
  private boolean actionMenuInDisplay;
  private int startX;
  private int startY;
  private int endX;
  private int endY;
  private int dx;
  private int dy;
  
  // Constructor method
  public MainPanel(MagicTheGathering mtg) {
    this.mtg = mtg;
    this.actionMenuFrame = null;
    this.battlefield = new ArrayList<Card>();
    this.cardSelected = null;
    this.dragging = false;
    this.actionMenuInDisplay = false;
    this.startX = 0;
    this.startY = 0;
    this.endX   = 0;
    this.endY   = 0;
    this.dx     = 0;
    this.dy     = 0;
    setPlayMat(DEFAULT_PLAYMAT_URL);
    setPreferredSize(new Dimension(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT));
    addMouseListener(this);
    addMouseMotionListener(this);
  }
  
  // Returns all the cards contained in the mainPanel.
  public ArrayList<Card> getBattlefield() {
    return this.battlefield;
  }
  
  // Sets the image of the background.
  public void setPlayMat(String playMatURL) {
    try {
      URL url = new URL(playMatURL);
      playMatImage = ImageIO.read(url);
    }
    catch (MalformedURLException mue) {
      JOptionPane.showMessageDialog(this,"Could not load image --- Bad URL!","Error",JOptionPane.ERROR_MESSAGE);
    }
    catch (IOException e) {
      JOptionPane.showMessageDialog(this,"Could not load image --- Bad URL!","Error",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  // The painComponent method defined in the Graphics class.
  public void paintComponent(Graphics g) {
    g.drawImage(playMatImage,0,0,null); // Draws the background.
    for(Card c: battlefield) {
      renderCard(c,g);
    }
    if (dragging) {
      g.setColor(new Color(32,32,255,104));
      int width  = endX - startX;
      int height = endY - startY;
      if (endX > startX && endY > startY) {
        g.fillRect(startX,startY,width,height);
      }
      if (endX < startX && endY > startY) {
        g.fillRect( endX,startY,-width,height);
      }
      if (endX < startX && endY < startY) {
        g.fillRect( endX, endY,-width,-height);
      }
      if (endX > startX && endY < startY) {
        g.fillRect(startX, endY,width,-height);
      }
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
    if (!c.isTapped()) { // Untapped
      g.fillRect(c.getX(),c.getY(),CARD_WIDTH,CARD_HEIGHT);
      if (c.isHighlighted()) {
        g.setColor(Color.YELLOW);
        g.drawRect(c.getX(),c.getY(),CARD_WIDTH,CARD_HEIGHT);
        g.drawRect(c.getX() - 1, c.getY() - 1, CARD_WIDTH + 2, CARD_HEIGHT + 2);
      }
      if (c.getColor() != COLOR.BLACK) { // The card is non-black.
        g.setColor(Color.BLACK); 
      }
      else { // The card is black.
        g.setColor(Color.WHITE);
      }
      g.drawString(c.getCardName(), c.getX()+10, c.getY()+20);
    }
    else {
      int delta = (CARD_HEIGHT - CARD_WIDTH)/2;
      g.fillRect(c.getX() - delta, c.getY() + delta, CARD_HEIGHT, CARD_WIDTH); // Invert X and Y.
      if (c.isHighlighted()) {
        g.setColor(Color.YELLOW);
        g.drawRect(c.getX() - delta, c.getY() + delta, CARD_HEIGHT, CARD_WIDTH);
        g.drawRect(c.getX() - delta - 1, c.getY() + delta - 1, CARD_HEIGHT + 2, CARD_WIDTH + 2);
      }
      if (c.getColor() != COLOR.BLACK) { // The card is non-black.
        g.setColor(Color.BLACK); 
      }
      else { // The card is black.
        g.setColor(Color.WHITE);
      }
      g.drawString(c.getCardName(), c.getX() - 10, c.getY() + 50);
    }
  }
  
  // Highlight if clicked once. Tap if clicked twice. Display menu if hightlighted and right-clicked.
  public void mouseClicked (MouseEvent me) {
    cardSelected = null;
    for (Card c: battlefield) { // Check if the cursur is over any card in the mainPanel.
      boolean condition1 = me.getX() >= c.getX();
      boolean condition2 = me.getX() <= c.getX() + CARD_WIDTH;
      boolean condition3 = me.getY() >= c.getY();
      boolean condition4 = me.getY() <= c.getY() + CARD_HEIGHT;
      if (condition1 && condition2 && condition3 && condition4) { // Some card has been selected.
        this.cardSelected = c; // Update the selected card.
        this.dx = me.getX() - cardSelected.getX();
        this.dy = me.getY() - cardSelected.getY();
      }
    }
    if (me.getClickCount() == 1) {
      if (!me.isMetaDown()) {
        if (cardSelected != null) {
          if (!cardSelected.isHighlighted()) {
            cardSelected.setHighlighted(true);
          }
          else { // The card is already highlighted.
            cardSelected.setHighlighted(false);
          }
        }
        else {
          for (Card card: battlefield) {
            card.setHighlighted(false); // Unhighlight all cards.
          }
        }
      }
      else { // Right clicked.
        displayActionMenu(me);
        if (actionMenuInDisplay) {
          actionMenuFrame.requestFocus();
          actionMenuFrame.setLocation(me.getXOnScreen()-50, me.getYOnScreen()-50);
        }
      }
    }
    else if (me.getClickCount() == 2) {
      if (cardSelected != null) { // Some card has been selected.
        if (cardSelected.getType() != TYPE.ENCHANTMENT) {
          cardSelected.setHighlighted(true);
          for (Card card: battlefield) {
            if(card.isHighlighted()) {
              if (!card.isTapped()) {
                card.setTapped(true);
                card.setHighlighted(!cardSelected.isHighlighted()); // Necessary to flip state.
              }
              else {
                card.setTapped(false);
                card.setHighlighted(!cardSelected.isHighlighted()); // Necessary to flip state.
              }
            }
          }
        }
        else {
          battlefield.remove(cardSelected);
          mtg.getGraveyardPanel().getGraveyard().add(cardSelected);
          mtg.getGraveyardPanel().setTopCard(cardSelected);
          repaint();
          mtg.getGraveyardPanel().repaint();
          mtg.notify(mtg.getPlayerName()+" moved "+cardSelected.getCardName()+" to "
                     +mtg.getGenderPronoun()+" graveyard.");
        }
      }
    }
  }

  // Check to see if a card has been selected. If so, update the cardSelected.
  public void mousePressed(MouseEvent me) {
    cardSelected = null;
    for (Card c: battlefield) { // Check if the cursur is over any card in the mainPanel.
      boolean condition1 = me.getX() >= c.getX();
      boolean condition2 = me.getX() <= c.getX() + CARD_WIDTH;
      boolean condition3 = me.getY() >= c.getY();
      boolean condition4 = me.getY() <= c.getY() + CARD_HEIGHT;
      if (condition1 && condition2 && condition3 && condition4) { // Some card has been selected.
        cardSelected = c; // Update the selected card.
        dx = me.getX() - cardSelected.getX();
        dy = me.getY() - cardSelected.getY();
      }
    }
    if (cardSelected == null) {
      dragging = true;
      startX = me.getX();
      startY = me.getY();
    }
  }
  
  // Update the position of the card.
  public void mouseDragged(MouseEvent me) {
    if (cardSelected != null) { // Some card is selected.
      cardSelected.setX(me.getX() - dx); // Fix the x-coordinate of dragging.
      cardSelected.setY(me.getY() - dy); // Fix the y-coordinate of dragging.
    }
    else { // No card is being selected.
      endX = me.getX();
      endY = me.getY();
      checkHighlightedCards(me);
    }
    repaint();
  }
  
  // Unselect the card.
  public void mouseReleased(MouseEvent me) {
    cardSelected = null;
    dragging = false;
    startX  = 0;
    startY  = 0;
    endX = 0;
    endY = 0;
    dx = 0;
    dy = 0;
    repaint();
  }
  
  // Highlight any cards if they tough the square caused by being dragged.
  public void checkHighlightedCards(MouseEvent me) {
    int dsx = Math.abs(me.getX() - startX);
    int dsy = Math.abs(me.getY() - startY);
    int dcx = CARD_WIDTH;
    int dcy = CARD_HEIGHT;
    int sx = 0; // The X coordinate of the top-left corner of the square 
    int sy = 0; // The Y coordinate of the top-left corner of the square
    if (me.getX() < startX) {
      sx = getX();
    }
    else {
      sx = startX;
    }
    if (me.getY() < startY) {
      sy = me.getY();
    }
    else {
      sy = startY;
    }
    for (Card c: battlefield) {
      int cx = c.getX(); // The X coordinate of the top-left corner of the card
      int cy = c.getY(); // The Y coordinate of the top-left corner of the card
      if (cx < sx + dsx && cx + dcx > sx && cy < sy + dsy && cy + dcy > sy) {
        c.setHighlighted(true);
      }
    }
  }
  
  // Displays the action menu, relative to the absolute location of the cursor.
  public void displayActionMenu(MouseEvent me) {
    
    if (!actionMenuInDisplay) { // The action menu is not already being displayed.
      actionMenuInDisplay = true;
      // Initialize the components.
      JLabel label = new JLabel("Select Action");
      JButton graveyardButton = new JButton("Move to Graveyard");
      graveyardButton.addActionListener(new ActionAdaptor(ZONE.GRAVEYARD));
      JButton handButton = new JButton("Move to Hand");
      handButton.addActionListener(new ActionAdaptor(ZONE.HAND));
      JButton topButton = new JButton("Move to Top of Library");
      topButton.addActionListener(new ActionAdaptor(ZONE.TOP));
      JButton bottomButton = new JButton("Move to Bottom of Library");
      bottomButton.addActionListener(new ActionAdaptor(ZONE.BOTTOM));
      JButton removeButton = new JButton("Remove from Game");
      removeButton.addActionListener(new ActionAdaptor(ZONE.REMOVE));
      actionMenuFrame = new JFrame("Action Menu");
      actionMenuFrame.setLayout(new BoxLayout(actionMenuFrame.getContentPane(), BoxLayout.Y_AXIS));
      actionMenuFrame.add(label);
      actionMenuFrame.add(graveyardButton);
      actionMenuFrame.add(handButton);
      actionMenuFrame.add(topButton);
      actionMenuFrame.add(bottomButton);
      actionMenuFrame.add(removeButton);
      actionMenuFrame.addWindowListener(new WindowAdaptor());
      actionMenuFrame.requestFocus();
      actionMenuFrame.setLocation(me.getXOnScreen()-50, me.getYOnScreen()-50); // Methods getX() getY() don't work.
      actionMenuFrame.setResizable(false);
      actionMenuFrame.setVisible(true);
      actionMenuFrame.pack();
    }
    
  }
  
  // The action listener class for the action menu.
  private class ActionAdaptor implements ActionListener {
    
    ZONE zone;
    
    private ActionAdaptor(ZONE zone) {
      this.zone = zone;
    }
    
    public void actionPerformed(ActionEvent ae ) {
      Iterator<Card> it = battlefield.iterator();
       while (it.hasNext()) {
         Card c = it.next();
         if (c.isHighlighted()) {
           if (c.isTapped()) {
             c.setTapped(false); // Necessary to untap.
           }
           if (c.isHighlighted()) {
             c.setHighlighted(false); // Necessary to unhighlight.
           }
           it.remove();
           repaint();
           if (!(c.getType() == TYPE.CREATURE && c.getManaCost() == null)) { // Not a token
             if (zone == ZONE.GRAVEYARD) {
               mtg.getGraveyardPanel().getGraveyard().add(c);
               mtg.getGraveyardPanel().setTopCard(c);
               mtg.getGraveyardPanel().repaint();
               mtg.notify(mtg.getPlayerName()+" moved "+c.getCardName()+" to "+mtg.getGenderPronoun()+" graveyard.");
             }
             else if (zone == ZONE.HAND) {
               mtg.getHandPanel().getHand().add(c);
               mtg.getHandPanel().repaint();
               mtg.notify(mtg.getPlayerName()+" moved "+c.getCardName()+" to "+mtg.getGenderPronoun()+" hand.");
             }
             else if (zone == ZONE.TOP) {
               mtg.getLibraryPanel().getMainBoard().add(0,c);
               mtg.getLibraryPanel().repaint();
               mtg.notify(mtg.getPlayerName()+" moved "+c.getCardName()
                            +" to the top of "+mtg.getGenderPronoun()+" library.");
             }
             else if (zone == ZONE.BOTTOM) {
               mtg.getLibraryPanel().getMainBoard().add(c);
               mtg.getLibraryPanel().repaint();
               mtg.notify(mtg.getPlayerName()+" moved "+c.getCardName()
                            +" to the bottom of "+mtg.getGenderPronoun()+" library.");
             }
             else if (zone == ZONE.REMOVE) {
               mtg.notify(mtg.getPlayerName()+" removed "+c.getCardName()+" from the game.");
             }
           }
           else { // "c" is a token.
             mtg.notify(mtg.getPlayerName()+" destroyed the "+c.getCardName()+" token.");
           }
         }
       }
       actionMenuFrame.setVisible(false);
       actionMenuInDisplay = false;
    }
    
  }
  
  // Notify the panel that the inquiry frame is no longer in display.
  private class WindowAdaptor implements WindowListener {
    public void windowClosing(WindowEvent we)     { actionMenuInDisplay = false; }
    public void windowActivated(WindowEvent we)   {}
    public void windowClosed(WindowEvent we)      {}
    public void windowDeactivated(WindowEvent we) {}
    public void windowDeiconified(WindowEvent we) {}
    public void windowIconified(WindowEvent we)   {}
    public void windowOpened(WindowEvent we)      {} 
  }
  
  // Unused methods.
  public void mouseEntered (MouseEvent me) {}
  public void mouseExited  (MouseEvent me) {}
  public void mouseMoved   (MouseEvent me) {}
  
  
  
}
