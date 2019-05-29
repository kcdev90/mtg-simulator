package mtgSimulator;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Dimension;
import java.awt.BorderLayout; // Found in java.awt, but not in javax.swing.
import java.awt.Point;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

// The central class that handles everything. Has 5 subcomponents.
public class MagicTheGathering extends JFrame implements Constants {
  
  //****************************************//
  public static void main(String[] args) {
    new MagicTheGathering();
  }
  //****************************************//
  
  
  // Constants
  private static final int DEFAULT_LIFE  = 20;
  private static final int MAX_HAND_SIZE = 7;
  private static final boolean DEFAULT_RESIZABILITY = false;
  
  // Fields related to the game
  private boolean initialHandDrawn;
  private boolean handKept;
  private boolean messageNotified; // Only allow one message at a time.
  private String playerName;
  private String genderPronoun;
  
  // The MagicTheGathering frame consists of 5 panels and 4 sub-panels.
  private LifePanel lifePanel;
  private ButtonPanel buttonPanel;
  private HandPanel handPanel;
  private MainPanel mainPanel;
  private LibraryPanel libraryPanel;
  private GraveyardPanel graveyardPanel;
  private MenuPanel menuPanel;
  
  // The constructor method is set up in 4 stages.
  public MagicTheGathering() {
    initFields();
    initPanels();
    initLayout();
  }
  
  // Initializes the fields.
  public void initFields() {
    this.initialHandDrawn = false;
    this.handKept         = false;
    this.messageNotified  = false;
    this.playerName       = "Anonymous";
    this.genderPronoun    = "his";
  }
  
  // Initializes the JPanels.
  public void initPanels() {
    // Necessary to take in "this" in order to communicate.
    this.lifePanel      = new LifePanel     ();
    this.buttonPanel    = new ButtonPanel   (this);
    this.mainPanel      = new MainPanel     (this);
    this.handPanel      = new HandPanel     (this);
    this.libraryPanel   = new LibraryPanel  (this);
    this.graveyardPanel = new GraveyardPanel(this);
    this.menuPanel      = new MenuPanel     (this);
  }
  
  // Initializes the frame environment.
  public void initLayout() {
    setLayout(new BorderLayout());
    add(menuPanel, BorderLayout.NORTH);
    add(handPanel, BorderLayout.SOUTH);
    add(mainPanel, BorderLayout.CENTER);
    // Creating a temporary panels is efficient and necessary.
    JPanel utilityPanel = new JPanel();
    utilityPanel.setLayout(new BoxLayout(utilityPanel, BoxLayout.Y_AXIS));
    utilityPanel.add(lifePanel);
    utilityPanel.add(buttonPanel);
    utilityPanel.setPreferredSize(new Dimension(UTILITY_PANEL_WIDTH, UTILITY_PANEL_HEIGHT));
    add(utilityPanel, BorderLayout.WEST);
    JPanel zonePanel = new JPanel();
    zonePanel.setLayout(new BoxLayout(zonePanel, BoxLayout.Y_AXIS));
    zonePanel.add(graveyardPanel);
    zonePanel.add(libraryPanel); // Add the library panel last, so that it'll appear bellow the graveyardPanel.
    zonePanel.setPreferredSize(new Dimension(ZONE_PANEL_WIDTH, ZONE_PANEL_HEIGHT));
    add(zonePanel, BorderLayout.EAST);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("MTG Deck Tester");
    pack();
    setLocationRelativeTo(null); // Display the window in the center.
    setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
    setResizable(DEFAULT_RESIZABILITY);
    setVisible(true);  
  }
  
  // Returns the name of the player.
  public String getPlayerName() {
    return this.playerName;
  }
  
