package Inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Main.Panel;
import gameStates.GameState;

public class InputMouse implements MouseListener, MouseMotionListener{
    private Panel panel;

    public InputMouse(Panel panel){
        this.panel = panel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch(GameState.state){
            case Playing:
                panel.getGame().getPlaying().mouseDragged(e);
                break;
            default:
                break;
            
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch(GameState.state){
            case Menu:
                panel.getGame().getMenu().mouseMoved(e);
                break;
            case Playing:
                panel.getGame().getPlaying().mouseMoved(e);
                break;
            default:
                break;
            
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch(GameState.state){
            case Playing:
                panel.getGame().getPlaying().mouseClicked(e);
                break;
            default:
                break;
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(GameState.state){
            case Menu:
                panel.getGame().getMenu().mousePressed(e);
                break;
            case Playing:
                panel.getGame().getPlaying().mousePressed(e);
                break;
            default:
                break;
            
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch(GameState.state){
            case Menu:
                panel.getGame().getMenu().mouseReleased(e);
                break;
            case Playing:
                panel.getGame().getPlaying().mouseReleased(e);
                break;
            default:
                break;
            
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
}
