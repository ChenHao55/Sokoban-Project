package model.beans;

import java.util.List;

import model.exceptions.IlegalPositionException;
import model.services.Action;
import model.services.GameObjectI;

public class RightAction extends Action {
	
	private static final long serialVersionUID = 1L;

	public RightAction(int x, int y, char[][] mat) {
		super(x, y, mat);
	}
	
	public char[][] move(WarehouseMan w, List<GameObjectI> gs, char[][] mat, Counter c) throws IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;

		switch(mat[x][y+1]) {
			case '+':
				break;
			case '#':
			case '@': // Manejar el caso de que una caja esté en una meta
				if(mat[x][y+2] == '+' || mat[x][y+2] == '#'|| mat[x][y+2] == '@') {
					break;
				} else {
					mat[x][y+1] = 'W';
					if(mat[x][y+2] == '*') {
						mat[x][y+2] = '@'; // Mover la caja a otra meta
					} else {
						mat[x][y+2] = '#'; // Mover la caja a un espacio vacío
					}
					if(mat[x][y+1] == '@') {
						mat[x][y+1] = '*'; // Restablecer la antigua posición de la caja a meta si era una meta
					}
					mat[x][y] = '.';
					w.setY(y+1);
					c.setBoxCount(c.getBoxCount() + 1);
					this.setLastBox(true);
				}
				c.setGlobalCount(c.getGlobalCount() + 1);
				break;
			default:
				mat[x][y+1] = 'W';
				mat[x][y] = '.';
				w.setY(y+1);
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
