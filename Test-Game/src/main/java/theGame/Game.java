
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
    
    public Game() {
        Window window = new Window(1000, 563, "Test Game", this);
        window.start();
        this.start();
        
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        
        BufferedImageLoader loader = new BufferedImageLoader();
        this.level = loader.loadImage("/test-level.png");
        
        this.LoadLevel(level);
    }
    
    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public void stop() {
        isRunning = false;
        try {
            thread.join();
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
        handler.tick();
    }
    
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        //Graphics settings
////////////////////////////////////////////
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 1000, 563);
        
        handler.render(g);
        
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
    			
    			if(red == 255) {
    				handler.addObject(new Block(i*32, j*32, ID.Block));
    			}
    			
    			if(blue == 255) {
    				handler.addObject(new Player(i*32, j*32, ID.Player, handler));
    			}
    		}
    	}
    }
    
    public static void main(String args[]) {
        Game game = new Game();
    }

    
}
