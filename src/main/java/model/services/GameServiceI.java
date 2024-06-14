package model.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import model.beans.Counter;
import model.beans.WarehouseMan;
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;

public interface GameServiceI{
	
	public void newGame(String fileName) throws IlegalPositionException, FileNotFoundException, IlegalMap;

	public void game(String fileName) throws IlegalPositionException, FileNotFoundException, IlegalMap;

	public boolean saveGame(File f);

	public boolean loadGame(File file) throws NumberFormatException, IlegalPositionException;

	public boolean loadGameMF(File file) throws NumberFormatException, IlegalPositionException;

	public void decrementBoxCounter();

	public void decrementWMCounter();

	public void decrementGlobalCounter();

	public void undoMovement() throws IlegalPositionException;

	public boolean isEndLevel();

	// Métodos para mover el personaje
	public void moveUp() throws IlegalPositionException;

	public void moveLeft() throws IlegalPositionException;

	public void moveDown() throws IlegalPositionException;

	public void moveRight() throws IlegalPositionException;

	public void restartLevel() throws FileNotFoundException, IlegalPositionException, IlegalMap;

	public int nextLevel() throws FileNotFoundException, IlegalPositionException, IlegalMap;

	// Getters y setters
	public WarehouseMan getW();

	public void setW(WarehouseMan w);

	public List<GameObjectI> getGs();

	public void setGs(List<GameObjectI> gs);

	public char[][] getMap();

	public void setMap(char[][] level);

	public int getLevelNumber();

	public String getLevelName();

	public void setLevelNumber(int level);

	public Counter getCounter();

	public void setCounter(Counter g);

	public void setTotalLevels(int totalLevels);

}
