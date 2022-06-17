package theGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class Game extends Canvas implements Runnable{
    
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private BufferedImage level = null;
    private BufferedImage sprites = null;
    private Sprites spriteSheet;
    private Camera camera;
    private MusicPlayer music;
    private Player player;
    private int SCREEN_WIDTH = 1000;
    private int SCREEN_HEIGHT = 563;

    
    public Game() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Window window = new Window(SCREEN_WIDTH, SCREEN_HEIGHT, "Firebunny Adventure", this);
        window.start();
        this.start();
        
        this.handler = new Handler();
        this.camera = new Camera(0, 0);
        
        BufferedImageLoader loader = new BufferedImageLoader();
        this.level = loader.loadImage("/test-level.png");
        this.sprites = loader.loadImage("/sprite-sheet.png");
        this.music = new MusicPlayer(handler);
        this.music.setSong("songs/demo-song.wav");

       
        this.spriteSheet = new Sprites(sprites);
        this.addKeyListener(new KeyInput(handler, spriteSheet));
        
        this.loadLevel(level);
        this.music.play();

    }
    
    private void start() {
        this.isRunning = true;
        this.thread = new Thread(this);
        this.thread.start();
    }
    
    public void stop() {
        this.isRunning = false;
        
        try {
            this.thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Game loop, made by Notch
/////////////////////////////////////////////
    
    @Override
    public void run() {
        this.requestFocus();
         long lastTime = System.nanoTime();
         double amountOfTicks = 60.0;
         double ns = 1000000000 / amountOfTicks;
         double delta = 0;
         long timer = System.currentTimeMillis();
         int frames = 0;
         while (isRunning) {
          long now = System.nanoTime();
          delta += (now - lastTime) /ns;
          lastTime = now;
          while(delta >= 1) {
           tick();
           //updates++;
           delta--;
          }
		
          render();

          frames++;

          if (System.currentTimeMillis() - timer > 1000) {
           timer += 1000;
           frames = 0;
           //updates = 0;
          }
          
          if(this.player != null & this.handler.toReset()) {
        	  if(this.player.isDead()) {
        		  this.handler.removeAllObjects();
        		  this.handler.resetEnemies();
        		  this.loadLevel(level);
        	  }
          }
         }
         stop();    
    }  
    
/////////////////////////////////////////////
    
    public void tick() {
    	
		for(int i = 0; i < handler.object.size(); i++) {
			
			if(handler.object.get(i).getID() == ID.Player) {
				this.camera.tick(this.handler.object.get(i));
			}
		}
	    this.handler.tick();
    }
    
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        
        //Graphics settings
////////////////////////////////////////////
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 1000, 563);
        
        g2d.translate(-camera.getX(), -camera.getY());
        
        handler.render(g);
        
        g2d.translate(camera.getX(), camera.getY());
        
        g.setColor(Color.gray);
        g.fillRect(5, 5, 200, 20);
        
    	if(this.player != null) {
            g.setColor(Color.black);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            g.drawString("HP: " + this.player.getHP(), 5, 40);
            g.setColor(Color.green);
        	g.fillRect(5, 5, this.player.getHP(), 20);
            gameOverScreen(g);
            roomClearedScreen(g);
    	}        
        
////////////////////////////////////////////
        
        g.dispose();
        bs.show();
    }
    public void roomClearedScreen(Graphics g) {
    	
    	if(this.handler.roomCleared()) {
			g.setColor(new Color(0,0,0,100));
			g.fillRect(0, 0, 1000, 563);
			g.setColor(Color.green);
	        g.setFont(new Font("Ink Free", Font.BOLD, 75));
			FontMetrics metrics = getFontMetrics(g.getFont());
	    	g.drawString("WELL DONE!", (SCREEN_WIDTH - metrics.stringWidth("WELL DONE!"))/2, SCREEN_HEIGHT/2 - 50);
	        g.setFont(new Font("Ink Free", Font.BOLD, 20));
	        g.setColor(Color.white);
			FontMetrics metrics2 = getFontMetrics(g.getFont());
	    	g.drawString("Press 'E' to continue",(SCREEN_WIDTH - metrics2.stringWidth("Press 'E' to continue"))/2, SCREEN_HEIGHT/2);
		}
    }
    public void gameOverScreen(Graphics g) {
		
		if(this.player.getHP() <= 0) {
			g.setColor(new Color(0,0,0,100));
			g.fillRect(0, 0, 1000, 563);
			g.setColor(Color.red);
	        g.setFont(new Font("Ink Free", Font.BOLD, 75));
			FontMetrics metrics = getFontMetrics(g.getFont());
	    	g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2 - 50);
	        g.setFont(new Font("Ink Free", Font.BOLD, 20));
	        g.setColor(Color.white);
			FontMetrics metrics2 = getFontMetrics(g.getFont());
	    	g.drawString("Press 'R' to reset",(SCREEN_WIDTH - metrics2.stringWidth("Press 'R' to reset"))/2, SCREEN_HEIGHT/2);
		}
    }
    
    private void loadLevel(BufferedImage image) {
    	int width = image.getWidth();
    	int height = image.getHeight();
    	
    	for(int i = 0; i < width; i++) {
    		for(int j = 0; j < height; j++) {
    			int pixel = image.getRGB(i, j);
    			int red = (pixel >> 16) & 0xff;
    			int green = (pixel >> 8) & 0xff;
    			int blue = (pixel) & 0xff;
    			
    			if(red == 255 && green == 255 && blue == 0) {
    				this.handler.addObject(new Enemy(i*32, j*32, ID.Enemy, handler, spriteSheet));
    			}
    			
    			if(red == 0 && green == 255 && blue == 255) {
    				this.handler.addObject(new Potion(i*32, j*32, ID.Potion, handler, spriteSheet));
    			}
    			
    			if(red == 255 && green == 0 && blue == 0) {
    				this.handler.addObject(new Block(i*32, j*32, ID.Block, spriteSheet));
    			}
    			
    			if(red == 0 && green == 0 && blue == 255) {
    				this.handler.addObject(player = new Player(i*32, j*32, ID.Player, handler, spriteSheet));
    			}		
    		}
    	}
    }
    
    public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Game game = new Game();

    }
}
