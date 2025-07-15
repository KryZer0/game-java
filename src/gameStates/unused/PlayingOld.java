// package gameStates;

// import java.awt.Graphics;
// import java.awt.event.KeyEvent;
// import java.awt.event.MouseEvent;
// import java.awt.geom.Rectangle2D;

// import Level.LevelManager;
// import Main.Game;
// import entities.EnemyManager;
// import entities.player;
// import utility.SaveLoad;
// import UI.GameOverOverlay;
// import UI.PauseOverlay;

// public class PlayingOld extends State implements StateMethod {

//     private LevelManager levelmanager;
//     private player Player;
//     private PauseOverlay pauseOverlay;
//     private boolean paused = false;
//     private EnemyManager enemyManager;
//     private GameOverOverlay gameOverOverlay;

//     private int xOffsetLevel;
//     private int leftBorder = (int) (0.4 * Game.Game_Width);
//     private int rightBorder = (int) (0.6 * Game.Game_Width);
//     private int levelTileWide = SaveLoad.getlvlData()[0].length;
//     private int maxTilesOffset = levelTileWide - Game.Tiles_in_Width;
//     private int maxLevelOffsetX = maxTilesOffset * Game.Tiles_Size;

//     private boolean gameOver;

//     public Playing(Game game) {
//         super(game);
//         initClasses();
//     }

//     private void initClasses() {
//         levelmanager = new LevelManager(game);
//         enemyManager = new EnemyManager(this);
//         Player =  new player(100, 250, (int) (64 * Game.Scale), (int) (64 * Game.Scale), this);
//         Player.LoadlevelData(levelmanager.getCurrentLevel().getLevelData());
//         pauseOverlay = new PauseOverlay(this);
//         gameOverOverlay = new GameOverOverlay(this);
//     }

//     @Override
//     public void update() {
//         if (!paused && !gameOver) {
//             levelmanager.update();
// 		    Player.update();
//             enemyManager.update(levelmanager.getCurrentLevel().getLevelData(), Player);
//             checkBorder();
//         } else if (gameOver){
//             Player.update();
//             enemyManager.update(levelmanager.getCurrentLevel().getLevelData(), Player);
//         } else if (paused) {
//             pauseOverlay.update();
//         }
//     }

//     private void checkBorder() {
//         int playerX = (int) Player.getHitBox().x;
//         int difference = playerX - xOffsetLevel;

//         if (difference > rightBorder) {
//             xOffsetLevel += difference - rightBorder;
//         } else if (difference < leftBorder) {
//             xOffsetLevel += difference - leftBorder;
//         }        

//         if (xOffsetLevel > maxLevelOffsetX) {
//             xOffsetLevel = maxLevelOffsetX;
//         } else if (xOffsetLevel < 0) {
//             xOffsetLevel = 0;
//         }
//     }

//     @Override
//     public void draw(Graphics g) {
//         levelmanager.draw(g, xOffsetLevel);
// 		Player.render(g, xOffsetLevel);
//         enemyManager.draw(g, xOffsetLevel);

//         if (paused) {
//             pauseOverlay.draw(g);
//         } else if (gameOver) {
//             gameOverOverlay.draw(g);
//         }
//     }

//     public void resetAll() {
//         gameOver = false;
//         paused = false;
//         Player.resetAll();
//         enemyManager.resetAllEnemies();
//     }

//     public void setGameOver(boolean gameOver) {
//         this.gameOver = gameOver;
//     }

//     public void checkEnemyHit(Rectangle2D.Float attackRange){
//         enemyManager.checkEnemyHit(attackRange);
//     }

//     @Override
//     public void mouseClicked(MouseEvent e) {
//         if (!gameOver)
//             if(e.getButton() == MouseEvent.BUTTON1)
//                 Player.setAttack(true);
//     }

//     @Override
//     public void keyPressed(KeyEvent e) {
//         if (gameOver)
//             gameOverOverlay.KeyPressed(e);
//         else
//         switch (e.getKeyCode()) {
//             case KeyEvent.VK_A:
//                 Player.setLeft(true);
//                 break;
//             case KeyEvent.VK_D:
//                 Player.setRight(true);
//                 break;
//             case KeyEvent.VK_SPACE:
//                 Player.setJump(true);
//                 System.out.println("try to jump");
//                 break;
//             case KeyEvent.VK_ESCAPE:
//                 paused = !paused;
//                 break;
//         }
//     }

//     @Override
//     public void keyReleased(KeyEvent e) {
//         if (!gameOver)
//             switch (e.getKeyCode()) {
//                 case KeyEvent.VK_A:
//                     Player.setLeft(false);
//                 break;
//                 case KeyEvent.VK_D:
//                     Player.setRight(false);
//                 break;
//                 case KeyEvent.VK_SPACE:
//                     Player.setJump(false);
//                 break;
//         }
//     }

//     @Override
//     public void mousePressed(MouseEvent e) {
//         if (!gameOver)
//             switch (e.getButton()) {
//                 case MouseEvent.BUTTON3:
//                     Player.setPrepareBlock(true);
//                 break;
//             }
//             if (paused) {
//                 pauseOverlay.mousePressed(e);
//             }
//     }

//     public void mouseReleased(MouseEvent e) {
//         if (!gameOver)
//             switch (e.getButton()) {
//                 case MouseEvent.BUTTON3:
//                     Player.setBlock(false);
//                     Player.setReleaseBlock(true);
//                 break;
//             }
//             if (paused) 
//                 pauseOverlay.mouseReleased(e);
        
//     }
 
//     @Override
//     public void mouseMoved(MouseEvent e) {
//         if (!gameOver)
//             if (paused) {
//                 pauseOverlay.mouseMoved(e);
//             }
        
//     }

//     public void mouseDragged(MouseEvent e) {
//         if (!gameOver)
//             if (paused) {
//                 pauseOverlay.mouseDragged(e);
//             }
        
//     }

//     public void ContinueGame() {
//         paused = false;
//     }

//     public void PauseGame() {
//         paused = true;
//     }

//     public void windowLostFocus(){
//         PauseGame();
//         Player.resetdirection();
//     }

//     public player getPlayer() {
//         return Player;
//     }

//     public boolean getPausedGame() {
//         return paused;
//     }
// }
