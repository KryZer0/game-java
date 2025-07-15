package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utility.player_constant.constant.*;
import static utility.HelpMethod.*;

import Main.Game;
import gameStates.Playing;
import utility.SaveLoad;

public class player extends entity {
    private BufferedImage[][] Animation;
    private int animationtick, animationIndex, animationSpeed = 25;
    private int player_action = idle;
    private boolean left, up, right, down, jump, death = false, attacked = false, knockback = false;
    public boolean deathAnimationFinished = false;
    private boolean move = false, attack = false, prepare_block = false, block = false, release_block = false;
    private float playerSpeed = 1.0f * Game.Scale;
    private float playerMove = 1.0f * Game.Scale;
    private float xDrawOffset = 20 * Game.Scale;
	private float yDrawOffset = 28 * Game.Scale;
    private int AttackedDir = 0;

        //Gravity 
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.Scale;
    private float jumpSpeed = - 2.5f * Game.Scale;
    private float fallSpeed = 0.5f * Game.Scale;
    private boolean inAir = false;

    // HUD Status Bar
	private BufferedImage hudStatusBar;

	private int statusBarWidth = (int) (192 * Game.Scale);
	private int statusBarHeight = (int) (58 * Game.Scale);
	private int statusBarX = (int) (10 * Game.Scale);
	private int statusBarY = (int) (10 * Game.Scale);

	private int healthBarWidth = (int) (150 * Game.Scale);
	private int healthBarHeight = (int) (4 * Game.Scale);
	private int healthBarXStart = (int) (34 * Game.Scale);
	private int healthBarYStart = (int) (14 * Game.Scale);

	private int maxHealth = 100;
	private int currentHealth = 100;
	private int healthWidth = healthBarWidth;

    //attack range
    private Rectangle2D.Float attackRange;
    private boolean attackChecked;

    //change player texture direction
    private int flipX = 0;
    private int flipW = 1;


    private int[][] LevelData;

    private Playing playing;

