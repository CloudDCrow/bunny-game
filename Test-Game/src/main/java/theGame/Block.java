package theGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Block extends GameObject{
	
	private BufferedImage blockSprite;

	public Block(int x, int y, ID id, Sprites sprite) {
		super(x, y, id, sprite);
		this.blockSprite = sprite.getSubimage(8, 1, 32, 32);
	}
	
	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(blockSprite, x, y, null);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
}
