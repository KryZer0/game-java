package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utility.SaveLoad;
import static utility.player_constant.UI.PauseButtons.*;

public class SoundButton extends PauseButton {
    private BufferedImage[][] soundImages;
    private int rowIndex, colIndex;
    private boolean mouseHover, mousePressed;
    private boolean muted;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        
        loadSoundsImages();
    }

    private void loadSoundsImages() {
        BufferedImage temp = SaveLoad.getTextureAtlas(SaveLoad.SOUND_BUTTONS);
        soundImages = new BufferedImage[2][3];
        for (int j = 0; j < soundImages.length; j++){
            for (int i = 0; i < soundImages[j].length; i++){
                soundImages[j][i] = temp.getSubimage(i * SoundSizeDefault, j *SoundSizeDefault, SoundSizeDefault, SoundSizeDefault);
            }
        }

    }

    void update() {
        if (muted) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        colIndex = 0;
        if (mouseHover) {
            colIndex = 1;
        }if (mousePressed) {
            colIndex = 2;
        }
    }

    public void resetButtons() {
        mouseHover = false;
        mousePressed = false;
    }

    void draw(Graphics g) {
        g.drawImage(soundImages[rowIndex][colIndex], x, y, SoundSize, SoundSize, null);
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

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
    
}
