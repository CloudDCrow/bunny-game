package theGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Rock extends GameObject {
    private BufferedImage rockSprite;

    public Rock(int x, int y, ID id, Sprites sprite) {
        super(x, y, id, sprite);
        this.rockSprite = sprite.getSubimage(8, 2, 32, 32);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(rockSprite, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
