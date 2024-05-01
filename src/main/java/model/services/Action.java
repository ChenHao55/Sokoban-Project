package model.services;

import model.beans.WarehouseMan;

public class Action implements ActionI {
	
	private WarehouseMan w;
	private char[][] mat;
	boolean lastBox = false;
	
	public Action(WarehouseMan w, char[][] level) {
		this.setMat(level);
		this.setW(w);
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
	
	@Override
	public boolean isLastBox() {
		return lastBox;
	}
	
	@Override
	public void setLastBox(boolean lastBox) {
		this.lastBox = lastBox;
	}
}
