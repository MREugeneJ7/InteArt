/**
 * IA Pract1
 * Coche.java
 * Purpose: Clase que define el coche inteligente.
 *
 * @author G.P.A (Gamusinos, Perros y Aerodirigibles)
 * @version 0.10 8/10/2017
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
		 * Asigna un valor a la variable que define la posicion de la meta
		 * @param meta
		 */
		public void setPosMeta(Coordenada meta){
			this.posMetaRel = meta;
		}
		
		public Coordenada move(boolean[] collisions){
			Coordenada movimiento;
			if(Math.abs(posMetaRel.getX()) < Math.abs(posMetaRel.getY()) && posMetaRel.getX() > 0 )
			{
				movimiento = new Coordenada(0,(int)Math.signum(posMetaRel.getY()));
				posMetaRel = posMetaRel.diff(movimiento);
				return movimiento;
			} else {
				movimiento = new Coordenada((int)Math.signum(posMetaRel.getX()),0);
				posMetaRel = posMetaRel.diff(movimiento);
				return movimiento;
			}
		}
}
