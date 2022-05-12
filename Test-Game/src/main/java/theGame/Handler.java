
package theGame;

import java.util.LinkedList;


public class Handler {
    
    LinkedList<GameObject> object = new LinkedList<>();
    
    public void tick() {
        for(int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
        }
    }    
}
