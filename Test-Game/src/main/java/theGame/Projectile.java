package theGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Projectile extends GameObject {

	private Handler handler;
	
	public Projectile(int x, int y, ID id, Handler handler, int velY, int velX) {
		super(x, y, id);
		this.handler = handler;
		
		 //Projectile movements
		/////////////////////////////////////////

			if(this.handler.isGoingUp()) {
				this.velY = -10;
			} 
			
			if(this.handler.isGoingDown()) {
				this.velY = 10;
			}
			if(this.handler.isGoingLeft()) {
				this.velX = -10;
			}
			
			if(this.handler.isGoingRight()) {
				this.velX = 10;
			}
				
		/////////////////////////////////////////
	}

	@Override
	public void tick() {

		this.x += this.velX;
		this.y += this.velY;

		this.collision();
	}


    private void collision() {
    	
    	for(int i = 0; i < handler.object.size(); i++) {
    		GameObject tempObject = handler.object.get(i);
    		
    		if(tempObject.getID() == ID.Block) {	
    			
    			if(getBounds().intersects(tempObject.getBounds())) {
    				handler.removeObject(this);
    			}
    		}
    	}
    }
    
	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(x, y, 10, 10);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 8, 8);
	}

}
