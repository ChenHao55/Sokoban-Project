package model.services;

import java.util.ArrayList;

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
		
		WarehouseMan warehouseMan = null;
		for(int i=0; i<level.length; i++) {
			for(int j=0; j<level[i].length; j++) {
				if(level[i][j] == 'W') {
					if(warehouseMan != null) {
						throw new IlegalPositionException("There were found two WarehouseMan");
					}
					warehouseMan = new WarehouseMan(i,j);
				}
			}
		}
		
		if(warehouseMan == null) {throw new IlegalPositionException("No warehouseMan found");}
		return warehouseMan;
	}

	@Override
	public ArrayList<GameObjectI> createGoals(char[][] level) throws IlegalPositionException, IlegalMap {
		
		if(level.length == 0 || level[0].length == 0) {throw new IlegalMap(msg);}
		
		int boxGoalN = 0;
		ArrayList<GameObjectI> gs = new ArrayList<>();
		
		for(int i=0; i<level.length; i++) {
			for(int j=0; j<level[firstCol].length; j++) {
				if(level[i][j] == '*') {
					boxGoalN ++;
					gs.add(new GoalPosition(i,j));
				}
				else if(level[i][j] == '#') {
					boxGoalN --;
				}
			}
		}
		
		// Return null the number of boxes and goals arent the same
		if(boxGoalN == 0)
			return gs;
		else
			return new ArrayList<>();
	}
}
