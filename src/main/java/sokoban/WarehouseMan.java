package sokoban;

import exceptions.IlegalPositionException;
import interfaces.ObjectInterface;

public class WarehouseMan implements ObjectInterface{
	private int x;
	private int y;
	private int count;
	private int boxCount;
	
	public WarehouseMan(int x, int y) throws IlegalPositionException{
		if (x < 0 || y < 0){
			throw new IlegalPositionException("Invalid initial position");
		}
		this.x = x;
		this.y = y;
		this.count = 0;
		this.boxCount = 0;;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
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
