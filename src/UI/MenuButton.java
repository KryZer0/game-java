package UI;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gameStates.GameState;
import utility.SaveLoad;
import static utility.player_constant.UI.Button.*;

public class MenuButton {
    
    private int xPos, yPos, RowIndexButton, index;
    private int xOffsetCenter = Button_Width / 2;
    private GameState state;
    private BufferedImage[] imgs;
    private boolean mouseHover, mousePressed;
    private Rectangle Bounds;

    public MenuButton(int xPos, int yPos, int RowIndexButton, GameState state){
        this.xPos = xPos;
        this.yPos = yPos;
        this.RowIndexButton = RowIndexButton;
        this.state = state;
        loadImg();
        initBound();
    }

    private void initBound() {
        Bounds = new Rectangle(xPos - xOffsetCenter, yPos, Button_Width, Button_Height);
    }

    private void loadImg() {
        imgs = new BufferedImage[3];
        BufferedImage is = SaveLoad.getTextureAtlas(SaveLoad.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = is.getSubimage(i * Button_Width_Default, RowIndexButton * Button_Height_Default, Button_Width_Default, Button_Height_Default);
        }
    }

    public void draw (Graphics g){
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, Button_Width, Button_Height, null);
    }

    public void update() {
        index = 0;
        if (mouseHover){
            index = 1;
        } if (mousePressed) {
            index = 2;
        }
    }

    public boolean isMouseHover() {
        return mouseHover;
    }

    public void mouseHover(boolean mouseHover){
        this.mouseHover = mouseHover;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setmousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }
    
    public Rectangle getBounds() {
        return Bounds;
    }

    public void applyGameState() {
        GameState.state = state;
    }

    public void resetButton() {
        mouseHover = false;
        mousePressed = false;
    }
}
