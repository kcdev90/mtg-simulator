package mtgSimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

public class LibraryPanel extends JPanel implements MouseListener, Constants {
  
  // Constatns
  private static final int MINIMUM_DECK_SIZE = 60;
  private static final int SIDEBOARD_SIZE = 15;
  private static final int THRESHOLD = 10; // The number of cards in the library at which it gets dangerours.
  private enum BOARD {MAIN,SIDE}
  
  // Fields
  private MagicTheGathering mtg;
  private BufferedImage cardImage;
  private ArrayList<Card> mainBoard;
  private ArrayList<Card> sideBoard;
  private String[] availableDecks;
  private String deckName;
  
  // The contructor method
  public LibraryPanel(MagicTheGathering mtg) {
    this.mtg = mtg;
    initFields();
    initImage();
    setPreferredSize(new Dimension(ZONE_PANEL_WIDTH,ZONE_PANEL_HEIGHT/2));
    addMouseListener(this);
    repaint(); // Must be done last.
  }
  
  public void initFields() {
    this.mainBoard = new ArrayList<Card>();
    this.sideBoard = new ArrayList<Card>();
    this.availableDecks = new String[]{"Junk Rites","Bant Auras","Jund Midrange"
      ,"Esper Control","Naya Midrange","Aristocrats","American Midrange"};
  }
  
