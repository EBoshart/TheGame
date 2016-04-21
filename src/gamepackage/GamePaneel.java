package gamepackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.plaf.synth.SynthSpinnerUI;
import org.springframework.web.client.RestTemplate;



import animate.Walker;

public class GamePaneel extends JPanel implements KeyListener, ActionListener {
	int index;
	int x;
	Tileset[] gameworld;
	boolean gameover = false;
	ArrayList<Rectangle> rCube = new ArrayList<>();

	public Image currentSprite,currentSprite2, standaard, w0, w1, w2, w3, w4, w5, w6, w7, j1, j2,standaard2, W0, W1, W2, W3, W4, W5, W6, W7, J1, J2;
	Walker anim;
	Walker anim2;
	private int default_time = 1500;
	private int timebullet = 0;
	Character[] boss;
	Character c;
	Character bullet;
	
	Character cpowerup;
	int powerUpDuration = 750;
	
	double growfactor=1.8;
	Rectangle[] rBoss;
	Rectangle rCharacter;
	Rectangle rpowerup;
	int bulletWidth=268/85*20;
	int bulletHeight=188/85*20;
	int rectsize = 100;
	long frameCounter = 0;
	long startTime = System.currentTimeMillis();
	double playTime = 0;
	int fpsCounter = 0;
	int fpsOutput = 0;

	double gravity = 0.5;
	boolean gamefinished = false;
	// BufferedImage image = readimage("Sprites/pikachu.png");
	BufferedImage image0 = readimage("Sprites/tile1HD.png");

	BufferedImage image1 = readimage("Sprites/tile2HD.png");
	BufferedImage finishImg = readimage("Sprites/finish.png");
	BufferedImage hero = readimage("Sprites/hero.png");
	BufferedImage grass = readimage("Sprites/BG_grass.png");
	BufferedImage sky = readimage("Sprites/BG_sky.png");
	BufferedImage charizard = readimage("Sprites/charizard2.png");
	BufferedImage bulletbill =readimage("Sprites/bulletbill.png");
	BufferedImage powerupimg=readimage("Sprites/powerup_potion_green.png");
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
	int movementSpeed = 8;

	boolean bulletforward=true;
	boolean shooting =false;
	ArrayList<Integer> movementKeys = new ArrayList<Integer>();
	ArrayList<Long> movementFrames = new ArrayList<Long>();
	ArrayList<Rectangle> rBullet2=new ArrayList<>();
	ArrayList<Character> bullet2=new ArrayList<>();
	ArrayList<Boolean> bulletforward2 =new ArrayList<>();
	boolean[] bossforward;
	double[] bossgravity;
	boolean powerup=false;


	Timer time = new Timer(15, this);
	boolean paused = false;

	int X=50;
	int poweruptimer;


	GamePaneel(int x, Tileset[] world, Character c) {
		this.x = x;
		this.c = c;
		this.gameworld = world;
		

		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		time.start();
		rCharacter = new Rectangle();
		numberOfBosses = X;
		rpowerup=new Rectangle(0,0,0,0);
		cpowerup=new Character(0,0);
		// rBoss=new Rectangle[numberOfBosses];
		// boss= new Character[numberOfBosses];

		bullet=new Character(0,0);
		rBoss=new Rectangle[numberOfBosses];
		boss= new Character[numberOfBosses];
		bossforward=new boolean[numberOfBosses];
		bossgravity=new double[numberOfBosses];
		spawncharizards();
		spawnpowerup();

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
		standaard2 = readimage("Sprites/defaultn.png");
		W1 = readimage("Sprites/0n.png");
		W2 = readimage("Sprites/1n.png");
		W3 = readimage("Sprites/2n.png");
		W4 = readimage("Sprites/3n.png");
		W5 = readimage("Sprites/4n.png");
		W6 = readimage("Sprites/5n.png");
		W7 = readimage("Sprites/6n.png");
		J1 = readimage("Sprites/j1n.png");
		J2 = readimage("Sprites/j2n.png");

		anim = new Walker();
		anim.addFrame(w1, 50);
		anim.addFrame(w2, 50);
		anim.addFrame(w3, 50);
		anim.addFrame(w4, 50);
		anim.addFrame(w5, 50);
		anim.addFrame(w6, 50);
		anim.addFrame(w7, 50);

		anim2 = new Walker();
		anim2.addFrame(W1, 50);
		anim2.addFrame(W2, 50);
		anim2.addFrame(W3, 50);
		anim2.addFrame(W4, 50);
		anim2.addFrame(W5, 50);
		anim2.addFrame(W6, 50);
		anim2.addFrame(W7, 50);
		currentSprite2=anim2.getImage();
		currentSprite = anim.getImage();
	}

