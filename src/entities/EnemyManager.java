package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Level.level;
import gameStates.Playing;
import utility.SaveLoad;

import static utility.player_constant.EnemyConstant.*;

public class EnemyManager {
    
    private Playing playing; 
    private BufferedImage[][] DarkKnight;
    private ArrayList<Dark_knight> darkKnight = new ArrayList<>();
    private Rectangle2D.Float attackRange;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void createEnemies(level level) {
        darkKnight = level.getEnemies1();
        System.out.println("Total Enemies : " + darkKnight.size());
    }

    public void update(int[][]LevelData, player player) {
        boolean enemyActive = false;            
        for (Dark_knight d: darkKnight)
            if (d.isEnemyActive()){
                d.update(LevelData, player);
                enemyActive = true;
            }
        if (!enemyActive){
            playing.setLevelCompleted(true);
        }
    }
    
    public void draw(Graphics g, int OffsetLevel) {
        drawEnemy1(g, OffsetLevel);
    }

    private void drawEnemy1(Graphics g, int OffsetLevel) {
        for (Dark_knight d: darkKnight){
            if (d.isEnemyActive()){
                g.drawImage(DarkKnight[d.getEnemyState()][d.getAnimationIndex()], (int) d.getHitBox().x - OffsetLevel - ENEMY1_OFFSET_X + d.flipX(), (int) d.getHitBox().y - ENEMY1_OFFSET_Y, ENEMY1_WIDTH * d.flipW(), ENEMY1_HEIGHT, null);
            // d.drawHitBox is for hitbox debugging purposes
                // d.drawHitBox(g, OffsetLevel);
                // d.drawAttackRange(g, OffsetLevel);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackRange) {
        for (Dark_knight d: darkKnight){
            if (attackRange.intersects(d.getHitBox())) {
                d.attacked(10);
                return;
            }
        }
    }

    public void resetAllEnemies() {
        for (Dark_knight d: darkKnight) {
            d.resetEnemies(); 
        }
    }

    private void loadEnemyImgs() {
        BufferedImage is = SaveLoad.getTextureAtlas(SaveLoad.ENEMY1);
        DarkKnight = new BufferedImage[6][8];

        for (int j = 0; j < DarkKnight.length; j++)
            for (int i = 0; i < DarkKnight[j].length; i++)
            
                DarkKnight[j][i] =is.getSubimage(i * ENEMY1_WIDTH_DEFAULT, j * ENEMY1_HEIGHT_DEFAULT, ENEMY1_WIDTH_DEFAULT, ENEMY1_HEIGHT_DEFAULT);
    }
}
