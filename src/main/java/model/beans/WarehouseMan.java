package model.beans;

import model.exceptions.IlegalPositionException;
import model.services.GameObject;

public class WarehouseMan extends GameObject {
	
	private int count;
	private int boxCount;
	
	public WarehouseMan(int x, int y) throws IlegalPositionException {
		super(x,y);
		this.boxCount = 0;
		this.count = 0;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getBoxCount() {
		return boxCount;
	}

	public void setBoxCount(int boxCount) {
		this.boxCount = boxCount;
	}
}
