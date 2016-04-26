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



import javax.swing.*;

public class Character extends JPanel{
	
	int posX;
	int posY;
	
	

	
	
	Character(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
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


