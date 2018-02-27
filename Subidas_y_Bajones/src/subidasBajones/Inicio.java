package subidasBajones;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Inicio {
	
	static String[][] tablero = new String[23][12];
	static String [] jugador = new String[4];
	static int dimensionTablero = 5;
	static int numSubidas = 1;
	static int numBajadas = 1;
	static int numJugadores = 2;
	
	static Scanner kbReader = new Scanner(System.in);
	
	public static void printMainMenu() {
		System.out.println(
				"\n==== Menu Principal ==== \n"
				+ "1. Iniciar juego \n"
				+ "2. Regresar al juego \n"
				+ "3. Configuracion \n"
				+ "4. Salir");
	}
	
	public static void settingsMenu() {
		while (true) {
			System.out.println(
					"\n==== Menu Configuracion ==== \n"
					+ "1. Dimension del tablero \n"
					+ "2. Cantidad de subidas y bajadas \n"
					+ "3. Ingresar jugadores \n"
					+ "4. Regresar");
			
			switch (kbReader.nextInt()) {
			case 1:
				dimensionsMenu();
				break;
			case 2:
				subidasBajadasMenu();
				break;
			case 3:
				playersMenu();
				break;
			case 4:
				return;
			default:
				System.out.println("\nSeleccionar una opcion valida del menu \n");
			    delay();
				break;
			}
		}
	}
	
	public static void dimensionsMenu() {
		while (true) {
			System.out.println(
					"\n=== Dimension del Tablero === \n"
					+ "1. 5x5 \n"
					+ "2. 11x11 \n"
					+ "3. Regresar");

			switch (kbReader.nextInt()) {
			case 1:
				dimensionTablero = 5;
				System.out.println("\nTablero 5x5 escogido exitosamente");
				delay();
				break;
			case 2:
				dimensionTablero = 11;
				System.out.println("\nTablero 11x11 escogido exitosamente");
				delay();
				break;
			case 3:
				return;
			default:
				System.out.println("\nPor favor elegir una opcion valida del menu");
				delay();
				break;
			}
		}
	}
	
	public static void subidasBajadasMenu() {
		while (true) {
			System.out.println(
					"\n=== Subidas y Bajadas === \n"
					+ "1. Cantidad de Subidas \n"
					+ "2. Cantidad de Bajadas \n"
					+ "3. Regresar");
			
			switch (kbReader.nextInt()) {
			case 1: //Gotta set a max. number
				do {
					System.out.println("\nDeterminar el numero de subidas");
					numSubidas = kbReader.nextInt();				
					delay();
					if (numSubidas > dimensionTablero*dimensionTablero/3) {
						System.out.println("\nEl numero de subidas debe estar entre 1 - "+dimensionTablero*dimensionTablero/6);
					}
				} while (numSubidas > dimensionTablero*dimensionTablero/3);
				break;
			case 2: //Gotta set a max. number
				System.out.println("\nDeterminar el numero de bajadas");
				numBajadas = kbReader.nextInt();
				delay();
				break;
			case 3:
				return;
			default:
				System.out.println("\nPor favor elegir una opcion valida del menu");
				delay();
				break;
			}
		}
	}
	
	public static void playersMenu() {
		while (true) {
			System.out.println(
					"\n=== Jugadores === \n"
					+ "1. Numero de Jugadores \n"
					+ "2. Ingresar Jugadores \n"
					+ "3. Regresar");
			
			switch (kbReader.nextInt()) {
			case 1:
				System.out.println("\nDeterminar el numero de jugadores");
				numJugadores = kbReader.nextInt();				
				delay();
				break;
			case 2:
				System.out.println("\nIngresar el nombre de los jugadores");
				for (int i = 0; i < numJugadores; i++) {
					System.out.print("Jugador "+(i+1)+": ");
					jugador[i] = kbReader.next();
				}
				delay();
				break;
			case 3:
				return;
			default:
				System.out.println("\nPor favor elegir una opcion valida del menu");
				delay();
				break;
			}
		}
	}
	
	public static void startGame() {
	    // initialize the board
	    initBoard();
	    drawBoard();
	    //continueGame();
	}

	public static void continueGame() {

	    while (true) {
	        // draw the current state of the board
	        drawBoard();

	        // check for win
	        if (won()) {
	            printTablaPosiciones();
	            break;
	        }

	        // prompt for move
	        for (int i = 0; i < numJugadores; i++) {
	        		int dice = ThreadLocalRandom.current().nextInt(1, 7);
	        		
	        		System.out.println(jugador[i]+" moves "+dice+"spaces");
	        		System.out.print("Type any key to confirm move, or type MENU to go back to main menu _");
	        	
		        // go back to main menu if user inputs 0
		        if (kbReader.next().equals("MENU")) {
		            return;
		        }
		        move(dice, i);
	        }
	    }
	}
	
	public static boolean won() {
		//Checkear si el cuadro final esta ocupado por un jugador
		if (tablero[dimensionTablero*2-1][dimensionTablero].equals("  $  |")) {
			return false;
		}
		return true;
	}

	public static void move(int dice, int player) {
		
	}
	
	public static void printTablaPosiciones() {
		
	}
	
	public static void initBoard() {
	
	    for (int row = 0; row <= dimensionTablero*2; row++) {
	    		if (row % 2 == 0) {
	    			tablero[row][0] = "\t";
	    		}
	    		else {
	    			tablero[row][0] = "\t|";
	    		}
	    		for (int column = 1; column <= dimensionTablero; column++) {
	    			if (row % 2 == 0) {
	        			tablero[row][column] = " -----";
	        		}
	        		else {
	        			tablero[row][column] = "     |";
	        		}
	        }
	    }
	    // Start and end
	    tablero[1][1] = "  $  |";
	    tablero[dimensionTablero*2-1][dimensionTablero] = "  $  |";
	    
	    generarSubiBaja("subidas");
	    generarSubiBaja("bajadas");
	    
	}

	public static void generarSubiBaja(String flag) {
		for (int i = 0; i < numSubidas; i++) {
			int randomRS, randomCS, randomRE, randomCE;
			do {
				randomRS = ThreadLocalRandom.current().nextInt(1, dimensionTablero + 1) * 2 - 1;
				randomCS = ThreadLocalRandom.current().nextInt(1, dimensionTablero + 1);
			} while (! tablero[randomRS][randomCS].equals("     |"));
			
			do {
				do
					randomRE = ThreadLocalRandom.current().nextInt(1, dimensionTablero + 1) * 2 - 1;
				while (randomRE == randomRS);
				randomCE = ThreadLocalRandom.current().nextInt(1, dimensionTablero + 1);
			} while (! tablero[randomRE][randomCE].equals("     |"));
			
			if (flag.equals("subidas")) {
				tablero[randomRS][randomCS] = "  "+String.valueOf((char)('A'+i))+"  |";
				tablero[randomRE][randomCE] = "  "+String.valueOf((char)('A'+i))+"  |";
			} else {
				tablero[randomRS][randomCS] = "  "+(1+i)+"  |";
				tablero[randomRE][randomCE] = "  "+(1+i)+"  |";
			}
		}
	}
	
	public static void drawBoard() {
	//Iterate through every row and column to print the held value
		System.out.print("\n");
		for (int row = dimensionTablero*2; row >= 0; row--) {
			for (int column = 0; column <= dimensionTablero; column++) {
				System.out.print(tablero[row][column]);
	        }
			System.out.print("\n");
		}
	}
	
	public static void delay() {
		try {
		    Thread.sleep(1000);
		} 
		catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	public static void main(String[] args) {
		while (true) {
			
			printMainMenu();
						
			switch (kbReader.nextInt()) {
			case 1:
				startGame();
				break;
			case 2:
				continueGame();
				break;
			case 3:
				settingsMenu();
				break;
			case 4:
				System.out.println("Gracias por jugar! \n"
						+ "Vuelve pronto");
				return;
			default:
				System.out.println("Seleccionar una opcion valida del menu");
				break;
			}
		}
	}
}