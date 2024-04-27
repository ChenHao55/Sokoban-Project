package model.services;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;

public interface ActionI {

	//Metodo para desplazar a la derecha, izq, arriba o abajo
	boolean move(WarehouseMan w, GoalPosition g, char[][] mat, boolean undo) throws WallException, IlegalPositionException;
}
