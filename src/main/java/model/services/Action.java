package model.services;

import model.beans.WarehouseMan;

public class Action implements ActionI {
	
	private WarehouseMan w;
	private char[][] mat;
	
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
}
