package gamepackage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import Sound.SoundClass;

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
public class Test {
	
	
	static int width=1900;
	static int height=1000;
	static final GameWorld world=new GameWorld();
	static Tileset[] getworld=world.getGameWorld();
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("FrameDemo");
		Character c = new Character(100,500);
		SoundClass sound = new SoundClass();
		
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//for(int i=0;i<10;i++) {

		//frame.getContentPane().add(c.createCharacter());
		frame.add(new GamePaneel(0,getworld,c));
		
		sound.PlaySound();
		
		frame.setVisible(true);
		
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		}



	}

