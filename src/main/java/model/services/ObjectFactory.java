package model.services;

import java.util.ArrayList;

import model.beans.Box;
import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;

public class ObjectFactory implements ObjectFactoryI {
	
	private String msg = "Incorrect map dimensions";
	private int firstCol = 0;

	@Override
	public GameObjectI createWarehouseMan(char[][] level) throws IlegalPositionException, IlegalMap {
	
		if(level.length == 0 || level[0].length == 0) {throw new IlegalMap(msg);}
		
		for(int i=0; i<level.length; i++) {
			for(int j=0; j<level[firstCol].length; j++) {
				if(level[i][j] == 'W') {
					return new WarehouseMan(i,j);
				}
			}
		}
		return null;
	}

	@Override
	public GameObjectI createBox(char[][] level) throws IlegalPositionException, IlegalMap {
		
		if(level.length == 0 || level[0].length == 0) {throw new IlegalMap(msg);}
		
		for(int i=0; i<level.length; i++) {
			for(int j=0; j<level[firstCol].length; j++) {
				if(level[i][j] == '#') {
					return new Box(i,j);
				}
			}
		}
		return null;
	}

	@Override
	public ArrayList<GameObjectI> createGoals(char[][] level) throws IlegalPositionException, IlegalMap {
		
		if(level.length == 0 || level[0].length == 0) {throw new IlegalMap(msg);}
		
		ArrayList<GameObjectI> gs = new ArrayList<GameObjectI>();
		
		for(int i=0; i<level.length; i++) {
			for(int j=0; j<level[firstCol].length; j++) {
				if(level[i][j] == '*') {
					gs.add((GameObjectI) new GoalPosition(i,j));
				}
			}
		}
		return gs;
	}
}
