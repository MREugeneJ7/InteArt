
public class Coordenada {
	private int x;
	private int y;
	
	public Coordenada (int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX ()
	{
		return x;
	}
	
	public int getY ()
	{
		return y;
	}
	
	public boolean equals (Coordenada posicion)
	{
		if ((this.x == posicion.getX()) && (this.y == posicion.getY()))
			return true;
		else return false;
	}
}