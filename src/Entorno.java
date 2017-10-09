import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.*;

/**
 * IA Pract1
 * Entorno.java
 * Purpose: Se encarga de controlar la matriz que contiene el coche y los obstaculos.
 *
 * @author G.P.A (Grupo Putos Amos)
 * @version 0.10 8/10/2017
 */

public class Entorno {
	
	private static final int N_MAX = 200, M_MAX = 200;
	private Miembros matriz[][];
	private static int porcentaje;
	private static Scanner in;
	private Coordenada coche;

	public Entorno() {
		matriz = new Miembros[1][1];
		matriz[0][0] = new Miembros();
		porcentaje = 10;
	}
	/**
	 * Constructor aleatorio del entorno
	 * 
	 * @param n Numero de filas
	 * @param m Numero de columnas
	 * @throws ConstructorException Cuando el tama�o de la matriz no es valido
	 */
	public Entorno(int n, int m) throws ConstructorException {
		if(n <= 0 || m <= 0 || (m == 1 && n == 1) || n > N_MAX || m > M_MAX ) throw new ConstructorException("Tamaño no valido");
		boolean hayMeta = false;
		matriz = new Miembros[n][m];
		for(int i = 0;i < n;i++) for (int j = 0; j < m;j++) matriz[i][j] = new Miembros();
		for(int i = 0; i < n;i++) for(int j = 0; j < m;j++) if(porcentaje>(int)(Math.random() * (100))) matriz[i][j] = new Obstaculo();
		int nTemp = (int)(Math.random() * n);
		int mTemp = (int)(Math.random() * m);
		coche = new Coordenada(nTemp,mTemp);
		matriz[nTemp][mTemp] = new Coche();
		while(!hayMeta) {
			nTemp = (int)(Math.random() * n);
			mTemp = (int)(Math.random() * m);
			if(matriz[nTemp][mTemp].getName() != 'c') {
				matriz[nTemp][mTemp] = new Meta();
				hayMeta = true;
			}
		}
		((Coche)matriz[coche.getX()][coche.getY()]).setPosMeta(new Coordenada(nTemp-coche.getX(),mTemp-coche.getY()));
	}
	/**
	 * Constructor manual del entorno
	 * 
	 * @param n Numero de filas
	 * @param m Numero de columnas
	 * @param coche Posicion del coche
	 * @param meta Posicion de la meta
	 * @param obstaculos Vector de posiciones de los obstaculos.
	 * @throws ConstructorException Cuando el tama�o de la matriz no es valido o no hay coche
	 */
	public Entorno(int n, int m, Coordenada meta, Coordenada coche, Coordenada[] obstaculos) throws ConstructorException {
		if(n <= 0 || m <= 0 || (m == 1 && n == 1) || n > N_MAX || m > M_MAX ) throw new ConstructorException("Tamaño no valido");
		boolean fail = false;
		matriz = new Miembros[n][m];
		for(int i = 0;i < n;i++) for (int j = 0; j < m;j++) matriz[i][j] = new Miembros();
		this.coche = coche;
		if(meta.equals(coche)) throw new ConstructorException("Coche no existente");
		matriz[meta.getX()][meta.getY()] = new Meta();
		matriz[coche.getX()][coche.getY()] = new Coche();
		((Coche)matriz[this.coche.getX()][this.coche.getY()]).setPosMeta(meta.diff(coche));
		for(int i = 0; i < obstaculos.length; i++) if(obstaculos[i].equals(coche) || obstaculos[i].equals(meta)) fail = true;
		else matriz[obstaculos[i].getX()][obstaculos[i].getY()] = new Obstaculo();
		try {
			if (fail) throw new ConstructorException("Obstaculos Conflictivos eliminados");
		} catch(ConstructorException e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Constructor por fichero
	 * @param file
	 * @throws IOException
	 * @throws ConstructorException 
	 */
	public Entorno(String file) throws IOException, ConstructorException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		    String line = br.readLine();
		    int n = Integer.parseInt(line);
		    line = br.readLine();
		    int m = Integer.parseInt(line);
		    if(n <= 0 || m <= 0 || (m == 1 && n == 1) || n > N_MAX || m > M_MAX ) {
		    	br.close();
				throw new ConstructorException("Tamaño no valido");
			}
		    matriz = new Miembros[n][m];
		    for(int i = 0; i < n; i++) {
		    	for(int j = 0; j < m; j++) {
		    	switch ((char) br.read()){
		    	case 'c':
		    		matriz[i][j] = new Coche();
		    		coche = new Coordenada(i,j);
		    		break;
		    	case 'M':
		    		matriz[i][j] = new Meta();
		    		((Coche)matriz[coche.getX()][coche.getY()]).setPosMeta(new Coordenada(i-coche.getX(),j-coche.getY()));
		    		break;
		    	case 'o':
		    		matriz[i][j] = new Obstaculo();
		    		break;
		    	default:
		    		matriz[i][j] = new Miembros();
		    	}
		    	}
		    	String sobras = br.readLine();
		    	if(sobras != null && sobras.length() > 0){
		    		br.close();
		    		throw new ConstructorException("Fichero no completamente leido");
		    	}
		    }
		    if(br.readLine() != null){
		    	br.close();
		    	throw new ConstructorException("Fichero no completamente leido");
		    }
		    br.close();
		    if (!test()) throw new ConstructorException("coche no encontrado");
	}
	/**
	 * Imprime la matriz por consola
	 */
	public void show() {
		ColoredPrinter cp = new ColoredPrinter.Builder(1, false).build();
		for(int i = 0; i < matriz.length; i++) { 
			for(int j = 0; j < matriz[i].length;j++) if(matriz[i][j] != null) if(matriz[i][j].getName() == 'c' || matriz[i][j].getName() == 'M') {
				cp.print(matriz[i][j].getName(), Attribute.BOLD, FColor.GREEN, BColor.NONE);
				cp.clear();
			} else System.out.print(matriz[i][j].getName());
			System.out.println();
		}
	}
	/**
	 * Genera el men� que permite crear la matriz por consola de forma aleatoria y manual, adem�s de cambiar el porcentaje de aparici�n de obstaculos.
	 */
	public static Entorno menu() {
		boolean salir = false;
		int opcion,n,m,x,y,numObs = 0;
		Entorno prueba = new Entorno();
		in = new Scanner(System.in);

		while (!salir) {
			System.out.println("1. Constructor aleatorio");
			System.out.println("2. Constructor manual");
			System.out.println("3. Constructor por fichero");
			System.out.println("4. Elegir porcentaje de obstaculos aleatorios");
			System.out.println("5. Salir");
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

					Coordenada coche = new Coordenada(x - 1,y - 1);
					System.out.println("Inserte x para la meta");
					x = in.nextInt();
					System.out.println("Inserte y para la meta");
					y = in.nextInt();
					Coordenada meta = new Coordenada(x - 1,y - 1);
					System.out.println("Cuantos obs?");
					numObs = in.nextInt();
					Coordenada obstaculos[] = new Coordenada[numObs];
					for(int i = 0; i < numObs; i++) {
						System.out.println("Inserte x para el obstaculo");
						x = in.nextInt();
						System.out.println("Inserte y para el obstaculo");
						y = in.nextInt();
						Coordenada obs = new Coordenada(x - 1,y - 1);
						obstaculos[i] = obs;
					}
					try {
						prueba = new Entorno(n,m,meta,coche,obstaculos);
						prueba.show();
					} catch (ConstructorException e) {
						System.out.println(e.getMessage());
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Alguno de lo miembros estaba fuera de la matriz");
					}
					break;
				case 3:
					System.out.println("Escriba el nombre del fichero");
					String temp = in.next();
					try {
						prueba = new Entorno(temp);
						prueba.show();
					} catch (IOException | NumberFormatException e) {
						System.out.println("Fichero no encontrado o corrupto");
					} catch (ConstructorException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 4:
					System.out.println("Escriba el porcentaje");
					x = in.nextInt();
					prueba.setPorcentaje(x);
					break;
				case 5:
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
	/**
	 * Cambia el porcentaje de aparici�n de obstaculos del constructor aleatorio
	 * 
	 * @param p valor del porcentaje de aparici�n de obstaculos.
	 */
	public void setPorcentaje(int p) {
		porcentaje = p;
	}
	/**
	 * Devuelve la matriz
	 * 
	 * @return Matriz con todos los objetos
	 */
	public Miembros[][] getMatriz( ){
		return matriz;
	}
	/**
	 * Devuelve el porcentaje
	 * 
	 * @return valor del porcentaje
	 */
	public int getPorcentaje() {
		return porcentaje;
	}
	/**
	 * Cambia el objeto que ocupa una de las celdas de la matriz
	 * 
	 * @param row Fila
	 * @param column Columna
	 * @param miembros Nuevo objeto
	 */
	public void setMatrizCell(int row, int column, Miembros miembros) {
		matriz[row][column] = miembros;	
	}
	/**
	 * Comprueba que la matriz sea valida o que todavia no se ha llegado a la meta
	 * 
	 * @return Si hay �nicamente 1 coche y 1 meta
	 */
	public boolean test() {
		int coches = 0,metas = 0;
		for(int i = 0; i < matriz.length;i++) for(int j = 0;j < matriz[i].length;j++) {
			if(Character.toString(matriz[i][j].getName()).equals("c")) coches++;
			if(Character.toString(matriz[i][j].getName()).equals("M")) metas++;
		}
		return ((coches == 1) && (metas == 1));
	}


};
