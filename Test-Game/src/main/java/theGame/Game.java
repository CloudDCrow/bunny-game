package theGame;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.logging.*;


public class Game extends Canvas implements Runnable{
    
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private BufferedImage level = null;
    private BufferedImage sprites = null;
    private Sprites spriteSheet;
    private Camera camera;
    
    public Game() {
        Window window = new Window(1000, 563, "Test Game", this);
        window.start();
        this.start();
        
        this.handler = new Handler();
        this.camera = new Camera(0, 0);
        this.addKeyListener(new KeyInput(handler));
        
        BufferedImageLoader loader = new BufferedImageLoader();
        this.level = loader.loadImage("/test-level.png");
        this.sprites = loader.loadImage("/sprite-sheet.png");
        
        this.spriteSheet = new Sprites(sprites);
        this.LoadLevel(level);
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
    
    //Game loop
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
        
////////////////////////////////////////////
        
        g.dispose();
        bs.show();
    }
    
    private void LoadLevel(BufferedImage image) {
    	int width = image.getWidth();
    	int height = image.getHeight();
    	
    	for(int i = 0; i < width; i++) {
    		for(int j = 0; j < height; j++) {
    			int pixel = image.getRGB(i, j);
    			int red = (pixel >> 16) & 0xff;
    			int green = (pixel >> 8) & 0xff;
    			int blue = (pixel) & 0xff;
    			
    			if(red == 255 && green == 255) {
    				this.handler.addObject(new Enemy(i*32, j*32, ID.Enemy, handler, spriteSheet));
    			}
    			
    			if(red == 255 && green == 0) {
    				this.handler.addObject(new Block(i*32, j*32, ID.Block, spriteSheet));
    			}
    			
    			if(blue == 255) {
    				this.handler.addObject(new Player(i*32, j*32, ID.Player, handler, spriteSheet));
    			}		
    		}
    	}
    }
    
    public static void main(String args[]) {
        Game game = new Game();
    }
}
