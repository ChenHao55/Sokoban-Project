package interfaces;

import java.io.FileNotFoundException;

public interface CreateMapI {
	
	//Metodo para leer el mapa del fichero y crearlo
	char[][] createMap(String fileName) throws FileNotFoundException;
}
