
package theGame;

import java.awt.Graphics;
import java.util.ArrayList;


public class Handler {
    
    ArrayList<GameObject> object = new ArrayList<>();
    
    private boolean up = false,
                    down = false,
                    left = false,
                    right = false;
    
    public void tick() {
        for(GameObject obj: this.object) {
            obj.tick();
        }
    }    
    
    public void render(Graphics g) {
        for(GameObject obj: this.object) {
            obj.render(g);
        }
    }
    
    public void addObject(GameObject tempObject) {
        this.object.add(tempObject);
    }
    
    public void removeObject(GameObject tempObject) {
        this.object.remove(tempObject);
    }

    //Movement Getters and Setters
    /////////////////////////////////////
    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
    ////////////////////////////////////////
}
