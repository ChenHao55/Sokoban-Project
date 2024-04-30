package model.beans;

import model.exceptions.IlegalPositionException;
import model.services.GameObject;

public class GoalPosition extends GameObject{
	
	public GoalPosition(int x, int y) throws IlegalPositionException {
		super(x,y);
	}
}
