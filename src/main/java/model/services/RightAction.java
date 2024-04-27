package model.services;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;

public class RightAction implements ActionI {
	
	private WarehouseMan w;
	private char[][] mat;
	private boolean movedBox;

	
	public RightAction(WarehouseMan w, char[][] mat) {
		this.w = w;
		this.mat = mat;
		movedBox =  false;
	}

	public boolean move(WarehouseMan w, GoalPosition g, char[][] mat) throws WallException, IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		boolean moved = false;

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
					this.movedBox = true;
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

		goal = (mat[g.getX()][g.getY()] == '.');
		mat[g.getX()][g.getY()] = goal ? '*' : mat[g.getX()][g.getY()];
		
		return moved;
	}
	
	public void undo(WarehouseMan w, GoalPosition g, char[][] mat, boolean movedBox) throws WallException, IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		
		if(mat[x][y+1] == '#' && movedBox) {
			mat[x][y-1] = 'W';
			mat[x][y] = '#';
			mat[x][y+1] = '.';
			w.setY(y-1);
			w.setBoxCount(w.getBoxCount() -1);
		}
		else {
			mat[x][y-1] = 'W';
			mat[x][y] = '.';
			w.setY(y-1);
			w.setCount(w.getCount() - 1);
		}
		
		goal = (mat[g.getX()][g.getY()] == '.');
		mat[g.getX()][g.getY()] = goal ? '*' : mat[g.getX()][g.getY()];
	}

	public boolean isMovedBox() {
		return movedBox;
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

