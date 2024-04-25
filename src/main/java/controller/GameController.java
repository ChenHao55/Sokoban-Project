package controller;

import java.io.FileNotFoundException;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.exceptions.WallException;
import model.services.ActionI;
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
	
	public GameController(MainFrame mf, MapPanel mp) {
		this.mf = mf;
		this.mp = mp;
	}
	
	//Aqui añadiria metodos como cambiar de nivel, una vez superado...
	
	//METODOS PARA MOVER EL PERSONAJE
	public void moveUp() {
		atc = new UpAction(w, level);
		try {
			atc.move(w, g, level);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}

	public void moveLeft() {
		atc = new LeftAction(w, level);
		try {
			atc.move(w, g, level);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}

	public void moveDown() {
		atc = new DownAction(w, level);
		try {
			atc.move(w, g, level);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	public void moveRight() {
		atc = new RightAction(w, level);
		try {
			atc.move(w, g, level);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	//Este metodo se encarga de crear el mapa desde el principio
	public void createMap(String fileName) {
		try {
			level = cm.createMap(fileName, w, g);
			mp.createMap(level);
			mf.paintMap(mp);
		} catch (FileNotFoundException | IlegalPositionException | ObjectPositionNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Este metodo se encarga de actualizar el mapa despues de los movimientos
	private void updateMap() {
		try {
			mp.createMap(level);
			mp.repaint();
			mf.paintMap(mp);
		} catch (IlegalPositionException | ObjectPositionNotFoundException e) {
			e.printStackTrace();
		}
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
