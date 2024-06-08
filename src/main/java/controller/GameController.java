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
		this.upDateLevelName(gs.getLevelNumber());
		this.updatecounters(gs.getW().getBoxCount(), gs.getW().getCount(), gs.getW().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void saveGame() throws IlegalPositionException, ObjectPositionNotFoundException {
		gs.saveGame();
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void loadGame() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException {
		gs.loadGame();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void loadGameMF() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException {
		if(gs.loadGameMF()) {
			this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
			this.updateMap(gs.getMap());
			this.paintMap();
		}
	}
	
	public void undoMovement() throws IlegalPositionException, ObjectPositionNotFoundException {
		gs.undoMovement();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void moveUp() throws ObjectPositionNotFoundException, WallException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveUp();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
	}
	
	public void moveDown() throws ObjectPositionNotFoundException, WallException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveDown();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
	}
	
	public void moveRight() throws ObjectPositionNotFoundException, WallException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveRight();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
	}
	
	public void moveLeft() throws ObjectPositionNotFoundException, WallException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveLeft();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
	}
	
	public void restartLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
		gs.restartLevel();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void nextLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
		switch(gs.nextLevel()) {
			
			case 1:
				this.upDateLevelName(gs.getLevelNumber());
				this.updatecounters(gs.getW().getBoxCount(), gs.getW().getCount(), gs.getW().getGlobalCount());
				this.updateMap(gs.getMap());
				this.paintMap();
				break;
			case 2:
				this.showCongrats(gs.getW().getGlobalCount());
				break;
			case 3:
				this.updateMap(gs.getMap());
				this.paintMap();
				this.showError();
				this.nextLevel();
				break;
			default:
				break;
		}
	}
	
	private void upDateLevelName(int levelNumber) {
		this.mp.levelName.setText("Nivel: " + levelNumber);
	}
	
	private void showCongrats(int count) {
		mf.showCongrats(count);
	}
	
	private void showError() {
		mf.showError();
	}
	
	private void updatecounters(int boxCount, int wCount, int globalCount) {
		mp.turnBox.setText("P: " + boxCount);
		mp.turnWarehouseman.setText("W: " + wCount);
		mp.turnCount.setText("T: " + globalCount);
	}
	
	//Este metodo se encarga de actualizar el mapa despues de los movimientos
	private void updateMap(char[][] level) throws IlegalPositionException, ObjectPositionNotFoundException {
		mp.createMap(level);
	}
	
	private void paintMap() throws IlegalPositionException, ObjectPositionNotFoundException {
		mf.paintMap(mp);
	}
	
}
