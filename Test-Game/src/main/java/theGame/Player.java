package theGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject{
    
    Handler handler;
    private int HP = 1000;
    private BufferedImage playerSprite;
    		
    public Player(int x, int y, ID id, Handler handler, Sprites sprite) {
        super(x, y, id, sprite);
        this.handler = handler;
        this.playerSprite = sprite.getSubimage(1, 1, 32, 48);
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
    		
			if(tempObject.getID() == ID.Enemy) {	
    			
    			if(getBounds().intersects(tempObject.getBounds())) {
    				loseHP();
    			}
    		}
    	}
    }
    
    public int loseHP() {
    	return this.HP -= 100;
    }
    
    public boolean dead() {
    	return HP == 0;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(playerSprite, x , y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle (x, y, 32, 46);
    }
}
