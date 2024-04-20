package interfaces;

import exceptions.WallException;
import sokoban.GoalPosition;
import sokoban.WarehouseMan;

public interface ActionI {

	//Metodo para desplazar a la derecha, izq, arriba o abajo
	boolean move(WarehouseMan w, GoalPosition g, char[][] mat) throws WallException;
}
