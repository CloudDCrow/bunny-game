
package theGame;

import java.awt.Canvas;


public class Game extends Canvas implements Runnable{
    
    public Game() {
        new Window(1000, 563, "Test Game", this);
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public static void main(String args[]) {
        new Game();
    }

    
}
