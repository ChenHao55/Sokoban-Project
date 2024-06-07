package model.services;

import model.exceptions.IlegalPositionException;

public class GameObject implements GameObjectI {
	
	private int x;
	private int y;
	
	private int count = 0;
	private int boxCount = 0;
	private int globalCount = 0;
	
	public GameObject(int x, int y) throws IlegalPositionException {
		if(x < 0 || y < 0) {
			throw new IlegalPositionException("Invalid position");
		}
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public void setX(int x) throws IlegalPositionException {
		if (x < 0){
			throw new IlegalPositionException("Invalid initial position");
		}
		this.x = x;
	}

	@Override
	public void setY(int y) throws IlegalPositionException {
		if (y < 0){
			throw new IlegalPositionException("Invalid initial position");
		}
		this.y = y;
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
