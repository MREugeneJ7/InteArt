import java.util.Comparator;

/**
 * IA Pract1
 * Meta.java
 * Purpose: Clase que define el ultimo miembro a visitar por el coche.
 *
 * @author G.P.A (Gruppies de Pablo Alboran)
 * @version 1.1.a 9/10/2017
 */
public class Meta extends Miembros implements Comparator<Coordenada> {
	/** Constructor de la clase Meta*/
	private Coordenada miPos;
	public Meta(Coordenada pos) {
		name = 'M';
		miPos = pos;
	}

	@Override
	/**
	 * Si la primera es más cercana a la meta devolverá 
	 * un negativo en caso contrario un positivo
	 */
	public int compare(Coordenada o1, Coordenada o2) {
		o1 = o1.diff(miPos);
		o2 = o2.diff(miPos);		
		return (Math.abs(o1.getX()) + Math.abs(o1.getY())) - (Math.abs(o2.getX()) + Math.abs(o2.getY()));
	}
}
