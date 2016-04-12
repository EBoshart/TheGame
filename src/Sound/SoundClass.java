package Sound;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;



public class SoundClass {

	static private MediaPlayer mediaPlayer;
	JFXPanel fxPanel = new JFXPanel();

	public static void PlaySound(){

		String bip = "file:/C:/Users/Student/git/TheGame/bin/Sound/Nyan.mp3";
		//String bip = "file:/C:/Users/Sabuzyra/git/TheGame/bin/Sound/Nyan.mp3";
		System.out.println(bip);
		Media hit = new Media(bip);
		mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mediaPlayer.seek(Duration.ZERO);
			}
		});
		mediaPlayer.play();

	}
}

