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

	double gravity = 0.5;
	boolean gamefinished=false;

	BufferedImage image0 = readimage("Sprites/tile1HD.png");
	BufferedImage image1 = readimage("Sprites/tile2HD.png");

	boolean debug = false;
	DecimalFormat df = new DecimalFormat("#.##");

	//movements
	boolean moveRight = false;
	boolean moveLeft = false;
	boolean jump = false;
	boolean forward = true;
	boolean jumpAllowed = true;

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


	public void move(int direction){
		if(direction > 0){ //right
			if (!(testCollission(rCube,rCharacter) && forward)){
				forward = true;

				if(c.posX+rectsize < (getWidth()/3)){
					c.move(direction);
				}
				else{
					x=x-direction;
				}
			}
		}
		
		else if(direction <0){ //left
			if (!(testCollission(rCube,rCharacter) && !forward)){
				forward = false;
				if(c.posX > (getWidth()/8)){
					c.move(direction);
				}
				else{
					x=x-direction;
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {

		//move right
		if(moveRight){
			move(10);
		}

		// move left
		if(moveLeft){
			move(-10);
		}

		for (Rectangle cube : rCube) {
			cube.y = (int) (cube.y - gravity);
		}

		if (testCollission(rCube, rCharacter)) {
			gravity = 0;
			jumpAllowed = true;	}
		/* bounce functie
				if (jump) {
					c.moveup(+gravity);
					gravity += 0.1;
		
					if (gravity > 6) {
						jump = false;
					}
		
				}
		 */
		else{
			c.moveup(-gravity);
			gravity += 0.1;
		}

		for (Rectangle cube : rCube) {
			cube.y = (int) (cube.y + gravity);
		}

		if (jump) {
			if(jumpAllowed){
				c.moveup(-gravity + 15);
				gravity += 0.1;
			}
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

		} else {
			g2.drawImage(image, c.posX + size, getHeight() - size - c.posY, -size, size, this);
		}
		rCharacter.setBounds(c.posX-10, getHeight() - size - c.posY, size+20, size);
		int counter=0;
		for (int i = 0; i < gameworld.length; i++) {
			for (int j=0; j<gameworld[i].getSet().length;j++) {
				for (int k=0;k<gameworld[i].getSet()[j].length;k++) {
					if (gameworld[i].getSet()[j][k].type.equals("solid")) {
						Rectangle cube= new Rectangle();
						BufferedImage img=null;
						switch(gameworld[i].set) {
						case 0: g.setColor(Color.BLACK);
						img=image0;
						break;
						case 1: g.setColor(Color.RED);
						img=image0;
						break;
						case 2: g.setColor(Color.GREEN);
						img=image0;
						case 3: g.setColor(Color.BLUE);
						img=image1;
						break;
						case 4: g.setColor(Color.MAGENTA);
						img=image1;
						break;
						case 10: g.setColor(Color.DARK_GRAY);
						img=image1;
						break;
						default: g.setColor(Color.YELLOW);
						img=image1;
						}
						g.fillRect(((counter+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
						cube.setBounds(((counter+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize);
						g.drawImage(img, ((counter+k)*rectsize)+x, (getHeight()-rectsize*(j+1)), rectsize, rectsize, null);
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

		for(int i=rCube.size()-5; i< rCube.size();i++) {

			if(gameUpdate(rCube.get(i),rCharacter)) {
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

		if(debug){

			// grid
			g.setColor(Color.orange);
			for(int r=0;r<1000;r++){
				g.drawLine(-100, getHeight()-(50*r), getWidth(), getHeight()-(50*r)); //horizontale lijn
				g.drawLine((x+(50*r)-1000), 0, (x+(50*r)-1000), getHeight());	//verticale lijn
			}

			// character middle
			g.setColor(new Color(16,168,26));
			g.drawLine(0, getHeight()-c.posY-(rectsize/2), getWidth(), getHeight()-c.posY-(rectsize/2)); //horizontale lijn
			g.drawLine(c.posX+(rectsize/2), 0, c.posX+(rectsize/2), getHeight()); //verticale lijn

			// character x and y
			g.setColor(Color.BLUE);
			g.drawLine(0, getHeight()-c.posY, getWidth(), getHeight()-c.posY);
			g.drawLine(c.posX, 0, c.posX, getHeight());

			// screen scroll boundaries
			g.setColor(Color.black);
			g.drawLine(getWidth()/3, 0, getWidth()/3, getHeight());
			g.drawLine(getWidth()/8, 0, getWidth()/8, getHeight());

			// character collision
			g.drawRect(c.posX -10, getHeight() - size - c.posY, size + 20, size);

			// top left values
			Font debugFont = new Font ("Courier New", 1, 15);
			g.setFont (debugFont);
			int debugPos = 10;
			int debugTextPos = 15;
			g.setColor(Color.ORANGE);
			g.drawString("Window width: " +getWidth(), debugPos, debugTextPos); debugTextPos += 15;
			g.drawString("Window Height: " +getHeight(), debugPos, debugTextPos); debugTextPos += 15;
			g.setColor(Color.BLACK);
			g.drawString("x: " +x, debugPos, debugTextPos); debugTextPos += 15;
			g.setColor(Color.BLUE);
			g.drawString("c.posX: " +c.posX, debugPos, debugTextPos); debugTextPos += 15;
			g.drawString("c.PosY: " +c.posY, debugPos, debugTextPos); debugTextPos += 15;
			g.setColor(new Color(16,168,26));
			g.drawString("c.posX middle: " + (c.posX+(rectsize/2)), debugPos, debugTextPos); debugTextPos += 15;
			g.drawString("c.PosY middle: " + (c.posY-(rectsize/2)), debugPos, debugTextPos); debugTextPos += 15;
			g.setColor(Color.BLACK);
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
	
	public void respawn(){
		c.posX=200;c.posY=500;
		forward=true;
		gameworld=(new GameWorld()).getGameWorld();
		gravity=0;
		x=gameworld.length;
		gamefinished=false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (gameover) {
			if(keyCode==KeyEvent.VK_SPACE) {
				respawn();
			}
		} 

		else {
			switch(keyCode){
			case KeyEvent.VK_RIGHT :
			case KeyEvent.VK_D :
				moveRight=true;
				break;
			case KeyEvent.VK_LEFT :
			case KeyEvent.VK_A :
				moveLeft=true;
				break;
			case KeyEvent.VK_SPACE :
				jump=true;
				break;
			case KeyEvent.VK_F12 :
				debug = !debug;
				break;
			case KeyEvent.VK_R :
				respawn();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch(keyCode){
		case KeyEvent.VK_RIGHT :
		case KeyEvent.VK_D :
			moveRight=false;
			break;
		case KeyEvent.VK_LEFT :
		case KeyEvent.VK_A :
			moveLeft=false;
			break;
		case KeyEvent.VK_SPACE :
			jump=false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}

