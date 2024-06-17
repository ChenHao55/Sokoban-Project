package controller;

import java.io.FileNotFoundException;

import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;

public interface GameControllerI {
	
	public void newGame() throws FileNotFoundException, IlegalPositionException, IlegalMap, ObjectPositionNotFoundException;
	
	public void saveGame() throws ObjectPositionNotFoundException, IlegalPositionException;
	
	public void loadGame() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException;
	
	public void loadGameMF() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException;
	
	public void undoMovement() throws IlegalPositionException, ObjectPositionNotFoundException;
	
	public void moveUp() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap;
	
	public void moveDown() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap;
	
	public void moveRight() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap;
	
	public void moveLeft() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap;
	
	public void restartLevel() throws FileNotFoundException, IlegalPositionException, IlegalMap, ObjectPositionNotFoundException;
	
	public void nextLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap;

}
