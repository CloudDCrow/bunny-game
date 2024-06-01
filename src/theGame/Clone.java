package theGame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Clone extends GameObject{
    private boolean changedMovement;
    public boolean gotHit;
    private int velX;
    private int velY;
    private int count = 0;
    private int HP;
    private long lastHit;

    private final Handler handler;
    private final Random rng = new Random();
    private BufferedImage bossSprite;
    private final MusicPlayer music;


    public Clone(int x, int y, ID id, Handler handler, Sprites sprite) {
        super(x, y, id, sprite);
        this.gotHit = false;
        this.changedMovement = false;
        this.HP = 100;
        this.velX = rng.nextInt(6 + 4) - 4;
        this.velY = rng.nextInt(6 + 4) - 4;

        this.handler = handler;
        this.music = new MusicPlayer(handler);

        this.bossSprite = sprite.getSubimage(19, 1, 32, 64);
    }

    @Override
    public void tick() {

        //Sprite commands
        if(velX <= 0) {
            this.bossSprite = sprite.getSubimage(19, 1, 32, 64);
        }

        if(velX >= 0) {
            this.bossSprite = sprite.getSubimage(19, 1, 32, 64);
        }

        if(velX <= 0 && System.currentTimeMillis() - lastHit < 500) {
            this.bossSprite = sprite.getSubimage(19, 1, 32, 64);
        }

        if(velX >= 0 && System.currentTimeMillis() - lastHit < 500) {
            this.bossSprite = sprite.getSubimage(19, 1, 32, 64);
        }


        this.x += velX;
        this.y += velY;
        collision();

        //Movement logic
        if((changedMovement & count == 14) || velX == 0 || velY == 0 || count == 100) {
            this.velX = rng.nextInt(6 + 4) - 4;
            this.velY = rng.nextInt(6 + 4) - 4;
            changedMovement = false;
            count = 0;
        }

        while(velX == 0 || velY == 0) {
            this.velX = rng.nextInt(6 + 4) - 4;
            this.velY = rng.nextInt(6 + 4) - 4;
        }

        count++;
    }

    public void collision() {
        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getID() == ID.Projectile) {

                if(getBounds().intersects(tempObject.getBounds())) {
                    this.handler.removeObject(tempObject);
                    this.lastHit = System.currentTimeMillis();
                    this.HP -= 100;
                    if(this.HP == 0) {
                        this.music.setSong("/OOF.wav");
                        this.music.playSong(false);
                        this.handler.removeObject(this);
                        this.handler.gainPP(20);
                    }
                }
            }

            if(tempObject.getID() == ID.Block) {

                if(getLargeBounds().intersects(tempObject.getBounds())) {

                    //Hitting wall logic
                    count = 0;
                    changedMovement = true;

                    this.x += this.velX *-1;
                    this.y += this.velY * -1;

                    velX = velX * -1;
                    velY = velY * -1;
                }
            }
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bossSprite, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 64);
    }

    public Rectangle getLargeBounds() {
        return new Rectangle(x - 8, y - 8, 32, 64);
    }
}
