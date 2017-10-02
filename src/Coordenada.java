
public class Coordenada {
	int x;
	int y;
	
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
		if ((this.x == posicion.x) && (this.y == posicion.y))
			return true;
		else return false;
	}
}