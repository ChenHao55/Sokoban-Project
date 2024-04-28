package model.services;

import java.util.ArrayList;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;

public interface ActionI {

	//Method to see if the action moved a box
	boolean isMovedBox();
	
	//Metodo para desplazar a la derecha, izq, arriba o abajo
	boolean move(WarehouseMan w, ArrayList<GoalPosition> gs, char[][] mat) throws WallException, IlegalPositionException;
	
	//Method to undo the action
	void undo(WarehouseMan w, ArrayList<GoalPosition> gs, char[][] mat, boolean movedBox) throws WallException, IlegalPositionException;
}
