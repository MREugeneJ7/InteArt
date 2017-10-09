
public enum Directions {
	NORTH(-1,0),
	SOUTH(1,0),
	EAST(0,1),
	WEST(0,-1);
	
	private Coordenada dir;
	
	private Directions(int x, int y) {
		dir = new Coordenada(x,y);
	}
	
	public Coordenada getDir() {
		return dir;
	}

}
