
package theGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Box extends GameObject{

    public Box(int x, int y) {
        super(x, y);
        
        velX = 1;
    }

    public void tick() {
        x += velX;
        y += velY;
    }

    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fill3DRect(this.x, this.y, 50, 30, true);
    }

    public Rectangle getBounds() {
        return null;
    }
    
}
