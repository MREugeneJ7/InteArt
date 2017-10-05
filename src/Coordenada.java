/**
 * IA Pract1
 * Coordenada.java
 * Purpose: Clase que guarda un par de enteros para indicar la posición de los miembros de la matriz.
 *
 * @author G.P.A (Grupo Programadores Amateur)
 * @version 0.7.j 5/10/2017
 */
public class Coordenada {
	private int x;
	private int y;
	
	public Coordenada (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX () {
		return x;
	}
	
	public int getY () {
		return y;
	}
	
	public boolean equals (Coordenada posicion) {
		if ((this.x == posicion.getX()) && (this.y == posicion.getY())) return true;
		else return false;
	}
}