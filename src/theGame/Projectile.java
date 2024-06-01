package theGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Projectile extends GameObject {

	private Handler handler;
    private BufferedImage projectileSprite;

	
	public Projectile(int x, int y, ID id, Handler handler, int velY, int velX, Sprites sprite) {
		super(x, y, id, sprite);
		this.handler = handler;

		getDirection();	
	}

	@Override
	public void tick() {
		
		if(this.handler.canGoToNextLevel()) {
			handler.removeObject(this);
		}

		this.x += this.velX;
		this.y += this.velY;
		
		collision();
	}


    private void collision() {
    	
    	for(int i = 0; i < handler.object.size() - 1; i++) {
    		GameObject tempObject = handler.object.get(i);
    		
    		if(tempObject.getID() == ID.Block || tempObject.getID() == ID.Rock || tempObject.getID() == ID.Gold) {
    			
    			if(getBounds().intersects(tempObject.getBounds())) {
    				handler.removeObject(this);
    			}
    		}
    	}
    }
    
    private void getDirection() {

		if(this.handler.isGoingUp()) {
			this.velY = -10;
	        this.projectileSprite = sprite.getSubimage(5, 1, 10, 15);
		} 
		
		if(this.handler.isGoingDown()) {
			this.velY = 10;
	        this.projectileSprite = sprite.getSubimage(4, 1, 10, 15);

		}
		
		if(this.handler.isGoingLeft()) {
			this.velX = -10;
	        this.projectileSprite = sprite.getSubimage(7, 1, 15, 10);

		}
		
		if(this.handler.isGoingRight()) {
			this.velX = 10;
	        this.projectileSprite = sprite.getSubimage(6, 1, 15, 10);
		}
    }
    
	@Override
	public void render(Graphics g) {
        g.drawImage(projectileSprite, x , y, null);
}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 10, 10);
	}
}
