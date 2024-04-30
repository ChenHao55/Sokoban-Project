package model.services;

import java.util.ArrayList;

import model.beans.Box;
import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;

public class ObjectFactory implements ObjectFactoryI {

	@Override
	public GameObjectI createWarehouseMan(char[][] level) throws IlegalPositionException {
		for(int i=0; i<level.length; i++) {
			for(int j=0; j<level[0].length; j++) {
				if(level[i][j] == 'W') {
					return new WarehouseMan(i,j);
				}
			}
		}
		return null;
	}

	@Override
	public GameObjectI createBox(char[][] level) throws IlegalPositionException {
		for(int i=0; i<level.length; i++) {
			for(int j=0; j<level[0].length; j++) {
				if(level[i][j] == '#') {
					return new Box(i,j);
				}
			}
		}
		return null;
	}

	@Override
	public ArrayList<GameObjectI> createGoals(char[][] level) throws IlegalPositionException {
		
		ArrayList<GameObjectI> gs = new ArrayList<GameObjectI>();
		
		for(int i=0; i<level.length; i++) {
			for(int j=0; j<level[0].length; j++) {
				if(level[i][j] == '*') {
					gs.add((GameObjectI) new GoalPosition(i,j));
				}
			}
		}
		return gs;
	}
}
