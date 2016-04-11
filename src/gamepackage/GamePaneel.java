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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.sun.javafx.geom.Rectangle;

import javafx.css.SimpleStyleableObjectProperty;

public class GamePaneel extends JPanel implements KeyListener{
	int x;
	Tileset[] gameworld;
	Character c;
	boolean gameover=false;
	ArrayList< Rectangle>  rCube = new ArrayList();
	Rectangle rCharacter;
	int rectsize=100;


	GamePaneel(int x,Tileset[] world, Character c) {
		this.x=x;
		this.c=c;
		this.gameworld=world;

		addKeyListener(this);
		setFocusable(true);
		requestFocus();

		rCharacter = new Rectangle();

	}


	public void paintComponent(Graphics g) {
		rCube.clear();

		Graphics2D g2 = (Graphics2D) g;

		super.paintComponent(g);		
		String path = "Sprites/pikachu.png";
		File file = new File(path);
		BufferedImage image;
		try {
			image = ImageIO.read(file);
			int size = 100;
			g2.drawImage(image, c.posX, getHeight() - size - c.posY  , size, size, this); 
			rCharacter.setBounds(c.posX, getHeight()  - size - c.posY, size, size);
			g.drawRect(c.posX, getHeight()  - size - c.posY, size, size);

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
							Rectangle cube=new Rectangle();
							cube.setBounds(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							rCube.add(cube);
							g.setColor(Color.ORANGE);
							g.drawRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);

						}
						else if(gameworld[i].set==1) {
							g.setColor(Color.RED);
							g.fillRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							//							g.fillRect((3*(i-x/300)+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							Rectangle cube=new Rectangle();
							cube.setBounds(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							rCube.add(cube);
							g.setColor(Color.ORANGE);
							g.drawRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
						}
						else if(gameworld[i].set==2) {
							g.setColor(Color.GREEN);
							g.fillRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							//							g.fillRect((3*(i-x/300)+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							Rectangle cube=new Rectangle();
							cube.setBounds(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							rCube.add(cube);
							g.setColor(Color.ORANGE);
							g.drawRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
						}

						else {
							g.setColor(Color.YELLOW);
							g.fillRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							//							g.fillRect((3*(i-x/300)+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							Rectangle cube=new Rectangle();
							cube.setBounds(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							rCube.add(cube);
							g.setColor(Color.ORANGE);
							g.drawRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
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
		boolean collission=false;
		int keyCode = e.getKeyCode();
		//	int A=(int) x/(3*rectsize);
		if (gameover) {
			if(keyCode==KeyEvent.VK_SPACE) {
				c.posX=0;c.posY=0;
			}


		} else {


			switch( keyCode ){

			//	case if(gameworld[(int) x/(3*rectsize)].getSet()] 
			case KeyEvent.VK_RIGHT :
				for(int i=0;i<rCube.size();i++) {

					//	System.out.println(rCharacter.x+" "+rCharacter.y+" "+ rCube.get(i).x+ " "+rCube.get(i).y );
					if(testCollision(rCharacter.x,rCharacter.y,rCube.get(i).x,rCube.get(i).y,rectsize))
					{
						System.out.println(rCharacter.x+" "+rCharacter.y+" "+ rCube.get(i).x+ " "+rCube.get(i).y );
						System.out.println(i);
						collission =true; 
						System.out.print(collission);
					}

				}
				if (collission) {
					System.out.println("test iets");

				}
				else {
					x=x-10;
				}

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

	public boolean testCollision(int x1, int y1 , int x2, int y2, int rectsize)
	{
		if(x1 > x2 && y1 > y2)
		{

			if(x2 + rectsize > x1 && y1 - rectsize > y2)
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		if(x1 < x2 && y1 > y2)
		{
			if(x1 + rectsize > x2 && y1 - rectsize > y2)
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		if(x1 < x2 && y1 < y2)
		{
			if(x1 + rectsize > x2 && y2 - rectsize > y1)
			{
				return true;
			}
			else
			{
				return false;
			}

		}

		if(x1 > x2 && y1 < y2)
		{
			if(x2 + rectsize > x1 && y2 - rectsize < y1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;

	}

}
