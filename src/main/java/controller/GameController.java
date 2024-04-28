package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.exceptions.WallException;
import model.services.Action;
import model.services.ActionI;
import model.services.ActionsFactory;
import model.services.ActionsFactoryI;
import model.services.ActionsManager;
import model.services.ActionsManagerI;
import model.services.OptionsI;
import model.services.Options;
import view.MainFrame;
import view.MapPanel;

public class GameController {
	
	private MainFrame mf;
	private MapPanel mp;
	private ActionI atc;
	private char[][] level;
	private WarehouseMan w = new WarehouseMan();
	private WarehouseMan wClone;
	private char[][] levelClone;
	private ArrayList<GoalPosition> gs = new ArrayList<GoalPosition>();
	private ActionsManagerI am = new ActionsManager();
	private ActionsFactoryI af = new ActionsFactory();
	private OptionsI o = new Options(); 
	
	public GameController(MainFrame mf, MapPanel mp) {
		this.mf = mf;
		this.mp = mp;
	}
	
	//Aqui añadiria metodos como cambiar de nivel, una vez superado...
	
	//Este metodo se encarga de crear el mapa desde el principio
	public void newGame(String fileName) throws IlegalPositionException, ObjectPositionNotFoundException, FileNotFoundException {
		level = o.newGame(fileName, w, gs);
		mp.createMap(level);
		mf.paintMap(mp);
	}
	
	public void saveGame() {
		o.saveGame(level);
		try {
			updateMap();
		} catch (IlegalPositionException | ObjectPositionNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadGame() {
		this.level = o.loadGame(w, gs);
		try {
			updateMap();
		} catch (IlegalPositionException | ObjectPositionNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void undoMovement() throws IlegalPositionException, ObjectPositionNotFoundException {
		atc = am.undo();
		this.level = ((Action) atc).getMat();
		this.w = ((Action) atc).getW();
		updateMap();
	}
	
	//METODOS PARA MOVER EL PERSONAJE
	public void moveUp() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		wClone = this.w.clone();
		levelClone = new char[this.level.length][];
		cloneMap(levelClone);
		atc = af.createAction('u', wClone, levelClone);
		am.newAction(atc);
		atc.move(w, gs, level);
		updateMap();
	}

	public void moveLeft() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		wClone = this.w.clone();
		levelClone = new char[this.level.length][];
		cloneMap(levelClone);
		atc = af.createAction('l', wClone, levelClone);
		am.newAction(atc);
		atc.move(w, gs, level);
		updateMap();
	}

	public void moveDown() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		wClone = this.w.clone();
		levelClone = new char[this.level.length][];
		cloneMap(levelClone);
		atc = af.createAction('d', wClone, levelClone);
		am.newAction(atc);
		atc.move(w, gs, level);
		updateMap();
	}
	
	public void moveRight() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		wClone = this.w.clone();
		levelClone = new char[this.level.length][];
		cloneMap(levelClone);
		atc = af.createAction('r', wClone, levelClone);
		am.newAction(atc);
		atc.move(w, gs, level);
		updateMap();
	}
	
	//Checks if the game has ended. If it has, it shows the congratulations screen
	public void endGame() {
		boolean endGame = true;
		
		for (GoalPosition g : gs) {
			if(level[g.getX()][g.getY()] != '#') {
				endGame = false;
				break;
			}
		}
		
		if(endGame) {
			mf.showCongrats();
		}
	}
	//Este metodo se encarga de actualizar el mapa despues de los movimientos
	private void updateMap() throws IlegalPositionException, ObjectPositionNotFoundException {
		mp.createMap(level);
		mf.paintMap(mp);
	}
	
	private void cloneMap(char[][] levelClone) {
		for(int i = 0; i<this.level.length; i++) {
			levelClone[i] = this.level[i].clone();
		}
	}
	
	//GETTERS Y SETTERS
	public WarehouseMan getW() {
		return this.w;
	}
	
	public void setW(WarehouseMan w) {
		this.w = w;
	}
	
	public ArrayList<GoalPosition> getGs() {
		return this.gs;
	}
	
	public void setGs(ArrayList<GoalPosition> gs) {
		this.gs = gs;
	}
	
	public MainFrame getmf() {
		return mf;
	}

	public void setmf(MainFrame mf) {
		this.mf = mf;
	}

	public MapPanel getMp() {
		return mp;
	}

	public void setMp(MapPanel mp) {
		this.mp = mp;
	}
	
	public char[][] getMap(){
		return this.level;
	}
	
	public void setMap(char[][] level) {
		this.level = level;
	}
}
