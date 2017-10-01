import java.util.*;

public class Entorno {

	Miembros matriz[][];
	private static Scanner in;
	
	public Entorno(int N, int M)
	{
		boolean carPlaced = false;
		matriz = new Miembros[N][M];
		for(int i=0; i<N;i++)
		{
			for(int j=0; j<M;j++)
			{
				if(M*N==(int)Math.random()*(M*N))
				{
					if(!carPlaced)
					{
						matriz[i][j]=new Coche();
					}
					else
					{
						matriz[i][j] = new Obstaculo();
					}
				}
			}
		}
	}
	
	public void show()
	{
		for(int i = 0; i<matriz.length; i++)
		{
			for(int j=0; j<matriz[i].length;j++)
			{
				System.out.print(matriz[i][j].getName());
			}
			System.out.println();
		}
	}
	
	public static void main(String args[])
	{
		System.out.println("Inserte N");
		in = new Scanner(System.in);
		int N = in.nextInt();
		System.out.println("Inserte M");
		in = new Scanner(System.in);
		int M = in.nextInt();
		Entorno prueba = new Entorno(N,M);
		prueba.show();
	}
};
