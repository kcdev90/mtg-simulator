package mtgSimulator;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;

public class MenuPanel extends JPanel implements Constants {

  // Fields
  private MagicTheGathering mtg;
  private JMenuBar menuBar;
  private JMenu editMenu;
  private JMenu toolsMenu;
  private JMenu viewMenu;
  private JMenu helpMenu;
  private JMenuItem loadDeck;
  private JMenuItem newGame;
  private JMenuItem restart;
  private JMenuItem setName;
  private JMenuItem displayDeckList;
  private JMenuItem availableDecks;
  private JMenuItem analyzeDeck;
  private JMenuItem setResizability;
  private JRadioButtonMenuItem mainBackground1;
  private JRadioButtonMenuItem mainBackground2;
  private JRadioButtonMenuItem mainBackground3;
  private JRadioButtonMenuItem handBackground1;
  private JRadioButtonMenuItem handBackground2;
  private JRadioButtonMenuItem handBackground3;
  private ButtonGroup mainGroup;
  private ButtonGroup handGroup;
  private JFrame loadDeckFrame;
  private JFrame setNameFrame;
  private boolean loadDeckFrameInDisplay;
  private boolean setNameFrameInDisplay;
  private boolean resizable;
  
  // The Constructor method.
  public MenuPanel(MagicTheGathering mtg) {
    this.mtg = mtg;
    this.resizable = false;
    initComponents();
    initLayout();
  }
  
  // Initilizes the components in the panel.
  public void initComponents() {
    initEditMenu();
    initToolsMenu();
    initViewMenu();
    initHelpMenu();
    initMenuBar(); // Must be done last.
  }
  
