package model.services;

import model.beans.WarehouseMan;

public class ActionsFactory implements ActionsFactoryI{
	
	public ActionI createAction(char direction, WarehouseMan w, char[][] mat) {
		switch(direction) {
			case 'l':
				return new LeftAction(w, mat);
			case 'r':
				return new RightAction(w, mat);
			case 'u':
				return new UpAction(w, mat);
			case 'd':
				return new DownAction(w, mat);
		}
		return null;
	}
}
