package gamepackage;

import com.sun.javafx.geom.Rectangle;

public class Tileset {
	int set;

	private Rectangle r;

	final static int numberOfTileSets=4;
	Tileset(int set) {
		this.set=set;
	}
	Tile[][] getSet() {
		//Tile[][] x =new Tile[3][3];

		switch(set) {
		case 1:   return new Tile[][]{{new Tile("solid"),new Tile("solid"),new Tile("solid")}, {new Tile("empty"),new Tile("solid"),new Tile("empty")},{new Tile("empty"),new Tile("empty"),new Tile("empty")}};
		case 2:   return new Tile[][]{{new Tile("solid"),new Tile("solid"),new Tile("solid")}, {new Tile("solid"),new Tile("solid"),new Tile("solid")},{new Tile("solid"),new Tile("solid"),new Tile("solid")}};
		case 3:  return new Tile[][]{{new Tile("solid"),new Tile("empty"),new Tile("solid")}, {new Tile("empty"),new Tile("empty"),new Tile("empty")},{new Tile("empty"),new Tile("empty"),new Tile("empty")}};
		default: return new Tile[][]{{new Tile("solid"),new Tile("solid"),new Tile("solid")}, {new Tile("empty"),new Tile("empty"),new Tile("empty")},{new Tile("empty"),new Tile("empty"),new Tile("empty")}};
		}

	}


	public void checkVerticalCollision(Rectangle rtop, Rectangle rbot){


		if (rtop.intersection(r) != null){
			System.out.println("upper collision");
		}

		if (rtop.intersection(r) != null){
			System.out.println("lower collision");
		}
	}
}
