package model.services;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;

public interface OptionsI {
	
	//Metodo para leer el mapa del fichero y crearlo
	char[][] newGame(String fileName) throws FileNotFoundException, IlegalPositionException;
	
	//Metodo para guardar una partida
	void saveGame(char[][] map, WarehouseMan w, ArrayList<GameObjectI> gs);
	
	//Metodo para cargar una partida
	char[][] loadGame(WarehouseMan w, ArrayList<GameObjectI> gs) throws NumberFormatException, IlegalPositionException;
}
