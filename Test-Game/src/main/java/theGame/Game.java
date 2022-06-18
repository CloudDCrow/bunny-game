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
	
    private final int SCREEN_WIDTH = 1000;
    private final int SCREEN_HEIGHT = 563;
    private final int startingHP = 200;
    private final int enemiesInFirstLevel = 2;
    private final int enemiesInSecondsLevel = 4;
    private final int enemiesInThridLevel = 10;

    
    private boolean isRunning = false;
    private boolean inIntro = true;
    private Thread thread;
    private Handler handler;
    private BufferedImage level = null;
    private BufferedImage sprites = null;
    private BufferedImageLoader loader = null;
    private Sprites spriteSheet;
    private Camera camera;
    private MusicPlayer music;
    private Player player;
    private String currentLevel;
    private String currentSong;
    private int HP;
    private long lastKeyPress;

    
    public Game(){
        Window window = new Window(SCREEN_WIDTH, SCREEN_HEIGHT, "Firebunny Adventure", this);
        window.start();
        this.start();
        this.currentLevel = "/level_1.png";
        this.currentSong = "songs/intro.wav";
        this.HP = startingHP;
        
        this.handler = new Handler();
        this.handler.setNumberOfEnemies(enemiesInFirstLevel);
        
        this.camera = new Camera(0, 0);
        this.music = new MusicPlayer(handler);
        this.music.setSong(currentSong); 
        this.music.playSong(false);
       
        this.loader = new BufferedImageLoader();
        this.sprites = loader.loadImage("/sprite-sheet.png");
        this.level = loader.loadImage(currentLevel);

       
        this.spriteSheet = new Sprites(sprites);
        this.addKeyListener(new KeyInput(handler, spriteSheet));
    	
        this.loadLevel(level);
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
          
          if(inIntro)  {
        	  nextSong("songs/level-1-song.wav");
        	  inIntro = false;
          }	
          
    		
          
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

    	//Update Camera
////////////////////////////////////////////////////////////////////

		for(int i = 0; i < handler.object.size(); i++) {
			
			if(handler.object.get(i).getID() == ID.Player) {
				this.camera.tick(this.handler.object.get(i));
			}
		}
////////////////////////////////////////////////////////////////////
		
		//Reset from start
////////////////////////////////////////////////////////////////////

        if(this.player != null & this.handler.toReset()) {
      	  if(this.player.isDead()) {
      		  this.handler.removeAllObjects();
      		  this.handler.resetEnemies(enemiesInFirstLevel);
      		  if(!this.currentLevel.equals("/level_1.png")) {
      			  nextSong("songs/level-1-song.wav");
      		  }
      		  this.currentLevel = "/level_1.png";
      		  this.HP = startingHP;
              this.level = loader.loadImage(currentLevel);
      		  this.loadLevel(level);
      		  this.handler.startGame(true);
      	  }
        }  
////////////////////////////////////////////////////////////////////

        //Go to next level
////////////////////////////////////////////////////////////////////
        
        if(handler.roomCleared() & this.handler.canGoToNextLevel()) {
        	nextLevelLoader();
   	 	}
////////////////////////////////////////////////////////////////////

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
        
        introScreen(g, bs);
        
        
    	if(this.player != null) {
            g.setColor(Color.gray);
            g.fillRect(5, 5, 200, 20);
            g.setColor(Color.black);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            g.drawString("HP: " + player.getHP(), 5, 40);
            g.setColor(Color.green);
        	g.fillRect(5, 5, player.getHP(), 20);
            gameOverScreen(g);
            roomClearedScreen(g);
    	}        
        
////////////////////////////////////////////
        
        g.dispose();
        bs.show();
    }
    
    public void introScreen(Graphics g,  BufferStrategy bs) {
    	while(!this.handler.canStart() && this.currentLevel.equals("/level_1.png")) {
    		this.inIntro = true;
			g.setColor(Color.gray);
			g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			g.setColor(Color.orange);
		    g.setFont(new Font("Ink Free", Font.BOLD, 75));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("FireBunny Adventure", (SCREEN_WIDTH - metrics.stringWidth("FireBunny Adventure"))/2, SCREEN_HEIGHT/2 - 50);
			
			if(System.currentTimeMillis() - this.lastKeyPress > 150) {
		        g.setFont(new Font("Ink Free", Font.BOLD, 20));
				g.setColor(Color.white);
				FontMetrics metrics2 = getFontMetrics(g.getFont());
		    	g.drawString("Press 'Enter' to start",(SCREEN_WIDTH - metrics2.stringWidth("Press 'Enter' to start"))/2, SCREEN_HEIGHT/2);
        		this.lastKeyPress = System.currentTimeMillis();
			} else {
				g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			}
	        g.dispose();
	        bs.show();
    	}
    }
    
    public void creditsScreen(Graphics g) {
    	g.setColor(Color.black);
		g.fillRect(0, 0, 1000, 563);
		g.setColor(Color.green);
	    g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("YEY", (SCREEN_WIDTH - metrics.stringWidth("YEY"))/2, SCREEN_HEIGHT/2 - 50);
        g.dispose();
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
    				this.handler.addObject(player = new Player(i*32, j*32, ID.Player, this.HP, handler, spriteSheet));
    			}		
    		}
    	}
    }
    
    public void nextLevelLoader() {
 	    	
 	        this.handler = new Handler();
 	        this.camera = new Camera(0, 0);
 	        this.music.stopSong();
 	        this.music = new MusicPlayer(handler);
 	        getNextLevel();
        
 	    	this.level = loader.loadImage(currentLevel);
 	        this.loadLevel(level);
 	        
 	        this.addKeyListener(new KeyInput(handler, spriteSheet));
 	    
    }
    
    public void getNextLevel() {
    	switch (this.currentLevel) {
	        case "/level_1.png":
	        	this.currentLevel = "/level_2.png";
	          	this.music.setSong("songs/boss.wav");
	        	this.music.playSong(true);
	        	this.HP = this.player.getHP();
	        	this.handler.setNumberOfEnemies(enemiesInSecondsLevel);
	        	break;
    	default:
	        	this.currentLevel = "/level_1.png";
	        	this.handler.startGame(false);
	        	this.handler.setNumberOfEnemies(enemiesInFirstLevel);
	        	this.music.setSong("songs/intro.wav");
	        	this.music.playSong(false);
	        	this.HP = startingHP;
	        }
    }
    
    public void nextSong(String song) {
        if(this.music.getClip() != null) {
        	this.music.stopSong();
        	this.music = new MusicPlayer(handler);
        	this.music.setSong(song);
        	this.music.playSong(true);
        }
    }
    public static void main(String args[]) {
        Game game = new Game();

    }
}
