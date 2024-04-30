package model.beans;

import java.util.ArrayList;

import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;
import model.services.Action;
import model.services.GameObjectI;

public class LeftAction extends Action{

	public LeftAction(WarehouseMan w, char[][] mat) {
		super(w, mat);
	}

	public char[][] move(WarehouseMan w, ArrayList<GameObjectI> gs, char[][] mat) throws WallException, IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		
		switch(mat[x][y-1]) {
			case '+':
				break;
			case '#':
				if(mat[x][y-2] == '+' || mat[x][y-2] == '#') {
					break;
				} /*else if(mat[x][y-2] == '*') {
					mat[x][y-1] = 'W';
					mat[x][y-2] = '#';
					mat[x][y] = '.';
					return true;
				}*/ else {
					mat[x][y-1] = 'W';
					mat[x][y-2] = '#';
					mat[x][y] = '.';
					w.setY(y-1);
					w.setBoxCount(w.getBoxCount() + 1);
					break;
				}
			default:
				mat[x][y-1] = 'W';
				mat[x][y] = '.';
				w.setY(y-1);
				w.setCount(w.getCount() + 1);
		}			

		for (GameObjectI g : gs) {
			goal = (mat[g.getX()][g.getY()] == '.');
			mat[g.getX()][g.getY()] = goal ? '*' : mat[g.getX()][g.getY()];
		}
		
		return mat;
	}
}

