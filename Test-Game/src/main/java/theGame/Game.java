
package theGame;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.logging.*;


public class Game extends Canvas implements Runnable{
    
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    
    public Game() {
        Window window = new Window(1000, 563, "Test Game", this);
        window.start();
        this.start();
        
        handler = new Handler();
        
        handler.addObject(new Box(100,100));
        handler.addObject(new Box(400,100));
        handler.addObject(new Box(200,300));

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
    
    //Gameloop
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
    
    public void tick() {
        handler.tick();
    }
    
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        ///////////////////////////////////
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 1000, 500);
        
        handler.render(g);
        
        ///////////////////////////////////
        g.dispose();
        bs.show();
    }
    
    public static void main(String args[]) {
        Game game = new Game();
    }

    
}
