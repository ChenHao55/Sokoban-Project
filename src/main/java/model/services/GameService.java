package model.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import model.beans.Counter;
import model.beans.DownAction;
import model.beans.GenericCounter;
import model.beans.LeftAction;
import model.beans.RightAction;
import model.beans.UpAction;
import model.beans.WarehouseMan;
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import view.OptionsGamePanel;

public class GameService {

		private OptionsGamePanel op = new OptionsGamePanel(); //He creado este objeto para no tener Swing en el options.java
		private char[][] level;
		private GameObjectI w;
		private List<GameObjectI> gs;
		private ActionsManagerI am = new ActionsManager();
		private ActionsFactoryI af = new ActionsFactory();
		private ObjectFactoryI of = new ObjectFactory();
		private OptionsI o = new Options();
		private int levelNumber = 1;
		private int totalLevels = 10;
		private String fileSeparator = File.separator;
		private GenericCounter c = new GenericCounter();
		
		
		public GameService() throws IlegalPositionException {
			this.w = new WarehouseMan(0,0);
			this.gs = new ArrayList<>();
		}

//Este metodo se encarga de crear el mapa
public void newGame(String fileName) throws IlegalPositionException, FileNotFoundException, IlegalMap {
	levelNumber = 1;
		
	c.setCurrentCount(new Counter());
	c.setLevelCount(new Counter());
	c.cloneCounter(c.getLevelCount(), c.getCurrentCount());
	am.clearActions();
	game(fileName);
}

//Este metodo se encarga de crear el mapa
public void game(String fileName) throws IlegalPositionException, FileNotFoundException, IlegalMap {
	level = o.newGame(fileName);
	GameObjectI wAux = of.createWarehouseMan(level);
	if(wAux != null) {
		this.w = wAux;
		this.gs = of.createGoals(level);
	}
}

//Metodo para guardar la partida
public void saveGame() {
	File f = op.saveGame('s'); 
	if(f != null)
		o.saveGame(level, (WarehouseMan) w, gs, am.getActions(), levelNumber, f, c);
}

//Metodo para cargar una partida guardada
public void loadGame() throws NumberFormatException, IlegalPositionException {
	File file = op.saveGame('l');
	if(file != null) {
		Pair<Integer, char[][]> p = o.loadGame((WarehouseMan) w, gs, am, file, c);
				
		if(p != null) {
			level = p.getValue1();
			levelNumber = p.getValue0();
		}
	}
}

//Metodo para cargar una partida guardada
public boolean loadGameMF() throws NumberFormatException, IlegalPositionException {
	File file = op.saveGame('l');
	boolean res = false;
	if(file != null) {
		Pair<Integer, char[][]> p = o.loadGame((WarehouseMan) w, gs, am, file, c);
				
		if(p != null) {
			level = p.getValue1();
			levelNumber = p.getValue0();
			res = true;
		}
	}
	return res;
}

public void decrementBoxCounter() throws NumberFormatException{
	c.getCurrentCount().setBoxCount(c.getCurrentCount().getBoxCount()-1);
}

public void decrementWMCounter() throws NumberFormatException{
	c.getCurrentCount().setCount(c.getCurrentCount().getCount()-1);
}

public void decrementGlobalCounter() throws NumberFormatException{
	c.getCurrentCount().setGlobalCount(c.getCurrentCount().getGlobalCount()-1);
}

private void cloneMap(char[][] levelClone) {
	for(int i = 0; i<this.level.length; i++) {
		levelClone[i] = this.level[i].clone();
	}
}

public void undoMovement() throws IlegalPositionException {
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


//METODOS PARA MOVER EL PERSONAJE
public void moveUp() throws IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = c.getCurrentCount().getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('u', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((UpAction) atc).move((WarehouseMan) w, gs, level, c.getCurrentCount());
	if(c.getCurrentCount().getGlobalCount()  == globalCounter)
		am.deleteAction(atc);
}

public void moveLeft() throws IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = c.getCurrentCount().getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('l', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((LeftAction) atc).move((WarehouseMan) w, gs, level, c.getCurrentCount());
	if(c.getCurrentCount().getGlobalCount() == globalCounter)
		am.deleteAction(atc);
}

public void moveDown() throws IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = c.getCurrentCount().getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('d', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((DownAction) atc).move((WarehouseMan) w, gs, level, c.getCurrentCount());
	if(c.getCurrentCount().getGlobalCount() == globalCounter)
		am.deleteAction(atc);
}

public void moveRight() throws IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = c.getCurrentCount().getGlobalCount();
	cloneMap(levelClone);

	ActionI atc = af.createAction('r', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	
	((RightAction) atc).move((WarehouseMan) w, gs, level, c.getCurrentCount());
	if(c.getCurrentCount().getGlobalCount() == globalCounter)
		am.deleteAction(atc);
}

//Restarts the level
public void restartLevel() throws FileNotFoundException, IlegalPositionException, IlegalMap {
	this.game(new File("maps" + fileSeparator + "level_" + levelNumber + ".txt").getAbsolutePath());
	am.clearActions();
	c.cloneCounter(c.getCurrentCount(), c.getLevelCount());
}

public int nextLevel() throws FileNotFoundException, IlegalPositionException, IlegalMap {
	int res = 0;
		
	if(levelNumber != totalLevels) {
		levelNumber += 1;
		this.game(new File("maps" + this.fileSeparator + "level_" + this.levelNumber + ".txt").getAbsolutePath());
		am.clearActions();
		res = 1;
	}
	else if(w != null) {
		levelNumber = 1;
		am.clearActions();
		return 2;
	}

	if(gs == null || gs.isEmpty() || w == null) {
		return 3;
	}
	return res;
}

//GETTERS Y SETTERS
	public WarehouseMan getW() {
		return (WarehouseMan) this.w;
	}
	
	public void setW(WarehouseMan w) {
		this.w = w;
	}
	
	public List<GameObjectI> getGs() {
		return this.gs;
	}
	
	public void setGs(List<GameObjectI> gs) {
		this.gs = gs;
	}
	
	public char[][] getMap(){
		return this.level;
	}
	
	public void setMap(char[][] level) {
		this.level = level;
	}
	
	public int getLevelNumber() {
		return this.levelNumber;
	}
	
	public void setLevelNUmber(int level) {
		this.levelNumber = level;
	}
	
	public GenericCounter getGenericCounter() {
		return this.c;
	}
}
