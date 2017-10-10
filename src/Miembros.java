/**
 * IA Pract1
 * Miembros.java
 * Purpose: Clase que define un miembro cualquiera de la matriz.
 *
 * @author G.P.A (Grupo Problematico y Alborotador)
 * @version 1.1.a 9/10/2017
 */
public class Miembros {

	protected char name;
	/** Constructor de la clase Miembros*/
	public Miembros() {
		name = '.';
	}
	public Miembros(char c) {
		name = c;
	}
	/** Devuelve el nombre del miembro*/
	public char getName() {
		return name;
	}
	/**Sobreescribe el metodo toString de la clase object */
	public String toString() {
		return Character.toString(name);
	}
}
