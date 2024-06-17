package model.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.javatuples.Triplet;

import model.beans.Counter;
import model.beans.DownAction;
import model.beans.LeftAction;
import model.beans.RightAction;
import model.beans.UpAction;
import model.beans.WarehouseMan;
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;

public class GameService implements GameServiceI{

		private char[][] level;
		private GameObjectI w;
		private List<GameObjectI> gs;
		private ActionsManagerI am = new ActionsManager();
		private ActionsFactoryI af = new ActionsFactory();
		private ObjectFactoryI of = new ObjectFactory();
		private OptionsI o = new Options();
		private String levelName = "";
		private Counter c = new Counter();
		private String path = new File("maps").getAbsolutePath();
		private File directory = new File(path);
		private File[] files;

		private int levelPos = 0;
		
		
		public GameService() throws IlegalPositionException {
			this.w = new WarehouseMan(0,0);
			this.gs = new ArrayList<>();
			files = directory.listFiles((dir, name) -> name.startsWith("level_") && name.endsWith(".txt"));
			
			if(files != null && files.length > 0) {
				Arrays.sort(files, new Comparator<File>() {
					@Override
					public int compare(File f1, File f2) {
						int num1 = extractNumber(f1.getName());
						int num2 = extractNumber(f2.getName());
						return Integer.compare(num1, num2);
					}
					private int extractNumber(String name) {
						String number = name.replaceAll("\\D+", "");
						return number.isEmpty() ? 0 : Integer.parseInt(number);
					}
				});
			}
		}

//Este metodo se encarga de crear el mapa
public void newGame() throws IlegalPositionException, IlegalMap, FileNotFoundException {	
	c = new Counter();
	am.clearActions();
	levelPos = 0;
	game(files[levelPos].getAbsolutePath());
}

//Este metodo se encarga de crear el mapa
public void game(String fileName) throws IlegalPositionException, IlegalMap, FileNotFoundException {
	level = o.newGame(fileName);
	levelName = files[levelPos].getName();
	GameObjectI wAux = of.createWarehouseMan(level);
	this.w = wAux;
	this.gs = of.createGoals(level);
}

//Metodo para guardar la partida
public boolean saveGame(File f) {
	boolean saved = false;
	if(f != null) {
		o.saveGame(level, (WarehouseMan) w, gs, am.getActions(), levelPos, levelName, f, c);
		saved = true;
	}
	return saved;
}

//Metodo para cargar una partida guardada
public boolean loadGame(File file) throws NumberFormatException, IlegalPositionException {
	boolean loaded = false;
	if(file != null) {
		Triplet<Integer, String, char[][]> p = o.loadGame((WarehouseMan) w, gs, am, file, c);
				
		if(p != null) {
			level = p.getValue2();
			levelName = p.getValue1();
			levelPos = p.getValue0();
			loaded = true;
		}
	}
	return loaded;
}

//Metodo para cargar una partida guardada
public boolean loadGameMF(File file) throws NumberFormatException, IlegalPositionException {
	boolean res = false;
	if(file != null) {
		Triplet<Integer, String, char[][]> p = o.loadGame((WarehouseMan) w, gs, am, file, c);
				
		if(p != null) {
			level = p.getValue2();
			levelName = p.getValue1();
			levelPos = p.getValue0();
			res = true;
		}
	}
	return res;
}

public void decrementBoxCounter() {
	c.setBoxCount(c.getBoxCount()-1);
}

public void decrementWMCounter() {
	c.setCount(c.getCount()-1);
}

public void decrementGlobalCounter() {
	c.setGlobalCount(c.getGlobalCount()-1);
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
	int globalCounter = c.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('u', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((UpAction) atc).move((WarehouseMan) w, gs, level, c);
	if(c.getGlobalCount()  == globalCounter)
		am.deleteAction(atc);
}

public void moveLeft() throws IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = c.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('l', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((LeftAction) atc).move((WarehouseMan) w, gs, level, c);
	if(c.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
}

public void moveDown() throws IlegalPositionException {
	char[][] levelClone = new char[this.level.length][];
	int globalCounter = c.getGlobalCount();
	cloneMap(levelClone);
	ActionI atc = af.createAction('d', this.w.getX(), this.w.getY(), levelClone);
	am.newAction(atc);
	((DownAction) atc).move((WarehouseMan) w, gs, level, c);
	if(c.getGlobalCount() == globalCounter)
		am.deleteAction(atc);
}

public void moveRight() throws IlegalPositionException {
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
public void restartLevel() throws FileNotFoundException, IlegalPositionException, IlegalMap {
	this.game(files[levelPos].getAbsolutePath());
	am.clearActions();
	c.setGlobalCount(c.getGlobalCount()-c.getBoxCount()-c.getCount());
	c.setBoxCount(0);
	c.setCount(0);
}

public int nextLevel() throws FileNotFoundException, IlegalPositionException, IlegalMap {
	int res = 0;
		
	if(levelPos != files.length-1) {
		levelPos += 1;
		this.game(files[levelPos].getAbsolutePath());
		am.clearActions();
		res = 1;
		c.setBoxCount(0);
		c.setCount(0);
	}
	else if(w != null) {
		levelPos = 0;
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
	
	public int getLevelPos() {
		return this.levelPos;
	}
	
	public String getLevelName() {
		return this.levelName;
	}
	
	public void setLevelPos(int level) {
		this.levelPos = level;
	}
	
	public Counter getCounter() {
		return this.c;
	}
	
	public void setCounter(Counter g) {
		this.c = g;
	}
	
	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}
}
