package gamepackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePaneel extends JPanel implements KeyListener{
	int x;
	Tileset[] gameworld;
	Character c;
	boolean gameover=false;
	GamePaneel(int x,Tileset[] world, Character c) {
		this.x=x;
		this.c=c;
		this.gameworld=world;

		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}



	public void paintComponent(Graphics g) {

		int rectsize=100;
		Graphics2D g2 = (Graphics2D) g;

		super.paintComponent(g);		
		String path = "Sprites/pikachu.png";
		File file = new File(path);
		BufferedImage image;
		try {
			image = ImageIO.read(file);
			int size = 50;
			g2.drawImage(image, c.posX, getHeight() - 200 - size - c.posY  , size, size, this); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//	Tileset[] gameworld=Test.world.getGameWorld();
		//	GameWorld bla=Test.world;
		//	Tileset[] gameworld=world.


		//	System.out.println("bla");
		//System.out.println(x);
		
		for(int i=0;i<gameworld.length;i++) {
			for (int j=0;j<3;j++) {

				for (int k=0;k<3;k++) {
//						System.out.println(gameworld[i].getSet()[j][k].type);
					if (gameworld[i].getSet()[j][k].type.equals("solid")) {

						//	System.out.println("solid");
						if (gameworld[i].set==0) {
							g.setColor(Color.BLACK);
							g.fillRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
//							g.fillRect((3*(i-x/300)+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
						}
						else if(gameworld[i].set==1) {
							g.setColor(Color.RED);
							g.fillRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
//							g.fillRect((3*(i-x/300)+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
						}
						else if(gameworld[i].set==2) {
							g.setColor(Color.GREEN);
							g.fillRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
//							g.fillRect((3*(i-x/300)+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
						}

						else {
							g.setColor(Color.YELLOW);
							g.fillRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
//							g.fillRect((3*(i-x/300)+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
						}



					}
					else {
						//System.out.println("empty");
						//	g.setColor(Color.GREEN);
						//	g.fillRect((3*i+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);

					}
				}
			}
		}
		if(c.posY<=-200) {
			gameover=true;
			System.out.println(x);
			System.out.println(c.posX);
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.BLACK);
			Font myFont = new Font ("Courier New", 1, 130);
		

			g.setFont (myFont);
		
			//g.drawString("Game Over", c.posX+400, 500);
			g.drawString("Game Over", 600, 500);
		//	g.drawString("press space to restart", c.posX-100,800);
			g.drawString("press space to restart",100,800);
			
			System.out.println("game over");
			}
		else {
			gameover=false;
		}



	}



	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
	//	int A=(int) x/(3*rectsize);
		if (gameover) {
			if(keyCode==KeyEvent.VK_SPACE) {
				c.posX=0;c.posY=0;
			}
			
			
		} else {
			
		
		switch( keyCode ){
		
		//	case if(gameworld[(int) x/(3*rectsize)].getSet()] 
			case KeyEvent.VK_RIGHT : x = x - 10;
		//	System.out.println(x);
			break;
			case KeyEvent.VK_LEFT : x = x + 10;
			break;
			case KeyEvent.VK_UP : c.moveup(10);
			break;
			case KeyEvent.VK_DOWN: c.moveup(-10);
			break;
		}
		}
		repaint();
	}


	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
