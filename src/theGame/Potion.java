package theGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Potion extends GameObject{
	
	private int HP;

	private Handler handler;
	private BufferedImage potionSprite;

	public Potion(int x, int y, ID id, Handler handler, Sprites sprite) {
		super(x, y, id, sprite);
		this.HP = 20;

		this.handler = handler;
		this.potionSprite = sprite.getSubimage(4, 2, 20, 25);
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
					this.handler.gainPP(20);
				}
			}
			if(tempObject.getID() == ID.Projectile) {
				
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(tempObject);
					this.HP -= 10;
					if(this.HP <= 0) {
						handler.removeObject(this);
					}
				}
			}
		}
	}
 
	@Override
	public void render(Graphics g) {
		g.drawImage(potionSprite, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x , y , 20, 25);
	}
}