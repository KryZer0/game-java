package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import gameStates.GameState;
import gameStates.Playing;
import utility.SaveLoad;
import static utility.player_constant.UI.CreButton.*;

public class LevelCompleteOverlay {
    
    private Playing playing;
    private CreButton MenuB, RestartB, NextB;
    private BufferedImage img;
    private int imgX, imgY, imgW, imgH;

    public LevelCompleteOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButton();
        
    }

    private void initButton() {
        int MenuX = (int) (325 * Game.Scale);
        int ReplayX = (int) (387.5 * Game.Scale);
        int ContinueX = (int) (450 * Game.Scale);
        int bY = (int) (195 *Game.Scale);

        MenuB = new CreButton(MenuX, bY, CRESize, CRESize, 2);
        RestartB = new CreButton(ReplayX, bY, CRESize, CRESize, 1);
        NextB = new CreButton(ContinueX, bY, CRESize, CRESize, 0);
    }

    private void initImg() {
        img = SaveLoad.getTextureAtlas(SaveLoad.COMPLETE_PANEL);
        imgW = (int) (img.getWidth() * Game.Scale);
        imgH = (int) (img.getHeight() * Game.Scale);
        imgX = Game.Game_Width / 2 - imgW / 2;
        imgY = (int) (75 * Game.Scale);
    }

    public void update() {
        MenuB.update();
        RestartB.update();
        NextB.update();
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.Game_Width, Game.Game_Height);

        g.drawImage(img, imgX, imgY, imgW, imgH, null);
        MenuB.draw(g);
        RestartB.draw(g);
        NextB.draw(g);

    }

    public void mouseMoved(MouseEvent e) {
        MenuB.setMouseHover(false);
        RestartB.setMouseHover(false);
        NextB.setMouseHover(false);

        if (isIn (e, MenuB)) {
            MenuB.setMouseHover(true);
        } else if (isIn (e, RestartB)){
            RestartB.setMouseHover(true);
        } else if (isIn (e, NextB)) {
            NextB.setMouseHover(true);
        }
        
    }

    public void mousePressed(MouseEvent e) {
        if (isIn (e, MenuB)){
            MenuB.setMousePressed(true);
        } else if (isIn (e, RestartB)){
            RestartB.setMousePressed(true);
        } else if (isIn (e, NextB)){
            NextB.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, MenuB)){
            if (MenuB.isMousePressed()){
                GameState.state = GameState.Menu;
            }
        } else if (isIn(e, RestartB)){
            if (RestartB.isMousePressed()){
                playing.resetAll();
                playing.ContinueGame();
//                System.out.println("Replay button will added next update");
            }
        } else if (isIn(e, NextB)){
            if (NextB.isMousePressed()){
                playing.loadNextLevel();
            }
        }
        MenuB.resetButtons();
        RestartB.resetButtons();
        NextB.resetButtons();
    }

    public boolean isIn(MouseEvent e, CreButton p) {
        return p.getBounds().contains(e.getX(), e.getY());
    }

}
