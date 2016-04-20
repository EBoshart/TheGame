package gamepackage;

public class Tileset {
	int set;
	int numberoftiles;
	final static int numberOfTileSets=8;
	Tile[][] tileset;
	Tileset(int set) {
		this.set=set;

	}
	Tile[][] getSet() {
		//Tile[][] x =new Tile[3][3];

		switch(set) {

		//	oooo
		//	oxxo
		//	xxxx
		case 1:   numberoftiles=6;return this.tileset=new Tile[][]{
			{new Tile("solid"),new Tile("solid"),new Tile("solid"),new Tile("solid")}, 
			{new Tile("empty"),new Tile("solid"),new Tile("solid"),new Tile("empty")},
			{new Tile("empty"),new Tile("empty"),new Tile("empty"),new Tile("empty")}};

		//	oxxxo
		//	oxxxo
		//	xxxxx
		case 2:  numberoftiles=11; return this.tileset= new Tile[][]{
			{new Tile("solid"),new Tile("solid"),new Tile("solid"),new Tile("solid"),new Tile("solid")}, 
			{new Tile("empty"),new Tile("solid"),new Tile("solid"),new Tile("solid"),new Tile("empty")},
			{new Tile("empty"),new Tile("solid"),new Tile("solid"),new Tile("solid"),new Tile("empty")}};
			
//			oox
		case 3:  numberoftiles=1; return this.tileset= new Tile[][]{
			{new Tile("emty"),new Tile("empty"),new Tile("solid")}};

//			xooooox
		case 4:  numberoftiles=2; return this.tileset= new Tile[][]{
			{new Tile("solid"),new Tile("empty"),new Tile("empty"),new Tile("empty"),new Tile("empty"),new Tile("empty"),new Tile("solid")}};
			
//			ooxoo
//			oxxxo
//			xxxxx
		case 5:   numberoftiles=9; return this.tileset= new Tile[][]{
			{new Tile("solid"),new Tile("solid"),new Tile("solid"),new Tile("solid"),new Tile("solid")}, 
			{new Tile("empty"),new Tile("solid"),new Tile("solid"),new Tile("solid"),new Tile("empty")},
			{new Tile("empty"),new Tile("empty"),new Tile("solid"),new Tile("empty"),new Tile("empty")}};

//			ooxooxoo
//			oxxooxxo
//			xxxooxxx
		case 6:   numberoftiles=12; return this.tileset= new Tile[][]{
			{new Tile("solid"),new Tile("solid"),new Tile("solid"),new Tile("empty"),new Tile("empty"),new Tile("solid"),new Tile("solid"),new Tile("solid"),}, 
			{new Tile("empty"),new Tile("solid"),new Tile("solid"),new Tile("empty"),new Tile("empty"),new Tile("solid"),new Tile("solid"),new Tile("empty"),},
			{new Tile("empty"),new Tile("empty"),new Tile("solid"),new Tile("empty"),new Tile("empty"),new Tile("solid"),new Tile("empty"),new Tile("empty"),}};
			
//			finish line
//			xxx
		case 10:   numberoftiles=3;return this.tileset= new Tile[][]{
			{new Tile("solid"),new Tile("solid"),new Tile("solid")}};

//			flat tile
//			xxx
		default: numberoftiles=3; return this.tileset= new  Tile[][]{
			{new Tile("solid"),new Tile("solid"),new Tile("solid")}};
		}

	}
}
