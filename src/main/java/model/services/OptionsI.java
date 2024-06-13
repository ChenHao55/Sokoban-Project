package model.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import model.beans.GenericCounter;
import model.beans.WarehouseMan;
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;

public interface OptionsI {
	
	//Metodo para leer el mapa del fichero y crearlo
	Pair<String, char[][]> newGame(String fileName) throws FileNotFoundException, IlegalPositionException, IlegalMap;
	
	//Metodo para guardar una partida
	boolean saveGame(char[][] map, WarehouseMan w, List<GameObjectI> gs, Deque<ActionI> s, int levelNumber,  String levelName, File file, GenericCounter c);
	
	//Metodo para cargar una partida
	Triplet<Integer, String, char[][]> loadGame(WarehouseMan w, List<GameObjectI> gs, ActionsManagerI am, File file, GenericCounter c) throws NumberFormatException, IlegalPositionException;
}
