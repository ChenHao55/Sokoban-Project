package model.services;

import java.io.FileNotFoundException;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;

public interface CreateMapI {
	
	//Metodo para leer el mapa del fichero y crearlo
	char[][] createMap(String fileName, WarehouseMan w, GoalPosition g) throws FileNotFoundException;
}
