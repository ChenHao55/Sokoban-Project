package model.beans;

import java.util.ArrayList;

import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;
import model.services.Action;
import model.services.GameObjectI;

public class LeftAction extends Action{
	
	public LeftAction(int x, int y, char[][] mat) {
		super(x, y, mat);
	}

	public char[][] move(WarehouseMan w, ArrayList<GameObjectI> gs, char[][] mat, Counter c) throws WallException, IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		
		switch(mat[x][y-1]) {
			case '+':
				break;
			case '#':
			case '@': // Incluyendo el caso de que la caja está en una meta
				if (mat[x][y - 2] == '+' || mat[x][y - 2] == '#' || mat[x][y-2] == '@') {
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
					c.setBoxCount(c.getBoxCount() + 1);
					this.setLastBox(true);
				}
				c.setGlobalCount(c.getGlobalCount() + 1);
				break;

			default:
				mat[x][y-1] = 'W';
				mat[x][y] = '.';
				w.setY(y-1);
				c.setCount(c.getCount() + 1);
				this.setLastBox(false);
				c.setGlobalCount(c.getGlobalCount() + 1);
		}			
		for (GameObjectI g : gs) {
			goal = (mat[g.getX()][g.getY()] == '.');
			mat[g.getX()][g.getY()] = goal ? '*' : mat[g.getX()][g.getY()];
		}
		
		return mat;
	}
}

