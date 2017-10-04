
public class Aplicacion {

	public Aplicacion()
	{
		new VentanaEntorno(new Entorno());
	}
	public static void main(String args[])
	{
		if(args.length>0)
		{
			if(args[0].equals("-g"))
			{
				new Aplicacion();
			}
			else if (args[0].equals("-t"))
			{
				Entorno.Menu();
				System.exit(0);
			}
			else
				System.out.println("Opcion Incorrecta. Pruebe -g o -t");
		}
		else
			new Aplicacion();
		
		
	}

}
