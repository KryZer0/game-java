package gameStates;

import java.awt.event.MouseEvent;

import Main.Game;
import UI.MenuButton;

public class State {
    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public boolean isIn (MouseEvent e, MenuButton MB) {        
        return MB.getBounds().contains(e.getX(),e.getY());
    }

    public Game getGame() {
        return game;
    }
}
