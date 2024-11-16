package Main;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame Frame;

    public GameWindow(Panel gamePanel) {
    
    Frame = new JFrame();

    Frame.add(gamePanel);
    Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Frame.setResizable(true);
    Frame.pack();
    Frame.setLocationRelativeTo(null);
    Frame.setVisible(true);
    Frame.addWindowFocusListener(new WindowFocusListener() {

        @Override
        public void windowGainedFocus(WindowEvent e) {

        }

        @Override
        public void windowLostFocus(WindowEvent e) {
            gamePanel.getGame().windowLostFocus();
        }
        
    });
    }

}