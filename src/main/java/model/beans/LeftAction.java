package model.beans;

import java.util.ArrayList;

import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;
import model.services.Action;
import model.services.GameObjectI;

public class LeftAction extends Action{
	boolean lastBox;
	
	public LeftAction(WarehouseMan w, char[][] mat) {
		super(w, mat);
		this.lastBox = false;
	}
	
	@Override
	public boolean isLastBox() {
		return lastBox;
	}

	@Override
	public void setLastBox(boolean lastBox) {
		this.lastBox = lastBox;
	}

	public char[][] move(WarehouseMan w, ArrayList<GameObjectI> gs, char[][] mat) throws WallException, IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		
		switch(mat[x][y-1]) {
			case '+':
				break;
			case '#':
			case '@': // Incluyendo el caso de que la caja está en una meta
				if (mat[x][y - 2] == '+' || mat[x][y - 2] == '#') {
					break;
				} else {
					mat[x][y - 1] = 'W'; // Mueve WarehouseMan
					mat[x][y] = '.'; // Vacía la posición anterior del WarehouseMan
					if (mat[x][y - 2] == '*') {
						mat[x][y - 2] = '@'; // Mueve la caja a una nueva meta
					} else {
						mat[x][y - 2] = '#'; // Mueve la caja a un espacio vacío
					}
					// Si la caja se movió desde una meta, restablecer la antigua posición de la caja a meta
					if (mat[x][y - 1] == '@') {
						mat[x][y - 1] = '*';
					}
					w.setY(y - 1);
					w.setBoxCount(w.getBoxCount() + 1);
					lastBox = true;
				}
				break;

			default:
				mat[x][y-1] = 'W';
				mat[x][y] = '.';
				w.setY(y-1);
				w.setCount(w.getCount() + 1);
				lastBox = false;
		}			
		
		w.setGlobalCount(w.getGlobalCount() + 1);
		for (GameObjectI g : gs) {
			goal = (mat[g.getX()][g.getY()] == '.');
			mat[g.getX()][g.getY()] = goal ? '*' : mat[g.getX()][g.getY()];
		}
		
		return mat;
	}
}