  // Sets the player name to the specified string value.
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }
  
  // Returns the gender pronoun.
  public String getGenderPronoun() {
    return this.genderPronoun;
  }
  
  // Sets the gender pronoun to the specified string value.
  public void setGenderPronoun(String genderPronoun) {
    this.genderPronoun = genderPronoun;
  }
  
  // Returns whether the hand has been kept or not.
  public boolean handKept() {
    return this.handKept;
  }
  
  // Returns the hand panel. Used by the components of the frame.
  public  HandPanel getHandPanel() {
    return this.handPanel;
  }
  
  // Returns the library panel. Used by the components of the frame.
  public LibraryPanel getLibraryPanel() {
    return this.libraryPanel;
  }
  
  // Returns the button panel. Used by the components of the frame.
  public ButtonPanel getButtonPanel() {
    return this.buttonPanel;
  }
  
  // Returns the main panel. Used by the components of the frame.
  public MainPanel getMainPanel() {
    return this.mainPanel;
  }
  
  // Returns the graveyard panel. Used by the components of the frame.
  public GraveyardPanel getGraveyardPanel() {
    return this.graveyardPanel;
  }
  
  // Notifys the action that was performed and adjusts the view position of text area in the button panel.
  public void notify(String message) {
    buttonPanel.getActivityLog().append(message+"\n"); // Automatically start a new line.
    buttonPanel.getScrollPane().getViewport()
      .setViewPosition(new Point(0,buttonPanel.getScrollPane().getVerticalScrollBar().getMaximum()));
  }
  
  // Draws a fresh hand of 7 cards.
  public void drawInitialHand() {
    shuffle(); // Shuffle before drawing.
    if (!initialHandDrawn) {
      for (int i = 0; i < MAX_HAND_SIZE; i++) { // No need to check for cards in the library or repaint.
        handPanel.getHand().add(getNextCard());
        libraryPanel.getMainBoard().remove(0);
      }
      initialHandDrawn = true;
    }
    handPanel.repaint(); // Reflect the change in the number of cards in the library.
    libraryPanel.repaint(); // Reflect the change in the number of cards in the library.
    notify(playerName+" drew "+genderPronoun+" initial hand.");
  }
  
  // Mulligans the hand.
  public void mulligan() {
    int cardsInHand = handPanel.getHand().size(); // Written in stone.
    if (initialHandDrawn && !handKept && cardsInHand > 0) {
      for (Card c : handPanel.getHand()) {
        libraryPanel.getMainBoard().add(c);
      }
      handPanel.getHand().clear();
      shuffle();
      // Draw one less card than in your previous hand.
      for (int i = 0; i < cardsInHand - 1; i++) { // No need to check for cards in the library or repaint.
        handPanel.getHand().add(getNextCard());
        libraryPanel.getMainBoard().remove(0);
      }
    }
    handPanel.repaint(); // Reflect the change in the number of cards in the library.
    libraryPanel.repaint(); // Reflect the change in the number of cards in the library.
    Integer handSize = handPanel.getHand().size();
    notify(playerName+" mulliganed to "+handSize.toString()+".");
  }
  
  // Keep the hand.
  public void keep() {
    this.handKept = true;
    notify(playerName+" kept "+genderPronoun+" hand.");
  }
  
  // Untaps all permanent cards on the battlefield.
  public void untap() {
    for (Card c: mainPanel.getBattlefield()) {
      if (c.isTapped()) {
        c.setTapped(false);
      }
    }
    mainPanel.repaint();
    notify(playerName+" untapped "+genderPronoun+" cards.");
  }
  
  // Draws a card. Do not draw if there are no cards left in the library.
  public void draw() {
    if (libraryPanel.getMainBoard().size() > 0) { // There are cards left in the library.
      handPanel.getHand().add(getNextCard());
      libraryPanel.getMainBoard().remove(0);
      handPanel.repaint(); // Reflect the change in the number of cards in the library.
      libraryPanel.repaint(); // Reflect the change in the number of cards in the hand.
      notify(playerName+" drew a card.");
    }
    else { // There are no cards left in the library.
      if (!messageNotified) { // A message has not been notified yet.
        notify(playerName+" lost the game!");
        this.messageNotified = true;
      }
    }
  }
  
  // Returns the card on top of the library.
  public Card getNextCard() {
    return libraryPanel.getMainBoard().get(0);
  }
 
  // Shuffles all the cards in the library.
  public void shuffle() {  
    // necessary to clone in order to avoid removing elements from the new list.
    ArrayList<Card> copy = (ArrayList<Card>) libraryPanel.getMainBoard().clone();
    for(int i = 0; i < libraryPanel.getMainBoard().size(); i++) {
      Random random = new Random();
      int index = random.nextInt(copy.size());
      libraryPanel.getMainBoard().set(i,(Card)copy.get(index));
      copy.remove(index);
    }
    notify(playerName+" shuffled "+genderPronoun+" library.");
  }
  
  // Creates a creature token, which is added to the mainPanel.
  public void createToken(String tokenType, COLOR color, int power, int toughness) {
    Card token = new Card(tokenType,color,power,toughness);
    token.setX((mainPanel.getWidth()  - CARD_WIDTH)/2);
    token.setY((mainPanel.getHeight() - CARD_HEIGHT)/2);
    mainPanel.getBattlefield().add(token);
    mainPanel.repaint();
    notify(playerName+" created a "+tokenType+" token.");
  }
  
  // Starts a new game with the same deck.
  public void newGame() {
    if (libraryPanel.getDeckName() != null) {
      String currentDeck = libraryPanel.getDeckName();
      resetFields();
      resetLifePanel();
      resetButtonPanel();
      resetLibraryPanel();
      resetHandPanel();
      resetMainPanel();
      resetGraveyardPanel();
      resetMenuPanel();
      libraryPanel.loadDeck(currentDeck);
      buttonPanel.getInitialHandButton().setEnabled(true);
      notify(playerName+" started a new game.");
    }
    else {
      notify("Please load a deck first.");
    }
  }
  
  // Restarts the game in 8 stages.
  public void restart() {
    resetFields();
    resetLifePanel();
    resetButtonPanel();
    resetLibraryPanel();
    resetHandPanel();
    resetMainPanel();
    resetGraveyardPanel();
    resetMenuPanel();
    notify(playerName+" restarted the game.");
  }
  
  // Resets the states of the fields to its initial values.
  public void resetFields() {
    this.initialHandDrawn = false;
    this.handKept = false;
    this.messageNotified = false;
  }
  
  // Resets the states of the life panel.
  public void resetLifePanel() {
    lifePanel.setPlayerLife(DEFAULT_LIFE);
    lifePanel.setOpponentLife(DEFAULT_LIFE);
  }
  
  // Resets the states of the button panel.
  public void resetButtonPanel() {
    buttonPanel.getInitialHandButton().setEnabled(false);
    buttonPanel.getMulliganButton().setEnabled(false);
    buttonPanel.getKeepButton().setEnabled(false);
    buttonPanel.getUntapButton().setEnabled(false);
    buttonPanel.getDrawButton().setEnabled(false);
    buttonPanel.getShuffleButton().setEnabled(false);
    buttonPanel.getTokenButton().setEnabled(false);
    buttonPanel.getActivityLog().setText(null); // Clears the text area.
    if (buttonPanel.getTokenFrame() != null) {
      buttonPanel.getTokenFrame().setVisible(false);
      buttonPanel.setTokenFrameInDisplay(false);
    }
  }
  
  // Resets the states of the library panel.
  public void resetLibraryPanel() {
    libraryPanel.setMainBoard(new ArrayList<Card>());
    libraryPanel.setSideBoard(new ArrayList<Card>());
    libraryPanel.setDeckName(null);
    libraryPanel.repaint();
  }
  
  // Resets the states of the hand panel.
  public void resetHandPanel() {
    handPanel.getHand().clear();
    handPanel.repaint();
  }
  
  // Resets the states of the main panel.
  public void resetMainPanel() {
    mainPanel.getBattlefield().clear();
    mainPanel.repaint();
  }
  
  // Resets the states of the graveyard panel.
  public void resetGraveyardPanel() {
    graveyardPanel.setGraveyard(new ArrayList<Card>());
    graveyardPanel.setTopCard(null);
    graveyardPanel.repaint();
  }
  
  // Simply closes any frames, if they are in display.
  public void resetMenuPanel() {
    if (menuPanel.getLoadDeckFrame() != null) {
      menuPanel.getLoadDeckFrame().setVisible(false);
      menuPanel.setLoadDeckFrameInDisplay(false);
    }
    if (menuPanel.getSetNameFrame() != null) {
      menuPanel.getSetNameFrame().setVisible(false);
      menuPanel.setSetNameFrameInDisplay(false);
    }
  }
  
  
  
}