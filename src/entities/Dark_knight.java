package entities;

import Main.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import static utility.player_constant.EnemyConstant.*;
import static utility.player_constant.direction.*;

public class Dark_knight extends Enemy {

    //attack range
    private Rectangle2D.Float attackRange;
    private int attackRangeXOffset;

    public Dark_knight(float x, float y) {
        super(x, y, ENEMY1_WIDTH, ENEMY1_HEIGHT, Enemy1);
        initHitBox(x, y, (int)(29 * Game.Scale), (int)(34 * Game.Scale));
        initAttackRange();
    }

    private void initAttackRange() {
        attackRange = new Rectangle2D.Float(x, y, (int) (55 * Game.Scale), (int) (20 * Game.Scale));
        attackRangeXOffset = (int) (-15 * Game.Scale);
    }

    public void update(int [][] LevelData, player player){
        updateMove(LevelData, player);
        updateAnimation();
        updateAttackRange();
    }

    private void updateAttackRange() {
        attackRange.x = HitBox.x + attackRangeXOffset;
        attackRange.y = HitBox.y + (int) (10 * Game.Scale);
    }

    public void drawAttackRange(Graphics g, int levelOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackRange.x - levelOffset), (int) attackRange.y, (int) (attackRange.width), (int) attackRange.height);
    }

    private void updateMove(int [][] LevelData, player player) {
        if (firstUpdate) {
            checkFirstUpdate(LevelData);
        }
        if (inAir) {
            checkInAir(LevelData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newEnemyState(RUNNING);
                break;
                case RUNNING:
                if (!player.deathAnimationFinished){
                    if (enemyCanSeePlayer(LevelData, player)) {
                        System.out.println("enemy detected!");
                        turnTowardsPlayer(player);
                    }
                    
                    if (attackAllowed(LevelData, player)){
                        System.out.println("hitting");
                        newEnemyState(HITTING);
                    }
                }
                    
                    move(LevelData);
                break;
                case HITTING:
                    if (animationIndex == 0)
                        attackChecked = false;

                    if (animationIndex == 3 && !attackChecked){
                        checkEnemyHit(attackRange, player);
                    }
            }
        }
    }

    public int flipX() {
		if (walkDirection == right){
			return 0;
        } else {
			return width;
        }
	}

	public int flipW() {
		if (walkDirection == right){
			return 1;
        } else {
			return -1;
        }
	}
}
