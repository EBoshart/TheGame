package Sound;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;



public class SoundClass {

	static private MediaPlayer mediaPlayer;
	JFXPanel fxPanel = new JFXPanel();
	


	public static void PlaySound(){
	
		String bip = "http://10.2.22.56/PoKeMan/resources/theme.mp3";
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

