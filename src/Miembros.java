
public class Miembros {

	protected char name;
	
	public Miembros()
	{
		name = '.';
	}
	
	public char getName()
	{
		return name;
	}
	
	public String toString()
	{
		return Character.toString(name);
	}
}
