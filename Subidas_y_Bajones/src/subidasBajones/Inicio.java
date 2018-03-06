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
	
	public void menuConfig() {
		while (true) {
			System.out.println(
					"\n==== Menu Configuracion ==== \n"
					+ "1. Dimension del tablero \n"
					+ "2. Casillas Especiales \n"
					+ "3. Ingresar jugadores \n"
					+ "4. Regresar");
			
			switch (getInt()) {
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
	
			switch (getInt()) {
			case 1: //La dimension sera 5
				dimensionTablero = 5;
				System.out.println("\nTablero 5x5 escogido exitosamente!\n"
						+ "Iniciar un nuevo juego para aplicar cambios");
				delay(500);
				if (numSubidas > 4 || numBajadas > 4) { //Caso extremo: Usuario configuro casillas especiales primero
					System.out.println("\nEl numero de subidas y bajadas previamente elegidos no pueden ser utilizados en este tablero.\n"
							+ "Se han reestablecido los valores por defecto, \n"
							+ "pero sientete libre de visitar el menu de casillas especiales de nuevo.");
					delay(4000);
					numSubidas = 1;
					numBajadas = 1;
				}
				return;
			case 2: //La dimension sera 11
				dimensionTablero = 11;
				System.out.println("\nTablero 11x11 escogido exitosamente!\n"
						+ "Iniciar un nuevo juego para aplicar cambios");
				delay(500);
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
			numSubidas = getInt();				
			
			System.out.print("Determinar el numero de bajadas: ");
			numBajadas = getInt();				
			
			//Verificar que el numero de subidas/bajadas este acorde a las dimensiones del tablero
			if ((numSubidas > 4 || numBajadas > 4 || numSubidas < 0 || numBajadas < 0) && (dimensionTablero == 5)) {
				System.out.println("\nEn el tablero 5x5 se pueden configurar 0-4 casillas especiales de cada tipo.\n"
						+ "Por favor prueba de nuevo\n");
				delay(1500);
			}	
			else if ((numSubidas > 15 || numBajadas > 15 || numSubidas < 0 || numBajadas < 0) && (dimensionTablero == 11)) {
				System.out.println("\nEn el tablero 11x11 se pueden configurar 0-15 casillas especiales de cada tipo.\n"
						+ "Por favor prueba de nuevo");
				delay(1500);
			}
			else {
				break;
			}	
		}

		System.out.println("\nTablero con "+numSubidas+" subidas y "+numBajadas+" bajadas configurado exitosamente!\n"
				+ "Iniciar un nuevo juego para aplicar cambios");
		delay(500);
	}
	
	public void menuJugadores() {
		System.out.println("\n==== Menu Jugadores ====");
		do {
			System.out.print("Determinar el numero de jugadores: ");
			numJugadores = getInt();				
			delay(500);
			
			if (numJugadores > 4 || numJugadores < 2) {
				System.out.println("\nNumero de jugadores debe estar entre 2 y 4");
				delay(500);
			}
			
		} while (numJugadores > 4 || numJugadores < 2);

		System.out.println("Ingresar el nombre de los jugadores");
		kbReader.nextLine();
		for (int i = 0; i < numJugadores; i++) {
			System.out.print("Jugador "+(i+1)+": ");
			jugador[i].nombre = kbReader.nextLine();
		}
		System.out.println("\nNuevos jugadores ingresados exitosamente!\n"
				+ "Iniciar un nuevo juego para aplicar cambios");
		delay(1500);
	}
	
	public Tablero iniciarJuego() {
		//limpiar posiciones de los jugadores
		for (Jugador j : jugador) {
			j.fila = 1;
			j.columna = 1;
			j.casilla = "  $  |";
		}
	    // Crear un nuevo tablero y generar sus casillas especiales
	    Tablero tablero = new Tablero(dimensionTablero);
	    tablero.generarSubida(numSubidas);
	    tablero.generarBajada(numBajadas);
	    
	    return tablero;
	}

	public int continuarJuego(Tablero tablero, int turno) {
		int i = turno; //En caso sea una continuacion
		while (true) {
	    		//Iterar sobre los jugadores
	        for (; i < numJugadores; i++) {
	        	
	        		delay(500);
	            tablero.dibujarTablero();

			    // Tirar dado y esperar confirmacion del jugador
	        		int dice = ThreadLocalRandom.current().nextInt(1, 7);	        		
	        		System.out.println(jugador[i].nombre+" se mueve "+dice+" espacios");
	        		System.out.print("Presiona cualquier tecla para confirmar, o ecribe MENU para regresar al Menu Principal _");
	        	
		        // Si el jugador desea, puede regresar al menu. NO pierde su turno
		        if (kbReader.next().equalsIgnoreCase("MENU")) {
		            return i;
		        }
		        
		        jugador[i].mover(dice, tablero, jugador);
				jugador[i].estaOcupada(jugador); //En caso haya un jugador en la casilla
		        
				//Si la casilla tiene el simbolo de una subida
		        if (jugador[i].casilla.matches("\\s+[A-Z]\\s+\\|")) { 
		        		Subida s = jugador[i].puedeSubir(tablero);
		        		if (s != null) {	
				    		delay(500);
				    		tablero.dibujarTablero();
			    			System.out.print(jugador[i].nombre+" ha caido en la subida "+jugador[i].casilla.substring(2,3)+"!\nPresiona una tecla para subir _");
		       			kbReader.next();
		       			jugador[i].subir(tablero, s);
						jugador[i].estaOcupada(jugador);
		        		}
		        }
		        
		        //Si la casilla tiene el simbolo de una bajada
		        else if (jugador[i].casilla.matches("\\s+[0-9]+\\s+\\|")) { 
		        		Bajada b = jugador[i].puedeBajar(tablero);
		        		if (b != null) {
				    		delay(500);
				    		tablero.dibujarTablero();
			    			System.out.print(jugador[i].nombre+" ha caido en la bajada  "+jugador[i].casilla.substring(1,3)+"!\nPresiona una tecla para bajar _");
		       			kbReader.next();
		       			jugador[i].bajar(tablero, b);
						jugador[i].estaOcupada(jugador);
		        		}
		        }
		        
		        // Verificar si el juego ha sido ganado
		        else if (juegoGanado(tablero)) {
		        		delay(500);
		        		tablero.dibujarTablero();
		        		System.out.println("\nFelicidades! El juego ha terminado\n"
		        				+ jugador[i].nombre+" es el ganador!\n");
		        		delay(3000);
		        		
		            imprimirTablaPosiciones(tablero);
		            delay(5000);
		            
		            return 0; //regresar al menu
		        }
		        
	        		delay(500);
		        tablero.dibujarTablero();
		        
		        //Fin de turno
        			System.out.print(jugador[i].nombre+", tu turno ha terminado. \n"
        					+ "Presiona cualquier tecla para confirmar o ecribe MENU para regresar al Menu Principal _");
		        	
		        // Si el jugador desea, puede regresar al menu. Turno pasa a siguiente jugador
		        if (kbReader.next().equalsIgnoreCase("MENU")) {
		        		if (i < numJugadores)
		        			return i+1;
		        		return 0;
		        }
	        }
	        i = 0; //Regresar el turno al primer jugador
	    }
	}
	
	public boolean juegoGanado(Tablero tablero) {
		// Verificar si la casilla final esta ocupada por un jugador
		if (tablero.matriz[dimensionTablero*2-1][dimensionTablero].equals("  $  |")){
			return false;
		}
		return true;
	}
	
	public void imprimirTablaPosiciones(Tablero tablero) {
		System.out.println("\n==== Tabla de Posiciones ====");
		for (int i = 0; i < numJugadores;) {	 //Correr hasta que se encuentren a todos los jugadores	
			for(int f = dimensionTablero*2-1; f > 0; f--) { //Recorrer de arriba hacia abajo
				if (f % 4 == 1) { //De derecha a izquierda
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
				} else { //De izquierda a derecha
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
	//Con propositos de animacion, y permitir al usuario leer los mensajes del juego antes de proceder
		try {
		    Thread.sleep(n);
		} catch (InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	public static int getInt() {
		//Manejo de error en caso el usuario introduzca texto cuando se solicitan integers
		//Evita una InputMismatchException
	    int n = 0;
	    boolean error = true;
	    while (error) {
	        if (kbReader.hasNextInt())
	            n = kbReader.nextInt();
	        else {
	            kbReader.next();
	            System.out.println("\nError! Por favor introduce un digito valido: ");
	            continue;
	        }
	        error = false;
	    }
	    return n;
	}
	
	public static void main(String[] args) {
		Inicio juego = new Inicio();
		Tablero tablero = null;
		int turno = 0; //Guarda el turno actual en caso el usuario sale al menu principal y luego desea regresar al juego
		while (true) {			
			System.out.println(
					"\n==== Menu Principal ====\n"
					+ "1. Iniciar juego \n"
					+ "2. Regresar al juego \n"
					+ "3. Configuracion \n"
					+ "4. Salir");
						
			switch (getInt()) {
			case 1: //Inicializar tablero y juego
				tablero = juego.iniciarJuego();
				turno = juego.continuarJuego(tablero, 0);
				break;
			case 2: //Continuar juego actual, si existe
				if (tablero == null || juego.juegoGanado(tablero)) {
					System.out.println("\nNo existe un juego en progreso. Por favor iniciar una nueva partida");
					break;
				}
				System.out.println("\nEl juego continua!");
				delay(1000);
				turno = juego.continuarJuego(tablero, turno);
				break;
			case 3: //Abrir menu de configuraciones
				juego.menuConfig();
				break;
			case 4: //Terminar aplicacion
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