package theGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject{
    
    private int HP;
    private long lastCollision;

    private Handler handler;
    private BufferedImage playerSprite;
    private MusicPlayer music;
    		
    public Player(int x, int y, ID id, int HP, Handler handler, Sprites sprite) {
        super(x, y, id, sprite);
        this.HP = HP;

        this.handler = handler;
        this.handler.playerDied(false);
        
        this.music = new MusicPlayer(handler);
        this.playerSprite = sprite.getSubimage(1, 1, 32, 48);
    }
    
    @Override
    public void tick() {
    	
    	//Updates sprites
/////////////////////////////////////////////////////////////////////

    	if(isDead()) {
            this.playerSprite = sprite.getSubimage(10, 1, 32, 48);
            this.handler.playerDied(true);
    	}
    	
		if(!isDead() && this.handler.roomCleared()) {
			this.playerSprite = sprite.getSubimage(11, 1, 32, 48);
		}
/////////////////////////////////////////////////////////////////////

        if(!isDead() && !this.handler.roomCleared()) {
        		
	        this.x += velX;
	        this.y += velY;
			collision();

	        //Player movements
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
/////////////////////////////////////////
    
    private void collision() {
    	
    	for(int i = 0; i < handler.object.size() ; i++) {
    		GameObject tempObject = handler.object.get(i);
    		
    		if(tempObject.getID() == ID.Block) {	
    			
    			if(getBounds().intersects(tempObject.getBounds())) {

				//Stops when hitting wall
    				this.x += velX * -1;
    				this.y += velY * -1;
    			}
    		}
    		
			if(tempObject.getID() == ID.Enemy) {	
    			
    			if(getBounds().intersects(tempObject.getBounds()) &
    				System.currentTimeMillis() - lastCollision > 2000) {
    				loseHP();
    				this.music.setSong("songs/scream.wav");
    				this.music.playSong(false);
            		lastCollision = System.currentTimeMillis();
    			}
    		}
			
			if(tempObject.getID() == ID.Potion) {	
    			
    			if(getBounds().intersects(tempObject.getBounds())) {
    				gainHP(50);
    				this.music.setSong("songs/drink.wav");
    				this.music.playSong(false);
    			}
    		}
    	}
    }
    
    //HP commands
/////////////////////////////////////////

    public int loseHP() {
    	if(this.HP > 0) {
	    	this.HP -= 100;
    	}
    	if(this.HP < 0) {
    		this.HP = 0;
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
/////////////////////////////////////////

    @Override
    public void render(Graphics g) {
        g.drawImage(playerSprite, x , y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle (x, y, 32, 46);
    }
}
