package theGame;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer {
	
	private Handler handler;
	
	public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	
		Scanner scn = new Scanner(System.in);
		
		File file = new File("res/demo-song.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		String g = scn.nextLine();
		if(handler.isMuted()) {
			clip.stop();;
		}
	}		 
}
