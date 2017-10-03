
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
		}
		Entorno.Menu();
		System.exit(0);
	}

}
