package gameStates;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import UI.MenuButton;
import utility.SaveLoad;

public class Menu extends State implements StateMethod {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage MenuPanel;
    private int MenuX, MenuY, MenuWidth, MenuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadMenuPanel();
    }

    private void loadMenuPanel() {
        MenuPanel = SaveLoad.getTextureAtlas(SaveLoad.MENU_PANEL);
        MenuWidth = (int) (MenuPanel.getWidth() * Game.Scale);
        MenuHeight = (int) (MenuPanel.getHeight() * Game.Scale);
        MenuX = Game.Game_Width / 2 - MenuWidth / 2;
        MenuY = (int) (45 * Game.Scale);

    }

    private void loadButtons() {
        buttons [0] = new MenuButton(Game.Game_Width / 2, (int) (150 * Game.Scale), 0,GameState.Playing);
        buttons [1] = new MenuButton(Game.Game_Width / 2, (int) (220 * Game.Scale), 1,GameState.Options);
        buttons [2] = new MenuButton(Game.Game_Width / 2, (int) (290 * Game.Scale), 2,GameState.Quit);
    }

    @Override
    public void update() {
        for(MenuButton MB : buttons)
            MB.update();       
        
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(MenuPanel, MenuX, MenuY, MenuWidth, MenuHeight, null);
        for (MenuButton MB : buttons) {
            MB.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton MB : buttons)
            if (isIn(e, MB)) {
                MB.setmousePressed(true);
            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton MB : buttons){
            if (isIn(e, MB)) {
                if (MB.isMousePressed())
                    MB.applyGameState();
                break;
            }
        }
        resetButton();
    }

    private void resetButton() {
        for (MenuButton MB : buttons)
            MB.resetButton();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton MB : buttons) {
            MB.mouseHover(false);
        }

        for (MenuButton MB : buttons) {
            if (isIn(e, MB)) {
                MB.mouseHover(true);
                break;
            }
        } 
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
}
