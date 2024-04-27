package model.services;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;

public class UpAction implements ActionI {
	
	private WarehouseMan w;
	private char[][] mat;

	public UpAction(WarehouseMan w, char[][] mat) {
		this.w = w;
		this.mat = mat;
	}

	public boolean move(WarehouseMan w, GoalPosition g, char[][] mat, boolean undo) throws WallException, IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		boolean moved = false;
		
		if(!undo) {
			switch(mat[x-1][y]) {
				case '+':
					break;
				case '#':
					if(mat[x-2][y] == '+') {
						break;
					} /*else if(mat[x+2][y]=='*') {
						mat[x+1][y] = 'W';
						mat[x+2][y] = '#';
						mat[x][y] = '.';
						return true;
					}*/ else {
						moved = true;
						mat[x-1][y] = 'W';
						mat[x-2][y] = '#';
						mat[x][y] = '.';
						w.setX(x-1);
						w.setBoxCount(w.getBoxCount() + 1);
						//return false;
						break;
					}
				default:
					moved = true;
					mat[x-1][y] = 'W';
					mat[x][y] = '.';
					w.setX(x-1);
					w.setCount(w.getCount() + 1);
			}			
		}
		else {
			switch(mat[x-1][y]) {
				case '#':
					/*else if(mat[x-2][y] == '*') {
						mat[x-1][y] = 'W';
						mat[x-2][y] = '#';
						mat[x][y] = '.';
						return true;
					}*/
						mat[x+1][y] = 'W';
						mat[x][y] = '#';
						mat[x-1][y] = '.';
						w.setX(x+1);
						w.setBoxCount(w.getBoxCount() - 1);
						//return false;
						break;
				default:
					mat[x+1][y] = 'W';
					mat[x][y] = '.';
					w.setX(x+1);
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
