package Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Main.Panel;
import gameStates.GameState;

public class InputKeyboard implements KeyListener {

    private Panel panel;

    public InputKeyboard(Panel panel){
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

        }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(GameState.state) {
            case Menu:
                panel.getGame().getMenu().keyPressed(e);
                break;
            case Playing:
                panel.getGame().getPlaying().keyPressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(GameState.state) {
            case Menu:
                panel.getGame().getMenu().keyReleased(e);
                break;
            case Playing:
                panel.getGame().getPlaying().keyReleased(e);
                break;
            default:
                break;
        }
    }
}
