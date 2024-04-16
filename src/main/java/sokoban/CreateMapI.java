package sokoban;

import exceptions.EmptyFileException;
import exceptions.FileException;

public interface CreateMapI {
	
	//Metodo para leer el mapa del fichero y crearlo
	char[][] createMap(String fileName) throws EmptyFileException, FileException;
}
