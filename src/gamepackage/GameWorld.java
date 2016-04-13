package gamepackage;

public class GameWorld {
	static int worldSize=10;
	Tileset bla=new Tileset(1);
	
	
		// TODO Auto-generated method stub

	
	Tileset[] getGameWorld() {
		Tileset[] gameWorld=new Tileset[worldSize];
		double y=Math.random()*Tileset.numberOfTileSets;
		int x=(int) y;
		gameWorld[0]=new Tileset(1);
		
		for(int i=1;i<worldSize ;i++) {
			y=  Math.random()*Tileset.numberOfTileSets;
			x=(int) y;
			//System.out.println(x);
			gameWorld[i]=new Tileset(x);
			
		}
		gameWorld[worldSize-1]=new Tileset(10);
		return gameWorld;
	}
}
