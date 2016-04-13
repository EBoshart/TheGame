package gamepackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePaneel extends JPanel implements KeyListener, ActionListener{
	int x;
	Tileset[] gameworld;
	Character c;
	boolean gameover=true;
	ArrayList< Rectangle>  rCube = new ArrayList<>();
	//	Rectangle cube = new Rectangle();
	Rectangle rCharacter;
	int rectsize=100;
	boolean forward = false;
	boolean jump = false;
	double gravity=1;
	boolean debug = false;
	DecimalFormat df = new DecimalFormat("#.##");


	GamePaneel(int x,Tileset[] world, Character c) {
		this.x=x;
		this.c=c;
		this.gameworld=world;

		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		Timer time = new Timer(10, this);
		time.start();
		rCharacter = new Rectangle();


	}

	private void toggleDebug() {
		debug = !debug;
	}

	public void actionPerformed(ActionEvent e) {

		for(Rectangle cube:rCube) {
			//for (int i=0;i<rcube.size;i++)
			cube.y=(int) (cube.y-gravity);
		}

		if(jump)
		{
			c.moveup(+gravity);
			gravity+=0.1;
			//x = x - 10;
			if(gravity >6)
			{
				jump = false;
			}

		}

		else if (testCollission(rCube,rCharacter)){
			gravity=1;
		}
		else {
			c.moveup(-gravity+6);
			gravity+=0.1;	
		}



		for(Rectangle cube:rCube) {
			cube.y=(int) (cube.y+gravity);
		}

		repaint();



		//		Graphics2D g2 = (Graphics2D) g;
		//
		//		super.paintComponent(g);		
		//		String path = "Sprites/pikachu.png";
		//		File file = new File(path);
		//		BufferedImage image;
		//		try {
		//			image = ImageIO.read(file);
		//			int size = 100;
		//			g2.drawImage(image, c.posX + 200, getHeight() - size - c.posY  , size, size, this); 
		//			rCharacter.setBounds(c.posX + 200, getHeight()  - size - c.posY, size + 10, size);
		//			//
		//			g.drawRect(c.posX + 200, getHeight()  - size - c.posY, size + 10, size);
		//
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
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
			int size = rectsize;
			if (forward) {
				g2.drawImage(image, c.posX, getHeight() - size - c.posY  , size, size, this); 
				rCharacter.setBounds(c.posX, getHeight()  - size - c.posY, size + 10, size);

			} else {
				g2.drawImage(image, c.posX+size, getHeight() - size - c.posY  , -size, size, this); 
				rCharacter.setBounds(c.posX, getHeight()  - size - c.posY, size + 10, size);

			}


			//g2.drawImage(image, c.posX + 200, getHeight() - size - c.posY  , size, size, this); 
			rCharacter.setBounds(c.posX, getHeight()  - size - c.posY, size + 10, size);
			//
			g.drawRect(c.posX , getHeight()  - size - c.posY, size + 10, size);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<gameworld.length;i++) {
			

				for (int j=0;j<3;j++) {

					for (int k=0;k<3;k++) {

						if (gameworld[i].getSet()[j][k].type.equals("solid")) {
							Rectangle cube= new Rectangle();

							if (gameworld[i].set==0) {
								g.setColor(Color.BLACK);
								g.fillRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
								//							g.fillRect((3*(i-x/300)+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);

								cube.setBounds(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
								rCube.add(cube);
								g.setColor(Color.ORANGE);
								g.drawRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);

							}
							else if(gameworld[i].set==1) {
								g.setColor(Color.RED);
								g.fillRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
								//							g.fillRect((3*(i-x/300)+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize
								cube.setBounds(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
								rCube.add(cube);
								g.setColor(Color.ORANGE);
								g.drawRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							}
							else if(gameworld[i].set==2) {
								g.setColor(Color.GREEN);
								g.fillRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
								//							g.fillRect((3*(i-x/300)+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
								cube.setBounds(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
								rCube.add(cube);
								g.setColor(Color.ORANGE);
								g.drawRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							}

							else {
								g.setColor(Color.YELLOW);
								g.fillRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
								//							g.fillRect((3*(i-x/300)+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
								cube.setBounds(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
								rCube.add(cube);
								g.setColor(Color.ORANGE);
								g.drawRect(((3*(i)+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
							}


						}
						else {
							//	g.setColor(Color.GREEN);
							//	g.fillRect((3*i+k)*rectsize, (getHeight()-rectsize*(j+1)), rectsize, rectsize);

						}
					}
				}

			}

			if(c.posY<0) {
				gameover=true;

				g.setColor(Color.WHITE);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.BLACK);
				Font myFont = new Font ("Courier New", 1, 130);

				g.setFont (myFont);

				gravity=-0.5;

				//g.drawString("Game Over", c.posX+400, 500);
				g.drawString("Game Over", 600, 500);
				//	g.drawString("press space to restart", c.posX-100,800);
				g.drawString("press space to restart",100,800);
			}
			else {
				gameover=false;
			}

			if(debug){
				
				g.setColor(Color.orange);
				for(int r=0;r<1000;r++){
					g.drawLine(-100, getHeight()-(50*r), getWidth(), getHeight()-(50*r)); //horizontale lijn
					g.drawLine((x+(50*r)-1000), 0, (x+(50*r)-1000), getHeight());	//verticale lijn
				}
				
				g.setColor(Color.green);
				g.drawLine(0, getHeight()-c.posY-(rectsize/2), getWidth(), getHeight()-c.posY-(rectsize/2)); //horizontale lijn
				g.drawLine(c.posX+(rectsize/2), 0, c.posX+(rectsize/2), getHeight()); //verticale lijn
				
				g.setColor(Color.black);
				g.drawLine(getWidth()/3, 0, getWidth()/3, getHeight());
				g.drawLine(getWidth()/8, 0, getWidth()/8, getHeight());

				Font debugFont = new Font ("Courier New", 1, 15);
				g.setFont (debugFont);

				int debugPos = 10;
				int debugTextPos = 15;
				g.drawString("Window width: " +getWidth(), debugPos, debugTextPos); debugTextPos += 15;
				g.drawString("Window Height: " +getHeight(), debugPos, debugTextPos); debugTextPos += 15;
				g.drawString("x: " +x, debugPos, debugTextPos); debugTextPos += 15;
				g.drawString("c.posX: " +c.posX, debugPos, debugTextPos); debugTextPos += 15;
				g.drawString("c.PosY: " +c.posY, debugPos, debugTextPos); debugTextPos += 15;
				g.drawString("Toon: Awesome", debugPos, debugTextPos); debugTextPos += 15;
				g.drawString("Gravity: " + df.format(gravity), debugPos, debugTextPos); debugTextPos += 15;
				g.drawString("Jump: " + jump, debugPos, debugTextPos); debugTextPos += 15;
				g.drawString("Forward: " + forward, debugPos, debugTextPos); debugTextPos += 15;
			}

		}
		public boolean testCollission(ArrayList<Rectangle> rectanglearraylist, Rectangle pikachu)
		{
			for(int i=0;i<rCube.size();i++) {
				if(gameUpdate(rCube.get(i),rCharacter)) 
				{
					return true;
				}

			}
			return false;


		}


		public boolean gameUpdate(Rectangle kubus, Rectangle pikachu)
		{
			if(kubus.intersects(pikachu))
			{

				return true;
			}
			else
			{
				return false;
			}
		}

		public void keyPressed(KeyEvent e) {
			boolean collission=false;
			int keyCode = e.getKeyCode();
			//	int A=(int) x/(3*rectsize);
			if (gameover) {
				if(keyCode==KeyEvent.VK_SPACE) {
					c.posX=200;c.posY=500;
					forward=true;
				}


			} else {


				switch( keyCode ){

				//	case if(gameworld[(int) x/(3*rectsize)].getSet()] 
				case KeyEvent.VK_D :
				case KeyEvent.VK_RIGHT :

					if (testCollission(rCube,rCharacter) && forward){

					}
					else {
						forward = true;
						if(c.posX+rectsize < (getWidth()/3)){
							c.move(10);
						}
						else{
							x=x-10;
						}

					}
					break;
				case KeyEvent.VK_LEFT : 
				case KeyEvent.VK_A : 
					if (testCollission(rCube,rCharacter) && !forward){


					}
					else {
						forward = false;
						if(c.posX > (getWidth()/8)){
							c.move(-10);
						}
						else{
							x=x+10;
						}
					}

					break;

				case KeyEvent.VK_UP : 
				case KeyEvent.VK_W : 
					if (testCollission(rCube,rCharacter)){
						c.moveup(10);
					}
					break;
				case KeyEvent.VK_DOWN : 
				case KeyEvent.VK_S : 
					if (testCollission(rCube,rCharacter)){
						c.moveup(-10);

					}
					break;
				case KeyEvent.VK_F12:
					toggleDebug();
					break;
				}


			}
			repaint();
		}




		public void keyReleased(KeyEvent e) {

			int keyCode = e.getKeyCode();

			switch( keyCode ){
			case KeyEvent.VK_SPACE :

			{
				jump = true;
			}
			}

		}
		public void keyTyped(KeyEvent e) {}



	}
