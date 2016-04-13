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


public class GamePaneel extends JPanel implements KeyListener, ActionListener {
	int x;
	Tileset[] gameworld;
	Character c;
	boolean gameover = true;
	ArrayList<Rectangle> rCube = new ArrayList<>();

	Rectangle rCharacter;
	int rectsize = 100;
	boolean forward = false;
	boolean jump = false;

	double gravity = 0.5;
	boolean gamefinished=false;

	boolean debug = false;
	DecimalFormat df = new DecimalFormat("#.##");


	GamePaneel(int x, Tileset[] world, Character c) {
		this.x = x;
		this.c = c;
		this.gameworld = world;

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


		for (Rectangle cube : rCube) {
			cube.y = (int) (cube.y - gravity);
		}

		if (jump) {
			c.moveup(+gravity);
			gravity += 0.1;

			if (gravity > 6) {
				jump = false;
			}

		}

		else if (testCollission(rCube, rCharacter)) {
			gravity = 1;
		} else {
			c.moveup(-gravity + 6);
			gravity += 0.1;
		}

		for (Rectangle cube : rCube) {
			cube.y = (int) (cube.y + gravity);
		}

		repaint();

	}




	public BufferedImage readimage(String PATH) {
		String path = PATH;





		File file = new File(path);
		BufferedImage image;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {

			image = null;
			e.printStackTrace();
		}


		return image;
	}

	public void paintComponent(Graphics g) {
		rCube.clear();

		Graphics2D g2 = (Graphics2D) g;
		BufferedImage image = readimage("Sprites/pikachu.png");
		super.paintComponent(g);
		int size = rectsize;
		if (forward) {
			g2.drawImage(image, c.posX, getHeight() - size - c.posY, size, size, this);
			rCharacter.setBounds(c.posX, getHeight() - size - c.posY, size + 10, size);

		} else {
			g2.drawImage(image, c.posX + size, getHeight() - size - c.posY, -size, size, this);
			rCharacter.setBounds(c.posX, getHeight() - size - c.posY, size + 10, size);

		}

		rCharacter.setBounds(c.posX, getHeight() - size - c.posY, size + 10, size);

		g.drawRect(c.posX, getHeight() - size - c.posY, size + 10, size);

		int counter=0;
		for (int i = 0; i < gameworld.length; i++) {

			for (int j=0; j<gameworld[i].getSet().length;j++) {

				for (int k=0;k<gameworld[i].getSet()[j].length;k++) {



					if (gameworld[i].getSet()[j][k].type.equals("solid")) {
						Rectangle cube= new Rectangle();

						switch(gameworld[i].set) {
						case 0: g.setColor(Color.BLACK);
						break;
						case 1: g.setColor(Color.RED);
						break;
						case 2: g.setColor(Color.GREEN);
						break;
						case 3: g.setColor(Color.BLUE);
						break;
						case 4: g.setColor(Color.MAGENTA);
						break;
						case 10: g.setColor(Color.DARK_GRAY);
						break;
						default: g.setColor(Color.YELLOW);

						}
						g.fillRect(((counter+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
						cube.setBounds(((counter+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
						rCube.add(cube);
						g.setColor(Color.ORANGE);
						g.drawRect(((counter+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);



					}
				}

			}
			counter+=gameworld[i].getSet()[0].length;
		}
		if(c.posY<0) {
			gameover=true;

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.BLACK);
			Font myFont = new Font ("Courier New", 1, 130);

			g.setFont (myFont);
			g.drawString("Game Over", 600, 500);
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
		for(int i=rCube.size()-5; i< rCube.size();i++) {
			if( gameUpdate(rCube.get(i), rCharacter)) {
				gamefinished = true;



			}
		}




		if(gamefinished) {
			gameover=true;
			System.out.println(x);
			System.out.println(c.posX);

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.BLACK);
			Font myFont = new Font("Courier New", 1, 130);

			gameworld = (new GameWorld()).getGameWorld();
			g.setFont(myFont);
			g.drawString("Game finished", 600, 500);
			g.drawString("press space to restart", 100, 800);

		} else {
			gamefinished = false;
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
		if (gameover || gamefinished) {
			if(keyCode==KeyEvent.VK_SPACE) {
				c.posX=200;c.posY=500;
				forward=true;
				gameworld=(new GameWorld()).getGameWorld();
				gravity=0;
				x=gameworld.length;
				gamefinished=false;

			}
		} else {
			switch( keyCode ){


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

	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
}

