package gamepackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import java.util.ListIterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import animate.Walker;

public class GamePaneel extends JPanel implements KeyListener, ActionListener {
	int index;
	int x;
	Tileset[] gameworld;

	boolean gameover = true;
	ArrayList<Rectangle> rCube = new ArrayList<>();

	public Image currentSprite, standaard, w0, w1, w2, w3, w4, w5, w6, w7, j1, j2;
	Walker anim;

	Character[] boss;
	Character c;
	Character bullet;
	Rectangle[] rBoss;
	Rectangle rCharacter;
	
	int rectsize = 100;
	long frameCounter = 0;
	long time = System.currentTimeMillis();
	int playTime = 0;
	int fpsCounter = 0;
	int fpsOutput = 0;

	double gravity = 0.5;
	boolean gamefinished = false;
	// BufferedImage image = readimage("Sprites/pikachu.png");
	BufferedImage image0 = readimage("Sprites/tile1HD.png");

	BufferedImage image1 = readimage("Sprites/tile2HD.png");
	BufferedImage hero = readimage("Sprites/hero.png");
	BufferedImage grass = readimage("Sprites/BG_grass.png");
	BufferedImage sky = readimage("Sprites/BG_sky.png");
	BufferedImage charizard = readimage("Sprites/charizard2.png");

	boolean debug = false;
	boolean showFPS = false;

	DecimalFormat df = new DecimalFormat("#.##");
	int numberOfBosses;
	// movements
	boolean moveRight = false;
	boolean moveLeft = false;
	boolean jump = false;
	boolean forward = true;
	boolean jumpAllowed = true;

	ArrayList<Integer> movementKeys = new ArrayList<Integer>();
	ArrayList<Long> movementFrames = new ArrayList<Long>();

	boolean[] bossforward;
	double[] bossgravity;

	Rectangle rBullet=new Rectangle(0,0,0,0);
	


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
		numberOfBosses = 100;

		// rBoss=new Rectangle[numberOfBosses];
		// boss= new Character[numberOfBosses];

		bullet=new Character(0,0);
		rBoss=new Rectangle[numberOfBosses];
		boss= new Character[numberOfBosses];
		bossforward=new boolean[numberOfBosses];
		bossgravity=new double[numberOfBosses];
		spawncharizards();
		
		
	



		
		

		


		standaard = readimage("Sprites/default.png");
		w1 = readimage("Sprites/0.png");
		w2 = readimage("Sprites/1.png");
		w3 = readimage("Sprites/2.png");
		w4 = readimage("Sprites/3.png");
		w5 = readimage("Sprites/4.png");
		w6 = readimage("Sprites/5.png");
		w7 = readimage("Sprites/6.png");
		j1 = readimage("Sprites/j1.png");
		j2 = readimage("Sprites/j2.png");

		anim = new Walker();
		anim.addFrame(w1, 50);
		anim.addFrame(w2, 50);
		anim.addFrame(w3, 50);
		anim.addFrame(w4, 50);
		anim.addFrame(w5, 50);
		anim.addFrame(w6, 50);
		anim.addFrame(w7, 50);

