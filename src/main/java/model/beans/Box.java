package model.beans;

import model.exceptions.IlegalPositionException;
import model.services.GameObject;

public class Box extends GameObject{
	
	public Box(int x, int y) throws IlegalPositionException{
		super(x,y);
	}
}
