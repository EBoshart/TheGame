package gamepackage;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.javafx.geom.Rectangle;

import javax.swing.*;

public class Character extends JPanel{
	
	int posX;
	int posY;
	public static Rectangle rekt = new Rectangle(0, 0, 0, 0);
	

	
	
	Character(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public JLabel createCharacter() throws IOException
	{
		String path = "Sprites/pikachu.png";
	    File file = new File(path);
	    BufferedImage image = ImageIO.read(file);
	    JLabel label = new JLabel(new ImageIcon(image));
		return label;
	
	}
	
	public void move(int direction){
		posX += direction;
		repaint();
		
	}
	public void moveup(double gravity) {
		posY+=gravity;
		repaint();
	}
	
	public void collisionDetection()
	{
		
	}

}


