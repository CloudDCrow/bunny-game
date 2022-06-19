package theGame;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
    
    protected int x, y;
    protected float velX = 0, velY = 0;
    protected ID id;
    protected Sprites sprite;

    public GameObject(int x, int y, ID id, Sprites sprite) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.sprite = sprite;
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    //Getters and Setters
////////////////////////////////////////
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }
    
    public ID getID() {
        return id;
    }

    public void setID(ID newID) {
        this.id = newID;
    }    
}
////////////////////////////////////////