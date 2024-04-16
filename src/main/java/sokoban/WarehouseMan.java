package sokoban;

import exceptions.IlegalPositionException;

public class WarehouseMan implements ObjectInterface{
	private int x;
	private int y;
	
	public WarehouseMan(int x, int y) throws IlegalPositionException{
		if (x < 0 || y < 0){
			throw new IlegalPositionException("Invalid initial position");
		}
		this.x = x;
		this.y = y;
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
}
