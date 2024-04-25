package model.services;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;

public class UpAction implements ActionI {

	public UpAction(WarehouseMan w, char[][] mat) {}

	public char[][] move(WarehouseMan w, GoalPosition g, char[][] mat) throws WallException, IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		
		goal = (x == g.getX()) && (y == g.getY());
		
		switch(mat[x-1][y]) {
			case '+':
				throw new WallException("Can't move, there is a wall");
			case '#':
				if(mat[x-2][y] == '+') {
					throw new WallException("Can't move, there is a wall");
				} /*else if(mat[x-2][y] == '*') {
					mat[x-1][y] = 'W';
					mat[x-2][y] = '#';
					mat[x][y] = '.';
					return true;
				}*/ else {
					mat[x-1][y] = 'W';
					mat[x-2][y] = '#';
					mat[x][y] = '.';
					w.setX(x-1);
					w.setBoxCount(w.getBoxCount() + 1);
					//return false;
					break;
				}
			default:
				mat[x-1][y] = 'W';
				mat[x][y] = '.';
				w.setX(x-1);
				w.setCount(w.getCount() + 1);
		}
		
		mat[x][y] = goal ? '*' : mat[x][y];
		
		return mat;
	}
}
