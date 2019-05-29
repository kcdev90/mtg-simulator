package mtgSimulator;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class ButtonPanel extends JPanel implements Constants {
  
  // Fields
  private MagicTheGathering mtg; // Necessary in order to communicate with container.
  private JButton initialHandButton;
  private JButton mulliganButton;
  private JButton keepButton;
  private JButton untapButton;
  private JButton drawButton;
  private JButton shuffleButton;
  private JButton tokenButton;
  private JTextArea activityLog;
  private JScrollPane scrollPane;
  private JFrame tokenFrame;
  private boolean tokenFrameInDisplay;
 
  // The constructor method is set up in 3 stages.
  public ButtonPanel(MagicTheGathering mtg) {
    this.mtg = mtg;
    this.tokenFrameInDisplay = false;
    initComponents();
    initLayout(); // Must be done after initComponents().   
  }
  
  // Sets up all the components in 8 stages.
  public void initComponents() {
    initInitialHandButton();
    initMulliganButton();
    initKeepButton();
    initUntapButton();
    initDrawButton();
    initShuffleButton();
    initTokenButton();
    initActivityLog();
  }
  
  // Initializes the layout. Calls the add methods in the order to be presented.
  public void initLayout() {
    setPreferredSize(new Dimension(BUTTON_PANEL_WIDTH, BUTTON_PANEL_HEIGHT));
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    add(initialHandButton);
    add(mulliganButton);
    add(keepButton);
    add(untapButton);
    add(drawButton);
    add(shuffleButton);
    add(tokenButton);
    add(scrollPane); // The scroll pane contains the text area.
  }
  
  // Initializes the initial hand button.
  public void initInitialHandButton() {
    initialHandButton = new JButton("          Draw Initial Hand          ");
    initialHandButton.setPreferredSize(new Dimension(1,20)); // X coordinate is irrelevant.
    initialHandButton.setMnemonic(KeyEvent.VK_I);
    initialHandButton.addActionListener(new InitialHandAdaptor());
    initialHandButton.setEnabled(false);
  }
  
  // Initializes the mulligan button.
  public void initMulliganButton() {
    mulliganButton = new JButton("                  Mulligan                  ");
    mulliganButton.setPreferredSize(new Dimension(1,20)); // X coordinate is irrelevant.
    mulliganButton.setMnemonic(KeyEvent.VK_M);
    mulliganButton.addActionListener(new MulliganAdaptor());
    mulliganButton.setEnabled(false);
  }
  
  // Initializes the keep button.
  public void initKeepButton() {
    keepButton = new JButton("                     Keep                     ");
    keepButton.setPreferredSize(new Dimension(1,20)); // X coordinate is irrelevant.
    keepButton.setMnemonic(KeyEvent.VK_K);
    keepButton.addActionListener(new KeepAdaptor());
    keepButton.setEnabled(false);
  }
  
  // Initializes the untap button.
  public void initUntapButton() {
    untapButton = new JButton("                    Untap                    ");
    untapButton.setPreferredSize(new Dimension(1,20));
    untapButton.setMnemonic(KeyEvent.VK_W);
    untapButton.addActionListener(new UntapAdaptor());
    untapButton.setEnabled(false);
  }
  
  // Initializes the draw button.
  public void initDrawButton() {
    drawButton = new JButton("                     Draw                     ");
    drawButton.setPreferredSize(new Dimension(1,20)); // X coordinate is irrelevant.
    drawButton.setMnemonic(KeyEvent.VK_D);
    drawButton.addActionListener(new DrawAdaptor());
    drawButton.setEnabled(false);
  }
  
  // Initializes the shuffle button.
  public void initShuffleButton() {
    shuffleButton = new JButton("                   Shuffle                   ");
    shuffleButton.setPreferredSize(new Dimension(1,20));
    shuffleButton.setMnemonic(KeyEvent.VK_Q);
    shuffleButton.addActionListener(new ShuffleAdaptor());
    shuffleButton.setEnabled(false);
  }
  
  // Initializes the token button.
  public void initTokenButton() {
    tokenButton = new JButton("             Create Token             ");
    tokenButton.setPreferredSize(new Dimension(1,20)); // X coordinate is irrelevant.
    tokenButton.setMnemonic(KeyEvent.VK_E);
    tokenButton.addActionListener(new TokenAdaptor());
    tokenButton.setEnabled(false);
  }
  
  // Initializes the activity log.
  public void initActivityLog() {
    activityLog = new JTextArea("     Welcome To MTG Deck Tester"+"\n"+"Load a deck to begin..."+"\n",10,20);
    activityLog.setEditable(false); // Disallow editing.
    activityLog.setLineWrap(true); // We must first enable line wrapping in order to enable setWrapStyleWord.
    activityLog.setWrapStyleWord(true); // Automatically insert "\n" at word boundaries.
    scrollPane = new JScrollPane(activityLog);
    scrollPane.setHorizontalScrollBar(null); // The horizontal scroll is bar not necessary.
  }
  
  // Returns the initial hand button.
  public JButton getInitialHandButton() {
    return this.initialHandButton;
  }
  
  // Returns the mulligan button.
  public JButton getMulliganButton() {
    return this.mulliganButton;
  }
  
  // Returns the keep button.
  public JButton getKeepButton() {
    return this.keepButton;
  }
  
  // Returns the untao button.
  public JButton getUntapButton() {
    return this.untapButton;
  }
  
  // Returns the draw button.
  public JButton getDrawButton() {
    return this.drawButton;
  }
  
  // Returns the shuffle button.
  public JButton getShuffleButton() {
    return this.shuffleButton;
  }
  
  // Returns the token button.
  public JButton getTokenButton() {
    return this.tokenButton;
  }
  
  // Returns the activity log.
  public JTextArea getActivityLog() {
    return this.activityLog;
  }
  
  // Returns the scroll pane.
  public JScrollPane getScrollPane() {
    return this.scrollPane;
  }
  
  // Returns the token frame.
  public JFrame getTokenFrame() {
    return this.tokenFrame;
  }
  
  // Assigns a boolean value to the tokenFrameInDisplay variable.
  public void setTokenFrameInDisplay(boolean inDisplay) {
    this.tokenFrameInDisplay = inDisplay;
  }
  
  // Draws the initial hand. Once it has been drawn, disable it and enable mulligan and keep buttons.
  private class InitialHandAdaptor implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      mtg.drawInitialHand();
      initialHandButton.setEnabled(false); // We can only draw our initial hand once.
      mulliganButton.setEnabled(true); // Cannot mulligan until initial hand has been drawn.
      keepButton.setEnabled(true); // Cannot keep until initial hand has been drawn.
    }
  }
  
  // The mulligan button. Disabled once hand is kept.
  private class MulliganAdaptor implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      mtg.mulligan();
    }
  }
  
  // Keeps the hand. Once hand is kept, disable the button and enable draw and create token buttons.
  private class KeepAdaptor implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      mtg.keep();
      mulliganButton.setEnabled(false);
      keepButton.setEnabled(false);
      untapButton.setEnabled(true);
      drawButton.setEnabled(true);
      shuffleButton.setEnabled(true);
      tokenButton.setEnabled(true);
    }
  }
  
  // Untaps all permanent cards on the battlefield.
  private class UntapAdaptor implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      mtg.untap();
    }
  }
  
  // Draws a card. Enabled after a hand has been kept.
  private class DrawAdaptor implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      mtg.draw();
    }
  }
  
  // Shuffles the library. Enabled after a hand has been kept.
  private class ShuffleAdaptor implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      mtg.shuffle();
    }
  }
  
  // Creates a creature token with the specidied stats, and adds it to the main panel.
  private class TokenAdaptor implements ActionListener {
  
  //***Uses a border layout. The center has its own layout, gird layout(4,2)***//
    
    // Components
    private JLabel nameLabel;
    private JTextField nameField; // BorderLayout.NORTH
    private JPanel statsPanel;
    private JLabel powerLabel, toughnessLabel;
    private JTextField powerField, toughnessField;
    private JCheckBox whiteBox, blueBox, blackBox, redBox, greenBox, colorlessBox;
    private JButton createButton; // BorderLayout.SOUTH
    
    // Feilds
    private String tokenType;
    private int power;
    private int toughness;
    private COLOR color;
    private boolean noNameEntered, nameMisformatted, noPowerEntered, powerMisformatted,
      noToughnessEntered, toughnessMisformatted, noColorSelected;
    private boolean atLeastOneColor;
    
    public void actionPerformed(ActionEvent ae) {
      if (!tokenFrameInDisplay) { // The inquiry tokenFrame is not in display.
        tokenFrameInDisplay = true;
        initFields();
        initComponents();
        initLayout();
      }
    }
    
    private void initFields() {
      noNameEntered         = false;
      nameMisformatted      = false;
      noPowerEntered        = false;
      powerMisformatted     = false;
      noToughnessEntered    = false;
      toughnessMisformatted = false;
      noColorSelected       = false;
      atLeastOneColor       = false;
    }
    
    private void initComponents() {
      nameLabel = new JLabel("Enter Token Name");
      nameLabel.setAlignmentX(1f);
      nameField = new JTextField();
      initStatsPanel();
      createButton = new JButton("Create Token");
      createButton.addActionListener(new CreationAdaptor());
      createButton.setAlignmentX(0.62f); // Set manually, as Component.CENTER_ALIGHMENT not working.
    }
    
    private void initLayout() {
      tokenFrame = new JFrame("Token");
      // Need to use getContentPane() when using a box layout for a JFrame!
      tokenFrame.setLayout(new BoxLayout(tokenFrame.getContentPane(), BoxLayout.Y_AXIS));
      tokenFrame.add(nameLabel);
      tokenFrame.add(nameField);
      tokenFrame.add(statsPanel);
      tokenFrame.add(createButton);
      tokenFrame.setSize(220,260); // Do not use setPreferredSize for JFrames.
      tokenFrame.setLocationRelativeTo(null);
      tokenFrame.setResizable(false);
      tokenFrame.setVisible(true);
      tokenFrame.addWindowListener(new WindowAdaptor()); // A custom close operation.
      tokenFrame.pack();
    }
    
    private void initStatsPanel() {
      powerLabel     = new JLabel("Power");
      toughnessLabel = new JLabel("Toughness");
      powerField     = new JTextField();
      toughnessField = new JTextField();
      whiteBox       = new JCheckBox("White");
      blueBox        = new JCheckBox("Blue");
      blackBox       = new JCheckBox("Black");
      redBox         = new JCheckBox("Red");
      greenBox       = new JCheckBox("Green");
      colorlessBox   = new JCheckBox("Colorless");
      colorlessBox.addActionListener( new ColorlessAdaptor());
      statsPanel = new JPanel();
      statsPanel.setLayout(new GridLayout(5,2));
      statsPanel.add(powerLabel);
      statsPanel.add(toughnessLabel);
      statsPanel.add(powerField);
      statsPanel.add(toughnessField);
      statsPanel.add(whiteBox);
      statsPanel.add(blueBox);
      statsPanel.add(blackBox);
      statsPanel.add(redBox);
      statsPanel.add(greenBox);
      statsPanel.add(colorlessBox);
    }
    
    private class ColorlessAdaptor implements ActionListener {
      
      private boolean colorBoxesEnabled = true;
      
      public void actionPerformed(ActionEvent ae) {
       toggle();
      }
      
      private void toggle() {
        if (colorBoxesEnabled) {
          whiteBox.setEnabled(false);
          blueBox.setEnabled(false);
          blackBox.setEnabled(false);
          redBox.setEnabled(false);
          greenBox.setEnabled(false);
        }
        else {
          whiteBox.setEnabled(true);
          blueBox.setEnabled(true);
          blackBox.setEnabled(true);
          redBox.setEnabled(true);
          greenBox.setEnabled(true);
        }
        colorBoxesEnabled = !colorBoxesEnabled;
      }
      
    }
    
    private class CreationAdaptor implements ActionListener {
      
      public void actionPerformed(ActionEvent ae) {
        initName();
        initPower();
        initToughness();
        initColor();
        if (!(noNameEntered || nameMisformatted || noPowerEntered || powerMisformatted ||
              noToughnessEntered || toughnessMisformatted || noColorSelected)) {
          mtg.createToken(tokenType,color,power,toughness);
          tokenFrame.setVisible(false);
          tokenFrameInDisplay = false;
        }
        else {
          StringBuffer buf = new StringBuffer();
          if (noNameEntered)         buf.append("A token name must be entered"+"\n");
          if (nameMisformatted)      buf.append("A vaild token name must consist of alphabetical letters"+"\n");
          if (noPowerEntered)        buf.append("A power must be entered"+"\n");
          if (powerMisformatted)     buf.append("A vaild power must consist of neumerical letters"+"\n");
          if (noToughnessEntered)    buf.append("A toughness must be entered"+"\n");
          if (toughnessMisformatted) buf.append("A vaild toughness must consist of neumerical letters"+"\n");
          if (noColorSelected)       buf.append("At lease one color must be selected");
          JOptionPane.showMessageDialog(tokenFrame, buf.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
      
      private void initName() {
        String text = nameField.getText();
        if (isEmptyString(text)) {
          noNameEntered = true;
          return;
        }
        else {
          noNameEntered = false;
        }
        if (containsNonletters(text)) { // A token name must consist of alphabetical letters and spaces.
          nameMisformatted = true;
          return;
        }
        else {
          nameMisformatted = false;
        }
        tokenType = text;
      }
      
      private void initPower() {
        String text = powerField.getText();
        if (isEmptyString(text)) {
          noPowerEntered = true;
          return;
        }
        else {
          noPowerEntered    = false;
        }
        if (containsNondigits(text)) { // The token power must consist of neumerical letters.
          powerMisformatted = true;
          return;
        }
        else {
          powerMisformatted = false;
        }
        power = Integer.parseInt(text); // Will not throw an exception, as "text" is checked.
      }
      
      private void initToughness() {
        String text = toughnessField.getText();
        if (isEmptyString(text)) {
          noToughnessEntered = true;
          return;
        }
        else {
          noToughnessEntered    = false;
        }
        if (containsNondigits(text)) { // The token toughness must consist of neumerical letters.
          toughnessMisformatted = true;
          return;
        }
        else {
          toughnessMisformatted = false;
        }
        toughness = Integer.parseInt(text); // Will not throw an exception, as "text" is checked.
      }
      
      private void initColor() {
        noColorSelected = false;
        if (colorlessBox.isSelected()) {
          color = COLOR.COLORLESS;
          return;
        }
        else {
          if (whiteBox.isSelected()) {
            setColor(COLOR.WHITE);
          }
          if (blueBox.isSelected()) {
            setColor(COLOR.BLUE);
          }
          if (blackBox.isSelected()) {
           setColor(COLOR.BLACK);
          }
          if (redBox.isSelected()) {
           setColor(COLOR.RED);
          }
          if (greenBox.isSelected()) {
           setColor(COLOR.GREEN);
          } 
        }
        if (!atLeastOneColor) {
          noColorSelected = true;
        }
      }
      
    }
    
    private void setColor(COLOR inputColor) {
      if (atLeastOneColor) {
        color = COLOR.GOLD;
      }
      else {
        color = inputColor;
        atLeastOneColor = true;
      }
    }
    
    private class WindowAdaptor implements WindowListener {
      public void windowClosing(WindowEvent we)     { tokenFrameInDisplay = false; }
      public void windowActivated(WindowEvent we)   {}
      public void windowClosed(WindowEvent we)      {}
      public void windowDeactivated(WindowEvent we) {}
      public void windowDeiconified(WindowEvent we) {}
      public void windowIconified(WindowEvent we)   {}
      public void windowOpened(WindowEvent we)      {}
    }
    
    private boolean containsNonletters(String str) {
      char[] charSeq = str.toCharArray();
      for (int i = 0; i < charSeq.length; i++) {
        if (!(Character.isLetter(charSeq[i]) || charSeq[i] == ' ')) {
          return true;
        }
      }
      return false;
    }
    
    private boolean containsNondigits(String str) {
      char[] charSeq = str.toCharArray();
      for (int i = 0; i < charSeq.length; i++) {
        if (!Character.isDigit(charSeq[i])) {
          return true;
        }
      }
      return false;
    }
    
    private boolean isEmptyString(String str) {
      char[] charSeq = str.toCharArray();
      for (int i = 0; i < charSeq.length; i++) {
        if (charSeq[i] != ' ') {
          return false;
        }
      }
      return true;
    }
    
  }
 
  
  
}