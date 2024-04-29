package model.beans;

import model.exceptions.IlegalPositionException;

public class WarehouseMan implements Cloneable{
	private int x;
	private int y;
	private int count;
	private int boxCount;
	
	public WarehouseMan() {
		this.count = 0;
		this.boxCount = 0;;
	}
	
	public WarehouseMan clone() {
		try {
			return (WarehouseMan) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) throws IlegalPositionException {
		if (x < 0){
			throw new IlegalPositionException("Invalid initial position");
		}
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) throws IlegalPositionException {
		if (y < 0){
			throw new IlegalPositionException("Invalid initial position");
		}
		this.y = y;
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
