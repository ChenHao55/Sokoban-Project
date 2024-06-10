package controller;

import java.io.File;
import java.io.FileNotFoundException;

import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.services.GameService;
import view.MainFrame;
import view.MapPanel;
import view.OptionsGamePanel;

public class GameController implements GameControllerI {
	
	private OptionsGamePanel op = new OptionsGamePanel(); //He creado este objeto para no tener Swing en el options.java
	private GameService gs;
	private MainFrame mf;
	private MapPanel mp;
	
	public GameController(GameService gs, MainFrame mf, MapPanel mp) {
		this.gs = gs;
		this.mf = mf;
		this.mp = mp;
	}
	
	public GameController() {/*No need implementation*/}
	
	public void newGame(String fileName) throws FileNotFoundException, IlegalPositionException, IlegalMap {
		gs.newGame(fileName);
		this.upDateLevelName(gs.getLevelNumber());
		this.updatecounters(gs.getGenericCounter().getCurrentCount().getBoxCount(), gs.getGenericCounter().getCurrentCount().getCount(), gs.getGenericCounter().getCurrentCount().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void saveGame() {
		File f = op.saveGame('s'); 
		gs.saveGame(f);
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void loadGame() throws NumberFormatException, IlegalPositionException {
		File file = op.saveGame('l');
		gs.loadGame(file);
		this.updatecounters(gs.getGenericCounter().getCurrentCount().getBoxCount(), gs.getGenericCounter().getCurrentCount().getCount(), gs.getGenericCounter().getCurrentCount().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void loadGameMF() throws NumberFormatException, IlegalPositionException {
		File file = op.saveGame('l');
		if(gs.loadGameMF(file)) {
			this.updatecounters(gs.getGenericCounter().getCurrentCount().getBoxCount(), gs.getGenericCounter().getCurrentCount().getCount(), gs.getGenericCounter().getCurrentCount().getGlobalCount());
			this.updateMap(gs.getMap());
			this.paintMap();
		}
	}
	
	public void undoMovement() throws IlegalPositionException {
		gs.undoMovement();
		this.updatecounters(gs.getGenericCounter().getCurrentCount().getBoxCount(), gs.getGenericCounter().getCurrentCount().getCount(), gs.getGenericCounter().getCurrentCount().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void moveUp() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveUp();
		this.updatecounters(gs.getGenericCounter().getCurrentCount().getBoxCount(), gs.getGenericCounter().getCurrentCount().getCount(), gs.getGenericCounter().getCurrentCount().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
	}
	
	public void moveDown() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveDown();
		this.updatecounters(gs.getGenericCounter().getCurrentCount().getBoxCount(), gs.getGenericCounter().getCurrentCount().getCount(), gs.getGenericCounter().getCurrentCount().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
	}
	
	public void moveRight() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveRight();
		this.updatecounters(gs.getGenericCounter().getCurrentCount().getBoxCount(), gs.getGenericCounter().getCurrentCount().getCount(), gs.getGenericCounter().getCurrentCount().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
	}
	
	public void moveLeft() throws ObjectPositionNotFoundException, IlegalPositionException, FileNotFoundException, IlegalMap {
		gs.moveLeft();
		this.updatecounters(gs.getGenericCounter().getCurrentCount().getBoxCount(), gs.getGenericCounter().getCurrentCount().getCount(), gs.getGenericCounter().getCurrentCount().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
		if(this.gs.isEndLevel()) {
			this.nextLevel();
		}
	}
	
	public void restartLevel() throws FileNotFoundException, IlegalPositionException, IlegalMap {
		gs.restartLevel();
		this.updatecounters(gs.getGenericCounter().getCurrentCount().getBoxCount(), gs.getGenericCounter().getCurrentCount().getCount(), gs.getGenericCounter().getCurrentCount().getGlobalCount());
		this.updateMap(gs.getMap());
		this.paintMap();
	}
	
	public void nextLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
		switch(gs.nextLevel()) {
			
			case 1:
				this.upDateLevelName(gs.getLevelNumber());
				this.updatecounters(gs.getGenericCounter().getCurrentCount().getBoxCount(), gs.getGenericCounter().getCurrentCount().getCount(), gs.getGenericCounter().getCurrentCount().getGlobalCount());
				this.updateMap(gs.getMap());
				this.paintMap();
				break;
			case 2:
				this.showCongrats(gs.getGenericCounter().getCurrentCount().getGlobalCount());
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
		this.mp.getLevelName().setText("Nivel: " + levelNumber);
	}
	
	private void showCongrats(int count) {
		mf.showCongrats(count);
	}
	
	private void showError() {
		mf.showError();
	}
	
	private void updatecounters(int boxCount, int wCount, int globalCount) {
		mp.getTurnBox().setText("P: " + boxCount);
		mp.getTurnWarehouseMan().setText("W: " + wCount);
		mp.getTurnCount().setText("T: " + globalCount);
	}
	
	//Este metodo se encarga de actualizar el mapa despues de los movimientos
	private void updateMap(char[][] level) {
		mp.createMap(level);
	}
	
	private void paintMap() {
		mf.paintMap(mp);
	}
	
}
