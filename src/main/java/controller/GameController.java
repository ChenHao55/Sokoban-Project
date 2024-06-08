package controller;

import java.io.FileNotFoundException;

import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.exceptions.WallException;
import model.services.GameService;
import view.MainFrame;
import view.MapPanel;

public class GameController {
	
	private GameService gs;
	private MainFrame mf;
	private MapPanel mp;
	
	public GameController(GameService gs, MainFrame mf, MapPanel mp) {
		this.gs = gs;
		this.mf = mf;
		this.mp = mp;
	}
	
	public void newGame(String fileName) throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
		gs.newGame(fileName);
	}
	
	public void saveGame() throws IlegalPositionException, ObjectPositionNotFoundException {
		gs.saveGame();
	}
	
	public void loadGame() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException {
		gs.loadGame();
	}
	
	public void loadGameMF() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException {
		gs.loadGameMF();
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
	
	public void upDateLevelName(int levelNumber) {
		this.mp.levelName.setText("Nivel: " + levelNumber);
	}
	
	public void showCongrats(int count) {
		mf.showCongrats(count);
	}
	
	public void showError() {
		mf.showError();
	}
	
	public void updatecounters(int boxCount, int wCount, int globalCount) {
		mp.turnBox.setText("P: " + boxCount);
		mp.turnWarehouseman.setText("W: " + wCount);
		mp.turnCount.setText("T: " + globalCount);
	}
	
	//Este metodo se encarga de actualizar el mapa despues de los movimientos
	public void updateMap(char[][] level) throws IlegalPositionException, ObjectPositionNotFoundException {
		mp.createMap(level);
	}
	
	public void paintMap() throws IlegalPositionException, ObjectPositionNotFoundException {
		mf.paintMap(mp);
	}
	
}
