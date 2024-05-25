package theGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bush extends GameObject{
    private BufferedImage bushSprite;

    public Bush(int x, int y, ID id, Sprites sprite) {
        super(x, y, id, sprite);
        this.bushSprite = sprite.getSubimage(9, 1, 32, 32);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bushSprite, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

}
