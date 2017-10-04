import java.util.*;

import javax.swing.table.TableModel;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.*;

public class Entorno {

	private Miembros matriz[][];
	private static int porcentaje;
	private static Scanner in;

	public Entorno()
	{
		matriz = new Miembros[1][1];
		matriz[0][0] = new Miembros();
		porcentaje = 10;
	}
	public Entorno(int N, int M) throws ConstructorException
	{
		if(N<=0||M<=0||(M==1&&N==1))
			throw new ConstructorException("Tamaño no valido");
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
	public Entorno(int N, int M, Coordenada meta, Coordenada coche, Coordenada[] obstaculos) throws ConstructorException
	{
		boolean fail=false;
		matriz = new Miembros[N][M];
		for(int i=0;i<N;i++)
		{
			for (int j=0; j<M;j++)
			{
				matriz[i][j]=new Miembros();
			}
		}
		if(meta.equals(coche))
			throw new ConstructorException("Coche no existente");
		matriz[meta.getX()][meta.getY()] = new Meta();
		matriz[coche.getX()][coche.getY()] = new Coche();
		for(int i=0; i< obstaculos.length; i++)
		{
			if(obstaculos[i].equals(coche)||obstaculos[i].equals(meta))
			{
				fail = true;
			}
			else
			{
				matriz[obstaculos[i].getX()][obstaculos[i].getY()] = new Obstaculo();
			}
		}
		try
		{
			if (fail)
				throw new ConstructorException("Obstaculos Conflictivos eliminados");
		}
		catch(ConstructorException e)
		{
			System.out.println(e.getMessage());
		}
	}
	public void show()
	{
		ColoredPrinter cp = new ColoredPrinter.Builder(1, false).build();
		for(int i = 0; i<matriz.length; i++)
		{
			for(int j=0; j<matriz[i].length;j++)
			{
				if(matriz[i][j]!=null)

					if(matriz[i][j].getName()=='c'||matriz[i][j].getName()=='M')
					{
						cp.print(matriz[i][j].getName(), Attribute.BOLD, FColor.GREEN, BColor.NONE);
						cp.clear();
					}
					else
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
			System.out.println("3. Elegir porcentaje de obstaculos aleatorios");
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
					try {
						prueba = new Entorno(n,m);
						prueba.show();
					} catch (ConstructorException e1) {
						System.out.println(e1.getMessage());
					}
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

					Coordenada coche = new Coordenada(x-1,y-1);
					System.out.println("Inserte x para la meta");
					x = in.nextInt();
					System.out.println("Inserte y para la meta");
					y = in.nextInt();
					Coordenada meta = new Coordenada(x-1,y-1);
					System.out.println("Cuantos obs?");
					numobs = in.nextInt();
					Coordenada obstaculos[] = new Coordenada[numobs];
					for(int i=0; i<numobs; i++)
					{
						System.out.println("Inserte x para el obstaculo");
						x = in.nextInt();
						System.out.println("Inserte y para el obstaculo");
						y = in.nextInt();
						Coordenada obs = new Coordenada(x-1,y-1);
						obstaculos[i]=obs;

					}
					try {
						prueba = new Entorno(n,m,meta,coche,obstaculos);
						prueba.show();
					} catch (ConstructorException e) {
						System.out.println(e.getMessage());
					}
					catch (ArrayIndexOutOfBoundsException e)
					{
						System.out.println("Alguno de lo miembros estaba fuera de la matriz");
					}
					break;
				case 3:
					System.out.println("Escriba el porcentaje");
					x = in.nextInt();
					prueba.setPorcentaje(x);
					break;
				case 4:
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

	public void setPorcentaje(int p)
	{
		porcentaje = p;
	}
	public Miembros[][] getMatriz(){
		return matriz;
	}
	public int getPorcentaje() {
		return porcentaje;
	}
	public void setMatriz(int row, int column, Miembros miembros) {
		matriz[row][column] = miembros;	
	}
	public boolean test() {
		int coches=0,metas=0;
		for(int i=0; i<matriz.length;i++)
			for(int j =0;j<matriz[i].length;j++)
			{
				if(Character.toString(matriz[i][j].getName()).equals("c"))
					coches++;
				if(Character.toString(matriz[i][j].getName()).equals("M"))
					metas++;
			}
		return ((coches==1)&&(metas==1));
	}


};
