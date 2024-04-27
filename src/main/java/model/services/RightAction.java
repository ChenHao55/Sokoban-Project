package model.services;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;

public class RightAction implements ActionI {
	
	private WarehouseMan w;
	private char[][] mat;
	
	public RightAction(WarehouseMan w, char[][] mat) {
		this.w = w;
		this.mat = mat;
	}
	
	@Override
	public void printMap(char[][] level) {
		for(int i = 0; i<level.length; i++) {
			for(int j = 0; j<level[0].length; j++) {
				System.out.print(level[i][j]);
			}
			System.out.println();
		}
	}

	public char[][] move(WarehouseMan w, GoalPosition g, char[][] mat) throws WallException, IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		
		goal = (x == g.getX()) && (y == g.getY());
		
		switch(mat[x][y+1]) {
			case '+':
				break;
			case '#':
				if(mat[x][y+2] == '+') {
					break;
				} /*else if(mat[x][y+2] == '*') {
					mat[x][y+1] = 'W';
					mat[x][y+2] = '#';
					mat[x][y] = '.';
					return true;
				}*/ else {
					mat[x][y+1] = 'W';
					mat[x][y+2] = '#';
					mat[x][y] = '.';
					w.setY(y+1);
					w.setBoxCount(w.getBoxCount() + 1);
					break;
				}
			default:
				mat[x][y+1] = 'W';
				mat[x][y] = '.';
				w.setY(y+1);
				w.setCount(w.getCount() + 1);
		}
		
		mat[x][y] = goal ? '*' : mat[x][y];
		return mat;
	}

	public WarehouseMan getW() {
		return w;
	}

	public void setW(WarehouseMan w) {
		this.w = w;
	}

	public char[][] getMat() {
		return mat;
	}

	public void setMat(char[][] mat) {
		this.mat = mat;
	}
}

