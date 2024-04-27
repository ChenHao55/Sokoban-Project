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

	public boolean move(WarehouseMan w, GoalPosition g, char[][] mat, boolean undo) throws WallException, IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		boolean moved = false;

		if(!undo) {
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
						moved = true;
						mat[x][y+1] = 'W';
						mat[x][y+2] = '#';
						mat[x][y] = '.';
						w.setY(y+1);
						w.setBoxCount(w.getBoxCount() + 1);
						break;
					}
				default:
					moved = true;
					mat[x][y+1] = 'W';
					mat[x][y] = '.';
					w.setY(y+1);
					w.setCount(w.getCount() + 1);
			}			
		}
		else {
			switch(mat[x][y+1]) {
				case '#':
					/*else if(mat[x][y+2] == '*') {
						mat[x][y+1] = 'W';
						mat[x][y+2] = '#';
						mat[x][y] = '.';
						return true;
					}*/
						mat[x][y-1] = 'W';
						mat[x][y] = '#';
						mat[x][y+1] = '.';
						w.setY(y-1);
						w.setBoxCount(w.getBoxCount() -1);
						break;
				default:
					mat[x][y-1] = 'W';
					mat[x][y] = '.';
					w.setY(y-1);
					w.setCount(w.getCount() - 1);
			}
		}
		goal = (mat[g.getX()][g.getY()] == '.');
		mat[g.getX()][g.getY()] = goal ? '*' : mat[g.getX()][g.getY()];
		
		return moved;
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

