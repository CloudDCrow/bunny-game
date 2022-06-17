package theGame;

import java.awt.Color;   
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject{
	

	private Handler handler;
	private Random rng = new Random();
	private int velX = 0;
	private int velY = 0;
	private boolean changedMovement = false;
	private int count = 0;
	private int HP = 0;
	private BufferedImage enemySprite;
	
	public Enemy(int x, int y, ID id, Handler handler, Sprites sprite) {
		super(x, y, id, sprite);
		this.handler = handler;
		this.HP = 200;
		this.velX = rng.nextInt(3 + 2) - 2;
		this.velY = rng.nextInt(3 + 2) - 2;
		this.enemySprite = sprite.getSubimage(12, 1, 32, 16);
	}
	
	@Override
	public void tick() {

		this.x += velX;
		this.y += velY;
		collision();

		
		if((changedMovement & count == 14) || velX == 0 || velY == 0 || count == 100) {
			this.velX = rng.nextInt(3 + 2) - 2;
			this.velY = rng.nextInt(3 + 2) - 2;
			changedMovement = false;
			count = 0;
		}
		
		while(velX == 0 || velY == 0) {
			this.velX = rng.nextInt(3 + 2) - 2;
			this.velY = rng.nextInt(3 + 2) - 2;
		}
		
		count++;
		

	}
	
	public void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Projectile) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(tempObject);
					this.HP -= 100;
					if(this.HP == 0) {
						handler.removeObject(this);
					}
				}
			}
			
			if(tempObject.getID() == ID.Block) {
				if(getLargeBounds().intersects(tempObject.getBounds())) {
					count = 0;
					changedMovement = true;
					
						this.x += this.velX *-1;
	    				this.y += this.velY * -1;
	    				
	    				velX = velX * -1;
	    				velY = velY * -1;

				}
			}
			
			if(tempObject.getID() == ID.Player) {
				if(getLargeBounds().intersects(tempObject.getBounds())) {
					
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(enemySprite, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 16);
	}
	
	public Rectangle getLargeBounds() {
		return new Rectangle(x - 8, y - 8, 64, 32);
	}

}
