package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
	private ArrayList<GoalPosition> gs = new ArrayList<GoalPosition>();
	private ActionsManager am = new ActionsManager();
	
	public GameController(MainFrame mf, MapPanel mp) {
		this.mf = mf;
		this.mp = mp;
	}
	
	//Aqui añadiria metodos como cambiar de nivel, una vez superado...
	
	public void undoMovement() throws IlegalPositionException, ObjectPositionNotFoundException {
		ActionI aux;
		aux = am.undo();
		
		if(aux != null) {
			if(aux instanceof UpAction)
				atc = new UpAction(w, level);
			else if(aux instanceof DownAction)
				atc = new DownAction(w, level);
			else if(aux instanceof LeftAction)
				atc = new LeftAction(w, level);
			else if(aux instanceof RightAction) 
				atc = new RightAction(w, level);

			try {
				atc.undo(w, gs, level, aux.isMovedBox());
				updateMap();
			} catch (WallException | IlegalPositionException e) {
				e.printStackTrace();
			}
		}
	}
	
	//METODOS PARA MOVER EL PERSONAJE
	public void moveUp() throws ObjectPositionNotFoundException {
		atc = new UpAction(w, level);
		try {
			if(atc.move(w, gs, level))
				am.newAction(atc);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}

	public void moveLeft() throws ObjectPositionNotFoundException {
		atc = new LeftAction(w, level);
		try {
			if(atc.move(w, gs, level))
				am.newAction(atc);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}

	public void moveDown() throws ObjectPositionNotFoundException {
		atc = new DownAction(w, level);
		try {
			if(atc.move(w, gs, level))
				am.newAction(atc);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	public void moveRight() throws ObjectPositionNotFoundException {
		atc = new RightAction(w, level);
		try {
			if(atc.move(w, gs, level))
				am.newAction(atc);
			updateMap();
		} catch (WallException | IlegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	//Este metodo se encarga de crear el mapa desde el principio
	public void createMap(String fileName) throws IlegalPositionException, ObjectPositionNotFoundException {
		try {
			level = cm.createMap(fileName, w, gs);
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
