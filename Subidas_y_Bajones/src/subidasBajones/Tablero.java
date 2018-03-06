package subidasBajones;

public class Tablero {

	int dimension;
	String[][] matriz = new String[23][12];
	Subida[] subida = new Subida[15];
	Bajada[] bajada = new Bajada[15];
	
	public Tablero(int d) {
		dimension = d;
		
		//Itera sobre una matriz dibujando los margenes de las distintas casillas
	    for (int fila = 0; fila <= dimension*2; fila++) {
	    		if (fila % 2 == 0)
	    			matriz[fila][0] = "\t";
	    		else
	    			matriz[fila][0] = "\t|";

	    		for (int columna = 1; columna <= dimension; columna++) {
	    			if (fila % 2 == 0)
	        			matriz[fila][columna] = " -----";
	        		else
	        			matriz[fila][columna] = "     |";
	        }
	    }
	    
	    // Indicar inicio y final del tablero
	    matriz[1][1] = "  $  |";
	    matriz[dimension*2-1][dimension] = "  $  |";   
	}
	
	public void generarSubida(int numEspecial) {
		//Se genera la cantidad de subidas especificadas y se le asigna una letra a traves de algebra de char
		for (int i = 0; i < numEspecial; i++) {
			subida[i] = new Subida("  "+String.valueOf((char)('A'+i))+"  |", this);
		}
	}
	
	public void generarBajada(int numEspecial) {
		//Se genera la cantidad de bajadas especificadas y se le asigna un numero que ocupe 2 espacios para mantener el formato de las casillas
		for (int i = 0; i < numEspecial; i++) {
			bajada[i] = new Bajada(" "+String.format("%2d", i)+"  |", this);
		}
	}
	
	public void dibujarTablero() {
		//Itera sobre cada fila y columna e imprime el valor contenido
		//Se recorre la matriz de tal manera que el espacio (1,1) quede en la esquina inferior izquierda
		System.out.print("\n");
		for (int fila = dimension*2; fila >= 0; fila--) {
			for (int columna = 0; columna <= dimension; columna++) {
				System.out.print(matriz[fila][columna]);
	        }
			System.out.print("\n");
		}
	}

}
