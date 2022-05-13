
package theGame;

import java.awt.Graphics;
import java.util.ArrayList;


public class Handler {
    
    ArrayList<GameObject> object = new ArrayList<>();
    
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
}
