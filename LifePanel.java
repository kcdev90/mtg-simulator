package mtgSimulator;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
  
public class LifePanel extends JPanel implements Constants {
  
  private static final int LABEL_WIDTH  = 82;
  private static final int LABEL_HEIGHT = 36;
  
  private JLabel playerLabel;
  private JLabel opponentLabel;
  private int playerLife;
  private int opponentLife;
  
  public LifePanel() {
    this.playerLife = 20;
    this.opponentLife = 20;
    setPreferredSize(new Dimension(LIFE_PANEL_WIDTH, LIFE_PANEL_HEIGHT));
    setLayout(new GridBagLayout());
    JButton button;
    GridBagConstraints c = new GridBagConstraints();
    //c.fill = GridBagConstraints.BOTH;
    c.weightx = 0;
    c.weighty = 0;
    
    playerLabel = new JLabel(getString(playerLife));
    playerLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
    playerLabel.setFont(new Font(null,0,20));
    c.ipadx = LABEL_WIDTH;
    c.ipady = LABEL_HEIGHT;
    c.gridx = 0;
    c.gridy = 0;
    c.gridheight = 2;
    add(playerLabel, c);
    
    button = new JButton("+");
    button.setPreferredSize(new Dimension(LABEL_HEIGHT/2, 10));
    button.addActionListener(new LifeAdaptor(true,true));
    c.ipadx = LABEL_HEIGHT/2;
    c.ipady = LABEL_HEIGHT/2;
    c.gridx = 0;
    c.gridy = 0;
    c.gridheight = 1;
    add(button, c);
    
    button = new JButton("-");
    button.setPreferredSize(new Dimension(LABEL_HEIGHT/2, 10));
    button.addActionListener(new LifeAdaptor(true,false));
    c.ipadx = LABEL_HEIGHT/2;
    c.ipady = LABEL_HEIGHT/2;
    c.gridx = 0;
    c.gridy = 1;
    c.gridheight = 1;
    add(button, c);
    
    opponentLabel = new JLabel(getString(opponentLife));
    opponentLabel.setPreferredSize(new Dimension(20, LABEL_HEIGHT));
    opponentLabel.setFont(new Font(null,0,20));
    c.ipadx = LABEL_WIDTH;
    c.ipady = LABEL_HEIGHT;
    c.gridx = 2;
    c.gridy = 0;
    c.gridheight = 2;
    add(opponentLabel, c);
   
    button = new JButton("+");
    button.setPreferredSize(new Dimension(LABEL_HEIGHT/2, LABEL_HEIGHT/2));
    button.addActionListener(new LifeAdaptor(false,true));
    c.ipadx = LABEL_HEIGHT/2;
    c.ipady = LABEL_HEIGHT/2;
    c.gridx = 2;
    c.gridy = 0;
    c.gridheight = 1;
    add(button, c);
    
    button = new JButton("-");
    button.setPreferredSize(new Dimension(LABEL_HEIGHT/2, LABEL_HEIGHT/2));
    button.addActionListener(new LifeAdaptor(false, false));
    c.ipadx = LABEL_HEIGHT/2;
    c.ipady = LABEL_HEIGHT/2;
    c.gridx = 2;
    c.gridy = 1;
    c.gridheight = 1;
    add(button, c);
  }
  
  private String getString(int n) {
    Integer N = (Integer) n;
    return N.toString();
  }
  
  // Sets the player life to the specified int value.
  public void setPlayerLife(int life) {
    this.playerLife = life;
    this.playerLabel.setText(getString(life));
  }
  
  // Sets the oppoent life to the specified int value.
  public void setOpponentLife(int life) {
    this.opponentLife = life;
    this.opponentLabel.setText(getString(life));
  }
  
    // A smart 4-in-1 action listener. Determines wheter the player or opponent's life should increase or decrease.
  private class LifeAdaptor implements ActionListener {
    
    private boolean player;
    private boolean increase;
    
    private LifeAdaptor(boolean player, boolean increase) {
      this.player = player;
      this.increase = increase;
    }
    
    public void actionPerformed(ActionEvent ae) {
      if (player && increase) {
        playerLife++;
        playerLabel.setText(getString(playerLife));
      }
      if (player && !increase) {
        playerLife--;
        playerLabel.setText(getString(playerLife));
      }
      if (!player && increase) {
        opponentLife++;
        opponentLabel.setText(getString(opponentLife));
      }
      if (!player && !increase) {
        opponentLife--;
        opponentLabel.setText(getString(opponentLife));
      }
    }
    
  }
  
}