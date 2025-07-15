package Main;

import java.awt.Graphics;

import gameStates.GameState;
import gameStates.Menu;
import gameStates.Playing;

public class Game implements Runnable {
    
    private GameWindow gameWindow;
    private Panel GamePanel;
    private Thread Gamethread;
    private final int FPS_SET = 75;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;

    public long lastCheck;
    public int fps;

    public final static int Default_Tiles_Sizes = 32;
    public final static int Tiles_in_Width = 26;
    public final static int Tiles_in_Height = 14;
    public final static float Scale = 1.5f;
    public final static int Tiles_Size = (int) (Default_Tiles_Sizes * Scale);
    public final static int Game_Width = Tiles_Size * Tiles_in_Width;
    public final static int Game_Height = Tiles_Size * Tiles_in_Height;

    public Game() {
        initClasses();

        GamePanel = new Panel(this);
        gameWindow = new GameWindow(GamePanel);
        GamePanel.setFocusable(true);;
        GamePanel.requestFocus();

        GameLoop();
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void GameLoop() {
        Gamethread = new Thread(this);
        Gamethread.start();
    }

    public void update() {
        switch(GameState.state) {
            case Menu:
                menu.update();
                break;
            case Playing:
                playing.update();
                break;
            case Options:
                
                break;
            case Quit:
            default:
                System.exit(0);
                break;
            
        }
    }

    public void render(Graphics g){
        switch(GameState.state) {
            case Menu:
                menu.draw(g);
                break;
            case Playing:
                playing.draw(g);
                break;
            default:
                break;
            
        }
    }

    @Override
    public void run() {
        double timePerFrames = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long lastframe = System.nanoTime();

        long previous = System.nanoTime();
        int updates = 0;

        lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true) {
            long currentframe = System.nanoTime();
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previous) / timePerUpdate;
            deltaF += (currentTime - previous) / timePerFrames;
            previous = currentTime;

            if (deltaU >= 1.0){
                update();
                updates++;
                deltaU--;
            }
            if (deltaF >= 1.0){
                GamePanel.repaint();
                fps++;
                deltaF--;
            }

        if (System.currentTimeMillis() - lastCheck >= 1000){
            lastCheck = System.currentTimeMillis();
            //System.out.println("FPS : " + fps /*+ " | " + "UPS : " + updates*/);
            fps = 0;
            updates = 0;
        }
        }
    }

    public void windowLostFocus() {
        if(GameState.state == GameState.Playing) {
            playing.PauseGame();
            playing.getPlayer().resetdirection();
        }

    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }
}
