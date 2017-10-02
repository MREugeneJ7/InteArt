import java.util.*;

public class Entorno {

	Miembros matriz[][];
	int porcentaje=10;
	private static Scanner in;
	
	public Entorno()
	{
		matriz = new Miembros[1][1];
		matriz[0][0] = new Miembros();
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
	public Entorno(int N, int M, Coordenada meta, Coordenada coche, Coordenada[] obstaculos)
	{
		matriz = new Miembros[N][M];
		for(int i=0;i<N;i++)
		{
			for (int j=0; j<M;j++)
			{
				matriz[i][j]=new Miembros();
			}
		}
		matriz[meta.getX()][meta.getY()] = new Meta();
		matriz[coche.getX()][coche.getY()] = new Coche();
		for(int i=0; i< obstaculos.length; i++)
		{
			matriz[obstaculos[i].getX()][obstaculos[i].getY()] = new Obstaculo();
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
	
	public static Entorno Menu() {
		boolean salir = false;
		int opcion,n,m,x,y,numobs=0;
		Entorno prueba = new Entorno();
		in = new Scanner(System.in);
		
		while (!salir) {
            System.out.println("1. Constructor aleatorio");
            System.out.println("2. Constructor manual");
            System.out.println("3. Salir");
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
                        salir=true;
                        break;
                    case 2:
                    	System.out.println("Inserte N");
                        n = in.nextInt();
                        System.out.println("Inserte M");
                        m = in.nextInt();
                        System.out.println("Inserte x para el coche");
                        x = in.nextInt();
                        System.out.println("Inserte y para el coche");
                        y = in.nextInt();
                        Coordenada coche = new Coordenada(x,y);
                        System.out.println("Inserte x para la meta");
                        x = in.nextInt();
                        System.out.println("Inserte y para la meta");
                        y = in.nextInt();
                        Coordenada meta = new Coordenada(x,y);
                        System.out.println("Cuantos obs?");
                        numobs = in.nextInt();
                        Coordenada obstaculos[] = new Coordenada[numobs];
                        for(int i=0; i<numobs; i++)
                        {
                        	System.out.println("Inserte x para el obstaculo");
                            x = in.nextInt();
                            System.out.println("Inserte y para el obstaculo");
                            y = in.nextInt();
                            Coordenada obs = new Coordenada(x,y);
                            obstaculos[i]=obs;
                            
                        }
                        prueba = new Entorno(n,m,meta,coche,obstaculos);
                        salir=true;
                        break;
                    case 3:
                        salir = true;
                        break;
                    default:
                        System.out.println("Solo numeros enteros entre 1 y 3");
                }
            } catch (InputMismatchException e) {
                    System.out.println("Debes insertar un numero");
                    in.next();
                }
		}
		return prueba;
	}
	
	public static void main(String args[])
	{
		Entorno prueba = Entorno.Menu();
		prueba.show();
		System.exit(0);
	}
};
