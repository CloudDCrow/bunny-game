package theGame;

import java.awt.Graphics;
import java.util.*;


public class Handler {
    
    ArrayList<GameObject> object;
    
    private int numberOfEnemies;
    private int PP;
    
    private boolean up = false,
            down = false,
            left = false,
            right = false,
            goingUp = false,
		    goingDown = false,
		    goingLeft = false,
		    goingRight = false,
			muted = false,
			proceed = false,
			begin = false,
			playerIsDead = false,
			reset = false;

    
    public Handler() {
    	this.object = new ArrayList<>();
    	this.PP = 200;
    }
    
   
    //Object commands
//////////////////////////////////////////////////////////

    public void addObject(GameObject tempObject) {
        this.object.add(tempObject);
    }
    
    public void removeObject(GameObject tempObject) {
        this.object.remove(tempObject);
    }
    
    public void removeAllObjects() {
    		this.object.clear();
        
    }

    //Player Commands
////////////////////////////////////////////
    
    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

	public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
    
	public void playerDied(boolean dead) {
		this.playerIsDead = dead;
	}
	
	public boolean playerIsDead() {
		return !this.playerIsDead;
	}    
    
    //Projectile commands
////////////////////////////////////////////
    
    public boolean isGoingUp() {
		return this.goingUp;
	}

	public void setGoUp(boolean goUp) {
		this.goingUp = goUp;
	}

	public boolean isGoingDown() {
		return this.goingDown;
	}

	public void setGoDown(boolean goDown) {
		this.goingDown = goDown;
	}

	public boolean isGoingLeft() {
		return this.goingLeft;
	}

	public void setGoLeft(boolean goLeft) {
		this.goingLeft = goLeft;
	}

	public boolean isGoingRight() {
		return this.goingRight;
	}

	public void setGoRight(boolean goRight) {
		this.goingRight = goRight;
	}

	//Music commands
////////////////////////////////////////////
	
	public void muteSong(boolean mute) {
		this.muted = mute;
	}
	
	public boolean isMuted() {
		return this.muted;
	}

	//Game state
////////////////////////////////////////////

	public void resetGame(boolean reset) {
		this.reset = reset;
	}
	
	public boolean toReset() {
		return this.reset;
	}
	
	public void nextLevel(boolean proceed) {
		this.proceed = proceed;
	}
	
	public boolean canGoToNextLevel() {
		return this.proceed;
	}
	
	public void startGame(boolean begin) {
		this.begin = begin;
	}
	
	public boolean canStart() {
		return this.begin;
	}
   public void enemyKilled() {
    	this.numberOfEnemies--;
    }
    
    public boolean roomCleared() {
    	return numberOfEnemies <= 0;
    }
    
    public void setNumberOfEnemies(int amount) {
    	this.numberOfEnemies = amount;
    }
    
    public void resetEnemies(int amount) {
    	this.numberOfEnemies = amount;
    }
    
    public void losePP() {
    	this.PP -= 10;
		if(this.PP == 0) {
			this.playerDied(true);
		}
    }
    
    public void gainPP(int amount) {
    	if(this.PP < 200) {
    		this.PP += amount;
    	}
    	if(this.PP > 200) {
    		this.PP = 200;
    	}
    }
    
    public void setPP(int amount) {
    	this.PP = amount;
    }
    
    public int getPP() {
    	return this.PP;
    }
    
////////////////////////////////////////////
    
    public void tick() {
    	for(int i = 0; i < this.object.size(); i++) {
    		GameObject obj = this.object.get(i);
    		obj.tick();
    	}
    }    
    
    public void render(Graphics g) {
    	for(int i = 0; i < this.object.size(); i++) {
    		GameObject obj = this.object.get(i);
    		obj.render(g);
    	}
    }
}