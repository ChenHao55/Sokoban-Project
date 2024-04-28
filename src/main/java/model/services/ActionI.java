package model.services;

import java.util.ArrayList;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;

public interface ActionI {
	
	//Metodo para desplazar a la derecha, izq, arriba o abajo
	char[][] move(WarehouseMan w, ArrayList<GoalPosition> gs, char[][] mat) throws WallException, IlegalPositionException;
}
