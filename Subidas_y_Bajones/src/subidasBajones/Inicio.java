package subidasBajones;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Inicio {
		
	Jugador[] jugador = new Jugador[]{new Jugador("Jugador 1", "  #  |", "  $  |"),
            new Jugador("Jugador 2", "  @  |", "  $  |"),
            new Jugador("Jugador 3", "  %  |", "  $  |"),
            new Jugador("Jugador 4", "  &  |", "  $  |")};
	int dimensionTablero = 5;
	int numSubidas = 1;
	int numBajadas = 1;
	int numJugadores = 2;
	
	static Scanner kbReader = new Scanner(System.in);
	
	public Inicio() {
		for (Jugador j : jugador) {
			j.fila = 1;
			j.columna = 1;
			j.casilla = "  $  |";
		}
	}
	
	public void menuConfig() {
		while (true) {
			System.out.println(
					"\n==== Menu Configuracion ==== \n"
					+ "1. Dimension del tablero \n"
					+ "2. Casillas Especiales \n"
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
			    delay(1000);
				break;
			}
		}
	}
	
	public void menuDimensiones() {
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
				break;
			}
		}
	}
	
	public void menuSubiBaja() {
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
				delay(500);
			}
			else {
				break;
			}	
		}

		System.out.println("\nTablero con "+numSubidas+" subidas y "+numBajadas+" bajadas configurado exitosamente!\n"
				+ "Iniciar un nuevo juego para aplicar cambios");
		delay(1500);
	}
	
	public void menuJugadores() {
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
			jugador[i].nombre = kbReader.next();
		}
		System.out.println("\nNuevos jugadores ingresados exitosamente!\n"
				+ "Iniciar un nuevo juego para aplicar cambios");
		delay(1500);
	}
	
	public Tablero iniciarJuego() {
	    // configurar el tablero	
	    Tablero tablero = new Tablero(dimensionTablero);
	    tablero.generarSubida(numSubidas);
	    tablero.generarBajada(numBajadas);
	    
	    return tablero;
	}

	public int continuarJuego(Tablero tablero, int turno) {
		int i = turno;
		while (true) {
	    		//Iterar sobre los jugadores
	        for (; i < numJugadores; i++) {
	        		
	        		delay(500);
		        tablero.dibujarTablero();

			    // Tirar dado y esperar confirmacion del jugador
	        		int dice = ThreadLocalRandom.current().nextInt(1, 7);	        		
	        		System.out.println(jugador[i].nombre+" se mueve "+dice+" espacios");
	        		System.out.print("Presione cualquier tecla para confirmar, o ecribe MENU para regresar al Menu Principal _");
	        	
		        // Si el jugador desea, puede salir del juego y regresar al menu
		        if (kbReader.next().equalsIgnoreCase("MENU")) {
		            return i;
		        }
		        
		        jugador[i].mover(dice, tablero, jugador);
				jugador[i].estaOcupada(jugador);
		        
		        if (jugador[i].casilla.matches("\\s+[A-Z]\\s+\\|")) {
		        		Subida s = jugador[i].puedeSubir(tablero);
		        		if (s != null) {
				    		delay(500);
				    		tablero.dibujarTablero();
			    			System.out.print(jugador[i].nombre+" ha caido en la subida "+jugador[i].casilla.substring(2,3)+"! Presiona una tecla para subir _");
		       			kbReader.next();
		       			jugador[i].subir(tablero, s);
						jugador[i].estaOcupada(jugador);
		        		}
		        }
		        
		        if (jugador[i].casilla.matches("\\s+[1-9]+\\s+\\|")) {
		        		Bajada b = jugador[i].puedeBajar(tablero);
		        		if (b != null) {
				    		delay(500);
				    		tablero.dibujarTablero();
			    			System.out.print(jugador[i].nombre+" ha caido en la bajada  "+jugador[i].casilla.substring(1,3)+"! Presiona una tecla para bajar _");
		       			kbReader.next();
		       			jugador[i].bajar(tablero, b);
						jugador[i].estaOcupada(jugador);
		        		}
		        }
		        
		        // verificar si el juego ha sido ganado
		        if (ganado(tablero)) {
		        		tablero.dibujarTablero();
		        		System.out.println("\nFelicidades! El juego ha terminado\n"
		        				+ jugador[i].nombre+" es el ganador!\n");
		        		delay(3000);
		        		
		        		Tablero tableroVacio = new Tablero(dimensionTablero);
		        		tableroVacio.dibujarTablero();
		            imprimirTablaPosiciones(tablero);
		            delay(6000);
		            
		            return 0;
		        }
	        }
	        i = 0;
	    }
	}
	
	public boolean ganado(Tablero tablero) {
		//Checkear si el cuadro final esta ocupado por un jugador
		if (tablero.matriz[dimensionTablero*2-1][dimensionTablero].equals("  $  |")) {
			return false;
		}
		delay(1000);
		return true;
	}
	
	public void imprimirTablaPosiciones(Tablero tablero) {
		System.out.println("\n==== Tabla de Posiciones ====");
		for (int i = 0; i < numJugadores;) {		
			for(int f = dimensionTablero*2-1; f > 0; f--) {
				if (f % 4 == 1) {
					for (int c = dimensionTablero; c > 0; c--) {
						if (tablero.matriz[f][c].equals(jugador[0].simbolo)) {
							System.out.println(i+1+". lugar: "+jugador[0].nombre);
							i++;
						}
						else if (tablero.matriz[f][c].equals(jugador[1].simbolo)) {
							System.out.println(i+1+". lugar: "+jugador[1].nombre);
							i++;
						}
						else if (tablero.matriz[f][c].equals(jugador[2].simbolo)) {
							System.out.println(i+1+". lugar: "+jugador[2].nombre);
							i++;
						}
						else if (tablero.matriz[f][c].equals(jugador[3].simbolo)) {
							System.out.println(i+1+". lugar: "+jugador[3].nombre);
							i++;
						}
					}
				} else {
					for (int c = 1; c <= dimensionTablero; c++) {
						if (tablero.matriz[f][c].equals(jugador[0].simbolo)) {
							System.out.println(i+1+". lugar: "+jugador[0].nombre);
							i++;
						}
						else if (tablero.matriz[f][c].equals(jugador[1].simbolo)) {
							System.out.println(i+1+". lugar: "+jugador[1].nombre);
							i++;
						}
						else if (tablero.matriz[f][c].equals(jugador[2].simbolo)) {
							System.out.println(i+1+". lugar: "+jugador[2].nombre);
							i++;
						}
						else if (tablero.matriz[f][c].equals(jugador[3].simbolo)) {
							System.out.println(i+1+". lugar: "+jugador[3].nombre);
							i++;
						}
					}
				}

			}
		}
	}
	
	public static void delay(int n) {
		try {
		    Thread.sleep(n);
		} catch (InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	public static void main(String[] args) {
		Inicio juego = new Inicio();
		Tablero tablero = null;
		int turno = 0;
		while (true) {			
			System.out.println(
					"\n==== Menu Principal ====\n"
					+ "1. Iniciar juego \n"
					+ "2. Regresar al juego \n"
					+ "3. Configuracion \n"
					+ "4. Salir");
						
			switch (kbReader.nextInt()) {
			case 1:
				juego = new Inicio();
				tablero = juego.iniciarJuego();
				turno = juego.continuarJuego(tablero, 0);
				break;
			case 2:
				if (tablero == null) {
					System.out.println("\nNo existe un juego en progreso. Por favor iniciar una nueva partida");
					break;
				}
				System.out.println("\nEl juego continua!");
				delay(1000);
				turno = juego.continuarJuego(tablero, turno);
				break;
			case 3:
				juego.menuConfig();
				break;
			case 4:
				System.out.println("\nGracias por jugar!\n"
						+ "Vuelve pronto");
				return;
			default:
				System.out.println("Seleccionar una opcion valida del menu");
				break;
			}
		}
	}
}