package controller;

import java.io.File;
import java.io.FileNotFoundException;

import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.services.GameService;
import model.services.GameServiceI;
import view.MainFrameI;

public class GameController implements GameControllerI {
	
	private GameServiceI gs;
	private MainFrameI mf;
	
	public GameController(MainFrameI mf) throws IlegalPositionException {
		this.gs = new GameService();
		this.mf = mf;
	}
	
	public GameController() {/*No need implementation*/}
	
	public void newGame(String fileName) throws FileNotFoundException, IlegalPositionException, IlegalMap, ObjectPositionNotFoundException {
		gs.newGame(fileName);
		this.updateMap(gs.getMap());
		this.paintMap();
		this.updateLevelName(gs.getLevelName());
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
	}
	
	public void saveGame() throws IlegalPositionException, ObjectPositionNotFoundException {
		File f = mf.saveGame('s');
		gs.saveGame(f);
		this.updateMap(gs.getMap());
		this.paintMap();

	}
	
	public void loadGame() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException {
		File f = mf.saveGame('l');
		gs.loadGame(f);
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		this.updateLevelName(gs.getLevelName());

	}
	
	public void loadGameMF() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException {
		File f = mf.saveGame('l');
		if(gs.loadGameMF(f)) {
			this.updateMap(gs.getMap());
			this.paintMap();
			this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
			this.updateLevelName(gs.getLevelName());
		}
	}
	
	public void undoMovement() throws IlegalPositionException, ObjectPositionNotFoundException {
		gs.undoMovement();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void moveUp() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveUp();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
		this.updateLevelName(gs.getLevelName());
	}
	
	public void moveDown() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveDown();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
	}
	
	public void moveRight() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveRight();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
	}
	
	public void moveLeft() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveLeft();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
	}
	
	public void restartLevel() throws FileNotFoundException, IlegalPositionException, IlegalMap, ObjectPositionNotFoundException {
		gs.restartLevel();
		this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void nextLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
		switch(gs.nextLevel()) {
			
			case 1:
				this.updateLevelName(gs.getLevelName());
				this.updatecounters(gs.getCounter().getBoxCount(), gs.getCounter().getCount(), gs.getCounter().getGlobalCount());
				this.updateMap(gs.getMap());
				this.paintMap();
				break;
			case 2:
				this.showCongrats(gs.getCounter().getGlobalCount());
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
	
	private void updateLevelName(String levelName) {
		mf.updateLevelName(levelName);
	}
	
	private void showCongrats(int count) {
		mf.showCongrats(count);
	}
	
	private void showError() {
		mf.showError();
	}
	
	private void updatecounters(int boxCount, int wCount, int globalCount) {
		mf.updateCounters(boxCount, wCount, globalCount);
	}
	
	private void updateMap(char[][] level) throws IlegalPositionException, ObjectPositionNotFoundException {
		mf.createMap(level);
	}
	
	private void paintMap() {
		mf.paintMap();
	}
	
}
