package sokoban;

import exceptions.WallException;

public class DownAction implements ActionI {

	public DownAction(WarehouseMan w, char[][] mat) {
		// TODO Auto-generated constructor stub
	}

	public boolean move(WarehouseMan w, char[][] mat) throws WallException {
		int x = w.getX();
		int y = w.getY();
		
		switch(mat[x+1][y]) {
			case '+':
				throw new WallException("Can't move, there is a wall");
			case '#':
				if(mat[x+2][y] == '+') {
					throw new WallException("Can't move, there is a wall");
				} /*else if(mat[x+2][y]=='*') {
					mat[x+1][y] = 'W';
					mat[x+2][y] = '#';
					mat[x][y] = '.';
					return true;
				}*/ else {
					mat[x+1][y] = 'W';
					mat[x+2][y] = '#';
					mat[x][y] = '.';
					w.setX(x+1);
					w.setY(y);
				}
			case '.':
				mat[x+1][y] = 'W';
				mat[x][y] = '.';
				w.setX(x+1);
				w.setY(y);
		}
		return false;
	}

}