		currentSprite = anim.getImage();
	}

	public void move(int direction) {
		animate();
		if (direction > 0) { // right
			if (!(testCollission(rCube, rCharacter) && forward)) {
				forward = true;

				if (c.posX + rectsize < (getWidth() / 3)) {
					c.move(direction);


				}
				else{
					x=x-direction;
					bullet.move(-direction);
					for(int i=0;i<numberOfBosses;i++) {

						boss[i].move(-direction);

					}
				}
			}
		}

		else if (direction < 0) { // left
			if (!(testCollission(rCube, rCharacter) && !forward)) {
				forward = false;
				if (c.posX > (getWidth() / 8)) {
					c.move(direction);


				}
				else{
					x=x-direction;
					bullet.move(-direction);
					for(int i=0;i<numberOfBosses;i++) {


						boss[i].move(-direction);
					}
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {


		/*if(rBullet.width!=0) {
			bullet.move(10);
		}*/

		// move right
		if (moveRight) {
			move(10);
		}

		// move left
		if (moveLeft) {
			move(-10);
		}
	

		// jump
		if (jump) {
			if (jumpAllowed) {

				c.moveup(-gravity + 15);
				gravity += 0.1;
			}
		}

		///////////////////////////////


		for(int i=0;i<numberOfBosses/2;i++) {




			if(testCollission(rCube,rBoss[i],bossgravity[i])) {
				
			
				bossgravity[i] = 0;
			}
			else{
				boss[i].moveup(-bossgravity[i]);
				bossgravity[i] += 0.1;

			}

		
		}
	


		



		// collision check y



		
		/* bounce functie
				if (jump) {
					c.moveup(+gravity);
					gravity += 0.1;

					if (gravity > 6) {
						jump = false;
					}

				}

		*/
		  for(int i=0;i<numberOfBosses;i++) {
		  
		  
		  if(testCollission(rCube,rBoss[i],bossgravity[i])) { 
			  bossgravity[i] =  0; 
			  }
		  
		  
		  else{ boss[i].moveup(-bossgravity[i]); bossgravity[i] += 0.1;
		  
		  }
		  
		  }


		///////////////////////////////
//		// collision check y



		if (testCollission(rCube, rCharacter, gravity)) {
			gravity = 0;
			jumpAllowed = true;
		}
		/*
		 * bounce functie if (jump) { c.moveup(+gravity); gravity += 0.1;
		 * 
		 * if (gravity > 6) { jump = false; }
		 * 
		 * }
		 */
		else {
			c.moveup(-gravity);
			gravity += 0.1;
			if (!jump && gravity > 1) {
				jumpAllowed = false;
			}
		}

		/*
		 * for (Rectangle cube : rCube) { cube.y = (int) (cube.y + oldgravity);
		 * }
		 */
		for (int i = 0; i < numberOfBosses; i++) {

			if (testCollission(rCube, rBoss[i])) {
				bossforward[i] = !bossforward[i];
			}


		

			if (bossforward[i]) {
				boss[i].move(-1);



			}
			else {
				boss[i].move(-1);

		
			}
		}

		frameCounter++;
		long currentTime = System.currentTimeMillis();
		if (playTime == (currentTime - time) / 1000) {
			fpsCounter++;
		} else {
			fpsOutput = fpsCounter;
			fpsCounter = 0;
		}

		

		playTime = (int) ((currentTime-time)/1000);
	/*	outer:
		for(int i=0;i<numberOfBosses;i++) {
			
			if(gameUpdate(rBoss[i],rBullet)) {
				//System.out.println(number);
			//	System.out.println(i);
				numberOfBosses--;
				Rectangle[] X= new Rectangle[numberOfBosses];
				for (int j=0;j<numberOfBosses;j++) {
					if(j!=i) {
					X[j]=new Rectangle(rBoss[i].x,rBoss[i].y,100,100);
					}
				}
				rBoss=X;
				break outer;
			}
		}*/



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


	public void animate() {
		anim.update(20);
	}

	public void paintComponent(Graphics g) {
		rCube.clear();

		Graphics2D g2 = (Graphics2D) g;

		super.paintComponent(g);
		int size = rectsize;

		// parralax background
		if (!debug) {
			int skyScrolling = x / 3;
			int grassScrolling = x / 2;

			for (int i = -1; i < 2; i++) {
				g2.drawImage(sky, (skyScrolling) + (getWidth() * i), 0, getWidth(), (getHeight() / 3) * 2, this); // sky
			}

			for (int i = -1; i < 3; i++) {
				g2.drawImage(grass, (grassScrolling) + (getWidth() * i), (getHeight() / 3) * 2, getWidth(),
						(getHeight() / 3), this); // grass
			}
		}

		// face character right way
		if (!moveRight && !moveLeft && forward && !jump) {
			g.drawImage(currentSprite = standaard, c.posX, getHeight() - size - c.posY, size, size, this);
		}

		
	
		else if ( jump &&forward) {
				g.drawImage(currentSprite = j1, c.posX, getHeight() - size - c.posY, size+25, size+25, this);
			}
	
		
		else if ( jump && !forward) {
			g.drawImage(currentSprite = j1, c.posX + size, getHeight() - size - c.posY, -(size+25), size+25, this);
			}
		
		else if (!moveRight && !moveLeft && !forward) {
			g.drawImage(currentSprite = standaard, c.posX + size, getHeight() - size - c.posY, -size, size, this);
		} else if (forward) {
			g.drawImage(currentSprite = anim.getImage(), c.posX, getHeight() - size - c.posY, size, size, this);
		} else {
			g.drawImage(currentSprite = anim.getImage(), c.posX + size, getHeight() - size - c.posY, -size, size, this);

		}


		// character collision 
		//rCharacter.setBounds(c.posX-10, getHeight() - size - c.posY, size+20, size);
		rCharacter.setBounds(c.posX, getHeight() - size - c.posY, size, size);
		for(int i=0;i<numberOfBosses;i++) {
		//	System.out.println(i);
			//ERROR ERROR
			//ERROR ERROR
			//ERROR ERROR
			//ERROR ERROR
			//ERROR ERROR
			//ERROR ERROR
			
			rBoss[i].setBounds(boss[i].posX, getHeight() - 100 - boss[i].posY, 100, 100);
		}
		for (int i = 0; i < numberOfBosses; i++) {
			// g.fillRect(boss[i].posX, getHeight()-size-boss[i].posY, size,
			// size);
			g.drawRect(boss[i].posX, getHeight() - size - boss[i].posY, size, size);
			g.drawImage(charizard, boss[i].posX, getHeight() - size - boss[i].posY, size, size, null);


		}

		
		rBullet.setBounds(bullet.posX,getHeight()-100-bullet.posY,30,30);
		if(rBullet.width!=0 ) {
		//	System.out.println("TEST TEST");
			g.setColor(Color.BLACK);
			
			g.fillRect(rBullet.x,rBullet.y,rBullet.width,rBullet.height);
		//	System.out.println(rBullet);
		}
		
		int counter=0;


		// character collision

		


		for (int i = 0; i < gameworld.length; i++) {
			for (int j = 0; j < gameworld[i].getSet().length; j++) {
				for (int k = 0; k < gameworld[i].getSet()[j].length; k++) {
					if (gameworld[i].getSet()[j][k].type.equals("solid")) {
						Rectangle cube = new Rectangle();
						BufferedImage img = null;
						switch (gameworld[i].set) {
						case 0:
							g.setColor(Color.BLACK);
							img = image0;
							break;
						case 1:
							g.setColor(Color.RED);
							img = image0;
							break;
						case 2:
							g.setColor(Color.GREEN);
							img = image0;
						case 3:
							g.setColor(Color.BLUE);
							img = image1;
							break;
						case 4:
							g.setColor(Color.MAGENTA);
							img = image1;
							break;
						case 10:
							g.setColor(Color.DARK_GRAY);
							img = image1;
							break;
						default:
							g.setColor(Color.YELLOW);
							img = image1;
						}
						g.fillRect(((counter + k) * rectsize) + x, (getHeight() - rectsize * (j + 1)), rectsize,
								rectsize);
						cube.setBounds(((counter + k) * rectsize) + x, (getHeight() - rectsize * (j + 1)), rectsize,
								rectsize);
						if (!debug) {
							g.drawImage(img, ((counter + k) * rectsize) + x, (getHeight() - rectsize * (j + 1)),
									rectsize, rectsize, null);
						}
						rCube.add(cube);
						g.setColor(Color.ORANGE);
					}
				}
			}
			counter += gameworld[i].getSet()[0].length;
		}

		if (c.posY < 0 || testCollission(rBoss, rCharacter)) {


			gameover=true;
			c.posY=-1;
			spawncharizards();
			rectsize=0;
			//	rBoss[0]=rCharacter;

			gameover = true;

			rectsize = 0;
			// rBoss[0]=rCharacter;

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.BLACK);
			Font myFont = new Font("Courier New", 1, 130);

			g.setFont(myFont);

			g.drawString("Game Over", 600, 500);
			g.drawString("press space to restart", 100, 800);
		} else {
			gameover = false;
		}

		for (int i = rCube.size() - 5; i < rCube.size(); i++) {


			if(gameUpdate(rCube.get(i),rCharacter,gravity)) {


				gamefinished = true;
			}
		}



		if(gamefinished) {
			spawncharizards();
			gameover=true;


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

		Font debugFont = new Font("Courier New", 1, 15);
		g.setFont(debugFont);
		g.setColor(Color.BLACK);
		int debugPos = 10;
		int debugTextPos = 15;

		if (showFPS) {
			g.drawString("fps: " + fpsOutput, debugPos, debugTextPos);
			debugTextPos += 15;
		}

		if (debug) {
			// grid
			g.setColor(Color.orange);
			for (int r = 0; r < 1000; r++) {
				g.drawLine(-100, getHeight() - (100 * r), getWidth(), getHeight() - (100 * r)); // horizontale
				// lijn
				g.drawLine((x + (100 * r) - 1000), 0, (x + (100 * r) - 1000), getHeight()); // verticale
				// lijn
			}

			// character middle
			g.setColor(new Color(16, 168, 26));
			g.drawLine(0, getHeight() - c.posY - (rectsize / 2), getWidth(), getHeight() - c.posY - (rectsize / 2)); // horizontale
			// lijn
			g.drawLine(c.posX + (rectsize / 2), 0, c.posX + (rectsize / 2), getHeight()); // verticale
			// lijn

			// character x and y
			g.setColor(Color.BLUE);
			g.drawLine(0, getHeight() - c.posY, getWidth(), getHeight() - c.posY);
			g.drawLine(c.posX, 0, c.posX, getHeight());

			// screen scroll boundaries
			g.setColor(Color.black);
			g.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight());
			g.drawLine(getWidth() / 8, 0, getWidth() / 8, getHeight());

			// character collision
			g.drawRect(c.posX - 10, getHeight() - size - c.posY, size + 20, size);

			// top left values
			g.setColor(Color.ORANGE);
			g.drawString("Window width: " + getWidth(), debugPos, debugTextPos);
			debugTextPos += 15;
			g.drawString("Window Height: " + getHeight(), debugPos, debugTextPos);
			debugTextPos += 15;
			g.setColor(Color.BLACK);
			g.drawString("x: " + x, debugPos, debugTextPos);
			debugTextPos += 15;
			g.setColor(Color.BLUE);
			g.drawString("c.posX: " + c.posX, debugPos, debugTextPos);
			debugTextPos += 15;
			g.drawString("c.PosY: " + c.posY, debugPos, debugTextPos);
			debugTextPos += 15;
			g.setColor(new Color(16, 168, 26));
			g.drawString("c.posX middle: " + (c.posX + (rectsize / 2)), debugPos, debugTextPos);
			debugTextPos += 15;
			g.drawString("c.PosY middle: " + (c.posY - (rectsize / 2)), debugPos, debugTextPos);
			debugTextPos += 15;
			g.setColor(Color.BLACK);
			g.drawString("Toon: Awesome", debugPos, debugTextPos); debugTextPos += 15;
			g.drawString("Forward: " + forward, debugPos, debugTextPos); debugTextPos += 15;
			g.drawString("Gravity: " + df.format(gravity), debugPos, debugTextPos); debugTextPos += 15;
			g.drawString("Jump: " + jump, debugPos, debugTextPos); debugTextPos += 15;
			g.drawString("JumpAllowed: " + jumpAllowed, debugPos, debugTextPos); debugTextPos += 15;
			g.drawString("Frames: " + frameCounter, debugPos, debugTextPos); debugTextPos += 15;
			g.drawString("Playtime: " + playTime, debugPos, debugTextPos); debugTextPos += 15;

			// movement input 
			int movXPos = getWidth()-120;
			int movYPos = 0;
			for(int i=0; i<movementKeys.size(); i++){
				g.drawString(movementFrames.get(i) + " : " + keyEventToString(movementKeys.get(i)), movXPos, movYPos+(movementFrames.size()*15));
				movYPos-=15;
			}
		}
	}

	// part of debug
	public String keyEventToString(int key){
		switch(key){
		case 68 :
		case 39 :
			return "right";
		case 65 :
		case 37 :
			return "left";
		case 32 :
			return "jump";
		default :
			return ""+key;
		}

	}

	public boolean testCollission(ArrayList<Rectangle> rectanglearraylist, Rectangle pikachu) {
		for (int i = 0; i < rectanglearraylist.size(); i++) {
			if (gameUpdate(rectanglearraylist.get(i), pikachu)) {

				index=i;
				//	deleteTile(index);
				return true;

			}
		}
		return false;
	}

	

	public boolean testCollission(Rectangle[] rekt, Rectangle pikachu) {
		for (int i = 0; i < rekt.length; i++) {
			if (gameUpdate(rekt[i], pikachu)) {

				index = i;
				// deleteTile(index);
				return true;

			}
		}
		return false;
	}

	public boolean testCollission(ArrayList<Rectangle> rectanglearraylist, Rectangle pikachu,double zwaartekracht)
	{
		ArrayList<Rectangle> x=new ArrayList<>();
		
		//x.addAll(rectanglearraylist);
	/*	for(Rectangle bla: x){
			bla.y=(int) (bla.y-zwaartekracht);
	}*/
		for(int i=0;i<rectanglearraylist.size();i++) {
		//	x.get(i).y=(int) (x.get(i).y-gravity);
			x.add(new Rectangle(rectanglearraylist.get(i)));
			x.get(i).y=(int) (x.get(i).y-zwaartekracht);
		}
		
		for(int i=0;i<x.size();i++) {
			if(gameUpdate(x.get(i),pikachu)) 
			{
				// deleteTile(index);

				return true;

			}
		}
		return false;
	}

	public void spawncharizards() {
		for(int i=0;i<numberOfBosses;i++) {
			//	boss[i]=new Character((int) Math.random()*50000,(int) Math.random()*2000);
			boss[i]=new Character((int) (Math.random()*10000+1000),(int) (Math.random()*1600));
			rBoss[i]=new Rectangle();

			rBoss[i].setBounds(boss[i].posX, getHeight() - 100 - boss[i].posY, 100, 100);

		}
		
	}


	/*public void deleteTile(int index) {
=======

	public void deleteTile(int index) {
>>>>>>> branch 'master' of https://github.com/EBoshart/TheGame.git
		int sum = 0;
		for (int i = 0; i < gameworld.length; i++) {

			// System.out.println(sum);
			if (sum + gameworld[i].numberoftiles > index) {
				// System.out.println(index+ " "+ sum);
				for (int j = 0; j < gameworld[i].numberoftiles; j++) {
					if (index == sum + j) {
						// System.out.println("sum="+(index+i+j));
						// gameworld[i].tileset[j][k]=new Tile("empty");
						gameworld[i].tileset = new Tile[][] { { new Tile("empty") } };
						// gameworld[i].tileset
						// System.out.println("test");
						// gameworld[i]=null;
						// System.out.println(gameworld[i].tileset[j][k]);
						return;
					}
				}
			} else {
				sum = sum + gameworld[i].numberoftiles;
			}
		}

	}*/

	public boolean gameUpdate(Rectangle kubus, Rectangle pikachu) {
		if (kubus.intersects(pikachu)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean gameUpdate(Rectangle kubus, Rectangle pikachu, double zwaartekracht)
	{	
		Rectangle tempkubus = new Rectangle(kubus);
		tempkubus.y=(int) (tempkubus.y-zwaartekracht);
		if(tempkubus.intersects(pikachu))
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	public void respawn(){
		c.posX=100;c.posY=500;
		forward=true;
		gameworld=(new GameWorld()).getGameWorld();
		gravity=0;
		x=0;
		spawncharizards();
		gamefinished=false;
	}

	public void addMovements(int key, long frame){
		if(
				(key==KeyEvent.VK_SPACE && jump == false) || 
				(((key==KeyEvent.VK_RIGHT) || (key==KeyEvent.VK_D)) && moveRight == false) ||
				(((key==KeyEvent.VK_LEFT) || (key==KeyEvent.VK_A)) && moveLeft == false)){
			movementKeys.add(key);
			movementFrames.add(frame);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		addMovements(keyCode, frameCounter);


		if (gameover) {
			if (keyCode == KeyEvent.VK_SPACE) {
				rectsize = 100;
				respawn();
			}
		}

		else {
			switch (keyCode) {
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				moveRight = true;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				moveLeft = true;
				break;
			case KeyEvent.VK_SPACE:
				jump = true;
				break;
			case KeyEvent.VK_F12:
				debug = !debug;
				break;
			case KeyEvent.VK_F11:
				showFPS = !showFPS;
				break;
			case KeyEvent.VK_R:
				respawn();

			    break;
	/*		case KeyEvent.VK_UP : 
			case KeyEvent.VK_W :
				shoot(rCharacter);*/

			case KeyEvent.VK_P :
				System.out.println(movementKeys); 

			}
		}
	}
	public void shoot(Rectangle rekt) {
	//	bullet=new Rectangle(rekt);
	//	rBullet.x=rBullet.x+100+10;
		rBullet.x=500;
		rBullet.y=rBullet.y+30;
		rBullet.width=30;
		rBullet.height=30;
		bullet.posX=c.posX;
		bullet.posY=c.posY;
		repaint();
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			moveRight = false;
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			moveLeft = false;
			break;
		case KeyEvent.VK_SPACE:
			jump = false;
		}

		addMovements(keyCode, frameCounter);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
