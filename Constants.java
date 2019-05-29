package mtgSimulator;

// Uses a box layout. Uses all 5 panels, NORTH, EAST, SOUTH, WEST, and CENTER.
public interface Constants {
  
  //*****************Layout View*****************//
  
  /*|---------------------------------------------|
    |              (Optioin Panel)                |
    |---------------------------------------------|
    | P B |                                 | P G |
    | a u |                                 | n Y |
    | n t |              Main               | l   |
    | e t |                                 |-----|
    | l o |              Panel              | P L |
    |   n |                                 | n B |
    |     |                                 | l R |
    |---------------------------------------------|
    |                (Hand Panel)                 |
    |---------------------------------------------|
   */
  
  //***Important Notice: Instants and sorceries are treated as enchantments that immediately go to the graveyard!***//
  public static enum TYPE  { CREATURE, PLANESWALKER, ENCHANTMENT, ARTIFACT, LAND }
  public static enum COLOR { WHITE, BLUE, BLACK, RED, GREEN, GOLD, COLORLESS }
  public static enum ZONE  { GRAVEYARD, HAND, TOP, BOTTOM, REMOVE }
  
  // The dimensions for the Frame
  public static final int WINDOW_WIDTH = 1000;
  public static final int WINDOW_HEIGHT = 618;
  
  // The dimensions for each panel.
  public static final int MENU_PANEL_WIDTH  = WINDOW_WIDTH;
  public static final int MENU_PANEL_HEIGHT = 20;
  public static final int HAND_PANEL_WIDTH    = WINDOW_WIDTH;
  public static final int HAND_PANEL_HEIGHT   = 168;
  
  // If resizable, the height of zone panel changes, but the width is fixed.
  public static final int ZONE_PANEL_WIDTH     = 150;
  public static final int ZONE_PANEL_HEIGHT    = WINDOW_HEIGHT - MENU_PANEL_HEIGHT - HAND_PANEL_HEIGHT;
  
  // The utility panel contains the life panel and the button panel. The height varies if resizable.
  public static final int UTILITY_PANEL_WIDTH  = 200;
  public static final int UTILITY_PANEL_HEIGHT = ZONE_PANEL_HEIGHT;
  public static final int LIFE_PANEL_WIDTH     = UTILITY_PANEL_WIDTH;
  public static final int LIFE_PANEL_HEIGHT    = 40;
  public static final int BUTTON_PANEL_WIDTH   = UTILITY_PANEL_WIDTH;
  public static final int BUTTON_PANEL_HEIGHT  = UTILITY_PANEL_HEIGHT - LIFE_PANEL_HEIGHT;
  
  // If resizable, both the width and height of the main panel may vary.
  public static final int MAIN_PANEL_WIDTH    = WINDOW_WIDTH - BUTTON_PANEL_WIDTH - ZONE_PANEL_WIDTH;
  public static final int MAIN_PANEL_HEIGHT   = ZONE_PANEL_HEIGHT;
  
  // The dimensions for a Card.
  public static final int CARD_WIDTH = 80;
  public static final int CARD_HEIGHT = 112;
  
  // The URLs for the card image, the background of the main panel, and that of the hand panel.
  public static final String CARD_IMAGE_URL = "http://images1.wikia.nocookie.net/__cb20090507031322/"
    +"mtgfanfiction/images/thumb/a/aa/Magic_the_gathering-card_back.jpg/100px-Magic_the_gathering-card_back.jpg";
  public static final String DEFAULT_PLAYMAT_URL = 
    "http://fc07.deviantart.net/fs36/i/2011/109/d/c/texture_55_by_sirius_sdz-d1n4jk1.jpg";
  public static final String PLAYMAT2_URL = 
    "http://www.xn--19201080-jdh.xn--p1ai/tekstura/17/17_zelenaya_tekstura_oboi_1920x1080.jpg";
  public static final String PLAYMAT3_URL = 
    "http://www.tofuhaus.com/images/2013/01/wood-background-texture-background-download.jpg";
  public static final String DEFAULT_BACKGROUND_URL =
    "http://2.bp.blogspot.com/-rF993k1TBdU/UEY-m04e0zI/AAAAAAAAGA8/ti-WNCcJ_KU/s1600/Pattern+Wallpapers+(30).jpg";
  public static final String BACKGROUND2_URL =
    "http://3.bp.blogspot.com/_KFgK4fTHWmk/TFdhqO9lBVI/AAAAAAAABCk/mOXAZpXbEiI/s1600/vintage_wallpaper2.jpg";
  public static final String BACKGROUND3_URL =
    "http://wallpaperscraft.com/image/patterns_wall_old_paint_18592_1680x1050.jpg";
  
  
  
}