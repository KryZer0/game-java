package entities;

import static utility.player_constant.EnemyConstant.*;
import static utility.player_constant.direction.*;

import java.awt.geom.Rectangle2D;

import Main.Game;
import static utility.HelpMethod.*;

public class Enemy extends entity {
    protected int animationIndex, enemyState, enemyType;
    protected int animationTick, animationSpeed = 25;
    protected boolean firstUpdate = true;
    //Gravity 
    protected float airSpeed = 0f;
    protected float gravity = 0.04f * Game.Scale;
    protected float jumpSpeed = - 2.5f * Game.Scale;
    protected float fallSpeed = 0.5f * Game.Scale;
    protected float enemySpeed = 0.35f * Game.Scale;
    
    protected boolean inAir;
    protected int walkDirection = left;
    protected int tileY;
    protected float attackRange = Game.Tiles_Size;
    protected boolean attackChecked;

    protected int maxHealth;
    protected int currentHealth;
    protected boolean enemyActive = true;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitBox(x, y, width, height);
        maxHealth = getMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    protected void checkFirstUpdate(int LevelData[][]) {
        if (!IsEntityOnFloor(HitBox, LevelData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void checkInAir(int LevelData[][]) {
        if (MoveAllowed(HitBox.x, HitBox.y + fallSpeed, HitBox.width, HitBox.height, LevelData)) {
            HitBox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            HitBox.y = getPlayerYPosUnderOrAboveTile(HitBox, fallSpeed);
            tileY = (int) (HitBox.y / Game.Tiles_Size);
        }
        
    }

    protected void move(int LevelData[][]) {
        float xSpeed = 0;
        if (walkDirection == left) {
            xSpeed = -enemySpeed;
        } else {
            xSpeed = enemySpeed;
        }
        if (MoveAllowed(HitBox.x + xSpeed, HitBox.y, HitBox.width, HitBox.height, LevelData)) 
            if (IsFloor(HitBox, xSpeed, LevelData)) {
                HitBox.x += xSpeed;
                return;
            }
        changewalkDirection();
    }

    // this method is to get new/reset animation of the enemy when enemy changing its state
    protected void newEnemyState(int enemyState) {
        this.enemyState = enemyState;
        animationIndex = 0;
        animationTick = 0;
    }


    protected boolean enemyCanSeePlayer(int LevelData[][], player Player) {
        int playerTileY = (int) (Player.getHitBox().y / Game.Tiles_Size); 
        
        if (tileY == playerTileY) {
            if (isPlayerInRange(Player)) {
                if (IsInSight(LevelData, HitBox, Player.HitBox, tileY)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean attackAllowed(int LevelData[][], player Player) {
        int playerTileY = (int) (Player.getHitBox().y / Game.Tiles_Size); 

        if (tileY == playerTileY) {
            if (isPlayerInAttackRadius(Player)) {
                if (IsInSight(LevelData, HitBox, Player.HitBox, tileY)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected boolean isPlayerInRange(player Player) {
        int absValue = (int) Math.abs(Player.HitBox.x - HitBox.x);
        return absValue <= attackRange * 5;
    }

    protected boolean isPlayerInAttackRadius(player Player) {
        int absValue = (int) Math.abs(Player.HitBox.x - HitBox.x);
        return absValue <= attackRange;
    }

    protected void turnTowardsPlayer(player Player) {
		if (Player.HitBox.x > HitBox.x){
			walkDirection = right;
            Player.setAttackedDir(1);
        } else {
			walkDirection = left;
            Player.setAttackedDir(2);
        }
            //Debugging purposes
        //System.out.println("turning toward player");
	}

    public void attacked(int amounts) {
        currentHealth -= amounts;
        if (currentHealth <= 0) {
            newEnemyState(DEAD);
        } else {
            newEnemyState(HURT);
        }
    }

    protected void updateAnimation() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex ++;

            if (animationIndex >= GetspriteAmount(enemyType, enemyState)) {
                animationIndex = 0;

                switch (enemyState) {
                    case HITTING,HURT -> enemyState = IDLE;
                    case DEAD -> enemyActive = false;
                }
/* Old code, replaced by switch case above

                if (enemyState == HITTING) 
                    enemyState = IDLE;
                else if (enemyState == HURT)
                    enemyState = IDLE;
                else if (enemyState == DEAD)
                    enemyActive = false;
*/              
            }
        }
    }

    protected void changewalkDirection() {
        if (walkDirection == left) 
            walkDirection = right;
        else 
            walkDirection = left;
    }

    protected void checkEnemyHit(Rectangle2D.Float attackRange ,player player) {
        if (attackRange.intersects(player.HitBox)){
            player.changeHealth(-getEnemyDamage(enemyType));
            player.setAttacked(true);
        }
        attackChecked = true;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean isEnemyActive() {
        return enemyActive;
    }

    public void resetEnemies() {
        HitBox.x = x;
        HitBox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newEnemyState(IDLE);
        enemyActive = true;
        fallSpeed = 0;
    }
}
