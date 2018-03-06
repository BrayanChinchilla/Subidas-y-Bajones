package subidasBajones;

public class Jugador {
	
	String nombre;
	String simbolo;
	String casilla;
	
	int fila;
	int columna;
	
	public Jugador(String nombre, String simbolo, String casillaPrevia) {
		this.nombre = nombre;
		this.simbolo = simbolo;
		this.casilla = casillaPrevia;
		fila = 1;
		columna = 1;
	}
	
	public void mover(int dado, Tablero tablero, Jugador[] jugador) {
		//Dejar la casilla en la que el jugador estaba tal y como la encontro antes de llegar
		tablero.matriz[fila][columna] = casilla;
		
		if (fila % 4 == 1) { //Logica para filas que van de izquierda a derecha
			if(columna + dado > tablero.dimension*2) { //Caso extremo valido solo para 5x5, tiro implica escalar dos filas
				fila = fila + 4;
				columna = (columna + dado) % tablero.dimension;
			}
			else if (columna + dado > tablero.dimension) { //Tiro implica un cambio de fila
				fila = fila + 2;
				columna = tablero.dimension*2 + 1 - (columna + dado);
			}
			else { //Tiro no implica un cambio de fila
				columna = columna + dado;
			}
		}
		else { //Logica para filas que van de derecha a izquierda
			if(columna - dado > 0) { //Tiro no implica un cambio de fila
				columna = columna - dado;
			}
			else if (columna - dado > -tablero.dimension) { //Tiro implica un cambio de fila
				fila = fila + 2;
				columna = 1 - (columna - dado);
			}
			else { //Caso extremo valido solo para 5x5, tiro implica escalar dos filas
				fila = fila + 4;
				columna = tablero.dimension*2 + (columna - dado);
			}
		}
		
		//Un shortcut en caso de que el jugador se pase de la meta
		if (fila >= tablero.dimension*2) {
			fila = tablero.dimension*2-1;
			columna = tablero.dimension;
		}
		
		//El jugador guarda su posicion y el tablero guarda la del jugador
		casilla = tablero.matriz[fila][columna];
		tablero.matriz[fila][columna] = simbolo;
	}

	public void estaOcupada(Jugador[] jugador) {
		// Si la casilla esta ocupada por otro jugador, asegurarse de que el otro jugador deje el simbolo del actual al momento de irse
		for (int i = 0; i < jugador.length; i++) {
			if (casilla == jugador[i].simbolo) {
				casilla = jugador[i].casilla;
				jugador[i].casilla = simbolo;
				break;
			}
		}
	}

	public Subida puedeSubir(Tablero tablero) {
		//Iterar sobre las subidas buscando la que tenga el mismo simbolo que la casilla del jugador
		for (Subida s : tablero.subida) {
			if ( s == null) { //hasta que no hayan
				return null;
			}
			else if (casilla.equals(s.simbolo) && fila < s.filaFinal) {
				return s;
			}
		}
		return null;
	}
	
	public void subir(Tablero tablero, Subida s) {
		//Dejar casilla anterior como estaba, y colocar al jugador al final de la subida
		tablero.matriz[fila][columna] = casilla;
		fila = s.filaFinal;
		columna = s.columnaFinal;
		casilla = tablero.matriz[fila][columna];
		tablero.matriz[fila][columna] = simbolo;
	}
	
	public Bajada puedeBajar(Tablero tablero) {
		//Iterar sobre las bajadas buscando la que tenga el mismo simbolo que la casilla del jugador
		for (Bajada b : tablero.bajada) {
			if ( b == null) { //hasta que no hayan
				return null;
			}
			if (casilla.equals(b.simbolo) && fila > b.filaFinal) {
				return b;
			}
		}
		return null;
	}
	
	public void bajar(Tablero tablero, Bajada b) {
		//Dejar casilla anterior como estaba, y colocar al jugador al final de la bajada
		tablero.matriz[fila][columna] = casilla;
		fila = b.filaFinal;
		columna = b.columnaFinal;
		casilla = tablero.matriz[fila][columna];
		tablero.matriz[fila][columna] = simbolo;
	}
}
