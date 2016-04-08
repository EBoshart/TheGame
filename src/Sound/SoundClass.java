package Sound;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class SoundClass {
	
	static private MediaPlayer mediaPlayer;
	JFXPanel fxPanel = new JFXPanel();

	public static void PlaySound(){
		
		String bip = "file:/C:/Users/Student/git/PlatformGame/bin/Sound/Nyan.mp3";
		System.out.println(bip);
		Media hit = new Media(bip);
		mediaPlayer = new MediaPlayer(hit);
		
		mediaPlayer.play();
		
	}
}

