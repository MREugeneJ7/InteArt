/**
 * IA Pract1
 * Miembros.java
 * Purpose: Clase que define un miembro cualquiera de la matriz.
 *
 * @author G.P.A (Grupo Problematico y Alborotador)
 * @version 0.7.i 5/10/2017
 */
public class Miembros {

	protected char name;
	
	public Miembros() {
		name = '.';
	}
	
	public char getName() {
		return name;
	}
	
	public String toString() {
		return Character.toString(name);
	}
}
