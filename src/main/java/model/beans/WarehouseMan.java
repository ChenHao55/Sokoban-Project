package model.beans;

import model.exceptions.IlegalPositionException;
import model.services.GameObject;

public class WarehouseMan extends GameObject {
	
	public WarehouseMan(int x, int y) throws IlegalPositionException {
		super(x,y);
	}

}
