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
		//Limpiar la casilla en la que el jugador estaba
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
		
		casilla = tablero.matriz[fila][columna];
		tablero.matriz[fila][columna] = simbolo;
	}

	public void estaOcupada(Jugador[] jugador) {
		// Logica en caso el jugador caiga en una casilla ocupada por otro jugador
		for (int i = 0; i < jugador.length; i++) {
			if (casilla == jugador[i].simbolo) {
				casilla = jugador[i].casilla;
				jugador[i].casilla = simbolo;
			}
		}
	}

	public Subida puedeSubir(Tablero tablero) {
		for (Subida s : tablero.subida) {
			if ( s == null) {
				return null;
			}
			else if (casilla.equals(s.simbolo) && fila < s.filaFinal) {
				return s;
			}
		}
		return null;
	}
	
	public void subir(Tablero tablero, Subida s) {
		tablero.matriz[fila][columna] = casilla;
		fila = s.filaFinal;
		columna = s.columnaFinal;
		casilla = tablero.matriz[fila][columna];
		tablero.matriz[fila][columna] = simbolo;
	}
	
	public Bajada puedeBajar(Tablero tablero) {
		for (Bajada b : tablero.bajada) {
			if ( b == null) {
				return null;
			}
			if (casilla.equals(b.simbolo) && fila > b.filaFinal) {
				return b;
			}
		}
		return null;
	}
	
	public void bajar(Tablero tablero, Bajada b) {
		tablero.matriz[fila][columna] = casilla;
		fila = b.filaFinal;
		columna = b.columnaFinal;
		casilla = tablero.matriz[fila][columna];
		tablero.matriz[fila][columna] = simbolo;
	}
}
