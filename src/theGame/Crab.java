package theGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Crab extends GameObject{
    private boolean changedMovement;
    public boolean gotHit;
    private int velX = 0;
    private int velY = 0;
    private int count = 0;
    private int HP = 0;
    private long lastHit;

    private Handler handler;
    private Random rng = new Random();
    private BufferedImage crabSprite;
    private MusicPlayer music;


    public Crab(int x, int y, ID id, Handler handler, Sprites sprite) {
        super(x, y, id, sprite);
        this.gotHit = false;
        this.changedMovement = false;
        this.HP = 400;
        this.velX = rng.nextInt(3 + 2) - 2;
        this.velY = rng.nextInt(3 + 2) - 2;

        this.handler = handler;
        this.music = new MusicPlayer(handler);

        this.crabSprite = sprite.getSubimage(12, 2, 64, 32);
    }

    @Override
    public void tick() {

        //Sprite commands
        if(velX <= 0) {
            this.crabSprite = sprite.getSubimage(12, 2, 64, 32);
        }

        if(velX >= 0) {
            this.crabSprite = sprite.getSubimage(13, 2, 64, 32);
        }

        if(velX <= 0 && System.currentTimeMillis() - lastHit < 500) {
            this.crabSprite = sprite.getSubimage(14, 2, 64, 32);
        }

        if(velX >= 0 && System.currentTimeMillis() - lastHit < 500) {
            this.crabSprite = sprite.getSubimage(14, 2, 64, 32);
        }


        this.x += velX;
        this.y += velY;
        collision();

        //Movement logic
        if((changedMovement & count == 14) || velX == 0 || velY == 0 || count == 100) {
            this.velX = rng.nextInt(5 + 2) - 2;
            this.velY = rng.nextInt(5 + 2) - 2;
            changedMovement = false;
            count = 0;
        }

        while(velX == 0 || velY == 0) {
            this.velX = rng.nextInt(3 + 2) - 2;
            this.velY = rng.nextInt(3 + 2) - 2;
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
                        this.handler.enemyKiled();
                        this.music.setSong("/OOF.wav");
                        this.music.playSong(false);
                        this.handler.removeObject(this);
                    }
                }
            }

            if(tempObject.getID() == ID.Rock || tempObject.getID() == ID.Block) {

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
        g.drawImage(crabSprite, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 64, 32);
    }

    public Rectangle getLargeBounds() {
        return new Rectangle(x - 8, y - 8, 96, 48);
    }

}
