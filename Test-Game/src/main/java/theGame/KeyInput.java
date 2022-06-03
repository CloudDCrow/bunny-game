package theGame;

import java.awt.event.*;

public class KeyInput extends KeyAdapter{
    
    private Handler handler;
    
    public KeyInput(Handler handler) {
        this.handler = handler;
    }
 
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        for(int i = 0; i < handler.object.size(); i++) {
        	GameObject  tempObject = handler.object.get(i);
        	
            if(tempObject.getID() == ID.Player) {            	
            	
            	if(key == (KeyEvent.VK_UP)) {
            		handler.setGoUp(true);

            		Projectile bullet = new Projectile(tempObject.getX()+2,
      						 tempObject.getY()+24,
       						 ID.Projectile, handler, 0, 0);

            		handler.addObject(bullet);
            	}
            	
            	if(key == (KeyEvent.VK_LEFT)) {
            		this.handler.setGoLeft(true);

            		Projectile bullet = new Projectile(tempObject.getX()+2,
      						 tempObject.getY()+24,
       						 ID.Projectile, handler, 0, 0);

            		this.handler.addObject(bullet);
            	}
            	
            	if(key == (KeyEvent.VK_DOWN)) {
            		this.handler.setGoDown(true);

            		Projectile bullet = new Projectile(tempObject.getX()+2,
      						 tempObject.getY()+24,
       						 ID.Projectile, handler, 0, 0);

            		handler.addObject(bullet);
            	}
            	
            	if(key == (KeyEvent.VK_RIGHT)) {
            		this.handler.setGoRight(true);

            		Projectile bullet = new Projectile(tempObject.getX()+2,
      						 tempObject.getY()+24,
       						 ID.Projectile, handler, 0, 0);

            		handler.addObject(bullet);
            	}
            	
                if(key == KeyEvent.VK_W) handler.setUp(true);
                if(key == KeyEvent.VK_A) handler.setLeft(true);
                if(key == KeyEvent.VK_S) handler.setDown(true);
                if(key == KeyEvent.VK_D) handler.setRight(true);
                
                
            }
        }
    }
    
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        for(int i = 0; i < handler.object.size(); i++) {
        	GameObject  tempObject = handler.object.get(i);
        	
            if(tempObject.getID() == ID.Player) {            	
            	
            	if(key == (KeyEvent.VK_UP)) {

            		this.handler.setGoUp(false);
            	}
            	
            	if(key == (KeyEvent.VK_LEFT)) {
 
            		this.handler.setGoLeft(false);
            	}
            	
            	if(key == (KeyEvent.VK_DOWN)) {

            		this.handler.setGoDown(false);
            	}
            	
            	if(key == (KeyEvent.VK_RIGHT)) {
             		this.handler.setGoRight(false);
            	}
            	
                if(key == KeyEvent.VK_W) handler.setUp(false);
                if(key == KeyEvent.VK_A) handler.setLeft(false);
                if(key == KeyEvent.VK_S) handler.setDown(false);
                if(key == KeyEvent.VK_D) handler.setRight(false);
                
                
            }
        }
    }
    
}
