package theGame;

import java.awt.event.*;

public class KeyInput extends KeyAdapter{
    
    private long lastKeyPress;

    private Handler handler;
    private Sprites sprite;
    
    
    public KeyInput(Handler handler, Sprites sprite) {
        this.handler = handler;
        this.sprite = sprite;
        this.lastKeyPress = 0;
    }
 
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        for(int i = 0; i < handler.object.size(); i++) {
        	GameObject  tempObject = handler.object.get(i);
        	
            if(tempObject.getID() == ID.Player) {            	
            	
            	//Projectile
///////////////////////////////////////////////////////////////////////////////////
            	if(key == (KeyEvent.VK_UP)) {
            		if(System.currentTimeMillis() - lastKeyPress > 150 &&
            				!this.handler.roomCleared()) {
            			if(!this.handler.playerIsDead()) {

		            		handler.setGoUp(true);
		            		
		            		if(this.handler.getPP() > 0) {
			            		Projectile bullet = new Projectile(tempObject.getX()+10,
			      						 tempObject.getY(),
			       						 ID.Projectile, handler, 0, 0, sprite);
			            		handler.losePP();
			            		handler.addObject(bullet);
			            		lastKeyPress = System.currentTimeMillis();
			            			
		            		}
		            		break;
            			}
            		}
            	}
            	
            	if(key == (KeyEvent.VK_LEFT)) {
            		if(System.currentTimeMillis() - lastKeyPress > 150 &&
            				!this.handler.roomCleared()) {
            			if(!this.handler.playerIsDead()) {


		            		this.handler.setGoLeft(true);
		            		
		            		if(this.handler.getPP() > 0) {
			            		Projectile bullet = new Projectile(tempObject.getX()+2,
			      						 tempObject.getY()+34,
			       						 ID.Projectile, handler, 0, 0, sprite);
			            		
			            		handler.losePP();
			            		this.handler.addObject(bullet);
			            		lastKeyPress = System.currentTimeMillis();
			            	}
		            		
		            		break;
            			}
            		}
            	}
            	
            	if(key == (KeyEvent.VK_DOWN)) {
            		if((System.currentTimeMillis() - lastKeyPress > 150) &&
            				!this.handler.roomCleared()) {
            			if(!this.handler.playerIsDead()) {

		            		this.handler.setGoDown(true);
		
		            		if(this.handler.getPP() > 0) {
		
			            		Projectile bullet = new Projectile(tempObject.getX()+10,
			      						 tempObject.getY()+34,
			       						 ID.Projectile, handler, 0, 0, sprite);
			            		
			            		handler.losePP();
			            		handler.addObject(bullet);
			            		lastKeyPress = System.currentTimeMillis();
		            		}
		            		
		            		break;
            			}
            		}
            	}
            	
            	if(key == (KeyEvent.VK_RIGHT)) {
            		if(System.currentTimeMillis() - lastKeyPress > 150 &&
            				!this.handler.roomCleared()) {
            			if(!this.handler.playerIsDead()) {

		            			
		            		this.handler.setGoRight(true);
		
		            		if(this.handler.getPP() > 0) {
		
			            		Projectile bullet = new Projectile(tempObject.getX()+10,
			      						 tempObject.getY()+34,
			       						 ID.Projectile, handler, 0, 0, sprite);
			
			            		handler.losePP();
			            		handler.addObject(bullet);
			            		lastKeyPress = System.currentTimeMillis();
		            		}
		            		
		            		break;
            			}
            		}
            	}
///////////////////////////////////////////////////////////////////////////////////

            	//Player
///////////////////////////////////////////////////////////////////////////////////
                if(key == KeyEvent.VK_W) handler.setUp(true);
                if(key == KeyEvent.VK_A) handler.setLeft(true);
                if(key == KeyEvent.VK_S) handler.setDown(true);
                if(key == KeyEvent.VK_D) handler.setRight(true);
///////////////////////////////////////////////////////////////////////////////////

                //Music
///////////////////////////////////////////////////////////////////////////////////
                if(key == KeyEvent.VK_M) handler.muteSong(!handler.isMuted());
///////////////////////////////////////////////////////////////////////////////////
                
                //Game
///////////////////////////////////////////////////////////////////////////////////

                if(key == KeyEvent.VK_R) handler.resetGame(true);
                if(key == KeyEvent.VK_E) handler.nextLevel(true);
                if(key == KeyEvent.VK_ENTER) handler.startGame(true);
///////////////////////////////////////////////////////////////////////////////////
            }
        }
    }    
    
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        for(int i = 0; i < handler.object.size(); i++) {
        	GameObject  tempObject = handler.object.get(i);
        	
            if(tempObject.getID() == ID.Player) {            	
            	
            	//Projectile
///////////////////////////////////////////////////////////////////////////////////

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
///////////////////////////////////////////////////////////////////////////////////

            	//Player
///////////////////////////////////////////////////////////////////////////////////
            	
                if(key == KeyEvent.VK_W) handler.setUp(false);
                if(key == KeyEvent.VK_A) handler.setLeft(false);
                if(key == KeyEvent.VK_S) handler.setDown(false);
                if(key == KeyEvent.VK_D) handler.setRight(false);
///////////////////////////////////////////////////////////////////////////////////

              //Game
///////////////////////////////////////////////////////////////////////////////////

                if(key == KeyEvent.VK_R) handler.resetGame(false);
                if(key == KeyEvent.VK_E) handler.nextLevel(false);
///////////////////////////////////////////////////////////////////////////////////
            }
        }
    }
}
