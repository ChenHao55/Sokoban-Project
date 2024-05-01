package model.beans;

import java.util.ArrayList;

import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;
import model.services.Action;
import model.services.GameObjectI;

public class RightAction extends Action {
	boolean lastBox;
	
	public RightAction(WarehouseMan w, char[][] mat) {
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
					w.setBoxCount(w.getBoxCount() + 1);
					lastBox = true;
				}
				break;
			default:
				mat[x][y+1] = 'W';
				mat[x][y] = '.';
				w.setY(y+1);
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
