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
	private String song;
	
	public MusicPlayer(Handler handler, String song) {
		this.handler = handler;
		this.song = song;
	}
	
	public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
			
		URL file = this.getClass().getResource(song);
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		
		 while(true) {			   
		   if(handler.isMuted()) {
			   clip.stop();
		   }
		   
		   if(!handler.isMuted()) {
		   		clip.start();
		   		clip.loop(Clip.LOOP_CONTINUOUSLY);
		   }
		 }
	}		 
}
