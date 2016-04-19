package gamepackage;

public class Tileset {
	int set;
	int numberoftiles;
final static int numberOfTileSets=6;
	Tile[][] tileset;
	Tileset(int set) {
		this.set=set;
		
	}
	Tile[][] getSet() {
	//Tile[][] x =new Tile[3][3];
	
	switch(set) {

	case 1:   numberoftiles=4;return this.tileset=new Tile[][]{
		{new Tile("solid"),new Tile("solid"),new Tile("solid"),new Tile("solid")}, 
		{new Tile("empty"),new Tile("solid"),new Tile("solid"),new Tile("empty")},
		{new Tile("empty"),new Tile("empty"),new Tile("empty"),new Tile("empty")}};
		
	case 2:  numberoftiles=9; return this.tileset= new Tile[][]{
		{new Tile("solid"),new Tile("solid"),new Tile("solid")}, 
		{new Tile("solid"),new Tile("solid"),new Tile("solid")},
		{new Tile("solid"),new Tile("solid"),new Tile("solid")}};
		
	case 3:  numberoftiles=1; return this.tileset= new Tile[][]{
		{new Tile("emty"),new Tile("empty"),new Tile("solid")}, 
		{new Tile("empty"),new Tile("empty"),new Tile("empty")},
		{new Tile("empty"),new Tile("empty"),new Tile("empty")}};
		
	case 4:   numberoftiles=6; return this.tileset= new Tile[][]{
		{new Tile("solid"),new Tile("solid"),new Tile("solid")}, 
		{new Tile("empty"),new Tile("solid"),new Tile("solid")},
		{new Tile("empty"),new Tile("empty"),new Tile("solid")}};
		
		//finish line
	case 10:   numberoftiles=3;return this.tileset= new Tile[][]{
		{new Tile("solid"),new Tile("solid"),new Tile("solid")}};

		//flat tile
	default: numberoftiles=3; return this.tileset= new  Tile[][]{
		{new Tile("solid"),new Tile("solid"),new Tile("solid")}};
	}
	
	}
}
