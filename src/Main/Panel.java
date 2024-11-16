package Main;

import java.awt.*;
import javax.swing.JPanel;
import Inputs.InputKeyboard;
import Inputs.InputMouse;
import static Main.Game.Game_Width;
import static Main.Game.Game_Height;

public class Panel extends JPanel {

    private InputMouse inputMouse;
    private Game game;

    // this method is to add panel to the game window
    public Panel(Game game){
        inputMouse = new InputMouse(this);
        this.game = game;

        PanelSize();
        addMouseListener(inputMouse);
        addKeyListener(new InputKeyboard(this));
        addMouseMotionListener(inputMouse);
    }

    public void updateGame() {

    }
    

    // this method is to assign panelSize to preferredSize, which in this case is 800x600
    private void PanelSize() {
        Dimension size = new Dimension(Game_Width, Game_Height);
        setPreferredSize(size);
        System.out.println("Size = " + Game_Width + " * " + Game_Height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame(){
        return game;
    }

}
