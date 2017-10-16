/**
 * IA Pract1
 * Coche.java
 * Purpose: Clase que define el coche inteligente.
 *
 * @author G.P.A (Gamusinos, Perros y Aerodirigibles)
 * @version 1.1.c 10/10/2017
 */

public class Coche extends Miembros {
	private boolean sensores[];
	private Coordenada posMetaRel;

	/** Constructor de la clase Coche*/
	public Coche() {
		sensores = new boolean[4];
		name = 'c';
	}
	/**
	 * Constructor copia
	 * @param cpy coche a copiar
	 */
	public Coche(Coche cpy){
		this.name = cpy.getName();
		this.posMetaRel = cpy.getPosMetaRel();
		this.sensores = cpy.getSensores();
	}
	/** Devuelve los sensores*/
	private boolean[] getSensores() {
		return sensores;
	}
	/** Devuelve la posicion de la meta*/
	private Coordenada getPosMetaRel() {
		return posMetaRel;
	}
	/**
	 * Asigna un valor a la variable que define la posicion de la meta
	 * @param meta
	 */
	public void setPosMeta(Coordenada meta){
		this.posMetaRel = meta;
	}
	/**
	 * Calcula el siguiente movimiento del coche
	 * @param collisions Datos para los sensores
	 * @return Coordenada con el siguiente movimiento del coche
	 */
	public Coordenada[] move(boolean[] collisions){
		Coordenada movimiento[];
		Coordenada dummy[];
		dummy = new Coordenada[4];
		int size = 0;
		sensores = collisions;		
		for (int i = 0; i < sensores.length; i++) {
			if (!sensores[i]) {
				switch (i) {
					case 0: 
						dummy[size] = Directions.NORTH.getDir();
						break;
					case 1:
						dummy[size] = Directions.SOUTH.getDir();
						break;
					case 2:
						dummy[size] = Directions.EAST.getDir();
						break;
					case 3:
						dummy[size] = Directions.WEST.getDir();
						break;
					
				}
				size++;
			}
		}
		movimiento = new Coordenada [size];
		for (int i = 0; i < size; i++) movimiento[i] = dummy[i];
		
		return movimiento;
	}
}
