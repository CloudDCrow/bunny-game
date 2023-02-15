package theGame;

import java.awt.Canvas;  
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;



@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable{
	
    private final int SCREEN_WIDTH = 1000;
    private final int SCREEN_HEIGHT = 580;
    private final int STARTING_HP = 200;
    private final int ENEMIES_IN_FIRST_LEVEL = 2;
    private final int ENEMIES_IN_SECOND_LEVEL = 1;
    //private final int enemiesInThirdLevel = 10;

    private boolean isRunning = false;
    private boolean inIntro = true;
    private boolean inCredits = false;
    private String currentLevel;
    private String currentSong;
    private int HP;
    private int countIntro = 1000;
    private int countFirst = 500;
    private long firstLevelTextTimer;

    private Window window;
    private Thread thread;
    private Handler handler;
    private BufferedImage level = null;
    private BufferedImage sprites = null;
    private BufferedImage intro = null;
    private BufferedImageLoader loader = null;
    private Sprites spriteSheet;
    private Sprites introImage;
    private Camera camera;
    private MusicPlayer music;
    private Player player;
    
    public static void main(String args[]) {
        @SuppressWarnings("unused")
		Game game = new Game();
    }
    
    public Game(){
    	//Load intro image
    	this.loader = new BufferedImageLoader();
        this.intro = loader.loadImage("/intro-image.png");
        this.introImage = new Sprites(intro);
        this.intro = this.introImage.getSubimage(1, 1, 1000, 563);
    	
    	this.window = new Window(SCREEN_WIDTH, SCREEN_HEIGHT, "Firebunny Adventure", this);
        this.window.start();

        this.start();
        
        this.currentLevel = "/level_1.png";
        this.currentSong = "/intro.wav";
        this.HP = STARTING_HP;
        
        this.handler = new Handler();
        this.handler.setNumberOfEnemies(ENEMIES_IN_FIRST_LEVEL);
        
        this.camera = new Camera(0, 0);
       
        this.music = new MusicPlayer(handler);
        this.music.setSong(currentSong); 
        this.music.playSong(false);
       
    	//Load  sprites
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
    
    //Change Song
    public void nextSong(String song) {
        if(this.music.getClip() != null) {
        	this.music.stopSong();
            this.music.setSong(song);
            this.music.playSong(true);
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
	          
	    	if(inIntro)  {
	    		nextSong("/level-1-song.wav");
	    		inIntro = false;
	    	}
	    	
	    	if(inCredits)  {
	    		nextSong("/OOF.wav");
	    		inCredits = false;
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
    
    public void tick() {

    	//Update Camera
////////////////////////////////////////////////////////////////////

		for(int i = 0; i < handler.object.size(); i++) {
			
			if(handler.object.get(i).getID() == ID.Player) {
				this.camera.tick(this.handler.object.get(i));
			}
		}
		
		//Reset from start
////////////////////////////////////////////////////////////////////

        if(this.player != null & this.handler.toReset()) {
      	  if(this.player.isDead()) {
      		  this.handler.removeAllObjects();
      		  this.handler.resetEnemies(ENEMIES_IN_FIRST_LEVEL);
      		  
      		  if(!this.currentLevel.equals("/level_1.png")) {
      			  nextSong("/level-1-song.wav");
      		  }
      		  
      		  this.currentLevel = "/level_1.png";
      		  this.HP = STARTING_HP;
      		  this.handler.setPP(STARTING_HP);
              this.level = loader.loadImage(currentLevel);
      		  this.loadLevel(level);
      		  this.handler.startGame(true);
      	  }
        }  

        //Go to next level
////////////////////////////////////////////////////////////////////
        
        if(handler.roomCleared() & this.handler.canGoToNextLevel()) {
        	nextLevelLoader();
   	 	}
        
	    this.music.checkMute();

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
                
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        g2d.translate(-camera.getX(), -camera.getY());
        
        handler.render(g);
        
        g2d.translate(camera.getX(), camera.getY());
        
        introScreen(g, bs);
        
        firstLevelScreen(g, bs);
        
    	if(this.player != null) {
            healthBar(g);
            gameOverScreen(g);
            roomClearedScreen(g);
            creditsScreen(g);
    	}        
                
        g.dispose();
        bs.show();
    }
    
    	//Graphics
//////////////////////////////////////////////////////////////////
    public void introScreen(Graphics g,  BufferStrategy bs) {

    	while(!this.handler.canStart() && this.currentLevel.equals("/level_1.png")) {
    		this.inIntro = true;
    		
			g.drawImage(intro, 0, 0, null);


			if(countIntro > 1250) {
				g.setColor(Color.red);
			    g.setFont(new Font("Zapfino", Font.BOLD, 75));
				FontMetrics metrics = getFontMetrics(g.getFont());
				g.drawString("FireBunny Adventure", (SCREEN_WIDTH - metrics.stringWidth("FireBunny Adventure"))/2, 150);
		        bs.show();
		        

				if(countIntro > 1450) {
					for(int i = 0; i < 100; i++) {
						g.setFont(new Font("Ink Free", Font.BOLD, 20));
						g.setColor(new Color(100,100,100,255));
						FontMetrics metrics3 = getFontMetrics(g.getFont());
				    	g.drawString("Press 'Enter' to start",(SCREEN_WIDTH - metrics3.stringWidth("Press 'Enter' to start"))/2, 190);
				       
				    	bs.show();
			   	        this.music.checkMute();
				    	
				    	if(this.handler.canStart()) {
				    		break;
				    	}
					}
					for(int i = 0; i < 100; i++) {
			    		g.drawImage(intro, 0, 0, null);
						g.setColor(Color.red);
					    g.setFont(new Font("Zapfino", Font.BOLD, 75));		
					    g.drawString("FireBunny Adventure", (SCREEN_WIDTH - metrics.stringWidth("FireBunny Adventure"))/2, 150);

					    bs.show();
			   	        this.music.checkMute();

				    	if(this.handler.canStart()) {
				    		break;
				    	}
					}
				}
			}
	    	this.firstLevelTextTimer = System.currentTimeMillis();
	    	
			countIntro ++;
			
			g.dispose();
   		 	bs.show();
   	        g = bs.getDrawGraphics();
   	        this.music.checkMute();
    	}
    }
    
    public void firstLevelScreen(Graphics g, BufferStrategy bs) {
    	
	    	if(System.currentTimeMillis() - this.firstLevelTextTimer < 4000) {

	    		if(countFirst > 0) {
	    		g.setFont(new Font("Ink Free", Font.BOLD, 20));
		        g.setColor(new Color(255,255,255,countFirst/2));
				FontMetrics metrics = getFontMetrics(g.getFont());
		    	g.drawString("Kill 2 worms",(SCREEN_WIDTH - metrics.stringWidth("Kill 2 worms"))/2, 200);
	    		}
		        countFirst--;
    		}
    }
    
    public void creditsScreen(Graphics g) {
    	
    	if(this.handler.roomCleared() & this.currentLevel == "/level_2.png") {

    		this.inCredits = true;
    		
	    	g.setColor(Color.black);
			g.fillRect(0, 0, 1000, 563);
			g.setColor(Color.green);
		    g.setFont(new Font("Ink Free", Font.BOLD, 75));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("VICTORY!", (SCREEN_WIDTH - metrics.stringWidth("VICTORY!"))/2, SCREEN_HEIGHT/2 - 50);
			g.setFont(new Font("Ink Free", Font.BOLD, 20));
	        g.setColor(Color.white);
			FontMetrics metrics2 = getFontMetrics(g.getFont());
	    	g.drawString("Press 'E' to restart",(SCREEN_WIDTH - metrics2.stringWidth("Press 'E' to restart"))/2, SCREEN_HEIGHT/2);
		
			g.dispose();
    	}
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
  
    public void healthBar(Graphics g) {
    	g.setColor(Color.lightGray);
        g.fillRect(5, 5, 200, 20);
        g.setColor(Color.black);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        g.drawString("HP: " + player.getHP(), 5, 42);

        g.setColor(Color.green);
     	g.fillRect(5, 5, player.getHP(), 20);
     	
     	g.setColor(Color.lightGray);
     	g.fillRect(5, 55, 200, 20);
     	g.setColor(Color.black);
     	g.setFont(new Font("Ink Free", Font.BOLD, 20));
     	g.drawString("PP: " + this.handler.getPP(), 5, 92);
     	g.setColor(new Color(0, 255, 255));
      	g.fillRect(5, 55, this.handler.getPP(), 20);
      	
    }
    
    	//Level commands
//////////////////////////////////////////////////////////////////

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
    	
        this.music.stopSong();
        this.music = new MusicPlayer(handler);
        this.handler = new Handler();
       	this.camera = new Camera(0, 0);


        getNextLevel();
    
    	this.level = loader.loadImage(currentLevel);
        this.loadLevel(level);
        
        this.addKeyListener(new KeyInput(handler, spriteSheet));
    
    }
    
    public void getNextLevel() {
    	switch (this.currentLevel) {
	        case "/level_1.png":
	        	this.currentLevel = "/level_2.png";
	          	this.music.setSong("/boss.wav");
	        	this.music.playSong(true);
	        	this.HP = this.player.getHP();
	        	this.handler.setNumberOfEnemies(ENEMIES_IN_SECOND_LEVEL);
	        	break;
	        default:
	        	this.currentLevel = "/level_1.png";
	        	this.music.setSong("/intro.wav");
	        	this.music.playSong(false);
	        	this.handler.startGame(false);
	        	countIntro = 1000;
	        	countFirst = 500;
	        	this.HP = STARTING_HP;
	        	this.handler.setNumberOfEnemies(ENEMIES_IN_FIRST_LEVEL);
	        }
    }
}