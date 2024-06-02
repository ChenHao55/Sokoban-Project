package model.beans;

import model.exceptions.IlegalPositionException;
import model.services.GameObject;

public class WarehouseMan extends GameObject {
	
	private int count;
	private int boxCount;
	private int globalCount;
	
	public WarehouseMan(int x, int y) throws IlegalPositionException {
		super(x,y);
		this.boxCount = 0;
		this.count = 0;
	}
	
	@Override
	public int getCount() {
		return count;
	}
	
	@Override
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public int getBoxCount() {
		return boxCount;
	}
	
	@Override
	public void setBoxCount(int boxCount) {
		this.boxCount = boxCount;
	}
	
	@Override
	public int getGlobalCount() {
		return globalCount;
	}
	
	@Override
	public void setGlobalCount(int globalCount) {
		this.globalCount = globalCount;
	}
}
