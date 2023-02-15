package theGame;

import java.awt.image.BufferedImage;

public class Sprites {

	private BufferedImage image;
	
	public Sprites (BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getSubimage(int column, int row, int width, int height) {
		return image.getSubimage((column * 32) -32, (row * 32) - 32, width, height);
	}
}
