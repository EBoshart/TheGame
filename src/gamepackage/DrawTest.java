package gamepackage;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class DrawTest {
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		 frame.setSize(300, 300);
		 

	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 100, 100, 100);
	}
}
