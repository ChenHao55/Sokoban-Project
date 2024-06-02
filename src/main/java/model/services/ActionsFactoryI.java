package model.services;

import model.exceptions.IlegalPositionException;

public interface ActionsFactoryI {
	
	//Metodo para crear una nueva accion (Patron Metodo de Fabrica)
	ActionI createAction(char direction, int x, int y, char[][] mat) throws IlegalPositionException;
}
