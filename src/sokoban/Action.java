package sokoban;

public interface Action {

	//Metodo para desplazar a la derecha, izq, arriba o abajo
	void move(WarehouseMan w, char[][] mat) throws WallException;
}
