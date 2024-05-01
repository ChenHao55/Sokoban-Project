package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.beans.DownAction;
import model.beans.LeftAction;
import model.beans.RightAction;
import model.beans.UpAction;
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
import model.services.GameObjectI;
import model.services.ObjectFactory;
import model.services.ObjectFactoryI;
import model.services.OptionsI;
import model.services.Options;
import view.MainFrame;
import view.MapPanel;

public class GameController {
	
	private MainFrame mf;
	private MapPanel mp;
	private ActionI atc;
	private char[][] level;
	private GameObjectI w;
	private GameObjectI wClone;
	private char[][] levelClone;
	private ArrayList<GameObjectI> gs;
	private ActionsManagerI am = new ActionsManager();
	private ActionsFactoryI af = new ActionsFactory();
	private ObjectFactoryI of = new ObjectFactory();
	private OptionsI o = new Options(); 
	private int level_n = 1;
	private final int total_levels = 2;
	private String fileSeparator = File.separator;

	
	public GameController(MainFrame mf, MapPanel mp) {
		this.mf = mf;
		this.mp = mp;
	}
	
	//Aqui añadiria metodos como cambiar de nivel, una vez superado...
	
	//Este metodo se encarga de crear el mapa desde el principio
	public void newGame(String fileName) throws IlegalPositionException, ObjectPositionNotFoundException, FileNotFoundException {
		level = o.newGame(fileName);
		this.w = of.createWarehouseMan(level);
		this.gs = of.createGoals(level);
		mp.createMap(level);
		mf.paintMap(mp);
	}
	
	public void saveGame() {
		o.saveGame(level, (WarehouseMan) w, gs);
		try {
			updateMap();
		} catch (IlegalPositionException | ObjectPositionNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadGame() throws NumberFormatException, IlegalPositionException {
		this.level = o.loadGame((WarehouseMan) w, gs);
		try {
			updateMap();
		} catch (IlegalPositionException | ObjectPositionNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void undoMovement() throws IlegalPositionException, ObjectPositionNotFoundException {
		atc = am.undo();
		if(atc != null) {
			this.level = ((Action) atc).getMat();
			this.w = ((Action) atc).getW();
			updateMap();
		}
	}
	
	//METODOS PARA MOVER EL PERSONAJE
	public void moveUp() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		wClone = (GameObjectI) this.w.clone();
		levelClone = new char[this.level.length][];
		cloneMap(levelClone);
		atc = af.createAction('u', (WarehouseMan) wClone, levelClone);
		am.newAction(atc);
		((UpAction) atc).move((WarehouseMan) w, gs, level);
		updateMap();
	}

	public void moveLeft() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		wClone = this.w.clone();
		levelClone = new char[this.level.length][];
		cloneMap(levelClone);
		atc = af.createAction('l', (WarehouseMan) wClone, levelClone);
		am.newAction(atc);
		((LeftAction) atc).move((WarehouseMan) w, gs, level);
		updateMap();
	}

	public void moveDown() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		wClone = this.w.clone();
		levelClone = new char[this.level.length][];
		cloneMap(levelClone);
		atc = af.createAction('d', (WarehouseMan) wClone, levelClone);
		am.newAction(atc);
		((DownAction) atc).move((WarehouseMan) w, gs, level);
		updateMap();
	}
	
	public void moveRight() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
		wClone = this.w.clone();
		levelClone = new char[this.level.length][];
		cloneMap(levelClone);
		atc = af.createAction('r', (WarehouseMan) wClone, levelClone);
		am.newAction(atc);
		((RightAction) atc).move((WarehouseMan) w, gs, level);
		updateMap();
	}
	
	//Checks if the game has ended
	public boolean isEndLevel() {
		boolean end= true;
		
		for (GameObjectI g : gs) {
			if(level[g.getX()][g.getY()] != '#') {
				end= false;
				break;
			}
		}
		return end;
	}
	
	//Restarts the level
	public void restartLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException {
		newGame(new File("maps" + fileSeparator + "map_level_" + level_n + ".txt").getAbsolutePath());
	}
	
	//If the level is completed, it passes to the next one or shows the congratulations screen
	public void nextLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException {
		if(isEndLevel() && level_n != total_levels) {
			level_n += 1;
			am.clearActions();
			newGame(new File("maps" + fileSeparator + "map_level_" + level_n + ".txt").getAbsolutePath());
		}
		else if(isEndLevel() && level_n == total_levels) {
			level_n = 1;
			am.clearActions();
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
		return (WarehouseMan) this.w;
	}
	
	public void setW(WarehouseMan w) {
		this.w = w;
	}
	
	public ArrayList<GameObjectI> getGs() {
		return this.gs;
	}
	
	public void setGs(ArrayList<GameObjectI> gs) {
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
