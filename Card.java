package mtgSimulator;

public class Card implements Constants {
  
  // Fields
  private TYPE type;
  private COLOR color;
  private String cardName;
  private String manaCost;
  private int power;
  private int toughness;
  private int loyalty;
  
  // The X and Y coordinates of the top-left corner. Used when rendering.
  private int x;
  private int y;
  private boolean tapped      = false;
  private boolean highlighted = false;
  
  // The smart constructor method for creatures.
  public Card(String cardName, String manaCost, COLOR color, int power, int toughness) {
    this.type = TYPE.CREATURE;
    this.cardName = cardName;
    this.manaCost = manaCost;
    this.color = color;
    this.power = power;
    this.toughness = toughness;
  }
  
  // The smart constructor method for planeswalkers.
  public Card(String cardName, String manaCost, COLOR color, int loyalty) {
    this.type = TYPE.PLANESWALKER;
    this.cardName = cardName;
    this.manaCost = manaCost;
    this.color = color;
    this.loyalty = loyalty;
  }
  
  // The smart constructor method for enchantments (instants and sorceries are subtypes of enchantments).
  public Card(String cardName, String manaCost, COLOR color) {
    this.type = TYPE.ENCHANTMENT;
    this.cardName = cardName;
    this.manaCost = manaCost;
    this.color = color;
  }
  
  // The smart constructor method for noncreature artifacts.
  public Card(String cardName, String manaCost) {
    this.type = TYPE.ARTIFACT;
    this.cardName = cardName;
    this.manaCost = manaCost;
  }
  
  // The smart constructor method for lands.
  public Card(String cardName) {
    this.type = TYPE.LAND;
    this.cardName = cardName;
  }
  
  // The smart constructor method for creature tokens.
  public Card(String tokenType, COLOR color, int power, int toughness) {
    this.type = TYPE.CREATURE; // A token is always a creature.
    this.cardName = tokenType;
    this.color = color;
    this.power = power;
    this.toughness = toughness;
  }
  
  public TYPE getType() { return this.type; }
  
  public COLOR getColor() { return this.color; }
  
  public String getCardName() { return this.cardName; }
  
  public String getManaCost() { return this.manaCost; }
  
  public int getPower() { return this.power; }
  
  public int getToughness() { return this.toughness; }
  
  public int getLoyalty() { return this.loyalty; }
  
  public boolean isTapped() { return this.tapped; }
  
  public void setTapped(boolean state) { this.tapped = state; }
  
  public boolean isHighlighted() { return this.highlighted; }
  
  public void setHighlighted(boolean highlighted) { this.highlighted = highlighted; }
    
  public int getX() { return this.x; }
  
  public void setX(int x) { this.x = x; }
  
  public int getY() { return this.y; }
  
  public void setY(int y) { this.y = y; }
  
  public int getConvertedManaCost() {
    if (this.manaCost == null) { return -1; } // "null" is an invalid parameter.
    if (this.manaCost.length() == 0) { return 0; } // CMC of lands = 0
    String firstChar = this.manaCost.substring(0,1); // Get the first char.
    try {
      int firstNumber = Integer.parseInt(firstChar); // The mana cost contains a number: ex 3UR, 1BB
      return this.manaCost.length() + firstNumber - 1;
    }
    catch (NumberFormatException nfe) { // No numbers included in the mana cost: ex. GG, BW
      return this.manaCost.length();
    }
  }
  
  
  
}