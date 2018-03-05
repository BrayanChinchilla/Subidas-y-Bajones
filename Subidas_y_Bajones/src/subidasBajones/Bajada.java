package subidasBajones;
import java.util.concurrent.ThreadLocalRandom;

public class Bajada {
	
	int filaInicio;
	int columnaInicio;
	int filaFinal;
	int columnaFinal;
	String simbolo;

	public Bajada(String simbolo, Tablero tablero) {
		this.simbolo = simbolo;

		//Generar una casilla random y verificar que no este ocupado
		do {
			filaInicio = ThreadLocalRandom.current().nextInt(2, tablero.dimension + 1) * 2 - 1;
			columnaInicio = ThreadLocalRandom.current().nextInt(1, tablero.dimension + 1);
		} while (! tablero.matriz[filaInicio][columnaInicio].equals("     |"));
		
		//Generar otra casilla random y verificar que no este ocupada
		do {	
			filaFinal = ThreadLocalRandom.current().nextInt(1, (filaInicio + 1)/2) * 2 - 1;
			columnaFinal = ThreadLocalRandom.current().nextInt(1, tablero.dimension + 1);
		} while (! tablero.matriz[filaFinal][columnaFinal].equals("     |"));
			
		//Anadir un caracter especial a la casilla
		tablero.matriz[filaInicio][columnaInicio] = simbolo;
		tablero.matriz[filaFinal][columnaFinal] = simbolo;
	}	
}
