/**
 * IA Pract1
 * ConstructorException.java
 * Purpose: Clase que define excepciones originadas en el constructor del entorno.
 *
 * @author G.P.A (Gara es una Profesora Asombrosa)
 * @version 1.1.a 9/10/2017
 */
public class BadMatrixException extends Exception {


	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor que notifica del motivo del fallo durante la construcci�n de un objeto
	 * @param msg Motivo de fallo
	 */
	public BadMatrixException(String msg) {
		super(msg);
	}
	
}