	public void move(int direction) {
		animate();
		if(powerup) {
			direction*=growfactor;
		}
		if (direction > 0) { // right
			if (!(testCollission(rCube, rCharacter) && forward)) {
				forward = true;

				if (c.posX + rectsize < (getWidth() / 3)) {
					c.move(direction);


				}
				else{
					x=x-direction;
					bullet.move(-direction);
					cpowerup.move(-direction);

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
					cpowerup.move(-direction);

					for(int i=0;i<numberOfBosses;i++) {


						boss[i].move(-direction);
					}
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(gamefinished){
			showHighscoreDialog();
			
		}

		if(gameover){
			showGameOverDialog();
		}




		for(int i=0;i<rBullet2.size();i++) {
		
			if(bulletforward2.get(i)) 
				bullet2.get(i).move(10);
			else
				bullet2.get(i).move(-10);



		}

		if (testCollission(rCube, rCharacter, true ) && moveRight) {

		}
		else if(moveRight){
			move(movementSpeed);
		}



		if(testCollission(rCube, rCharacter, false) && moveLeft){

		}
		else if(moveLeft){
			move(-movementSpeed);
		}

		// move right


		// move left



		// jump
		if (jump) {
			if (jumpAllowed) {
				if(!powerup) 
					c.moveup(-gravity + 15);
				else
					c.moveup(-gravity+15*growfactor*0.75);
				gravity += 0.1;
			}
		}
	
		if(!powerup &&gameUpdate(rCharacter,rpowerup)) {
			powerup=true;
			poweruptimer=0;
			rpowerup=null;
			
		}
		poweruptimer++;
		if(poweruptimer>powerUpDuration) {
			powerup=false;

			if(rpowerup==null) {
				spawnpowerup();
			}
		}
		timebullet+=50;

		if(shooting && timebullet > default_time) {
			timebullet=0; 
			shoot2(rCharacter);
		}

		for(int i=0;i<numberOfBosses/2;i++) {




			if(testCollission(rCube,rBoss[i],bossgravity[i])) {


				bossgravity[i] = 0;
			}
			else{
				boss[i].moveup(-bossgravity[i]);
				bossgravity[i] += 0.2;
			}
		}

		
		if (testCollission(rCube, rCharacter, gravity)) {
			gravity = 0;
			jumpAllowed = true;
		}

		else {
			c.moveup(-gravity);
			gravity += 0.1;
			if (!jump && gravity > 1) {
				jumpAllowed = false;
			}
		}


		for (int i = 0; i < numberOfBosses; i++) {

			if (testCollission(rCube, rBoss[i])) {
				bossforward[i] = !bossforward[i];
			}


			if (bossforward[i]) {
				boss[i].move(10);
			}
			else {
				boss[i].move(-10);
			}
		}

		frameCounter++;
		long currentTime = System.currentTimeMillis();
		if ((int)playTime == (currentTime - startTime) / 1000) {
			fpsCounter++;
		} else {
			fpsOutput = fpsCounter;
			fpsCounter = 0;
		}

		playTime = (currentTime-startTime);
		playTime = playTime/1000;


		for(int i=0;i<rBullet2.size();i++) {
			if(testCollission(rCube,rBullet2.get(i))) {
				rBullet2.remove(i);
				bullet2.remove(i);
				bulletforward2.remove(i);
			}
		}
		for(int i=0;i<numberOfBosses;i++) {
			for(int a=0;a<rBullet2.size();a++) {
				if(gameUpdate(rBoss[i],rBullet2.get(a))) {
					//System.out.println(number);
					//	System.out.println(i);
					numberOfBosses--;
					Rectangle[] X= new Rectangle[numberOfBosses];
					Character[] Y = new Character[numberOfBosses];
					for (int j=0;j<numberOfBosses;j++) {
						if(j<i) {
							X[j]=new Rectangle(rBoss[j].x,rBoss[j].y,100,100);
							Y[j]=new Character(boss[j].posX,boss[j].posY);
						}
						else {
							X[j]=new Rectangle(rBoss[j+1].x,rBoss[j+1].y,100,100);
							Y[j]=new Character(boss[j+1].posX,boss[j+1].posY);

						}
					}
					rBoss=X;
					boss=Y;
					bullet2.remove(a);
					rBullet2.remove(a);
					bulletforward2.remove(a);
					//	System.out.println(boss.length);
					//	System.out.println(rBoss.length);
					break; //outer;
				}
			}
		}
		for(int i=0;i<rBullet2.size();i++) {
			if(rBullet2.get(i).x > getWidth() || rBullet2.get(i).x <0) {
				bullet2.remove(i);
				rBullet2.remove(i);
				bulletforward2.remove(i);
				
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


	public void animate() {
		anim2.update(20);
		anim.update(20);
	}

	public void paintComponent(Graphics g) {
		rCube.clear();

		Graphics2D g2 = (Graphics2D) g;

		super.paintComponent(g);

		int size;
		if (!powerup) {
			size = rectsize;
		} else {
			size=(int) (growfactor*rectsize);
		}

		// parralax background
		if (!debug) {
			int skyScrolling = x / 3;
			int grassScrolling = x / 2;
			int numberOfBackgrounds = 4*gameworld.length*rectsize/getWidth();

			// sky
			for (int i = -1; i < numberOfBackgrounds; i++) {
				g2.drawImage(sky, (skyScrolling) + (getWidth() * i), 0, getWidth(), (getHeight() / 3) * 2, this); 
			}

			// grass
			for (int i = -1; i < numberOfBackgrounds; i++) {
				g2.drawImage(grass, (grassScrolling) + (getWidth() * i), (getHeight() / 3) * 2, getWidth(),(getHeight() / 3), this); 
			}
		}

		// face character right way
		if (!moveRight && !moveLeft && forward && !jump && jumpAllowed) {
			if(!powerup)
				g.drawImage(currentSprite = standaard, c.posX, getHeight() - size - c.posY, size, size, this);
			else
				g.drawImage(currentSprite2 = standaard2, c.posX, getHeight() - size - c.posY, size, size, this);

		}


		else if ( jump &&forward) {
			if(!powerup)
				g.drawImage(currentSprite = j1, c.posX, getHeight() - size - c.posY, size+25, size+25, this);
			else
				g.drawImage(currentSprite2 = J1, c.posX, getHeight() - size - c.posY, size+25, size+25, this);

		}


		else if (jump && !forward) {
			if(!powerup)
				g.drawImage(currentSprite = j1, c.posX + size, getHeight() - size - c.posY, -(size+25), size+25, this);
			else
				g.drawImage(currentSprite2 = J1, c.posX + size, getHeight() - size - c.posY, -(size+25), size+25, this);

		}



		else if (!jumpAllowed && !jump && forward) {
			if(!powerup)
				g.drawImage(currentSprite = j2, c.posX, getHeight() - size - c.posY, size+25, size+25, this);
			else
				g.drawImage(currentSprite2 = J2, c.posX, getHeight() - size - c.posY, size+25, size+25, this);

		}

		else if (!jumpAllowed && !jump && !forward) {
			if(!powerup)
				g.drawImage(currentSprite = j2, c.posX + size, getHeight() - size - c.posY, -(size+25), size+25, this);
			else
				g.drawImage(currentSprite2 = J2, c.posX + size, getHeight() - size - c.posY, -(size+25), size+25, this);

		}


		else if (!moveRight && !moveLeft && !forward) {
			if(!powerup)
				g.drawImage(currentSprite = standaard, c.posX + size, getHeight() - size - c.posY, -size, size, this);
			else
				g.drawImage(currentSprite2 = standaard2, c.posX + size, getHeight() - size - c.posY, -size, size, this);

		} else if (forward) {
			if(!powerup)
				g.drawImage(currentSprite = anim.getImage(), c.posX, getHeight() - size - c.posY, size, size, this);
			else
				g.drawImage(currentSprite2 = anim2.getImage(), c.posX, getHeight() - size - c.posY, size, size, this);

		} else {
			if(!powerup)
				g.drawImage(currentSprite = anim.getImage(), c.posX + size, getHeight() - size - c.posY, -size, size, this);
			else
				g.drawImage(currentSprite2 = anim2.getImage(), c.posX + size, getHeight() - size - c.posY, -size, size, this);


		}


		// character collision 
		//rCharacter.setBounds(c.posX-10, getHeight() - size - c.posY, size+20, size);
		rCharacter.setBounds(c.posX, getHeight() - size - c.posY, size, size);
		size=rectsize;
		for(int i=0;i<numberOfBosses;i++) {
			rBoss[i].setBounds(boss[i].posX, getHeight() - 100 - boss[i].posY, 100, 100);
		}
		for (int i = 0; i < numberOfBosses; i++) {
			// g.fillRect(boss[i].posX, getHeight()-size-boss[i].posY, size,
			// size);
		//	g.drawRect(boss[i].posX, getHeight() - size - boss[i].posY, size, size);
			g.drawImage(charizard, boss[i].posX, getHeight() - size - boss[i].posY, size, size, null);


		}



		for(int i=0;i<rBullet2.size();i++) {
			if(rBullet2.get(i).width!=0 ) {
				//	System.out.println("TEST TEST");
				g.setColor(Color.BLACK);
				int bulletwidth,bulletheight;
				if(powerup) {
					bulletwidth=(int) (growfactor*bulletWidth);
					bulletheight=(int) (growfactor*bulletHeight);
				}
				else {
					bulletwidth=bulletWidth;

					bulletheight=bulletHeight;
				}

				if(bulletforward2.get(i)){
					//	g.fillRect(rBullet2.get(i).x,rBullet2.get(i).y,rBullet2.get(i).width,rBullet2.get(i).height);
					rBullet2.get(i).setBounds(bullet2.get(i).posX,getHeight()-bulletheight-bullet2.get(i).posY,bulletwidth,bulletheight);
					g.drawImage(bulletbill, rBullet2.get(i).x,rBullet2.get(i).y,bulletwidth,bulletheight,null);

				}
				else {
					rBullet2.get(i).setBounds(bullet2.get(i).posX-bulletwidth,getHeight()-bulletheight-bullet2.get(i).posY,bulletwidth,bulletheight);
					g.drawImage(bulletbill, rBullet2.get(i).x,rBullet2.get(i).y,-bulletwidth,bulletheight,null);


				}
				
				//	System.out.println(rBullet);
			}

		}
		if(!powerup) {
			rpowerup.setBounds(cpowerup.posX,getHeight()-100-cpowerup.posY,rpowerup.width,rpowerup.height);

			//	g.fillRect(rpowerup.x, rpowerup.y, rpowerup.width, rpowerup.height);
			g.drawImage(powerupimg,rpowerup.x, rpowerup.y, rpowerup.width, rpowerup.height, null);
		} else {
			//rpowerup.setBounds(0,0,rpowerup.width,rpowerup.height);
		//	cpowerup=new Character(0,0);
		}

		int counter=0;

		// tilesets
		for (int i = 0; i < gameworld.length; i++) {
			for (int j = 0; j < gameworld[i].getSet().length; j++) {
				for (int k = 0; k < gameworld[i].getSet()[j].length; k++) {
					
					if (gameworld[i].getSet()[j][k].type.equals("solid")) {
			/*for (int j = 0; j < gameworld[i].tileset.length; j++) {
			
				for (int k = 0; k < gameworld[i].tileset[j].length; k++) {
					if(gameworld[i].tileset[j][k].type.equals("solid"))	{*/
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
						case 5:
							g.setColor(Color.CYAN);
							img = image1;
							break;
						case 6:
							g.setColor(Color.ORANGE);
							img = image1;
							break;
						case 10:
							g.setColor(Color.DARK_GRAY);
							img = finishImg;
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


		if (c.posY < -100 || testCollission(rBoss, rCharacter) && !powerup) {
			gameover=true;
		}
		for(int i=0;i<numberOfBosses;i++) {
		if (gameUpdate(rBoss[i],rCharacter)&& powerup) {
			numberOfBosses--;
			Rectangle[] X= new Rectangle[numberOfBosses];
			Character[] Y = new Character[numberOfBosses];
			for (int j=0;j<numberOfBosses;j++) {
				if(j<i) {
					X[j]=new Rectangle(rBoss[j].x,rBoss[j].y,100,100);
					Y[j]=new Character(boss[j].posX,boss[j].posY);
				}
				else {
					X[j]=new Rectangle(rBoss[j+1].x,rBoss[j+1].y,100,100);
					Y[j]=new Character(boss[j+1].posX,boss[j+1].posY);

				}
			}
			rBoss=X;
			boss=Y;
			break;

		}
		}


		for (int i = rCube.size() - 2; i < rCube.size(); i++) {


			if(gameUpdate(rCube.get(i),rCharacter,gravity)) {

				gamefinished = true;

			}
		}
	/*	if(testCollission(rCube,rCharacter,10.0)) {
			System.out.println(index);
			deleteTile(index);
		}*/
		// powerup hud
		if(powerup){
			
			g.setColor(new Color(7, 237, 65));
			g.fillRect((getWidth()/2)-200, 70, 400, 50);
			
			Font superFont = new Font("Courier New", 1, 50);
			g.setColor(new Color(4, 22, 219));
			g.setFont(superFont);
			g.drawString("SUPER", (getWidth()/2)-70, 110);
			
			g.setColor(new Color(201, 20, 20));
			g.fillRect((getWidth()/2)+200-400*poweruptimer/powerUpDuration, 70, 400*poweruptimer/powerUpDuration, 50);
		}

		// time hud
		g.setColor(new Color(17, 191, 75));
		Font timeFont = new Font("Courier New", 1, 50);
		g.setFont(timeFont);
		String timeText = "Time: " + df.format(playTime);
		g.drawString(timeText, (getWidth()/2)-150, 50);

		// debug
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
				g.drawLine(-100, getHeight() - (100 * r), getWidth(), getHeight() - (100 * r)); // horizontale lijn
				g.drawLine((x + (100 * r) - 1000), 0, (x + (100 * r) - 1000), getHeight()); // verticale lijn
			}

			// character middle
			g.setColor(new Color(16, 168, 26));
			g.drawLine(0, getHeight() - c.posY - (rectsize / 2), getWidth(), getHeight() - c.posY - (rectsize / 2)); // horizontale lijn
			g.drawLine(c.posX + (rectsize / 2), 0, c.posX + (rectsize / 2), getHeight()); // verticale lijn

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
			g.drawString("Playtime: " + df.format(playTime), debugPos, debugTextPos); debugTextPos += 15;
			g.drawString("Pause: " + paused, debugPos, debugTextPos); debugTextPos += 15;
			g.drawString("Powerup Time: " + poweruptimer, debugPos, debugTextPos); debugTextPos += 15;

			// movement input 
			int movXPos = getWidth()-120;
			int movYPos = 15;
			g.drawString("frame / input", movXPos-15, movYPos);
			for(int i=0; i<movementKeys.size(); i++){
				g.drawString(movementFrames.get(i) + " : " + keyEventToString(movementKeys.get(i)), movXPos, movYPos+(movementFrames.size()*15));
				movYPos-=15;
			}
			
			
		}

		if(paused){
			g.setColor(new Color(0f,0f,0f,.3f));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.black);
			Font pausedFont = new Font("Courier New", 1, 130);
			g.setFont(pausedFont);
			String pauseText = "Paused";
			FontMetrics pauseMetrics = g.getFontMetrics();
			int stringLength = pauseMetrics.stringWidth(pauseText);
			g.drawString(pauseText, (getWidth()/2)-(stringLength/2), getHeight()/5);
		}
	}

	public void showGameOverDialog(){
		togglePause();
		JOptionPane.showMessageDialog(this,  "You died after " + df.format(playTime) + " seconds of playing! \nPress OK to try again.", "Whoops!", JOptionPane.PLAIN_MESSAGE);
		respawn();
		togglePause();
	}

	public void showHighscoreDialog(){
		
		togglePause();
		String s = (String)JOptionPane.showInputDialog(this, "You beat the level in " + df.format(playTime) + " seconds! \nPlease enter your name:", "Congratulation", JOptionPane.PLAIN_MESSAGE);
//		System.out.println(s);
//		System.out.println(playTime);
		try {
	//	url=new URL("http://localhost/Test/servlettest/?param1="+s+"&param2="+playTime);
		//url=new URL("http://localhost/Test/servlettest/?param1="+urltest+"&param2="+urltest2);
		RestTemplate rest = new RestTemplate();
		//String j = rest.getForObject("http://localhost/Test/servlettest?param1="+urltest+"&param2="+urltest2,String.class);

		String j = rest.getForObject("http://localhost/PoKeMan/servlettest?param1="+s+"&param2="+playTime,String.class);

		} 
		catch(Exception exception) {
			
		}
		
	
		respawn();
		togglePause();
	}

	public void togglePause(){
		if(time.isRunning()){
			paused = true;
			time.stop();
			repaint();
		}
		else if(!time.isRunning()){
			paused = false;
			time.start();

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
		case 90 :
		case 17 :
			return "shoot";
		default :
			return ""+key;
		}
	}

	public boolean testCollission(ArrayList<Rectangle> rectanglearraylist, Rectangle pikachu) {
		for (int i = 0; i < rectanglearraylist.size(); i++) {
			if (gameUpdate(rectanglearraylist.get(i), pikachu)) {

				
				//	deleteTile(index);
				return true;

			}
		}
		return false;
	}



	public boolean testCollission(Rectangle[] rekt, Rectangle pikachu) {
		for (int i = 0; i < rekt.length; i++) {
			if (gameUpdate(rekt[i], pikachu)) {

				
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

	public boolean testCollission(ArrayList<Rectangle> rectanglearraylist, Rectangle pikachu,boolean dir)
	{
		ArrayList<Rectangle> x=new ArrayList<>();

		for(int i=0;i<rectanglearraylist.size();i++) {
			//	x.get(i).y=(int) (x.get(i).y-gravity);
			x.add(new Rectangle(rectanglearraylist.get(i)));
			if(dir){
				x.get(i).x=(int) (x.get(i).x-movementSpeed);

			}
			else{
				x.get(i).x=(int) (x.get(i).x+movementSpeed);
			}

		}

		for(int i=0;i<x.size();i++) {
			if(gameUpdate(x.get(i),pikachu)) 
			{
				// deleteTile(index);
				index=i;
				return true;

			}
		}
		return false;
	}

	public void spawncharizards() {

		numberOfBosses=X;
		boss=new Character[numberOfBosses];
		rBoss=new Rectangle[numberOfBosses];
		for(int i=0;i<numberOfBosses;i++) {
			//	boss[i]=new Character((int) Math.random()*50000,(int) Math.random()*2000);
			boss[i]=new Character((int) (Math.random()*10000+1000),(int) (Math.random()*1600));
			rBoss[i]=new Rectangle();

			rBoss[i].setBounds(boss[i].posX, getHeight() - 100 - boss[i].posY, 100, 100);

		}

	}


	

	public void deleteTile(int index) {
		int sum = 0;
		for (int i = 0; i < gameworld.length; i++) {

			// System.out.println(sum);
			if (sum + gameworld[i].numberoftiles > index) {
				// System.out.println(index+ " "+ sum);
				/*for (int j = 0; j < gameworld[i].numberoftiles; j++) {
					if (index == sum + j) {*/
						// System.out.println("sum="+(index+i+j));
						// gameworld[i].tileset[j][k]=new Tile("empty");
					//	System.out.println("well met");
					
					
				
						
						// gameworld[i].tileset
						// System.out.println("test");
						// gameworld[i]=null;
						// System.out.println(gameworld[i].tileset[j][k]);
				/*		return;
					}
				}
			} else {*/return;
				
			}
			sum = sum + gameworld[i].numberoftiles;
		}

	}

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
		jump = false;
		moveRight = false;
		moveLeft = false;
		shooting = false;
		powerup = false;
		forward=true;
		c.posX=100;c.posY=250;
		gameworld=(new GameWorld()).getGameWorld();
		gravity=2;
		x=0;
		spawncharizards();
		gamefinished=false;
		gameover=false;
		playTime =0;
		startTime = System.currentTimeMillis();
		spawnpowerup();
	}

	public void addMovements(int key, long frame){
		if(
				(key==KeyEvent.VK_SPACE && jump == false) || 
				(((key==KeyEvent.VK_RIGHT) || (key==KeyEvent.VK_D)) && moveRight == false) ||
				(((key==KeyEvent.VK_LEFT) || (key==KeyEvent.VK_A)) && moveLeft == false) ||
				(((key==KeyEvent.VK_CONTROL) || (key==KeyEvent.VK_Z)) && shooting == false)){
			movementKeys.add(key);
			movementFrames.add(frame);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		addMovements(keyCode, frameCounter);

		switch (keyCode) {
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			moveRight = true;
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			moveLeft = true;
			break;
		case KeyEvent.VK_CONTROL : 
		case KeyEvent.VK_Z : 
			shooting=true;
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
		case KeyEvent.VK_P :
			gamefinished=true;
		}
	}

	public void shoot2(Rectangle rekt) {
	
		if (!powerup) {
			rBullet2.add(new Rectangle(500,30,bulletWidth,bulletHeight));
			bullet2.add(new Character(c.posX+bulletWidth+10, c.posY+ bulletHeight/3));
		}
		
		else {
			rBullet2.add(new Rectangle(500,30,(int) (bulletWidth*growfactor),(int) (growfactor*bulletHeight)));
			bullet2.add(new Character((int) (c.posX+(bulletWidth+10)*growfactor), (int) (c.posY+growfactor*bulletHeight/3)));
		}
		bulletforward2.add(forward);

		repaint();

	}
	public void spawnpowerup() {
		rpowerup=new Rectangle(0,0,0,0);
		rpowerup.x=(int) (Math.random()*gameworld.length*3*rectsize);
		rpowerup.y=(int) (Math.random()*0.5*getHeight());
		rpowerup.y=500;
		rpowerup.width=100;
		rpowerup.height=100;


		cpowerup.posX=rpowerup.x;
		cpowerup.posY=rpowerup.y;
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
			break;
		case KeyEvent.VK_CONTROL:
		case KeyEvent.VK_Z:
			shooting=false;
		}
		addMovements(keyCode, frameCounter);
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}