    public player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        animation();
        if (!deathAnimationFinished){
            initHitBox(x,y,(int) 25 * Game.Scale,(int) 32 * Game.Scale);
            initAttackRange();
        }
    }


    
    private void initAttackRange() {
        attackRange = new Rectangle2D.Float(x, y, (int) (20 * Game.Scale), (int) (20 * Game.Scale));
    }

    // this method is to update images, so the images can change
    public void update() {
        if (currentHealth <= 0) {
            playing.setGameOver(true);
        }
        updateHealthBar();
        updateAttackRange();
        updatepos();
        if (attack){
            checkAttack();
        }
        updateAnimation();
        setAnimation();
        if (deathAnimationFinished) {
            death = true;
        }
    }

    private void checkAttack() {
        if (attackChecked || animationIndex !=3)
            return; 
            attackChecked = true;
            playing.checkEnemyHit(attackRange);
    }



    private void updateAttackRange() {
        if (right) {
            attackRange.x = HitBox.x + HitBox.width + (int) (1 * Game.Scale);
        } else if (left) {
            attackRange.x = HitBox.x - HitBox.width - (int) (-4 * Game.Scale);
        }
        attackRange.y = HitBox.y + (int) (12 * Game.Scale);
    }



    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    public void changeHealth(int value) {
        if (!block){
           currentHealth += value;
        }

        if (!block) {
            float knockback = 1.5f;
            airSpeed = jumpSpeed / 2.5f;
            float xSpeed = playerSpeed;
            if (MoveAllowed(HitBox.x, HitBox.y  + airSpeed, HitBox.width, HitBox.height, LevelData)) {
                // this knockback blocking only works to the right
                if (AttackedDir == 1) {
                    HitBox.x += knockback;
                    HitBox.y += airSpeed;
                    updatexPos(knockback);
                } else if (AttackedDir == 2) {
                    HitBox.x -= knockback;
                    HitBox.y += airSpeed;
                    updatexPos(-knockback);   
                }
                AttackedDir = 0;
            }  else {
				HitBox.y = getPlayerYPosUnderOrAboveTile(HitBox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeed;
				updatexPos(xSpeed);
			}
            if (!IsEntityOnFloor(HitBox, LevelData))
				inAir = true;
        }
        
        if (currentHealth <= 0) {
            currentHealth = 0;
            //gameOver();
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void LoadlevelData(int[][] LevelData){
        this.LevelData = LevelData;
        if (!IsEntityOnFloor(HitBox, LevelData))
            inAir = true;
    }

    private void setAnimation() {
		int startAnimation = player_action;

		if (move)
            player_action = running;
		else
            player_action = idle;

        if (inAir) {
            if (airSpeed < 0)
                player_action = jumping;
            else
                player_action = falling;
        }
		if (attack) {
            player_action = hitting;
            if (startAnimation != hitting) {
                animationIndex = 1;
                animationtick = 0;
                return;
            }
        }

        if (prepare_block) {
            player_action = prepare_blocking;
            block = true;
        }
        if (block)
            player_action = blocking;
            if (release_block) {
                block = false;
            }

        if (release_block) {
            player_action = release_blocking;
        }

        if (currentHealth <= 0) {
            player_action = dying;
        }

        if (death) {
            player_action = dead;
        }

        if (attacked) {
            player_action = hurt;
        }

		if (startAnimation != player_action)
			resetAnimation();
	}

    private void resetAnimation() {
        animationtick = 0;
        animationIndex = 0;
    }

    public void render(Graphics g, int OffsetLevel) {
        g.drawImage(Animation[player_action][animationIndex], (int) (HitBox.x - xDrawOffset) - OffsetLevel + flipX, (int) (HitBox.y - yDrawOffset), width * flipW, height, null);
        
        /* this drawHitBox is debugging purposes, remember to put drawHitBox into the commend
        drawHitBox(g, OffsetLevel);
        drawAttackRange(g, OffsetLevel);
        */  
        drawUI(g);
    }
  
    // To draw AttackBoxPlayer
    private void drawAttackRange(Graphics g, int levelOffset) {
        g.setColor(Color.red);
        g.drawRect((int) attackRange.x - levelOffset, (int) attackRange.y - 10, (int) attackRange.width, (int) attackRange.height + 13);
    }

// To draw HealthBar
    private void drawUI(Graphics g) {
        g.drawImage(hudStatusBar, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void updatepos() {
		move = false;

        if (!death)
		if (jump)
			jump();
        if (!inAir){
            if ((!right && !left) || (right && left)){
                return;
            }
        }

		float xSpeed = 0;

        if (currentHealth <= 0 || block) {
            playerSpeed = 0;
        } else {
            playerSpeed = playerMove;
        }
        
        if (right){
			xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }
		if (left) {
			xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }

		if (!inAir)
			if (!IsEntityOnFloor(HitBox, LevelData))
				inAir = true;

		if (inAir) {
			if (MoveAllowed(HitBox.x, HitBox.y + airSpeed, HitBox.width, HitBox.height, LevelData)) {
				HitBox.y += airSpeed;
				airSpeed += gravity;
				updatexPos(xSpeed);
			} else {
				HitBox.y = getPlayerYPosUnderOrAboveTile(HitBox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeed;
				updatexPos(xSpeed);
			}
		} else {
			updatexPos(xSpeed);
        }

        if (attacked) {
            knockback = true;
        }

        if (knockback) {
            //getKnockBackpos();
            knockback = false;
        }
		move = true;
	}


    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updatexPos(float xSpeed) {
        if (MoveAllowed(HitBox.x + xSpeed, HitBox.y, HitBox.width, HitBox.height, LevelData)) {
			HitBox.x += xSpeed;
		} else {
            HitBox.x = getPlayerXPos(HitBox,xSpeed);
            //System.out.println("you hit the wall");
        }
    }

    /*  this method is to import image, so we can assign in the animation method and
    assign each character in pixel art in multidimensional array*/
    private void animation() {
        BufferedImage img = SaveLoad.getTextureAtlas(SaveLoad.PLAYER_ATLAS);
        Animation = new BufferedImage[11][8];

        if (!death){
            for (int j=0; j < Animation.length; j++){
                for (int i=0; i < Animation[j].length; i++){
                    Animation[j][i] = img.getSubimage(i * 48, j * 32, 48, 32);
                }
            }
        } else {
            int i = 0; int j = 10;
            Animation[j][i] = img.getSubimage(i * 48, j * 32, 48, 32);
        }

        hudStatusBar = SaveLoad.getTextureAtlas(SaveLoad.HUD_STATUS);

    }

    private void updateAnimation() {
        
        animationtick++;
        if (animationtick >= animationSpeed){
            animationtick = 0;
            animationIndex++;
            if (animationIndex >= GetspriteAmount(player_action)) {
                animationIndex = 0;
                attack = false;
                prepare_block = false;
                release_block = false;
                attackChecked = false;
                attacked = false;
            }
        }
        if (player_action == dying && animationIndex == Animation[dying].length - 2) {
            deathAnimationFinished = true;
        }

        if (attacked) {
            attack = false;
        }

        if (attack || attackChecked) {
            prepare_block = false;
            block = false;
            release_block = false;
        }
        
    }

    private void getKnockBackpos() {
        float knockback = 1.5f;
        airSpeed = jumpSpeed / 4;
        float xSpeed = playerSpeed;
        if (MoveAllowed(HitBox.x, HitBox.y + airSpeed, HitBox.width, HitBox.height, LevelData)) {
            HitBox.x += xSpeed;
            HitBox.y += airSpeed;
            airSpeed += gravity;
            updatexPos(knockback);
        } else {
            HitBox.y = getPlayerYPosUnderOrAboveTile(HitBox, airSpeed);
            if (airSpeed > 0)
                resetInAir();
            else
                airSpeed = fallSpeed;
            updatexPos(knockback);
        }
    }

    public void resetdirection() {
        //up = false;
        left = false;
        //down = false;
        right = false;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public void setPrepareBlock (boolean prepare_block) {
        this.prepare_block = prepare_block;
    }

    public void setReleaseBlock (boolean release_block) {
        this.release_block = release_block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public void setAttackedDir(int AttackedDir) {
        this.AttackedDir = AttackedDir;
    }
    public void resetAll() {
        resetdirection();
        inAir = false;
        attack = false;
        move = false;
        death = false;
        deathAnimationFinished = false;
        player_action = idle;
        currentHealth = maxHealth;

        HitBox.x = x;
        HitBox.y = y;

        if (!IsEntityOnFloor(HitBox, LevelData))
            inAir = true;
    }
}
