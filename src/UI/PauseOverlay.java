package UI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import gameStates.GameState;
import gameStates.Menu;
import gameStates.Playing;
import utility.SaveLoad;
import gameStates.Playing;
import static utility.player_constant.UI.PauseButtons.*;
import static utility.player_constant.UI.CreButton.*;
import static utility.player_constant.UI.VolumeButton.*;

public class PauseOverlay {
 
    private Playing playing;
    private BufferedImage PausePanel;
    private int pauseX, pauseY, PauseWidth, PauseHeight;
    private SoundButton sfxButton, musicButton;
    private CreButton MenuB, ReplayB, ContinueB;
    private VolumeButton volumeButton;


    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadPausePanel();
        CreateSoundButtons();
        CreateCREButtons();
        CreateVolumeButtons();
    }

    private void CreateVolumeButtons() {
        int volX = (int) (309 * Game.Scale);
        int volY = (int) (278 * Game.Scale);
        volumeButton = new VolumeButton(volX, volY, VolumeSliderX, VolumeY);
    }

    private void CreateCREButtons() {
        int MenuX = (int) (313 * Game.Scale);
        int ReplayX = (int) (387 * Game.Scale);
        int ContinueX = (int) (462 * Game.Scale);
        int bY = (int) (325 *Game.Scale);

        MenuB = new CreButton(MenuX, bY, CRESize, CRESize, 2);
        ReplayB = new CreButton(ReplayX, bY, CRESize, CRESize, 1);
        ContinueB = new CreButton(ContinueX, bY, CRESize, CRESize, 0);
    }

    private void CreateSoundButtons() {
        int SoundX = (int) (450 * Game.Scale);
        int musicY = (int) (140 * Game.Scale);
        int sfxY = (int) (180 * Game.Scale);
        musicButton = new SoundButton(SoundX, musicY, SoundSize, SoundSize);
        sfxButton = new SoundButton(SoundX, sfxY, SoundSize, SoundSize);
    }

    private void loadPausePanel() {
        PausePanel = SaveLoad.getTextureAtlas(SaveLoad.PAUSE_PANEL);
        PauseWidth = (int) (PausePanel.getWidth() * Game.Scale);
        PauseHeight = (int) (PausePanel.getHeight() * Game.Scale);
        pauseX = Game.Game_Width / 2 - PauseWidth / 2;
        pauseY = (int) (20 * Game.Scale);

    }

    public void update() {
        musicButton.update(); 
        sfxButton.update();

        MenuB.update();
        ReplayB.update();
        ContinueB.update();

        volumeButton.update();
    }

    public void draw(Graphics g) {
        g.drawImage(PausePanel, pauseX, pauseY, PauseWidth, PauseHeight,null);

        musicButton.draw(g);
        sfxButton.draw(g);

        MenuB.draw(g);
        ReplayB.draw(g);
        ContinueB.draw(g);

        volumeButton.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn (e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn (e, sfxButton)){
            sfxButton.setMousePressed(true);
        } else if (isIn (e, MenuB)){
            MenuB.setMousePressed(true);
        } else if (isIn (e, ReplayB)){
            ReplayB.setMousePressed(true);
        } else if (isIn (e, ContinueB)){
            ContinueB.setMousePressed(true);
        } else if (isIn (e, volumeButton)){
            volumeButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if (isIn(e, sfxButton)){
            if (sfxButton.isMousePressed()){
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        } else if (isIn(e, MenuB)){
            if (MenuB.isMousePressed()){
                GameState.state = GameState.Menu;
            }
        } else if (isIn(e, ReplayB)){
            if (ReplayB.isMousePressed()){
                playing.resetAll();
                playing.ContinueGame();
            }
        } else if (isIn(e, ContinueB)){
            if (ContinueB.isMousePressed()){
                playing.ContinueGame();
            }
        }
        musicButton.resetButtons();
        sfxButton.resetButtons();
        MenuB.resetButtons();
        ReplayB.resetButtons();
        ContinueB.resetButtons();
        volumeButton.resetButtons();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseHover(false);
        sfxButton.setMouseHover(false);
        MenuB.setMouseHover(false);
        ReplayB.setMouseHover(false);
        ContinueB.setMouseHover(false);

        if (isIn (e, musicButton)) {
            musicButton.setMouseHover(true);
        } else if (isIn (e, sfxButton)) {
            sfxButton.setMouseHover(true);
        } else if (isIn (e, MenuB)) {
            MenuB.setMouseHover(true);
        } else if (isIn (e, ReplayB)){
            ReplayB.setMouseHover(true);
        } else if (isIn (e, ContinueB)) {
            ContinueB.setMouseHover(true);
        }
        
    }

    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed()) {
            volumeButton.ChangeVolumeX(e.getX());
        }
    }

    public boolean isIn(MouseEvent e, PauseButton p) {
        return p.getBounds().contains(e.getX(), e.getY());
    }
}
