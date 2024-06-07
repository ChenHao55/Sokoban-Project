package model.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.javatuples.Pair;

import model.beans.DownAction;
import model.beans.LeftAction;
import model.beans.RightAction;
import model.beans.UpAction;
import model.beans.WarehouseMan;
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.exceptions.WallException;
import view.MainFrame;
import view.MapPanel;
import view.OptionsGamePanel;

public class GameService {

		
		private MainFrame mf;
		public MapPanel mp;
		private OptionsGamePanel op = new OptionsGamePanel(); //He creado este objeto para no tener Swing en el options.java
		private char[][] level;
		private GameObjectI w;
		private ArrayList<GameObjectI> gs;
		private ActionsManagerI am = new ActionsManager();
		private ActionsFactoryI af = new ActionsFactory();
		private ObjectFactoryI of = new ObjectFactory();
		private OptionsI o = new Options();
		private int levelNumber = 1;
		private int totalLevels = 10;
		private String fileSeparator = File.separator;
		
		public GameService(MainFrame mf, MapPanel mp) {
			this.mf = mf;
			this.mp = mp;
		}


//Este metodo se encarga de crear el mapa desde el principio
public void newGame(String fileName) throws IlegalPositionException, ObjectPositionNotFoundException, FileNotFoundException, IlegalMap {
	levelNumber = 1;
	game(fileName);
}

//Este metodo se encarga de crear el mapa
public void game(String fileName) throws IlegalPositionException, ObjectPositionNotFoundException, FileNotFoundException, IlegalMap {
	level = o.newGame(fileName);
	this.w = of.createWarehouseMan(level);
	this.gs = of.createGoals(level);
	updatecounters();
	mp.createMap(level);
	mf.paintMap(mp);
}

//Metodo para guardar la partida
public void saveGame() {
	File f = op.saveGame('s'); 
	o.saveGame(level, (WarehouseMan) w, gs, am.getActions(), levelNumber, f); 
	try {
		updateMap();
	} catch (IlegalPositionException | ObjectPositionNotFoundException e) {
		e.getMessage();
	}
}

//Metodo para cargar una partida guardada
public void loadGame() throws NumberFormatException, IlegalPositionException {
	File file = op.saveGame('l');

	Pair<Integer, char[][]> p = o.loadGame((WarehouseMan) w, gs, am, file);
	
	if(p != null) {
		level = p.getValue1();
		levelNumber = p.getValue0();
	}
	updatecounters();
	try {
		updateMap();
	} catch (IlegalPositionException | ObjectPositionNotFoundException e) {
		e.getMessage();
	}
}

public void decrementBoxCounter() throws NumberFormatException{
	w.setBoxCount(w.getBoxCount()-1);
}

public void decrementWMCounter() throws NumberFormatException{
	w.setCount(w.getCount()-1);
}

public void decrementGlobalCounter() throws NumberFormatException{
	w.setGlobalCount(w.getGlobalCount()-1);
}

public void updatecounters() {
	this.mp.turnBox.setText("P: " + w.getBoxCount());
	this.mp.turnWarehouseman.setText("W: " + w.getCount());
	this.mp.turnCount.setText("T: " + w.getGlobalCount());
}

private void cloneMap(char[][] levelClone) {
	for(int i = 0; i<this.level.length; i++) {
		levelClone[i] = this.level[i].clone();
	}
}

public void undoMovement() throws IlegalPositionException, ObjectPositionNotFoundException {
	ActionI atc = am.undo();
	if(atc != null) {
		if(atc.isLastBox()) {
			decrementBoxCounter();
		}
		else {
			decrementWMCounter();
		}		
		decrementGlobalCounter();
		updatecounters();
		this.level = ((Action) atc).getMat();
		this.w.setX(atc.getX());
		this.w.setY(atc.getY());
		updateMap();
	}
}

//METODOS PARA MOVER EL PERSONAJE
public void moveUp() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = w.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('u', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((UpAction) atc).move((WarehouseMan) w, gs, level);
	if(w.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
	updatecounters();
	updateMap();
}

public void moveLeft() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = w.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('l', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((LeftAction) atc).move((WarehouseMan) w, gs, level);
	if(w.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
	updatecounters();
	updateMap();
}

public void moveDown() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = w.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('d', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((DownAction) atc).move((WarehouseMan) w, gs, level);
	if(w.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
	updatecounters();
	updateMap();
}

public void moveRight() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = w.getGlobalCount();
	cloneMap(levelClone);

	ActionI atc = af.createAction('r', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	
	((RightAction) atc).move((WarehouseMan) w, gs, level);
	if(w.getGlobalCount() == globalCounter)
		am.deleteAction(atc);

	updatecounters();
	updateMap();
}

//Checks if the game has ended
public boolean isEndLevel() {
	boolean end= true;
	
	for (GameObjectI g : gs) {
		if(level[g.getX()][g.getY()] != '@') {
			end= false;
			break;
		}
	}
	return end;
}

//Restarts the level
public void restartLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
	game(new File("maps" + fileSeparator + "level_" + levelNumber + ".txt").getAbsolutePath());
	am.clearActions();
	updatecounters();
}

//If the level is completed, it passes to the next one or shows the congratulations screen
public void nextLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
	if(isEndLevel() && levelNumber != totalLevels) {
		levelNumber += 1;
		am.clearActions();
		this.mp.levelName.setText("Nivel: " + levelNumber);
		game(new File("maps" + fileSeparator + "level_" + levelNumber + ".txt").getAbsolutePath());
	}
	else if(isEndLevel() && levelNumber == totalLevels) {
		levelNumber = 1;
		am.clearActions();
		mf.showCongrats(w.getGlobalCount());
	}
}

//Este metodo se encarga de actualizar el mapa despues de los movimientos
private void updateMap() throws IlegalPositionException, ObjectPositionNotFoundException {
	mp.createMap(level);
	mf.paintMap(mp);
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
