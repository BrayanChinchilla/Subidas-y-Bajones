package subidasBajones;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Inicio {
	
	static String[][] board = new String[23][12];
	static String [] player = new String[4];
	static int boardDimension = 5;
	static int numSubidas = 1;
	static int numBajadas = 1;
	static int numPlayers = 2;
	
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
				boardDimension = 5;
				System.out.println("\nTablero 5x5 escogido exitosamente");
				delay();
				break;
			case 2:
				boardDimension = 11;
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
					if (numSubidas > boardDimension*boardDimension/3) {
						System.out.println("\nEl numero de subidas debe estar entre 1 - "+boardDimension*boardDimension/6);
					}
				} while (numSubidas > boardDimension*boardDimension/3);
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
				numPlayers = kbReader.nextInt();				
				delay();
				break;
			case 2:
				System.out.println("\nIngresar el nombre de los jugadores");
				for (int i = 0; i < numPlayers; i++) {
					System.out.print("Jugador "+(i+1)+": ");
					player[i] = kbReader.next();
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
	        for (int i = 0; i < numPlayers; i++) {
	        		int dice = ThreadLocalRandom.current().nextInt(1, 7);
	        		
	        		System.out.println(player[i]+" moves "+dice+"spaces");
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
		if (board[boardDimension*2-1][boardDimension].equals("  $  |")) {
			return false;
		}
		return true;
	}

	public static void move(int dice, int player) {
		
	}
	
	public static void printTablaPosiciones() {
		
	}
	
	public static void initBoard() {
	
	    for (int row = 0; row <= boardDimension*2; row++) {
	    		if (row % 2 == 0) {
	    			board[row][0] = "\t";
	    		}
	    		else {
	    			board[row][0] = "\t|";
	    		}
	    		for (int column = 1; column <= boardDimension; column++) {
	    			if (row % 2 == 0) {
	        			board[row][column] = " -----";
	        		}
	        		else {
	        			board[row][column] = "     |";
	        		}
	        }
	    }
	    // Start and end
	    board[1][1] = "  $  |";
	    board[boardDimension*2-1][boardDimension] = "  $  |";
	    
	    //generarSubidas();
	    //generarBajadas();
	    
	}

	public static void generarSubidas() {
		for (int i = 0; i < numSubidas; i++) {
			int randomRS, randomCS, randomRE, randomCE;
			do {
				randomRS = ThreadLocalRandom.current().nextInt(1, boardDimension) * 2 - 1;
				randomCS = ThreadLocalRandom.current().nextInt(1, boardDimension + 1);
			} while (!board[randomRS][randomCS].matches("\\s+"));
			
			do {
				randomRE = ThreadLocalRandom.current().nextInt(boardDimension, randomRS) * 2 - 1;
				randomCE = ThreadLocalRandom.current().nextInt(1, boardDimension + 1);
			} while (!board[randomRE][randomCE].matches("\\s+"));

			board[randomRS][randomCS] = "  "+String.valueOf((char)('A'+i))+"  |";
			board[randomRE][randomCE] = "  "+String.valueOf((char)('A'+i))+"  |";
		}
	}
	
	public static void generarBajadas() {
		for (int i = 0; i < numBajadas; i++) {
			int randomRS, randomCS, randomRE, randomCE;
			do {
				randomRS = ThreadLocalRandom.current().nextInt(boardDimension, 1) * 2 - 1;
				randomCS = ThreadLocalRandom.current().nextInt(1, boardDimension + 1);
			} while (!board[randomRS][randomCS].matches("\\s+"));
			
			do {
				randomRE = ThreadLocalRandom.current().nextInt(randomRS/2, 0) * 2 - 1;
				randomCE = ThreadLocalRandom.current().nextInt(1, boardDimension + 1);
			} while (!board[randomRE][randomCE].matches("\\s+"));

			board[randomRS][randomCS] = "  "+(1+i)+"  |";
			board[randomRE][randomCE] = "  "+(1+i)+"  |";
		}
	}
	
	public static void drawBoard() {
	//Iterate through every row and column to print the held value
		System.out.print("\n");
		for (int row = boardDimension*2; row >= 0; row--) {
			for (int column = 0; column <= boardDimension; column++) {
				System.out.print(board[row][column]);
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