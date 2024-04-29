package model.beans;

import model.exceptions.IlegalPositionException;

public class GoalPosition {
	
	private int x;
	private int y;
	
	public GoalPosition(int x, int y) throws IlegalPositionException {
		if (x < 0 || y < 0){
			throw new IlegalPositionException("Invalid initial position");
		}
		this.x = x;
		this.y = y;
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
}
