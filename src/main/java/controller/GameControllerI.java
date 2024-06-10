package controller;

import java.io.FileNotFoundException;

import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;

public interface GameControllerI {
	
	public void newGame(String fileName) throws FileNotFoundException, IlegalPositionException, IlegalMap;
	
	public void saveGame();
	
	public void loadGame() throws NumberFormatException, IlegalPositionException;
	
	public void loadGameMF() throws NumberFormatException, IlegalPositionException;
	
	public void undoMovement() throws IlegalPositionException;
	
	public void moveUp() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap;
	
	public void moveDown() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap;
	
	public void moveRight() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap;
	
	public void moveLeft() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap;
	
	public void restartLevel() throws FileNotFoundException, IlegalPositionException, IlegalMap;
	
	public void nextLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap;

}
