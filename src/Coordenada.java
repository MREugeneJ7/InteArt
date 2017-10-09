/**
 * IA Pract1
 * Coordenada.java
 * Purpose: Clase que guarda un par de enteros para indicar la posición de los miembros de la matriz.
 *
 * @author G.P.A (Grupo Programadores Amateur)
 * @version 1.1.a 9/10/2017
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
	 * @param posicion Coordenada que se somete a comparación
	 * @return Si ambas coordenadas son iguales
	 */
	public boolean equals (Coordenada posicion) {
		if ((this.x == posicion.getX()) && (this.y == posicion.getY())) return true;
		else return false;
	}
	/**
	 * Metodo que devuelve la coordenada resultante de restar dos coordenadas
	 * @param x Coordenada que se resta
	 * @return Diferencia
	 */
	public Coordenada diff(Coordenada x) {
		return new Coordenada(this.x - x.getX(),this.y - x.getY());
	}
	/**
	 * Añade a la coordenada actual los valores de otra coordenada
	 * @param dir Coordenada que se añade
	 */
	public void add(Coordenada dir) {
		this.x += dir.getX();
		this.y += dir.getY();
	}
}