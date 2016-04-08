package gamepackage;

public class GameWorld {
	static int worldSize=100;
	Tileset bla=new Tileset(1);
	
	
		// TODO Auto-generated method stub

	
	Tileset[] getGameWorld() {
		Tileset[] gameWorld=new Tileset[worldSize];
		double y=Math.random()*Tileset.numberOfTileSets;
		int x=(int) y;
		gameWorld[0]=new Tileset(x);
		
		for(int i=1;i<100;i++) {
			y=  Math.random()*Tileset.numberOfTileSets;
			x=(int) y;
			//System.out.println(x);
			gameWorld[i]=new Tileset(x);
			
		}
		return gameWorld;
	}
}
