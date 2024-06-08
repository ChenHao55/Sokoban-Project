package model.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.javatuples.Pair;

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
		private Counter c = new Counter();
		
		public GameService() throws IlegalPositionException {
			this.w = new WarehouseMan(0,0);
			this.gs = new ArrayList<GameObjectI>();
		}

//Este metodo se encarga de crear el mapa
public void newGame(String fileName) throws IlegalPositionException, ObjectPositionNotFoundException, FileNotFoundException, IlegalMap {
	
	levelNumber = 1;
	c.setBoxCount(0);
	c.setCount(0);
	c.setGlobalCount(0);
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
}

//Metodo para guardar la partida
public void saveGame() throws IlegalPositionException, ObjectPositionNotFoundException {
	File f = op.saveGame('s'); 
	if(f != null) {
		o.saveGame(level, (WarehouseMan) w, gs, am.getActions(), levelNumber, f, c); 
	}
}

//Metodo para cargar una partida guardada
public void loadGame() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException {
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
public boolean loadGameMF() throws NumberFormatException, IlegalPositionException, ObjectPositionNotFoundException {
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
	c.setBoxCount(c.getBoxCount()-1);
}

public void decrementWMCounter() throws NumberFormatException{
	c.setCount(c.getCount()-1);
}

public void decrementGlobalCounter() throws NumberFormatException{
	c.setGlobalCount(c.getGlobalCount()-1);
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
public void moveUp() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = c.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('u', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((UpAction) atc).move((WarehouseMan) w, gs, level, c);
	if(c.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
}

public void moveLeft() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = c.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('l', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((LeftAction) atc).move((WarehouseMan) w, gs, level, c);
	if(c.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
}

public void moveDown() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = c.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('d', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((DownAction) atc).move((WarehouseMan) w, gs, level, c);
	if(c.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
}

public void moveRight() throws ObjectPositionNotFoundException, WallException, IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = c.getGlobalCount();
	cloneMap(levelClone);

	ActionI atc = af.createAction('r', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	
	((RightAction) atc).move((WarehouseMan) w, gs, level, c);
	if(c.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
}

//Restarts the level
public void restartLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
	this.game(new File("maps" + fileSeparator + "level_" + levelNumber + ".txt").getAbsolutePath());
	am.clearActions();
	c.setBoxCount(w.getBoxCount());
	c.setCount(w.getCount());
	c.setGlobalCount(w.getGlobalCount());
}

//If the level is completed, it passes to the next one or shows the congratulations screen
/*public void nextLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
	if(isEndLevel()) {
		
		boolean correctLevel = false;
		while(!correctLevel) {
			
			if(levelNumber != totalLevels) {
				levelNumber += 1;
				am.clearActions();
				this.gc.upDateLevelName(levelNumber);
				this.game(new File("maps" + fileSeparator + "level_" + levelNumber + ".txt").getAbsolutePath());
			}
			else if(levelNumber == totalLevels && w != null) {
				levelNumber = 1;
				am.clearActions();
				this.gc.showCongrats(w.getGlobalCount());
				correctLevel = true;
			}

			if(gs != null && gs.size() > 0 && w != null)
				correctLevel = true;
			else
				this.gc.showError();
		}
	}
}*/

public int nextLevel() throws FileNotFoundException, IlegalPositionException, ObjectPositionNotFoundException, IlegalMap {
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
	
	public int getLevelNumber() {
		return this.levelNumber;
	}
	
	public void setLevelNUmber(int level) {
		this.levelNumber = level;
	}
	
	public Counter getCounter() {
		return this.c;
	}
}
