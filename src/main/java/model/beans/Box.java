package model.beans;

import model.exceptions.IlegalPositionException;
import model.services.GameObject;

public class Box extends GameObject{

	private boolean onGoal = false;
	public Box(int x, int y) throws IlegalPositionException{
		super(x,y);
	}

	public boolean isOnGoal() {
		return onGoal;
	}

	public void setOnGoal(boolean onGoal) {
		this.onGoal = onGoal;
	}
}
