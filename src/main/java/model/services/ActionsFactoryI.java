package model.services;

import model.beans.WarehouseMan;

public interface ActionsFactoryI {
	
	//Metodo para crear una nueva accion (Patron Metodo de Fabrica)
	ActionI createAction(char direction, WarehouseMan w, char[][] mat);
}
