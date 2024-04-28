package model.services;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;

public interface OptionsI {
	
	//Metodo para leer el mapa del fichero y crearlo
	char[][] newGame(String fileName, WarehouseMan w, ArrayList <GoalPosition> gs) throws FileNotFoundException;
	
	//Metodo para guardar una partida
	void saveGame(char[][] map);
	
	//Metodo para cargar una partida
	char[][] loadGame(WarehouseMan w, ArrayList<GoalPosition> gs);
}
