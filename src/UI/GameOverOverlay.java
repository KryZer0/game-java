package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Main.Game;
import gameStates.GameState;
import gameStates.Playing;

public class GameOverOverlay {
    
    private Playing playing;
    public GameOverOverlay(Playing playing) {
        this.playing = playing;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.Game_Width, Game.Game_Height);

        g.setColor(Color.ORANGE);
        g.drawString("You're Dead", Game.Game_Width / 2, Game.Game_Height / 2);
        g.drawString("press escape to enter the main menu",Game.Game_Width / 2, Game.Game_Height / 4);
    }

    public void KeyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameState.state = GameState.Menu;
        }
    }
}
