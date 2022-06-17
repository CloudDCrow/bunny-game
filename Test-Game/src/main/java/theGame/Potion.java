package theGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Potion extends GameObject{
	
	private Handler handler;
	private BufferedImage potionSprite;
	private int HP;

	public Potion(int x, int y, ID id, Handler handler, Sprites sprite) {
		super(x, y, id, sprite);
		this.handler = handler;
		this.HP = 30;
	}

	@Override
	public void tick() {
		collision();
	}
	
	public void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Player) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(this);
				}
			}
			if(tempObject.getID() == ID.Projectile) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(tempObject);
					this.HP -= 10;
					if(this.HP == 0) {
						handler.removeObject(this);
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, 20, 25);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 20, 25);
	}

}
