package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import Main.Game;

public abstract class entity {
    
    protected float x,y;
    protected int width, height;
    protected Rectangle2D.Float HitBox;

    public entity (float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawHitBox(Graphics g, int OffsetLevel) {
        // Debugging purpose
        g.setColor(Color.red);
        g.drawRect((int) HitBox.x - OffsetLevel, (int) HitBox.y, (int) HitBox.width, (int) HitBox.height);
    }

    protected void initHitBox(float x, float y, float width, float height) {
        HitBox = new Rectangle2D.Float(x, y, width, height);
    }

//    protected void updateHitBox() {
//       HitBox.x = (int) x;
//        HitBox.y = (int) y;
//    }

    public Rectangle2D.Float getHitBox(){
        return HitBox;
    }
}
