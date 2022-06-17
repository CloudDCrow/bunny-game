package theGame;

import java.awt.Color;  
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject{
    
    private Handler handler;
    private int HP;
    private BufferedImage playerSprite;
    private long lastCollision;
    private MusicPlayer music;
    		
    public Player(int x, int y, ID id, Handler handler, Sprites sprite) {
        super(x, y, id, sprite);
        this.handler = handler;
        this.playerSprite = sprite.getSubimage(1, 1, 32, 48);
        this.HP = 200;
        music = new MusicPlayer(handler);
    }
    
    @Override
    public void tick() {
    	
        if(!isDead()) {
        	if(!this.handler.roomCleared()) {
        		
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
        	}	
        }
    }   
/////////////////////////////////////////
    
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
    			
    			if(getBounds().intersects(tempObject.getBounds()) &
    				System.currentTimeMillis() - lastCollision > 2000) {
    				loseHP();
    				this.music.setSong("songs/scream.wav");
    				this.music.play();
            		lastCollision = System.currentTimeMillis();
    			}
    		}
			
			if(tempObject.getID() == ID.Potion) {	
    			
    			if(getBounds().intersects(tempObject.getBounds())) {
    				gainHP(50);
    				this.music.setSong("songs/drink.wav");
    				this.music.play();
    			}
    		}
    	}
    }
    
    public int loseHP() {
    	if(this.getHP() > 0) {
	    	return this.HP -= 100;
    	}
    	return this.HP;
    }
    
    public int getHP() {
    	return this.HP;
    }
    
    public void gainHP(int amount) {
    	this.HP += 50;
    }
    
    public boolean isDead() {
    	return HP <= 0;    	
    }
    
    public void revive() {
    	this.HP = 200;
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
