package controller;

import java.io.FileNotFoundException;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.exceptions.WallException;
import model.services.ActionI;
import model.services.ActionsManager;
import model.services.CreateMap;
import model.services.CreateMapI;
import model.services.DownAction;
import model.services.LeftAction;
import model.services.RightAction;
import model.services.UpAction;
import view.MainFrame;
import view.MapPanel;

public class GameController {
	
	private MainFrame mf;
	private MapPanel mp;
	private CreateMapI cm = new CreateMap();
	private ActionI atc;
	private char[][] level;
	private WarehouseMan w = new WarehouseMan();
	private GoalPosition g = new GoalPosition();
	private ActionsManager am = new ActionsManager();
	
	public GameController(MainFrame mf, MapPanel mp) {
		this.mf = mf;
		this.mp = mp;
	}
	
	//Aqui añadiria metodos como cambiar de nivel, una vez superado...
	
	public void undoMovement() throws IlegalPositionException, ObjectPositionNotFoundException {
		char[][] aux;
		aux = am.undo();
		this.level = aux == null ? this.level : aux;
		updateMap();
	}
	
	//METODOS PARA MOVER EL PERSONAJE
	public void moveUp() throws ObjectPositionNotFoundException {
		atc = new UpAction(w, level);
		am.newAction(atc);
		try {
			atc.move(w, g, level);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}

	public void moveLeft() throws ObjectPositionNotFoundException {
		atc = new LeftAction(w, level);
		am.newAction(atc);
		try {
			atc.move(w, g, level);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}

	public void moveDown() throws ObjectPositionNotFoundException {
		atc = new DownAction(w, level);
		am.newAction(atc);
		try {
			atc.move(w, g, level);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	public void moveRight() throws ObjectPositionNotFoundException {
		atc = new RightAction(w, level);
		am.newAction(atc);
		try {
			atc.move(w, g, level);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	//Este metodo se encarga de crear el mapa desde el principio
	public void createMap(String fileName) throws IlegalPositionException, ObjectPositionNotFoundException {
		try {
			level = cm.createMap(fileName, w, g);
			mp.createMap(level);
			mf.paintMap(mp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Este metodo se encarga de actualizar el mapa despues de los movimientos
	private void updateMap() throws IlegalPositionException, ObjectPositionNotFoundException {
		mp.createMap(level);
		mf.paintMap(mp);
	}
	
	//GETTERS Y SETTERS
	public WarehouseMan getW() {
		return this.w;
	}
	
	public void setW(WarehouseMan w) {
		this.w = w;
	}
	
	public GoalPosition getG() {
		return this.g;
	}
	
	public void setG(GoalPosition g) {
		this.g = g;
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
