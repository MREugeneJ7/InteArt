import java.util.*;

public class Entorno {

	Miembros matriz[][];
	private static Scanner in;
	
	public Entorno(int N, int M)
	{
		boolean carPlaced = false;
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
				if(4==(int)(Math.random()*(10)))
				{
					if(!carPlaced)
					{
						matriz[i][j]=new Coche();
						carPlaced=true;
					}
					else
					{
						matriz[i][j] = new Obstaculo();
					}
				}
			}
		}
		if(!carPlaced)
		{
			int n = (int)(Math.random()*N);
			int m = (int)(Math.random()*M);
			matriz[n][m] = new Coche();
		}
		while(!hayMeta)
		{
			int n = (int)(Math.random()*N);
			int m = (int)(Math.random()*M);
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
		System.out.println("Inserte N");
		in = new Scanner(System.in);
		int N = in.nextInt();
		System.out.println("Inserte M");
		in = new Scanner(System.in);
		int M = in.nextInt();
		Entorno prueba = new Entorno(N,M);
		prueba.show();
		System.exit(0);
	}
};
