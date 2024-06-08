package model.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.javatuples.Pair;

import controller.GameController;
import model.beans.Counter;
import model.beans.DownAction;
import model.beans.LeftAction;
import model.beans.RightAction;
import model.beans.UpAction;
import model.beans.WarehouseMan;
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.exceptions.WallException;
import view.OptionsGamePanel;

public class GameService {
	
		private GameController gc;
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
		private Counter currentCount = new Counter();
		private Counter levelCount = new Counter();
		
		
		public GameService() throws IlegalPositionException {
			this.w = new WarehouseMan(0,0);
			this.gs = new ArrayList<GameObjectI>();
		}

//Este metodo se encarga de crear el mapa
public void newGame(String fileName) throws IlegalPositionException, ObjectPositionNotFoundException, FileNotFoundException, IlegalMap {
	
	levelNumber = 1;
	currentCount.setBoxCount(0);
	currentCount.setCount(0);
	currentCount.setGlobalCount(0);
	cloneCounter(levelCount, currentCount);
	am.clearActions();
	game(fileName);
	
}

//Este metodo se encarga de crear el mapa
public void game(String fileName) throws IlegalPositionException, ObjectPositionNotFoundException, FileNotFoundException, IlegalMap {
	level = o.newGame(fileName);
	GameObjectI w_aux = of.createWarehouseMan(level);
	if(w_aux != null) {
		this.w = w_aux;
		this.gs = of.createGoals(level);
	}
	this.gc.upDateLevelName(levelNumber);
	this.gc.updatecounters(currentCount.getBoxCount(), currentCount.getCount(), currentCount.getGlobalCount());
	this.gc.updateMap(level);
	this.gc.paintMap();
}

//Metodo para guardar la partida
public void saveGame() throws IlegalPositionException, ObjectPositionNotFoundException {
	File f = op.saveGame('s'); 
	if(f != null) {
		o.saveGame(level, (WarehouseMan) w, gs, am.getActions(), levelNumber, f, currentCount, levelCount); 
		this.gc.updateMap(level);
		this.gc.paintMap();
	}
	else
		this.gc.paintMap();
}

//Metodo para cargar una partida guardada
public void loadGame() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException {
	File file = op.saveGame('l');

	if(file != null) {
		Pair<Integer, char[][]> p = o.loadGame((WarehouseMan) w, gs, am, file, currentCount, levelCount);
				
		if(p != null) {
			level = p.getValue1();
			levelNumber = p.getValue0();
		}
		
		this.gc.updatecounters(currentCount.getBoxCount(), currentCount.getCount(), currentCount.getGlobalCount());
		this.gc.updateMap(level);
		this.gc.paintMap();
		
	}
	else
		this.gc.paintMap();
}



//Metodo para cargar una partida guardada
public void loadGameMF() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException {
	File file = op.saveGame('l');

	if(file != null) {
		Pair<Integer, char[][]> p = o.loadGame((WarehouseMan) w, gs, am, file, currentCount, levelCount);
				
		if(p != null) {
			level = p.getValue1();
			levelNumber = p.getValue0();
		}
		this.gc.updatecounters(currentCount.getBoxCount(), currentCount.getCount(), currentCount.getGlobalCount());
		this.gc.updateMap(level);
		this.gc.paintMap();
	}
}

public void decrementBoxCounter() throws NumberFormatException{
	currentCount.setBoxCount(currentCount.getBoxCount()-1);
}

public void decrementWMCounter() throws NumberFormatException{
	currentCount.setCount(currentCount.getCount()-1);
}

public void decrementGlobalCounter() throws NumberFormatException{
	currentCount.setGlobalCount(currentCount.getGlobalCount()-1);
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
		this.level = ((Action) atc).getMat();
		this.w.setX(atc.getX());
		this.w.setY(atc.getY());
	}
	this.gc.updatecounters(currentCount.getBoxCount(), currentCount.getCount(), currentCount.getGlobalCount());
	this.gc.updateMap(level);
	this.gc.paintMap();
}

//METODOS PARA MOVER EL PERSONAJE
public void moveUp() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = currentCount.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('u', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((UpAction) atc).move((WarehouseMan) w, gs, level, currentCount);
	if(currentCount.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
	
	this.gc.updatecounters(currentCount.getBoxCount(), currentCount.getCount(), currentCount.getGlobalCount());
	this.gc.updateMap(level);
	this.gc.paintMap();
}

public void moveLeft() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = currentCount.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('l', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((LeftAction) atc).move((WarehouseMan) w, gs, level, currentCount);
	if(currentCount.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
	
	this.gc.updatecounters(currentCount.getBoxCount(), currentCount.getCount(), currentCount.getGlobalCount());
	this.gc.updateMap(level);
	this.gc.paintMap();
}

public void moveDown() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = currentCount.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('d', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((DownAction) atc).move((WarehouseMan) w, gs, level, currentCount);
	if(currentCount.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
	
	this.gc.updatecounters(currentCount.getBoxCount(), currentCount.getCount(), currentCount.getGlobalCount());
	this.gc.updateMap(level);
	this.gc.paintMap();
}

public void moveRight() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = currentCount.getGlobalCount();
	cloneMap(levelClone);

	ActionI atc = af.createAction('r', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	
	((RightAction) atc).move((WarehouseMan) w, gs, level, currentCount);
	if(currentCount.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
	
	this.gc.updatecounters(currentCount.getBoxCount(), currentCount.getCount(), currentCount.getGlobalCount());
	this.gc.updateMap(level);
	this.gc.paintMap();
}

//Checks if the game has ended
private boolean isEndLevel() {
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
	this.game(new File("maps" + fileSeparator + "level_" + levelNumber + ".txt").getAbsolutePath());
	am.clearActions();
	cloneCounter(currentCount, levelCount);
	this.gc.updatecounters(currentCount.getBoxCount(), currentCount.getCount(), currentCount.getGlobalCount());
}

private void cloneCounter(Counter c1, Counter c2) {
	c1.setBoxCount(c2.getBoxCount());
	c1.setCount(c2.getCount());
	c1.setGlobalCount(c2.getGlobalCount());
}

//If the level is completed, it passes to the next one or shows the congratulations screen
public void nextLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
	if(isEndLevel()) {
		
		boolean correctLevel = false;
		while(!correctLevel) {
			
			if(levelNumber != totalLevels) {
				levelNumber += 1;
				am.clearActions();
				this.gc.upDateLevelName(levelNumber);
				this.game(new File("maps" + fileSeparator + "level_" + levelNumber + ".txt").getAbsolutePath());
				cloneCounter(levelCount, currentCount);
			}
			else if(levelNumber == totalLevels && w != null) {
				levelNumber = 1;
				am.clearActions();
				this.gc.showCongrats(currentCount.getGlobalCount());
				correctLevel = true;
			}

			if(gs != null && gs.size() > 0 && w != null) {
				correctLevel = true;
				
			}
			else
				this.gc.showError();
		}
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
	
	public char[][] getMap(){
		return this.level;
	}
	
	public void setMap(char[][] level) {
		this.level = level;
	}

	public GameController getGc() {
		return gc;
	}

	public void setGc(GameController gc) {
		this.gc = gc;
	}
}
