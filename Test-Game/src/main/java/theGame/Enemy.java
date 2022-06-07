package theGame;

import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends GameObject{
	
	private int VelX = 0;
	private int VelY = 0;
	private Handler handler;
	Random rng = new Random();
	int movement = 0;
	int HP = 200;
	
	public Enemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
	}
	
	@Override
	public void tick() {
		x += VelX;
		y += VelY;
		
		VelX = rng.nextInt(5 + 5) - 5;
		VelY = rng.nextInt(5 + 5) - 5;
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Projectile) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(tempObject);
					handler.removeObject(this);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect(x, y, 31, 31);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 31, 31);
	}

}
