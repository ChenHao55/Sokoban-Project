package model.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.List;

import org.javatuples.Pair;

import model.beans.Counter;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;

public interface OptionsI {
	
	//Metodo para leer el mapa del fichero y crearlo
	char[][] newGame(String fileName) throws FileNotFoundException, IlegalPositionException;
	
	//Metodo para guardar una partida
	void saveGame(char[][] map, WarehouseMan w, List<GameObjectI> gs, Deque<ActionI> s, int levelNumber, File file, Counter currentCount, Counter levelCount);
	
	//Metodo para cargar una partida
	Pair<Integer, char[][]> loadGame(WarehouseMan w, List<GameObjectI> gs, ActionsManagerI am, File file, Counter currentCount, Counter levelCount) throws NumberFormatException, IlegalPositionException;
}
