package sokoban;

import exceptions.WallException;

public class LeftAction implements ActionI{

	public LeftAction(WarehouseMan w, char[][] mat) {
		// TODO Auto-generated constructor stub
	}

	public boolean move(WarehouseMan w, char[][] mat) throws WallException {
		int x = w.getX();
		int y = w.getY();
		
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
					w.setX(x);
					w.setY(y-1);
				}
			case '.':
				mat[x][y-1] = 'W';
				mat[x][y] = '.';
				w.setX(x);
				w.setY(y-1);
		}
		return false;
	}

}
