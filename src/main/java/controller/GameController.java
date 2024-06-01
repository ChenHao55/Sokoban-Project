package controller;

import java.io.FileNotFoundException;

import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.exceptions.WallException;

public class GameController {
	
	private GameService gs;
	
	public GameController(GameService gs) {
		this.gs = gs;
	}
	
	public void newGame(String fileName) throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
		gs.newGame(fileName);
	}
	
	public void saveGame() {
		gs.saveGame();
	}
	
	public void loadGame() throws NumberFormatException, IlegalPositionException {
		gs.loadGame();
	}
	
	public void undoMovement() throws IlegalPositionException, ObjectPositionNotFoundException {
		gs.undoMovement();
	}
	
	public void moveUp() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		gs.moveUp();
	}
	
	public void moveDown() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		gs.moveDown();
	}
	
	public void moveRight() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		gs.moveRight();
	}
	
	public void moveLeft() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		gs.moveLeft();
	}
	
	public void restartLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
		gs.restartLevel();
	}
	
	public void nextLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
		gs.nextLevel();
	}
	
	
}