  // Initializes the image (back of a Magic card).
  public void initImage() {
    try {
      URL url = new URL(CARD_IMAGE_URL);
      cardImage = ImageIO.read(url);
    }
    catch (MalformedURLException mue) {
      JOptionPane.showMessageDialog(this,"Could not load image --- Bad URL!","Error",JOptionPane.ERROR_MESSAGE);
    }
    catch (IOException ioe) {
      JOptionPane.showMessageDialog(this,"Could not load image --- Unable to connect to the internet!"
                                      ,"Error",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  // Returns the name of the deck.
  public String getDeckName() {
    return this.deckName;
  }
  
  // Sets the name of the deck.
  public void setDeckName(String deckName) {
    this.deckName = deckName;
  }
  
  // Returns an array of all available decks.
  public String[] getAvailableDecks() {
    return availableDecks;
  }
  
  // Returns the main board.
  public ArrayList<Card> getMainBoard() {
    return this.mainBoard;
  }
  
  // Returns the side board.
  public ArrayList<Card> getSideBoard() {
    return this.sideBoard;
  }
  
  // Sets the main board to the specified ArrayList<Card>.
  public void setMainBoard(ArrayList<Card> mainBoard) {
    this.mainBoard = mainBoard;
  }
  
   // Sets the side board to the specified ArrayList<Card>.
  public void setSideBoard(ArrayList<Card> sideBoard) {
    this.sideBoard = sideBoard;
  }
  
  // Checks whether the available decks contains a deck with the given name.
  public boolean containsDeck(String deckName) {
    for (int i = 0;  i < availableDecks.length; i++) {
      if (deckName.equalsIgnoreCase(availableDecks[i])) {
        return true;
      }
    }
    return false;
  }
  
  // Tries to upload a deck. Returns true if a deck was successfully loaded.
  public void loadDeck(String deckName) {
    for(int i=0; i < availableDecks.length;i++){
      if (deckName.equalsIgnoreCase(availableDecks[i])) {
        this.deckName = availableDecks[i];
      }
    }
    if (deckName.equalsIgnoreCase("Junk Rites")) {
      initJunkRites();
    }
    if (deckName.equalsIgnoreCase("Bant Auras")) { 
      initBantAuras();
    }
    if (deckName.equals("Jund Midrange")) {
      //initJundMidrange();
    }
    if (deckName.equals("Naya Midrange")) {
      //initNayaMidrange();
    }
    if (deckName.equals("Esper Control")) {
      //initEsperControl();
    }
    if (deckName.equals("Aristocrats")) {
      //initAristocrats();
    }
    if (deckName.equals("American Midrange")) {
      //initAmericanMidrange();
    }
    checkLegality();
  }
  
  // Checks if the decks is legal.
  public void checkLegality() {
    if (this.deckName != null && !mtg.getButtonPanel().getDrawButton().isEnabled()) {
      if (mainBoard.size() < MINIMUM_DECK_SIZE) {
        mtg.notify("              -----Load Failed-----"+"\n"
                                      +"Not enough cards in the deck."+"\n");
        mainBoard.clear();
      }
      if (!(sideBoard.size() == 0 || sideBoard.size() == SIDEBOARD_SIZE)) {
        mtg.notify("              -----Load Failed-----"+"\n"
                                      +"The sideboard should contain Exactly 0 or 15 cards."+"\n");
        sideBoard.clear();
      }
    }
  }
  
   // Adds a specified number of creature cards to the desired board. 
  public void add(int copies, String cardName, String manaCost, COLOR color,
                  int power, int toughness, BOARD board) {
    if (board == BOARD.MAIN){
      for (int i = 0; i < copies; i++) {
        mainBoard.add(new Card(cardName,manaCost,color,power,toughness));
      }
    }
    if (board == BOARD.SIDE){
      for (int i = 0; i < copies; i++) {
        sideBoard.add(new Card(cardName,manaCost,color,power,toughness));
      }
    }
  }
  
  // Adds a specified number of planeswalker cards to the desired board. 
  public void add(int copies, String cardName, String manaCost, COLOR color, int loyalty, BOARD board) {
    if (board == BOARD.MAIN){
      for (int i = 0; i < copies; i++) {
        mainBoard.add(new Card(cardName,manaCost,color,loyalty));
      }
    }
    if (board == BOARD.SIDE){
      for (int i = 0; i < copies; i++) {
        sideBoard.add(new Card(cardName,manaCost,color,loyalty));
      }
    }
  }
  
  // Adds a specified number of enchantment cards to the desired board. 
  public void add(int copies, String cardName, String manaCost, COLOR color, BOARD board) {
    if (board == BOARD.MAIN){
      for (int i = 0; i < copies; i++) {
        mainBoard.add(new Card(cardName,manaCost,color));
      }
    }
    if (board == BOARD.SIDE){
      for (int i = 0; i < copies; i++) {
        sideBoard.add(new Card(cardName,manaCost,color));
      }
    }
  }
  
  // Adds a specified number of noncreature artifact cards to the desired board. 
  public void add(int copies, String cardName, String manaCost, BOARD board) {
    if (board == BOARD.MAIN){
      for (int i = 0; i < copies; i++) {
        mainBoard.add(new Card(cardName,manaCost));
      }
    }
    if (board == BOARD.SIDE){
      for (int i = 0; i < copies; i++) {
        sideBoard.add(new Card(cardName,manaCost));
      }
    }
  }
  
  // Adds a specified number of land cards to the desired board. 
  public void add(int copies, String cardName, BOARD board) {
    if (board == BOARD.MAIN){
      for (int i = 0; i < copies; i++) {
        mainBoard.add(new Card(cardName));
      }
    }
    if (board == BOARD.SIDE){
      for (int i = 0; i < copies; i++) {
        sideBoard.add(new Card(cardName));
      }
    }
  }
  
  // Calls the paintComponent method in the Graphics class.
  public void paintComponent(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0,0,ZONE_PANEL_WIDTH,this.getHeight());    // Width is constant, height is variable.
    int x = (ZONE_PANEL_WIDTH - cardImage.getWidth())/2;  // The X coordinate of th top-left corner of the image.
    int y = (this.getHeight() - cardImage.getHeight())/2; // The Y coordinate of th top-left corner of the image.
    g.drawImage(cardImage,x,y,null);
    int[] xs = {x,x+4,x};
    int[] ys = {y,y,y+4};
    g.fillPolygon(xs,ys,3);
    int width = cardImage.getWidth();
    int height = cardImage.getHeight();
    int[] xs2 = {x + width,x + width - 4,x + width};
    int[] ys2 = {y,y,y+4};
    g.fillPolygon(xs2,ys2,3);
    int[] xs3 = {x,x+4,x};
    int[] ys3 = {y + height,y + height,y + height - 4};
    g.fillPolygon(xs3,ys3,3);
    int[] xs4 = {x + width,x + width - 4,x + width};
    int[] ys4 = {y + height,y + height,y + height -4};
    g.fillPolygon(xs4,ys4,3);
    int fontSize = 28;
    g.setFont(new Font("Serif",1,fontSize));
    if (this.deckName != null) { // Only draw cardsInLibrary if a deck has been loaded.
      if (mainBoard.size() >= THRESHOLD) {
        g.setColor(Color.white);
      }
      else {
        g.setColor(Color.red);
      }
      Integer cardsInLibrary = mainBoard.size();
      int shift = 0;
      if (cardsInLibrary < 10) {
        shift = fontSize/4;
      }
      g.drawString(cardsInLibrary.toString(),(ZONE_PANEL_WIDTH/2) - (fontSize/2) + shift
                     ,(this.getHeight()/2) + (fontSize/4));
    }
  }
  
  // Draw a card if you click on the deck image.
  public void mouseClicked (MouseEvent me) {
    if (me.getClickCount() == 2) {
      int x = (ZONE_PANEL_WIDTH - cardImage.getWidth())/2;  // The X coordinate of th top-left corner of the image.
      int y = (this.getHeight() - cardImage.getHeight())/2; // The Y coordinate of th top-left corner of the image.
      boolean condition1 = me.getX() >= x;
      boolean condition2 = me.getX() <= x + cardImage.getWidth();
      boolean condition3 = me.getY() >= y;
      boolean condition4 = me.getY() <= y + cardImage.getHeight();
      if (condition1 && condition2 && condition3 && condition4) {
        if (mtg.handKept()) {
          mtg.draw();
        }
      }
    }
  }
  
  // Unused methods.
  public void mousePressed  (MouseEvent me) {}
  public void mouseReleased (MouseEvent me) {}
  public void mouseEntered  (MouseEvent me) {}
  public void mouseExited   (MouseEvent me) {}
  
  // Complete list of the Junk Rites deck
  public void initJunkRites() {
    // Spells
    add(3,"Angel of Serenity","4WWW",COLOR.WHITE,5,6,BOARD.MAIN);
    add(2,"Arbor Elf","G",COLOR.GREEN,1,1,BOARD.MAIN); 
    add(4,"Avacyn's Pilgrim","G",COLOR.GREEN,1,1,BOARD.MAIN); 
    add(2,"Centaur Healer","1GW",COLOR.GOLD,3,3,BOARD.MAIN);
    add(2,"Craterhoof Behemoth","5GGG",COLOR.GREEN,5,5,BOARD.MAIN);
    add(2,"Lotleth Troll","BG",COLOR.GOLD,2,1,BOARD.MAIN);
    add(1,"Obzedat, Ghost Council","3BW",COLOR.GOLD,5,5,BOARD.MAIN);
    add(3,"Restoration Angel","3W",COLOR.WHITE,3,4,BOARD.MAIN);
    add(4,"Thragtusk","4G",COLOR.GREEN,5,3,BOARD.MAIN);
    add(4,"Grisly Salvage","BG",COLOR.GOLD,BOARD.MAIN);
    add(3,"Lingering Souls","2W",COLOR.WHITE,BOARD.MAIN);
    add(3,"Mulch","1G",COLOR.GREEN,BOARD.MAIN);
    add(4,"Unburial Rites","4B",COLOR.BLACK,BOARD.MAIN);
    // Lands
    add(1,"Cavern of Souls",BOARD.MAIN);
    add(2,"Forest",BOARD.MAIN);
    add(1,"Gavony Township",BOARD.MAIN);
    add(2,"Godless Shrine",BOARD.MAIN);
    add(2,"Isolated Chapel",BOARD.MAIN);
    add(4,"Overgrown Tomb",BOARD.MAIN);
    add(3,"Sunpetal Grove",BOARD.MAIN);
    add(4,"Temple Garden",BOARD.MAIN);
    add(4,"Woodland Cemetry",BOARD.MAIN);
    // Sideboard
    add(3,"Abrupt Decay","BG",COLOR.GOLD,BOARD.SIDE);
    add(3,"Acidic Slime","3GG",COLOR.GREEN,2,2,BOARD.SIDE);
    add(1,"Cavern of Souls",BOARD.SIDE);
    add(2,"Centaur Healer","1GW",COLOR.GOLD,3,3,BOARD.SIDE);
    add(4,"Deathrite Shaman","H",COLOR.GOLD,1,2,BOARD.SIDE);
    add(1,"Obzedat, Ghost Council","3BW",COLOR.GOLD,5,5,BOARD.SIDE);
    add(1,"Vraska the Unseen","3BG",COLOR.GOLD,5,BOARD.SIDE);
  }
  
  public void initBantAuras() {
    // Creatures
    add(4,"Avacyn's Pilgrim","G",COLOR.GREEN,1,1,BOARD.MAIN);
    add(4,"Champion of the Parish","W",COLOR.WHITE,1,1,BOARD.MAIN); 
    add(4,"Fencing Ace","1W",COLOR.WHITE,1,1,BOARD.MAIN); 
    add(3,"Invisible Stalker","1U",COLOR.BLUE,1,1,BOARD.MAIN);
    add(1,"Nearheath Pilgrim","1W",COLOR.WHITE,2,1,BOARD.MAIN);
    add(3,"Geist of Saint Traft","1WU",COLOR.GOLD,2,2,BOARD.MAIN);
    add(3,"Silverblade Paladin","1WW",COLOR.WHITE,2,2,BOARD.MAIN);
    // Enchantments
    add(3,"Abundant Growth","G",COLOR.GREEN,BOARD.MAIN);
    add(4,"Rancor","G",COLOR.GREEN,BOARD.MAIN);
    add(4,"Ethereal Armor","W",COLOR.WHITE,BOARD.MAIN);
    add(4,"Spectral Flight","1U",COLOR.BLUE,BOARD.MAIN);
    // Sorceries
    add(2,"Increasing Savagery","3G",COLOR.GREEN,BOARD.MAIN);
    // Lands
    add(2,"Cavern of Souls",BOARD.MAIN);
    add(3,"Forest",BOARD.MAIN);
    add(4,"Breeding Pool",BOARD.MAIN);
    add(4,"Temple Garden",BOARD.MAIN);
    add(4,"Hallowed Fountain",BOARD.MAIN);
    add(3,"Glacial Fortress",BOARD.MAIN);
    add(1,"Sunpetal Grove",BOARD.MAIN);
    // Sideboard
 
  }
  
   
  
}