package theGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject{
    
    private int HP;
    private long lastCollision;

    private final Handler handler;
    private BufferedImage playerSprite;
    private final MusicPlayer music;
    		
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

		if (System.currentTimeMillis() - lastCollision > 2000) {
			this.playerSprite = sprite.getSubimage(10, 1, 32, 48);
		}

		if(System.currentTimeMillis() / 1000 % 2 == 0 && isMoving()) {
			this.playerSprite = sprite.getSubimage(2, 1, 32, 48);
		} else if (!isMoving()) {
			this.playerSprite = sprite.getSubimage(1, 1, 32, 48);
		} else {
			this.playerSprite = sprite.getSubimage(3, 1, 32, 48);

		}

    	if(isDead()) {
            this.playerSprite = sprite.getSubimage(10, 1, 32, 48);
            this.handler.playerDied(true);
    	}
    	
		if(!isDead() && this.handler.roomCleared()) {
			this.playerSprite = sprite.getSubimage(11, 1, 32, 48);
		}

        if(!isDead() && !this.handler.roomCleared()) {
        		
	        this.x += (int) velX;
	        this.y += (int) velY;
			collision();

	        //Player movements
/////////////////////////////////////////

			if (handler.isUp()) {
				velY = -5;
			} else if (handler.isDown()) {
				velY = 5;
			} else {
				velY = 0;
			}

			if (handler.isLeft()) {
				velX = -5;
			} else if (handler.isRight()) {
				velX = 5;
			} else {
				velX = 0;
			}
    	}
    }   
    
    private void collision() {
    	
    	for(int i = 0; i < handler.object.size() ; i++) {
    		GameObject tempObject = handler.object.get(i);
    		
    		if(tempObject.getID() == ID.Block || tempObject.getID() == ID.Rock || tempObject.getID() == ID.Gold) {

				// Horizontal collision
				if(getBoundsHorizontal().intersects(tempObject.getBounds())) {
					this.x += (int) (velX * -1);
					velX = 0;
				}

				// Vertical collision
				if(getBoundsVertical().intersects(tempObject.getBounds())) {
					this.y += (int) (velY * -1);
					velY = 0;
				}
    		}
    		
			if(tempObject.getID() == ID.Bug
			|| tempObject.getID() == ID.Crab
			|| tempObject.getID() == ID.Boss
			|| tempObject.getID() == ID.Clone) {
    			
    			if(getBounds().intersects(tempObject.getBounds()) &
    				System.currentTimeMillis() - lastCollision > 2000) {
					switch (tempObject.getID()) {
						case ID.Bug:
							loseHP("Bug");
							break;
						case ID.Crab:
							loseHP("Crab");
							break;
						case ID.Boss:
							loseHP("Boss");
							break;
						case ID.Clone:
							loseHP("Clone");
							break;
					}
					this.music.setSong("/OOF.wav");
    				this.music.playSong(false);
            		this.lastCollision = System.currentTimeMillis();
    			}
    		}

			if(tempObject.getID() == ID.Potion) {	
    			
    			if(getBounds().intersects(tempObject.getBounds())) {
    				gainHP(20);
    				this.music.setSong("/drink.wav");
    				this.music.playSong(false);
    			}
    		}
    	}
    }
	//Horizontal and Vertical logic
/////////////////////////////////////////
	private Rectangle getBoundsHorizontal() {
		return new Rectangle((int)(x + velX), y, 32, 46);
	}

	private Rectangle getBoundsVertical() {
		return new Rectangle(x, (int)(y + velY), 32, 46);
	}
    
    //HP commands
/////////////////////////////////////////

    public void loseHP(String enemy) {
    	if(this.HP > 0) {
			switch (enemy) {
				case "Bug":
					this.HP -= 70;
					break;
				case "Crab":
					this.HP -= 110;
					break;
				case "Clone":
					this.HP -= 60;
					break;
				case "Boss":
					this.HP -= 150;
					break;
			}
    	}
    	if(this.HP < 0) {
    		this.HP = 0;
    	}

	}

    //Getters and setters
/////////////////////////////////////////

    public int getHP() {
    	return this.HP;
    }
    
    public void gainHP(int amount) {
    	this.HP += amount;
    }
    
    public boolean isDead() {
    	return HP <= 0 || this.handler.getPP() <= 0;
    }

	public boolean isMoving() {
        return handler.isUp() || handler.isDown() || handler.isLeft() || handler.isRight();
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