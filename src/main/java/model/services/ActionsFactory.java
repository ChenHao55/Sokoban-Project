package model.services;

import model.beans.DownAction;
import model.beans.LeftAction;
import model.beans.RightAction;
import model.beans.UpAction;
import model.exceptions.IlegalPositionException;

public class ActionsFactory implements ActionsFactoryI{
	
	public ActionI createAction(char direction, int x, int y, char[][] mat) throws IlegalPositionException {
		
		switch(direction) {
			case 'l':
				return new LeftAction(x, y, mat);
			case 'r':
				return new RightAction(x, y, mat);
			case 'u':
				return new UpAction(x, y, mat);
			case 'd':
				return new DownAction(x, y, mat);
			default:
				return null;
		}
	}
}
