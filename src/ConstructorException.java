/**
 * IA Pract1
 * ConstructorException.java
 * Purpose: Clase que define excepciones originadas en el constructor del entorno.
 *
 * @author G.P.A (Gara es una Profesora Asombrosa)
 * @version 0.8 8/10/2017
 */
public class ConstructorException extends Exception {


	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor que notifica del motivo del fallo durante la construcción de un objeto
	 * @param msg Motivo de fallo
	 */
	public ConstructorException(String msg) {
		super(msg);
	}
	
}
