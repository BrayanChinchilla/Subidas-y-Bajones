//Just missing the comments but ready to go
package subidasBajones;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Inicio {
	
	static String[][] tablero = new String[23][12];
	static String [][] jugador = new String[][] 
			{{"Jugador 1", "  #  |", "  $  |"},
			{"Jugador 2", "  @  |", "  $  |"},
			{"Jugador 3", "  %  |", "  $  |"},
			{"Jugador 4", "  &  |", "  $  |"}}; 
	
	static int [][] posicionJugador = new int[4][2];
	static int dimensionTablero = 5;
	static int numSubidas = 1;
	static int numBajadas = 1;
	static int numJugadores = 2;
	
	static Scanner kbReader = new Scanner(System.in);
	
	public static void menuConfig() {
		while (true) {
			System.out.println(
					"\n===== Menu Configuracion ===== \n"
					+ "1. Dimension del tablero \n"
					+ "2. Cantidad de subidas y bajadas \n"
					+ "3. Ingresar jugadores \n"
					+ "4. Regresar");
			
			switch (kbReader.nextInt()) {
			case 1:
				menuDimensiones();
				break;
			case 2:
				menuSubiBaja();
				break;
			case 3:
				menuJugadores();
				break;
			case 4:
				return;
			default:
				System.out.println("\nSeleccionar una opcion valida del menu \n");
			    delay(500);
				break;
			}
		}
	}
	
	public static void menuDimensiones() {
		while (true) {
			System.out.print(
					"\n==== Dimension del Tablero ==== \n"
					+ "1. 5x5 \n"
					+ "2. 11x11 \n"
					+ "Eleccion: ");
	
			switch (kbReader.nextInt()) {
			case 1:
				dimensionTablero = 5;
				System.out.println("\nTablero 5x5 escogido exitosamente!\n"
						+ "Iniciar un nuevo juego para aplicar cambios");
				delay(1500);
				if (numSubidas > 4 || numBajadas > 4) {
					System.out.println("\nEl numero de subidas y bajadas previamente elegidos no pueden ser utilizados en este tablero.\n"
							+ "Se han reestablecido los valores por defecto, \n"
							+ "pero sientete libre de visitar el menu de casillas especiales de nuevo.");
					delay(4000);
					numSubidas = 1;
					numBajadas = 1;
				}
				return;
			case 2:
				dimensionTablero = 11;
				System.out.println("\nTablero 11x11 escogido exitosamente!\n"
						+ "Iniciar un nuevo juego para aplicar cambios");
				delay(1500);
				return;
			default:
				System.out.println("\nOpcion no valida, por favor prueba de nuevo");
				delay(1000);
			}
		}
	}
	
	public static void menuSubiBaja() {
		while (true) {
			System.out.println("\n==== Menu Casillas Especiales ====");

			System.out.print("Determinar el numero de subidas: ");
			numSubidas = kbReader.nextInt();				
			delay(500);
			
			System.out.print("Determinar el numero de bajadas: ");
			numBajadas = kbReader.nextInt();				
			delay(500);
						
			if ((numSubidas > 4 || numBajadas > 4) && (dimensionTablero == 5)) {
				System.out.println("\nEn el tablero 5x5 se pueden configurar un maximo de 4 casillas especiales de cada tipo.\n"
						+ "Por favor prueba de nuevo\n");
				delay(500);
			}	
			else if ((numSubidas > 15 || numBajadas > 15) && (dimensionTablero == 11)) {
				System.out.println("\nEn el tablero 11x11 se pueden configurar un maximo de 15 casillas especiales de cada tipo.\n"
						+ "Por favor prueba de nuevo");
				delay(1500);
			}
			else {
				break;
			}	
		}

		System.out.println("\nTablero con "+numSubidas+" subidas y "+numBajadas+" bajadas configurado exitosamente!\n"
				+ "Iniciar un nuevo juego para aplicar cambios");
		delay(1500);
	}
	
	public static void menuJugadores() {
		System.out.println("\n==== Menu Jugadores ====");
		do {
			System.out.print("Determinar el numero de jugadores: ");
			numJugadores = kbReader.nextInt();				
			delay(500);
			
			if (numJugadores > 4 || numJugadores < 2) {
				System.out.println("\nNumero de jugadores debe estar entre 2 y 4");
				delay(500);
			}
			
		} while (numJugadores > 4 || numJugadores < 2);

		System.out.println("Ingresar el nombre de los jugadores");
		for (int i = 0; i < numJugadores; i++) {
			System.out.print("Jugador "+(i+1)+": ");
			jugador[i][0] = kbReader.next();
		}
		System.out.println("\nNuevos jugadores ingresados exitosamente!\n"
				+ "Iniciar un nuevo juego para aplicar cambios");
		delay(1500);
	}
	
	public static void iniciarJuego() {
	    // configurar el tablero
	    configurarTablero();
	    
		for (int fila = 0; fila < posicionJugador.length; fila++) {
			for (int columna = 0; columna < posicionJugador[fila].length; columna++) {
				posicionJugador[fila][columna] = 1;
			}
		}
		
		for (int fila = 0; fila < jugador.length; fila++) {
			jugador[fila][2] = "  $  |";
		}
	    continuarJuego();
	}

	public static void continuarJuego() {

	    while (true) {
	    		//Iterar sobre los jugadores
	        for (int i = 0; i < numJugadores; i++) {
	        	
		        dibujarTablero();

			    // prompt for move
	        		int dice = ThreadLocalRandom.current().nextInt(1, 7);
	        		

	        		delay(500);
	        		System.out.println(jugador[i][0]+" se mueve "+dice+" espacios");
	        		System.out.print("Presione cualquier tecla para confirmar, o ecribe MENU para regresar al Menu Principal _");
	        	
		        // go back to main menu if user inputs 0
		        if (kbReader.next().equalsIgnoreCase("MENU")) {
		            return;
		        }
		        mover(dice, i);
		        
		        // verificar si el juego ha sido ganado
		        if (ganado()) {
		        		dibujarTablero();
		        		System.out.println("\nFelicidades! El juego ha terminado\n"
		        				+ jugador[i][0]+" es el ganador!\n");
		        		delay(1500);
		            imprimirTablaPosiciones();
		            delay(5000);
		            return;
		        }
	        }
	    }
	}
	
	public static boolean ganado() {
		//Checkear si el cuadro final esta ocupado por un jugador
		if (tablero[dimensionTablero*2-1][dimensionTablero].equals("  $  |")) {
			return false;
		}
		return true;
	}
	
	public static void mover(int dado, int n) {

		tablero[posicionJugador[n][0]][posicionJugador[n][1]] = jugador[n][2];
		
		if (posicionJugador[n][0] % 4 == 1) {
			if(posicionJugador[n][1] + dado > dimensionTablero*2) {
				posicionJugador[n][0] = posicionJugador[n][0] + 4;
				posicionJugador[n][1] = (posicionJugador[n][1] + dado) % dimensionTablero;
			}
			else if (posicionJugador[n][1] + dado > dimensionTablero) {
				posicionJugador[n][0] = posicionJugador[n][0] + 2;
				posicionJugador[n][1] = dimensionTablero*2 + 1 - (posicionJugador[n][1] + dado);
			}
			else {
				posicionJugador[n][1] = posicionJugador[n][1] + dado;
			}
		}
		else {
			if(posicionJugador[n][1] - dado > 0) {
				posicionJugador[n][1] = posicionJugador[n][1] - dado;
			}
			else if (posicionJugador[n][1] - dado > -dimensionTablero) {
				posicionJugador[n][0] = posicionJugador[n][0] + 2;
				posicionJugador[n][1] = 1 - (posicionJugador[n][1] - dado);
			}
			else {
				posicionJugador[n][0] = posicionJugador[n][0] + 4;
				posicionJugador[n][1] = dimensionTablero*2 + (posicionJugador[n][1] - dado);
			}
		}
		if (posicionJugador[n][0] > dimensionTablero*2-1) {
			posicionJugador[n][0] = dimensionTablero*2-1;
			posicionJugador[n][1] = dimensionTablero;
		}
		jugador[n][2] = tablero[posicionJugador[n][0]][posicionJugador[n][1]];
		tablero[posicionJugador[n][0]][posicionJugador[n][1]] = jugador[n][1];
		
		switch (jugador[n][2].substring(2,3)){
		case "#":
			jugador[n][2] = jugador[0][2];
			jugador[0][2] = jugador[n][1];
			break;
		case "@":
			jugador[n][2] = jugador[1][2];
			jugador[1][2] = jugador[n][1];
			break;
		case "%":
			jugador[n][2] = jugador[2][2];
			jugador[2][2] = jugador[n][1];
			break;
		case "&":
			jugador[n][2] = jugador[3][2];
			jugador[3][2] = jugador[n][1];
			break;	
		}
		
		if (jugador[n][2].matches("\\s+[A-Z]\\s+\\|")) {
			for (int x = 0; x < numJugadores; x++) {
				if(jugador[x][2].equals(jugador[n][2]) && x != n && posicionJugador[x][0] > posicionJugador[n][0]) {
					dibujarTablero();
					delay(500);
					System.out.print(jugador[n][0]+" ha caido en la subida "+jugador[n][2].substring(2,3)+"! Presiona una tecla para subir _");
					kbReader.next();
					
					tablero[posicionJugador[n][0]][posicionJugador[n][1]] = jugador[n][2];
					posicionJugador[n][0] = posicionJugador[x][0];
					posicionJugador[n][1] = posicionJugador[x][1];
					tablero[posicionJugador[n][0]][posicionJugador[n][1]] = jugador[n][1];
					
					jugador[x][2] = jugador[n][1];
					return;
				}
			}
			
			for (int fila = posicionJugador[n][0] + 1; fila < dimensionTablero*2; fila++) {
				for (int columna = 1; columna <= dimensionTablero; columna++) {
					if(tablero[fila][columna].equals(jugador[n][2])) {
						dibujarTablero();
						delay(500);
						System.out.print(jugador[n][0]+" ha caido en la subida "+jugador[n][2].substring(2,3)+"! Presiona una tecla para subir _");
						kbReader.next();
						
						tablero[posicionJugador[n][0]][posicionJugador[n][1]] = jugador[n][2];
						posicionJugador[n][0] = fila;
						posicionJugador[n][1] = columna;
						tablero[posicionJugador[n][0]][posicionJugador[n][1]] = jugador[n][1];
						return;
					}
				}
			}
		}
		else if (jugador[n][2].matches("\\s+[1-9]+\\s+\\|")) {
			for (int x = 0; x < numJugadores; x++) {
				if(jugador[x][2].equals(jugador[n][2]) && x != n && posicionJugador[x][0] < posicionJugador[n][0]) {
					dibujarTablero();
					delay(500);
					System.out.print(jugador[n][0]+" ha caido en la bajada "+jugador[n][2].substring(2,3)+"! Presiona una tecla para bajar _");
					kbReader.next();
					
					tablero[posicionJugador[n][0]][posicionJugador[n][1]] = jugador[n][2];
					posicionJugador[n][0] = posicionJugador[x][0];
					posicionJugador[n][1] = posicionJugador[x][1];
					tablero[posicionJugador[n][0]][posicionJugador[n][1]] = jugador[n][1];
					
					jugador[x][2] = jugador[n][1];
					return;
				}
			}
			
			for (int fila = posicionJugador[n][0] - 1; fila > 0; fila--) {
				for (int columna = 1; columna <= dimensionTablero; columna++) {
					if(tablero[fila][columna].equals(jugador[n][2])) {
						dibujarTablero();
						delay(500);
						System.out.print(jugador[n][0]+" ha caido en la bajada "+jugador[n][2].substring(2,3)+"! Presiona una tecla para bajar _");
						kbReader.next();
						
						tablero[posicionJugador[n][0]][posicionJugador[n][1]] = jugador[n][2];
						posicionJugador[n][0] = fila;
						posicionJugador[n][1] = columna;
						tablero[posicionJugador[n][0]][posicionJugador[n][1]] = jugador[n][1];
						return;
					}
				}
			}
		}
	}
	
	public static void imprimirTablaPosiciones() {
		System.out.println("=== Tabla de Posiciones ===");
		for (int i = 0; i < numJugadores;) {
			for(int fila = dimensionTablero*2-1; fila > 0; fila--) {
				for (int columna = dimensionTablero; columna > 0; columna--) {
					if (tablero[fila][columna].equals(jugador[0][1])) {
						System.out.println(i+1+". lugar: "+jugador[0][0]);
						i++;
					}
					else if (tablero[fila][columna].equals(jugador[1][1])) {
						System.out.println(i+1+". lugar: "+jugador[1][0]);
						i++;
					}
					else if (tablero[fila][columna].equals(jugador[2][1])) {
						System.out.println(i+1+". lugar: "+jugador[2][0]);
						i++;
					}
					else if (tablero[fila][columna].equals(jugador[3][1])) {
						System.out.println(i+1+". lugar: "+jugador[3][0]);
						i++;
					}
				}
			}
		}
	}
	
	public static void configurarTablero() {
	
	    for (int fila = 0; fila <= dimensionTablero*2; fila++) {
	    		if (fila % 2 == 0) {
	    			tablero[fila][0] = "\t";
	    		}
	    		else {
	    			tablero[fila][0] = "\t|";
	    		}
	    		for (int columna = 1; columna <= dimensionTablero; columna++) {
	    			if (fila % 2 == 0) {
	        			tablero[fila][columna] = " -----";
	        		}
	        		else {
	        			tablero[fila][columna] = "     |";
	        		}
	        }
	    }
	    // Indicar inicio y final del tablero
	    tablero[1][1] = "  $  |";
	    tablero[dimensionTablero*2-1][dimensionTablero] = "  $  |";
	    
	    generarCasillaEspecial(numSubidas, "subidas");
	    generarCasillaEspecial(numBajadas, "bajadas");
	    
	}

	public static void generarCasillaEspecial(int numEspecial, String tipo) {
		for (int i = 0; i < numEspecial; i++) {
			int rndFila1, rndColumna1, rndFila2, rndColumna2;
			//Generar una casilla random y verificar que no este ocupado
			do {
				rndFila1 = ThreadLocalRandom.current().nextInt(1, dimensionTablero + 1) * 2 - 1;
				rndColumna1 = ThreadLocalRandom.current().nextInt(1, dimensionTablero + 1);
			} while (! tablero[rndFila1][rndColumna1].equals("     |"));
			
			//Generar otra casilla random y verificar que no este ocupada
			do {
				do //Verificar que la fila de la segunda casilla no es igual a la fila de la primera
					rndFila2 = ThreadLocalRandom.current().nextInt(1, dimensionTablero + 1) * 2 - 1;
				while (rndFila2 == rndFila1);
				rndColumna2 = ThreadLocalRandom.current().nextInt(1, dimensionTablero + 1);
			} while (! tablero[rndFila2][rndColumna2].equals("     |"));
			
			//Anadir un caracter especial a la casilla
			if (tipo.equals("subidas")) {
				tablero[rndFila1][rndColumna1] = "  "+String.valueOf((char)('A'+i))+"  |";
				tablero[rndFila2][rndColumna2] = "  "+String.valueOf((char)('A'+i))+"  |";
			} else {
				tablero[rndFila1][rndColumna1] = " "+String.format("%2d", i)+"  |";
				tablero[rndFila2][rndColumna2] = " "+String.format("%2d", i)+"  |";
			}
		}
	}
	
	public static void dibujarTablero() {
	//Iterate through every row and column to print the held value
		delay(500);
		System.out.print("\n");
		for (int fila = dimensionTablero*2; fila >= 0; fila--) {
			for (int columna = 0; columna <= dimensionTablero; columna++) {
				System.out.print(tablero[fila][columna]);
	        }
			System.out.print("\n");
		}
	}
	
	public static void delay(int n) {
		try {
		    Thread.sleep(n);
		} 
		catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	public static void main(String[] args) {
		while (true) {
			System.out.println(
					"\n===== Menu Principal ===== \n"
					+ "1. Iniciar juego \n"
					+ "2. Regresar al juego \n"
					+ "3. Configuracion \n"
					+ "4. Salir");
						
			switch (kbReader.nextInt()) {
			case 1:
				iniciarJuego();
				break;
			case 2:
				continuarJuego();
				break;
			case 3:
				menuConfig();
				break;
			case 4:
				System.out.println("\nGracias por jugar! \n"
						+ "Vuelve pronto");
				delay(3000);
				return;
			default:
				System.out.println("Seleccionar una opcion valida del menu");
				break;
			}
		}
	}
}