import java.util.*;

public class Entorno {

	Miembros matriz[][];
	static int porcentaje=10;
	private static Scanner in;
	
	public static Entorno Menu() {
		boolean salir = false;
		int opcion,n,m;
		Entorno prueba;
		
		while (!salir) {
            System.out.println("1. Constructor aleatorio");
            System.out.println("2. Constructor manual");
            System.out.println("3. Cambiar porcentaje");
            System.out.println("4. Salir");
            try {
            	 
                System.out.println("Escribe una de las opciones");
                opcion = in.nextInt();
 
                switch (opcion) {
                    case 1:
                        System.out.println("Inserte N");
                        n = in.nextInt();
                        System.out.println("Inserte M");
                        m = in.nextInt();
                        prueba = new Entorno(n,m);
                        break;
                    case 2:
                        System.out.println("Has seleccionado la opcion 2");
                        break;
                    case 3:
                        System.out.println("Has seleccionado la opcion 3");
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 4");
                }
            } catch (InputMismatchException e) {
                    System.out.println("Debes insertar un número");
                    in.next();
                }
		}
		return prueba;
	}

	public Entorno(int N, int M)
	{
		boolean hayMeta = false;
		matriz = new Miembros[N][M];
		for(int i=0;i<N;i++)
		{
			for (int j=0; j<M;j++)
			{
				matriz[i][j]=new Miembros();
			}
		}
		for(int i=0; i<N;i++)
		{
			for(int j=0; j<M;j++)
			{
				if(porcentaje>(int)(Math.random()*(100)))
				{
						matriz[i][j] = new Obstaculo();
				}
			}
		}
		int n = (int)(Math.random()*N);
		int m = (int)(Math.random()*M);
		matriz[n][m] = new Coche();
		while(!hayMeta)
		{
			n = (int)(Math.random()*N);
			m = (int)(Math.random()*M);
			if(matriz[n][m].getName()!='c')
			{
				matriz[n][m] = new Meta();
				hayMeta=true;
			}
		}
	}
	
	public void show()
	{
		for(int i = 0; i<matriz.length; i++)
		{
			for(int j=0; j<matriz[i].length;j++)
			{
				if(matriz[i][j]!=null)
					System.out.print(matriz[i][j].getName());
			}
			System.out.println();
		}
	}
	
	public static void main(String args[])
	{

		Entorno prueba = Entorno.Menu();
		prueba.show();
		System.exit(0);
	}
};