  // Initializes the edit menu.
  public void initEditMenu() {
    editMenu = new JMenu("Edit");
    loadDeck = new JMenuItem("Load Deck");
    newGame  = new JMenuItem("New Game");
    setName  = new JMenuItem("Set Name");
    restart  = new JMenuItem("Restart");
    loadDeck.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK));
    newGame.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
    restart.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.ALT_MASK));
    loadDeck.addActionListener(new LoadDeckAdaptor());
    newGame.addActionListener (new NewGameAdaptor());
    restart.addActionListener (new RestartAdaptor());
    setName.addActionListener (new SetNameAdaptor());
    editMenu.add(loadDeck);
    editMenu.add(newGame);
    editMenu.add(restart);
    editMenu.add(setName);
  }
  
  // Initializes the tools menu.
  public void initToolsMenu() {
    toolsMenu       = new JMenu("Tools");
    displayDeckList = new JMenuItem("Display Deck List");
    availableDecks  = new JMenuItem("Display Available Decks");
    analyzeDeck     = new JMenuItem("Analyze Deck");
    toolsMenu.setMnemonic(KeyEvent.VK_T);
    displayDeckList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,ActionEvent.CTRL_MASK));
    availableDecks.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,ActionEvent.ALT_MASK));
    displayDeckList.addActionListener(new DisplayDeckListListener());
    availableDecks.addActionListener(new AvailableDecksAdaptor());
    analyzeDeck.addActionListener(new AnalyzeDeckAdaptor());
    toolsMenu.add(displayDeckList);
    toolsMenu.add(availableDecks);
    toolsMenu.add(analyzeDeck);
  }
  
  // Initializes the view menu.
  public void initViewMenu() {
    viewMenu        = new JMenu("View");
    setResizability = new JMenuItem("Allow Resize"); // Default option.
    mainGroup       = new ButtonGroup();
    mainBackground1 = new JRadioButtonMenuItem("Background 1");
    mainBackground2 = new JRadioButtonMenuItem("Background 2");
    mainBackground3 = new JRadioButtonMenuItem("Background 3");
    handGroup       = new ButtonGroup();
    handBackground1 = new JRadioButtonMenuItem("Background 1");
    handBackground2 = new JRadioButtonMenuItem("Background 2");
    handBackground3 = new JRadioButtonMenuItem("Background 3");
    viewMenu.setMnemonic(KeyEvent.VK_V);
    setResizability.addActionListener(new SetResizabilityAdaptor());
    viewMenu.add(setResizability);
    viewMenu.addSeparator();
    mainBackground1.setSelected(true);
    mainBackground1.addActionListener(new SetMainBackground1Adaptor());
    mainBackground2.addActionListener(new SetMainBackground2Adaptor());
    mainBackground3.addActionListener(new SetMainBackground3Adaptor());
    mainGroup.add(mainBackground1);
    mainGroup.add(mainBackground2);
    mainGroup.add(mainBackground3);
    viewMenu.add(mainBackground1);
    viewMenu.add(mainBackground2);
    viewMenu.add(mainBackground3);
    viewMenu.addSeparator();
    handBackground1.setSelected(true);
    handBackground1.addActionListener(new SetHandBackground1Adaptor());
    handBackground2.addActionListener(new SetHandBackground2Adaptor());
    handBackground3.addActionListener(new SetHandBackground3Adaptor());
    handGroup.add(handBackground1);
    handGroup.add(handBackground2);
    handGroup.add(handBackground3);
    viewMenu.add(handBackground1);
    viewMenu.add(handBackground2);
    viewMenu.add(handBackground3);
  }
  
  // Initializes the help menu.
  public void initHelpMenu() {
    helpMenu = new JMenu("Help");
    helpMenu.setMnemonic(KeyEvent.VK_H);
    helpMenu.addActionListener(new HelpAdaptor());
  }
  
  // Initializes the menu bar.
  public void initMenuBar() {
    menuBar = new JMenuBar();
    menuBar.add(editMenu);
    menuBar.add(toolsMenu);
    menuBar.add(viewMenu);
    menuBar.add(helpMenu);    
  }
  
  // Initializes the layout of the panel.
  public void initLayout() {
    setPreferredSize(new Dimension(MENU_PANEL_WIDTH,MENU_PANEL_HEIGHT));
    setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
    add(menuBar);
  }
  
  // Returns the load deck frame.
  public JFrame getLoadDeckFrame() {
    return this.loadDeckFrame;
  }
  
  // Sets the variable loadDeckFrameInDisplay to the specified boolean value.
  public void setLoadDeckFrameInDisplay(boolean inDisplay) {
    this.loadDeckFrameInDisplay = inDisplay;
  }
  
  // Returns the set name frame.
  public JFrame getSetNameFrame() {
    return this.setNameFrame;
  }
  
  // Sets the variable setNameFrameInDisplay to the specified boolean value.
  public void setSetNameFrameInDisplay(boolean inDisplay) {
    this.setNameFrameInDisplay = inDisplay;
  }
 
  // A complecated action listenr with nested classes. Opens an inquiry box.
  private class LoadDeckAdaptor implements ActionListener {
    
    // Fields
    JTextField textField;
    
    // Creates a text field that implements an action listener of its own.
    public void actionPerformed(ActionEvent ae) {
      if (!loadDeckFrameInDisplay) { // We do not want more than one frame at any given time.
        loadDeckFrameInDisplay = true;
        loadDeckFrame = new JFrame("Enter a deck name"); // Acts as a pseudo-constructor.
        loadDeckFrame.setSize(250,65);
        loadDeckFrame.setLocationRelativeTo(null);
        loadDeckFrame.setVisible(true);
        loadDeckFrame.addWindowListener(new WindowAdaptor()); // Sets a custom close operation.
        textField = new JTextField();
        textField.addActionListener(new TextAdaptor());
        loadDeckFrame.add(textField);
      }
    }
    
    // The nested inner class. Reads the text in the text field.
    private class TextAdaptor implements ActionListener {
      
      public void actionPerformed(ActionEvent ae) {
        if (mtg.getLibraryPanel().containsDeck(textField.getText())) { // The entry matches some deck.
          if (mtg.getLibraryPanel().getDeckName() == null) { // The is the first time loading a deck.
            mtg.getLibraryPanel().loadDeck(textField.getText());
            mtg.getLibraryPanel().repaint();
          }
          else { // Some deck has already been loaded, and we are loading for at least the second time.
            mtg.restart();
            mtg.getLibraryPanel().loadDeck(textField.getText());
          }
          loadDeckFrame.setVisible(false); // Close the inquiry frame once a valid deck is loaded.
          loadDeckFrameInDisplay = false; // Update the lockDeckFrameInDisplay variable.
          mtg.getButtonPanel().getInitialHandButton().setEnabled(true);
          mtg.notify(mtg.getLibraryPanel().getDeckName()+" deck loaded.");
        }
        else { // A deck was not properly loaded.
          textField.selectAll(); // Highlights the text field.
          mtg.notify("            -----Load Failed-----"+"\n"+"Unable to find "+textField.getText()+" deck.");
        }
      }
      
    }
    
    // Notify the panel that the inquiry frame is no longer in display.
    private class WindowAdaptor implements WindowListener {
      public void windowClosing(WindowEvent we)     { loadDeckFrameInDisplay = false; }
      public void windowActivated(WindowEvent we)   {}
      public void windowClosed(WindowEvent we)      {}
      public void windowDeactivated(WindowEvent we) {}
      public void windowDeiconified(WindowEvent we) {}
      public void windowIconified(WindowEvent we)   {}
      public void windowOpened(WindowEvent we)      {}
    }
    
  }
  
  // Starts a new game with the same deck.
  private class NewGameAdaptor implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      mtg.newGame();
    }
  }
  
  // Restarts the game, i.e. all variables are reverted to their initial state.
  private class RestartAdaptor implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      mtg.restart();
    }
  }
  
  private class SetNameAdaptor implements ActionListener {
    
    // Fields
    JLabel label;
    JTextField nameField;
    JRadioButton maleButton;
    JRadioButton femaleButton;
    ButtonGroup group;
    
    public void actionPerformed(ActionEvent ae) {
      if (!setNameFrameInDisplay) { // Only allow one frame at a time.
        setNameFrameInDisplay = true;
        initComponents();
        initLayout();
      }
    }
    
    private void initComponents() {
      label = new JLabel("Enter Player Name");
      maleButton = new JRadioButton("Male");
      maleButton.setSelected(true);
      nameField = new JTextField();
      nameField.addActionListener(new NameAdaptor());
      femaleButton = new JRadioButton("Female");
      group = new ButtonGroup();
      group.add(maleButton);
      group.add(femaleButton);
    }
    
    private void initLayout() {
      setNameFrame = new JFrame("Enter Name");
      setNameFrame.setLayout(new GridLayout(2,2));
      setNameFrame.add(label);
      setNameFrame.add(maleButton);
      setNameFrame.add(nameField);
      setNameFrame.add(femaleButton);
      setNameFrame.addWindowListener(new WindowAdaptor());
      setNameFrame.setSize(350,50);
      setNameFrame.setLocationRelativeTo(null);
      setNameFrame.setResizable(false);
      setNameFrame.setVisible(true);
      setNameFrame.pack();
    }
      
    private class NameAdaptor implements ActionListener {
        
      public void actionPerformed(ActionEvent ae) {
        String oldName = mtg.getPlayerName();
        mtg.setPlayerName(nameField.getText());
        if (maleButton.isSelected()) { // Male
          mtg.setGenderPronoun("his");
        }
        else { // Female
          mtg.setGenderPronoun("her");
        }
        setNameFrame.setVisible(false);
        setNameFrameInDisplay = false;
        mtg.notify(oldName+" changed "+mtg.getGenderPronoun()+" name to "+mtg.getPlayerName()+".");
      }
        
    }
      
    // Notify the panel that the inquiry frame is no longer in display.
    private class WindowAdaptor implements WindowListener {
      public void windowClosing(WindowEvent we)     { setNameFrameInDisplay = false; }
      public void windowActivated(WindowEvent we)   {}
      public void windowClosed(WindowEvent we)      {}
      public void windowDeactivated(WindowEvent we) {}
      public void windowDeiconified(WindowEvent we) {}
      public void windowIconified(WindowEvent we)   {}
      public void windowOpened(WindowEvent we)      {}
    }
      
  }
    
  
  private class DisplayDeckListListener implements ActionListener {
    
    public void actionPerformed(ActionEvent ae) {
      System.out.println("Display Deck List");
    }
    
  }
  
  private class AvailableDecksAdaptor implements ActionListener {
    
    public void actionPerformed(ActionEvent ae) {
      System.out.println("Display Available Decks");
    }
    
  }
   
  private class AnalyzeDeckAdaptor implements ActionListener {
    
    public void actionPerformed(ActionEvent ae) {
      System.out.println("Analyze Deck");
    }
    
  }
  
  // Sets the resizability of the frame. Toggle the appearance of the button after being pressed. 
  private class SetResizabilityAdaptor implements ActionListener {
    
    public void actionPerformed(ActionEvent ae) {
      if (!resizable) {
        mtg.setResizable(true);
        viewMenu.remove(setResizability);
        setResizability = new JMenuItem("Disallow Resize");
        setResizability.addActionListener(this);
        viewMenu.add(setResizability,0); // Add it to the top of the menu.
      }
      else {
        mtg.setResizable(false);
        viewMenu.remove(setResizability);
        setResizability = new JMenuItem("Allow Resize");
        setResizability.addActionListener(this);
        viewMenu.add(setResizability,0); // Add it to the top of the menu.
      }
      resizable = !resizable;
    }
    
  }
  
  // Sets the background of the main panel to the default play mat.
  private class SetMainBackground1Adaptor implements ActionListener {
    
    public void actionPerformed(ActionEvent ae) {
      mtg.getMainPanel().setPlayMat(DEFAULT_PLAYMAT_URL);
      mtg.getMainPanel().repaint();
    }
    
  }
  
  // Sets the background of the main panel to playmat 2.
  private class SetMainBackground2Adaptor implements ActionListener {
    
    public void actionPerformed(ActionEvent ae) {
     // mtg.notify("Loading image..."+"\n");
      mtg.getMainPanel().setPlayMat(PLAYMAT2_URL);
      mtg.getMainPanel().repaint();
      mtg.notify("Playmat image 2 loaded.");
    }
    
  }
    
  // Sets the background of the main panel to play mat 3.
  private class SetMainBackground3Adaptor implements ActionListener {
    
    public void actionPerformed(ActionEvent ae) {
      mtg.getMainPanel().setPlayMat(PLAYMAT3_URL);
      mtg.getMainPanel().repaint();
    }
    
  }
  
  private class SetHandBackground1Adaptor implements ActionListener {
    
    public void actionPerformed(ActionEvent ae) {
      mtg.getHandPanel().setBackground(DEFAULT_BACKGROUND_URL);
      mtg.getHandPanel().repaint();
    }
    
  }
  
  private class SetHandBackground2Adaptor implements ActionListener {
    
    public void actionPerformed(ActionEvent ae) {
      mtg.getHandPanel().setBackground(BACKGROUND2_URL);
      mtg.getHandPanel().repaint();
    }
    
  }
  
  private class SetHandBackground3Adaptor implements ActionListener {
    
    public void actionPerformed(ActionEvent ae) {
      mtg.getHandPanel().setBackground(BACKGROUND3_URL);
      mtg.getHandPanel().repaint();
    }
    
  }
  
  private class HelpAdaptor implements ActionListener {
    
    public void actionPerformed(ActionEvent ae) {
      System.out.println("Help!");
    }
    
  }
  
  
  
}