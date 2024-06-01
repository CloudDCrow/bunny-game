package theGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Gold extends GameObject{
    private final BufferedImage goldSprite;

    public Gold(int x, int y, ID id, Sprites sprite) {
        super(x, y, id, sprite);
        this.goldSprite = sprite.getSubimage(8, 1, 32, 32);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(goldSprite, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
