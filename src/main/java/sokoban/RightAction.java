package sokoban;

import exceptions.WallException;

public class RightAction implements ActionI {
	
	public RightAction(WarehouseMan w, char[][] mat) {}

	public void move(WarehouseMan w, char[][] mat) throws WallException {
		int x = w.getX();
		int y = w.getY();
		
		switch(mat[x+1][y+1]) {
			case '+':
				throw new WallException("Can't move, there is a wall");
			case '#':
				if(mat[x+2][y+2] == '+') {
					throw new WallException("Can't move, there is a wall");
				} else {
					mat[x+1][y+1] = 'W';
					mat[x+2][y+2] = '#';
					mat[x][y] = '.';
					w.setX(x+1);
					w.setY(y+1);
				}
			case '.':
				mat[x+1][y+1] = 'W';
				mat[x][y] = '.';
				w.setX(x+1);
				w.setY(y+1);
		}
	}
}
