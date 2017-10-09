/**
 * IA Pract1
 * Directions.java
 * Purpose: Lista de distintas direcciones posibles en la matriz.
 *
 * @author G.P.A (Grupo de pibonazos atractivos)
 * @version 1.1.a 9/10/2017
 */
public enum Directions {
	NORTH(-1,0),
	SOUTH(1,0),
	EAST(0,1),
	WEST(0,-1);

	private Coordenada dir;
	/**
	 * Constructor con coordenada
	 * @param x 
	 * @param y
	 */
	private Directions(int x, int y) {
		dir = new Coordenada(x,y);
	}
	/** Devuelve la coordenada asociada a la dirección*/
	public Coordenada getDir() {
		return dir;
	}

}
