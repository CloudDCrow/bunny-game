package theGame;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
	
	public void play() {
		this.clip.start();
	}
	
	public boolean isPlaying() {
		return clip.isRunning();
	}
	
	public void stop() {
		this.clip.stop();
	}
	
	public void loop() {
		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void mute() {
		
		while(true) {			   
			if(handler.isMuted()) {
			   this.clip.stop();
		   }
		   
			if(!handler.isMuted()) {
				this.clip.start();
				this.clip.loop(Clip.LOOP_CONTINUOUSLY);
		   }
		 }
	}
}
