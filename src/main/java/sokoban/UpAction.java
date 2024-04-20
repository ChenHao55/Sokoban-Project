package sokoban;

import exceptions.WallException;
import interfaces.ActionI;

public class UpAction implements ActionI {

	public UpAction(WarehouseMan w, char[][] mat) {
		// TODO Auto-generated constructor stub
	}

	public boolean move(WarehouseMan w, GoalPosition g, char[][] mat) throws WallException {
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
		
		return false;
	}

}
