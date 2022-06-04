package theGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends GameObject{
    
    Handler handler;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }
    
    @Override
    public void tick() {
    	
        this.x += velX;
        this.y += velY;
		collision();

        //Players movements
/////////////////////////////////////////
        
        if(handler.isUp()) {
            velY = -5;
        } else if(!handler.isDown()) {
            velY = 0;
        }
        
        if(handler.isDown()) {
            velY = 5;
        }else if(!handler.isUp()) {
            velY = 0;
        }
        
        if(handler.isLeft()) {
            velX = -5;
        }else if(!handler.isRight()) {
            velX = 0;
        }
        
        if(handler.isRight()) {
            velX = 5;
        }else if(!handler.isLeft()){
            velX = 0;
        } 
        
/////////////////////////////////////////

    }
    
    private void collision() {
    	
    	for(int i = 0; i < handler.object.size(); i++) {
    		GameObject tempObject = handler.object.get(i);
    		
    		if(tempObject.getID() == ID.Block) {	
    			
    			if(getBounds().intersects(tempObject.getBounds())) {
    				this.x += velX * -1;
    				this.y += velY * -1;
    			}
    		}
    	}
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 20, 50);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle (x, y, 20, 50);
    }
}
