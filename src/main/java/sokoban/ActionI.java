package sokoban;

import exceptions.WallException;

public interface ActionI {

	//Metodo para desplazar a la derecha, izq, arriba o abajo
	void move(WarehouseMan w, char[][] mat) throws WallException;
}
