package gameStates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import Level.LevelManager;
import Main.Game;
import entities.EnemyManager;
import entities.player;
import utility.SaveLoad;
import UI.GameOverOverlay;
import UI.LevelCompleteOverlay;
import UI.PauseOverlay;

public class Playing extends State implements StateMethod {

    private LevelManager levelmanager;
    private player Player;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    private EnemyManager enemyManager;
    private GameOverOverlay gameOverOverlay;
    private LevelCompleteOverlay levelComplete;

    private int xOffsetLevel;
    private int leftBorder = (int) (0.4 * Game.Game_Width);
    private int rightBorder = (int) (0.6 * Game.Game_Width);
    // old code, don't forget to remove it later
    // private int levelTileWide = SaveLoad.getlvlData()[0].length;
    // private int maxTilesOffset = levelTileWide - Game.Tiles_in_Width;
    private int maxLevelOffsetX;

    private boolean lvlCompleted = false;
    private boolean gameOver;
    public boolean blockButton = false;

    public Playing(Game game) {
        super(game);
        initClasses();

        calculateLevelOffSet();
        loadLevel();
    }

    public void loadNextLevel() {
        resetAll();
        levelmanager.loadNextLevel();
    }

    private void calculateLevelOffSet() {
        maxLevelOffsetX = levelmanager.getCurrentLevel().getLevelOffSet();
    }

    private void loadLevel() {
        enemyManager.createEnemies(levelmanager.getCurrentLevel());
    }

    private void initClasses() {
        levelmanager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        Player =  new player(100, 250, (int) (64 * Game.Scale), (int) (64 * Game.Scale), this);
        Player.LoadlevelData(levelmanager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelComplete = new LevelCompleteOverlay(this);
    }

    @Override
    public void update() {
        if (!paused && !gameOver) {
            levelmanager.update();
		    Player.update();
            enemyManager.update(levelmanager.getCurrentLevel().getLevelData(), Player);
            checkBorder();
        } else if (gameOver){
            Player.update();
            enemyManager.update(levelmanager.getCurrentLevel().getLevelData(), Player);
        } else if (paused) {
            pauseOverlay.update();
        } //else if (lvlCompleted) {
            levelComplete.update();
        //}
    }

    private void checkBorder() {
        int playerX = (int) Player.getHitBox().x;
        int difference = playerX - xOffsetLevel;

        if (difference > rightBorder) {
            xOffsetLevel += difference - rightBorder;
        } else if (difference < leftBorder) {
            xOffsetLevel += difference - leftBorder;
        }        

        if (xOffsetLevel > maxLevelOffsetX) {
            xOffsetLevel = maxLevelOffsetX;
        } else if (xOffsetLevel < 0) {
            xOffsetLevel = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        levelmanager.draw(g, xOffsetLevel);
		Player.render(g, xOffsetLevel);
        enemyManager.draw(g, xOffsetLevel);

        if (paused) {
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        } else if (lvlCompleted){
            levelComplete.draw(g);
        }
    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        blockButton = false;
        Player.resetAll();
        lvlCompleted = false;
        enemyManager.resetAllEnemies();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackRange){
        enemyManager.checkEnemyHit(attackRange);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverlay.KeyPressed(e);
        else if(!gameOver && !lvlCompleted){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    Player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    Player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    Player.setJump(true);
                    //System.out.println("try to jump");
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver && !lvlCompleted)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    Player.setLeft(false);
                break;
                case KeyEvent.VK_D:
                    Player.setRight(false);
                break;
                case KeyEvent.VK_SPACE:
                    Player.setJump(false);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver)
            if(e.getButton() == MouseEvent.BUTTON1){
                if (blockButton == true) {
                    Player.setAttack(false);
                } else {
                    Player.setAttack(true);
                }
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver)
            switch (e.getButton()) {
                case MouseEvent.BUTTON3:
                    Player.setPrepareBlock(true);
                    blockButton = true;
                break;
            }
            if (paused) {
                pauseOverlay.mousePressed(e);
            } else if (lvlCompleted) {
                levelComplete.mousePressed(e);
            }
    }

    public void mouseReleased(MouseEvent e) {
        if (!gameOver)
            switch (e.getButton()) {
                case MouseEvent.BUTTON3:
                if (!blockButton){
                } else {
                    Player.setBlock(false);
                    Player.setReleaseBlock(true);
                    blockButton = false;
                }

                break;
            }
            if (paused) {
                pauseOverlay.mouseReleased(e);
            }else if (lvlCompleted) {
                levelComplete.mouseReleased(e);
            }
        
    }
 
    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver)
            if (paused) {
                pauseOverlay.mouseMoved(e);
            } else if (lvlCompleted) {
                levelComplete.mouseMoved(e);
            }
        
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver)
            if (paused) {
                pauseOverlay.mouseDragged(e);
            }
        
    }

    public void setMaxLevelOffSet(int levelOffset) {
        this.maxLevelOffsetX = levelOffset;
    }

    public void ContinueGame() {
        paused = false;
    }

    public void PauseGame() {
        paused = true;
    }

    public void setLevelCompleted(boolean levelComplete) {
        this.lvlCompleted = levelComplete;
    }

    public void windowLostFocus(){
        PauseGame();
        Player.resetdirection();
    }

    public player getPlayer() {
        return Player;
    }

    public boolean getPausedGame() {
        return paused;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }


    // public void SecondCheck() {
    //     long lastCheck = System.currentTimeMillis();
    //     if (System.currentTimeMillis() - lastCheck >= 500){
    //         lastCheck = System.currentTimeMillis();
    //         attack = false;
    //     }
    // }
}
