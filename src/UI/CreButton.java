package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utility.SaveLoad;
import static utility.player_constant.UI.CreButton.*;

public class CreButton extends PauseButton {

    private BufferedImage[] imgs;
    private int rowIndex, index;
    private boolean mouseHover, mousePressed;

    public CreButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadButton();
    }

    private void loadButton() {
        BufferedImage is = SaveLoad.getTextureAtlas(SaveLoad.CRE_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = is.getSubimage(i * CRESizeDefault, rowIndex * CRESizeDefault, CRESizeDefault, CRESizeDefault);
        }
    }

    public void update() {
        index = 0;
        if (mouseHover) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }
    public void resetButtons() {
        mouseHover = false;
        mousePressed = false;
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, CRESize, CRESize, null);
    }
    
    public boolean isMouseHover() {
        return mouseHover;
    }

    public void setMouseHover(Boolean mouseHover) {
        this.mouseHover = mouseHover;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
