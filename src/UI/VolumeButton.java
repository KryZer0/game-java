package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utility.SaveLoad;
import static utility.player_constant.UI.VolumeButton.*;

public class VolumeButton extends PauseButton{

    private BufferedImage[] imgs;
    private BufferedImage slider;
    private int index = 0;
    private int buttonX, minX, maxX;
    private boolean mouseHover, mousePressed;

    public VolumeButton(int x, int y, int width, int height) {
        super(x + width / 2, y, VolumeX, height);
        bounds.x -= VolumeX / 2;
        buttonX = x + width / 2 ;
        this.x = x;
        this.width = width;
        minX = x + VolumeX / 2;
		maxX = x + width - VolumeX / 2;
        loadButton();
        
    }

    private void loadButton() {
        BufferedImage is = SaveLoad.getTextureAtlas(SaveLoad.VOLUME_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = is.getSubimage(i * VolumeXSizeDefault, 0, VolumeXSizeDefault, VolumeYSizeDefault);
        }
        slider = is.getSubimage(3 * VolumeXSizeDefault, 0, VolumeSliderXDefault, VolumeYSizeDefault);
    }

    public void update() {
        index = 0;
        if (mouseHover) {
            index = 1;
        } if (mousePressed) {
            index = 2;
        }
    }
    public void resetButtons() {
        mouseHover = false;
        mousePressed = false;
    }

    public void draw(Graphics g) {
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(imgs[index], buttonX - VolumeX / 2, y, VolumeX, VolumeY, null);
    }

    public void ChangeVolumeX(int x) {
        if (x < minX) {
            buttonX = minX;
        } else if (x > maxX) {
            buttonX = maxX;
        } else {
            buttonX = x;
        }
        bounds.x = buttonX - VolumeX / 2;
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
