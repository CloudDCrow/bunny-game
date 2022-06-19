package theGame;


import java.net.URL; 
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class MusicPlayer {
	
	private Handler handler;
	private Clip clip;
	
	public MusicPlayer(Handler handler) {
		this.handler = handler;
	}
	
	public void setSong(String song) {
			
		try {
			URL url = this.getClass().getResource(song);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
			this.clip = AudioSystem.getClip();
			this.clip.open(audioStream);

		} catch(Exception e) {	
		}
	}
	
	public void playSong(boolean loop) {

		if(clip != null) {
			if(handler.isMuted()) {
				   this.clip.stop();
			   }
			
			if(!handler.isMuted()) {
					this.clip.start();
					if(loop) loop();
			}
		} 
	}
	
	public boolean songIsPlaying() {
		return clip.isRunning();
	}
	
	public void stopSong() {
		this.clip.stop();
	}
	
	public Clip getClip() {
		return this.clip;
	}
	
	public void loop() {
		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void checkMute() {
		if(clip != null) {
			if(handler.isMuted()) {
				   this.clip.stop();
			   }
			   
			if(!handler.isMuted()) {
				this.clip.start();
				loop();				
		   	}
		} 
	}
}
