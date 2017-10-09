/**
 * IA Pract1
 * Aplicacion.java
 * Purpose: Clase que contiene el metodo main y enlaza la interfaz grafica con el algoritmo.
 *
 * @author G.P.A (Grupo Pringados Adorables)
 * @version 0.10 8/10/2017
 */
public class Aplicacion {
	/**
	 * Constructor de la clase aplicación
	 */
	public Aplicacion() {
		new VentanaEntorno(new Entorno());
	}
	public static void main(String args[]) {
		if(args.length>0)
			if(args[0].equals("-g")) new Aplicacion();
			else if (args[0].equals("-t")) {
				Entorno.menu();
				System.exit(0);
			} else System.out.println("Opcion Incorrecta. Pruebe -g o -t");
		else new Aplicacion();
	}

}
