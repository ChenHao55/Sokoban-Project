package sokoban;

import exceptions.WallException;
import interfaces.ActionI;

public class LeftAction implements ActionI{

	public LeftAction(WarehouseMan w, char[][] mat) {
		// TODO Auto-generated constructor stub
	}

	public boolean move(WarehouseMan w, GoalPosition g, char[][] mat) throws WallException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		
		goal = (x == g.getX()) && (y == g.getY());
		
		switch(mat[x][y-1]) {
			case '+':
				throw new WallException("Can't move, there is a wall");
			case '#':
				if(mat[x][y-2] == '+') {
					throw new WallException("Can't move, there is a wall");
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
		
		mat[x][y] = goal ? '*' : mat[x][y];
		
		return false;
	}

}
