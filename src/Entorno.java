import java.awt.Component;
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
 * @version 1.1.c 10/10/2017
 */

public class Entorno {

	private static final int N_MAX = 200, M_MAX = 200;
	private Miembros matriz[][];
	private static int porcentaje;
	private static Scanner in;
	private Coordenada coche,meta;
	private long tTS;
	private List<Coordenada> visitados = new ArrayList<Coordenada>();
	private List<Coordenada> caminoFinal = new ArrayList<Coordenada>();
	private List<Coordenada> mejor = new ArrayList<Coordenada>();

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
				meta = new Coordenada(nTemp,mTemp);
				matriz[nTemp][mTemp] = new Meta(meta);
				hayMeta = true;
			}
		}
		((Coche)matriz[this.coche.getX()][this.coche.getY()]).setPosMeta(meta.diff(coche));
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
		this.meta = meta;
		if(meta.equals(coche)) throw new ConstructorException("Coche no existente");
		matriz[meta.getX()][meta.getY()] = new Meta(meta);
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
					meta = new Coordenada(i,j);
					matriz[i][j] = new Meta(meta);
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
		((Coche)matriz[coche.getX()][coche.getY()]).setPosMeta(meta.diff(coche));
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
			} else if(matriz[i][j].getName() == 'x' ){
				cp.print(matriz[i][j].getName(), Attribute.BOLD, FColor.RED, BColor.NONE);
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
			System.out.println("5. Resolver");
			System.out.println("6. Salir");
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
					try {
						prueba.solve();
						prueba.showSolution();
					} catch (BadMatrixException e) {
						System.out.println(e.getMessage());
					}

					break;
				case 6:
					salir = true;
					break;
				default:
					System.out.println("Solo numeros enteros entre 1 y 6");
				}
			} catch (InputMismatchException e) {
				System.out.println("Debes insertar un numero");
				in.next();
			}
		}
		return prueba;
	}
	private void showSolution() {
		Coordenada temp;
		coche = caminoFinal.get(0);
		matriz[coche.getX()][coche.getY()] = new Coche();
		matriz[meta.getX()][meta.getY()] = new Meta(meta);
		System.out.println("Paso 0");
		show();
		System.out.println("*********************");
		for(int i = 1; i < caminoFinal.size(); i++) {
			temp = new Coordenada(coche.getX(),coche.getY());
			coche = caminoFinal.get(i);
			matriz[coche.getX()][coche.getY()] = matriz[temp.getX()][temp.getY()];
			matriz[temp.getX()][temp.getY()] = new Miembros('F');
			System.out.println("Paso "+i);
			show();
			System.out.println("*********************");
		}
	}
	/**
	 * Resuelve el problema
	 * @throws BadMatrixException 
	 */
	public void solve() throws BadMatrixException {
		tTS = System.currentTimeMillis();
		restartLists();
		while(test()) {
			step();
			Object[] st = caminoFinal.toArray();
			for (Object s : st) {
				if (caminoFinal.indexOf(s) != caminoFinal.lastIndexOf(s)) {
					caminoFinal.remove(caminoFinal.lastIndexOf(s));
				}
			}
		}
		//Limpia los visitados no pertenecientes al camino optimo
		int tempSize;
		for(int i = caminoFinal.size()-1; i > 0; i--) {
			List<Coordenada> aux = caminoFinal.subList(i, caminoFinal.size());
			for(int j = 0; j < i; j++) {
				tempSize = caminoFinal.size();
				if(caminoFinal.get(j).dist(caminoFinal.get(i)) == 1) {
					caminoFinal = caminoFinal.subList(0,j+1);
					caminoFinal.addAll(aux);
					tempSize -= caminoFinal.size();
					i -= tempSize; 
				}
			}
		}
		
		//Quita duplicados de visitados
		Object[] st = visitados.toArray();
		for (Object s : st) {
			if (visitados.indexOf(s) != visitados.lastIndexOf(s)) {
				visitados.remove(visitados.lastIndexOf(s));
			}
		}
		tTS = System.currentTimeMillis() - tTS;

	}
	/**
	 * Hace los calculos necesarios para un �nico paso del problema
	 * @throws BadMatrixException 
	 */
	public void step() throws BadMatrixException {
		mejor.add(coche);
		visitados.add(coche);
		caminoFinal.add(coche);
		boolean x[] = new boolean[4], thrower = false;
		Coordenada temp, possibleDirs[];
		int i = 0;
		for(Directions d : Directions.values()) {
			temp = new Coordenada(coche.getX(),coche.getY());
			temp.add(d.getDir());
			try {
				if(matriz[temp.getX()][temp.getY()].toString().equals("o")) {
					x[i] = true;
				} else x[i] = false;
			} catch (ArrayIndexOutOfBoundsException e) {
				x[i] = true;
			}
			i++;
		}
		temp = new Coordenada(coche.getX(),coche.getY());
		possibleDirs = ((Coche)matriz[coche.getX()][coche.getY()]).move(x);
		for(int j = 0; j < possibleDirs.length; j++) {	
			if(!visitados.contains(coche.sum(possibleDirs[j]))) mejor.add(coche.sum(possibleDirs[j]));
		}
		mejor.sort(((Meta)matriz[meta.getX()][meta.getY()]));
		while(visitados.contains(mejor.get(0))){
			mejor.remove(0);
			if(mejor.isEmpty()) { 
				matriz[coche.getX()][coche.getY()] = new Miembros();
				coche = caminoFinal.get(0);
				matriz[coche.getX()][coche.getY()] = new Coche();
				((Coche)matriz[coche.getX()][coche.getY()]).setPosMeta(meta.diff(coche));
				tTS = System.currentTimeMillis() - tTS;
				thrower = true;
			}
			if(thrower) throw new BadMatrixException("No existen caminos validos");
		}
		coche = mejor.get(0);
		visitados.add(mejor.get(0));
		caminoFinal.add(mejor.get(0));
		matriz[coche.getX()][coche.getY()] = matriz[temp.getX()][temp.getY()];
		matriz[temp.getX()][temp.getY()] = new Miembros('.');
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
		findCar();
		findMeta();
	}
	private void findMeta() {
		for(int i = 0; i < matriz.length;i++) for(int j = 0;j < matriz[i].length;j++) {
			if(Character.toString(matriz[i][j].getName()).equals("M")) meta = new Coordenada(i,j);
		}
	}
	/**
	 * Da las coordenadas del coche.
	 */
	private void findCar() {
		Coordenada meta = new Coordenada(0,0);
		int coches = 0;
		for(int i = 0; i < matriz.length;i++) for(int j = 0;j < matriz[i].length;j++) {
			if(Character.toString(matriz[i][j].getName()).equals("c")) {
				coche = new Coordenada(i,j);
				coches++;
			}
			if(Character.toString(matriz[i][j].getName()).equals("M")) meta = new Coordenada(i,j);
		}
		if(coches > 0) ((Coche)matriz[this.coche.getX()][this.coche.getY()]).setPosMeta(meta.diff(coche));
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
	public void restartLists() {
		mejor.clear();
		visitados.clear();
		caminoFinal.clear();
	}
	public void showOptimalStep(int i) {
		Coordenada temp;
		if(i == 0) {
			coche = caminoFinal.get(0);
			matriz[coche.getX()][coche.getY()] = new Coche();
			matriz[meta.getX()][meta.getY()] = new Meta(meta);
		}else {
			temp = new Coordenada(coche.getX(),coche.getY());
			coche = caminoFinal.get(i);
			matriz[coche.getX()][coche.getY()] = matriz[temp.getX()][temp.getY()];
			matriz[temp.getX()][temp.getY()] = new Miembros('F');
		}
	}
	public long getTTS() {
		return tTS;
	}
	public List<Coordenada> getVisitados() {
		return visitados;
	}

};
