/**
 * IA Pract1
 * Coche.java
 * Purpose: Clase que define el coche inteligente.
 *
 * @author G.P.A (Gamusinos, Perros y Aerodirigibles)
 * @version 0.8 8/10/2017
 */

public class Coche extends Miembros {
		private boolean sensores[];
		
		/** Constructor de la clase Coche*/
		public Coche() {
			sensores = new boolean[4];
			name = 'c';
		}
}
