package gamepackage;

public class Tileset {
	int set;
final static int numberOfTileSets=6;
	Tileset(int set) {
		this.set=set;
		
	}
	Tile[][] getSet() {
	//Tile[][] x =new Tile[3][3];
	
	switch(set) {

	case 1:   return new Tile[][]{{new Tile("solid"),new Tile("solid"),new Tile("solid")}, {new Tile("empty"),new Tile("solid"),new Tile("empty")},{new Tile("empty"),new Tile("empty"),new Tile("empty")}};
	case 2:   return new Tile[][]{{new Tile("solid"),new Tile("solid"),new Tile("solid")}, {new Tile("solid"),new Tile("solid"),new Tile("solid")},{new Tile("solid"),new Tile("solid"),new Tile("solid")}};
	case 3:  return new Tile[][]{{new Tile("emty"),new Tile("empty"),new Tile("solid")}, {new Tile("empty"),new Tile("empty"),new Tile("empty")},{new Tile("empty"),new Tile("empty"),new Tile("empty")}};
	case 4:   return new Tile[][]{{new Tile("solid"),new Tile("solid"),new Tile("solid")}, {new Tile("empty"),new Tile("solid"),new Tile("solid")},{new Tile("empty"),new Tile("empty"),new Tile("solid")}};
	case 10:   return new Tile[][]{{new Tile("solid"),new Tile("empty"),new Tile("solid")}, {new Tile("empty"),new Tile("solid"),new Tile("empty")},{new Tile("solid"),new Tile("empty"),new Tile("solid")}};
	case 5:  return new Tile[][]{{new Tile("solid"),new Tile("solid"),new Tile("solid"),new Tile("solid")}, {new Tile("solid")},{new Tile("solid")},{new Tile("solid")},{new Tile("solid")},{new Tile("solid")},{new Tile("solid")}};


	default: return new Tile[][]{{new Tile("solid"),new Tile("solid"),new Tile("solid")}, {new Tile("empty"),new Tile("empty"),new Tile("empty")},{new Tile("empty"),new Tile("empty"),new Tile("empty")}};
	}
	
	}
}
