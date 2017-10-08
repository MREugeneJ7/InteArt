/**
 * IA Pract1
 * Coordenada.java
 * Purpose: Clase que guarda un par de enteros para indicar la posici�n de los miembros de la matriz.
 *
 * @author G.P.A (Grupo Programadores Amateur)
 * @version 0.8 8/10/2017
 */
public class Coordenada {
	private int x;
	private int y;
	/**
	 * Constructor del metodo coordenada
	 * 
	 * @param x Posicion en el eje de abcisas
	 * @param y Posicion en el eje de ordenadas
	 */
	public Coordenada (int x, int y) {
		this.x = x;
		this.y = y;
	}
	/** Devuelve la x. */
	public int getX () {
		return x;
	}
	/** Devuelve la y. */
	public int getY () {
		return y;
	}
	/**
	 * Metodo que dictamina si dos coordenadas son iguales
	 * 
	 * @param posicion Coordenada que se somete a comparaci�n
	 * @return Si ambas coordenadas son iguales
	 */
	public boolean equals (Coordenada posicion) {
		if ((this.x == posicion.getX()) && (this.y == posicion.getY())) return true;
		else return false;
	}
}