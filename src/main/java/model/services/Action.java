package model.services;

import java.util.ArrayList;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;

public class Action implements ActionI {
	
	private WarehouseMan w;
	private char[][] mat;
	private ActionI a;
	
	public Action(WarehouseMan w, char[][] level) {
		this.setMat(level);
		this.setW(w);
	}
	
	@Override
	public char[][] move(WarehouseMan w, ArrayList<GoalPosition> gs, char[][] mat)
			throws WallException, IlegalPositionException {
		
		System.out.println(this instanceof UpAction);
		if(this instanceof UpAction) {
			a = new UpAction(w, mat);
			a.move(w, gs, mat);
		} else if(this instanceof DownAction) {
			a = new DownAction(w, mat);
			a.move(w, gs, mat);
		} else if(this instanceof LeftAction) {
			a = new LeftAction(w, mat);
			a.move(w, gs, mat);
		} else if(this instanceof RightAction) {
			a = new RightAction(w, mat);
			a.move(w, gs, mat);
		}
		
		return mat;
	}

	public WarehouseMan getW() {
		return w;
	}

	public void setW(WarehouseMan w) {
		this.w = w;
	}

	public char[][] getMat() {
		return mat;
	}

	public void setMat(char[][] mat) {
		this.mat = mat;
	}

}
