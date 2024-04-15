package sokoban;

public class DownAction implements Action {

	public DownAction(WarehouseMan w, char[][] mat) {
		// TODO Auto-generated constructor stub
	}

	public void move(WarehouseMan w, char[][] mat) throws WallException {
		int x = w.getX();
		int y = w.getY();
		
		switch(mat[x+mat[0].length][y+mat[0].length]) {
			case '+':
				throw new WallException("Can't move, there is a wall");
			case '#':
				if(mat[x+(mat[0].length+1)][y+(mat[0].length+1)] == '+') {
					throw new WallException("Can't move, there is a wall");
				} else {
					mat[x+mat[0].length][y+mat[0].length] = 'W';
					mat[x+(mat[0].length+1)][y+(mat[0].length+1)] = '#';
					mat[x][y] = '.';
					w.setX(x+mat[0].length);
					w.setY(y+mat[0].length);
				}
			case '.':
				mat[x+mat[0].length][y+mat[0].length] = 'W';
				mat[x][y] = '.';
				w.setX(x+mat[0].length);
				w.setY(y+mat[0].length);
		}
	}

}
