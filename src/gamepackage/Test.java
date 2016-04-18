package gamepackage;

import javax.swing.JFrame;
import Sound.SoundClass;
public class Test{
	
	static int width=1900;
	static int height=1000;
	static final GameWorld world=new GameWorld();
	static Tileset[] getworld=world.getGameWorld();
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		Character c = new Character(100,500);
		SoundClass sound = new SoundClass();
		
		JFrame frame = new JFrame("Superdupergame");
		frame.setSize(width, height);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//for(int i=0;i<10;i++) {

		//frame.getContentPane().add(c.createCharacter());
		frame.add(new GamePaneel(0,getworld,c));
		
		//sound.PlaySound();
		
		frame.setVisible(true);
		
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		}
	
	}

