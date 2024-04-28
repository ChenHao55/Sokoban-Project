package model.services;

import java.util.ArrayList;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;

public class UpAction extends Action {
	
	private WarehouseMan w;
	private char[][] mat;


	public UpAction(WarehouseMan w, char[][] mat) {
		super(w, mat);
	}

	public char[][] move(WarehouseMan w, ArrayList<GoalPosition> gs, char[][] mat) throws WallException, IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		
		switch(mat[x-1][y]) {
			case '+':
				break;
			case '#':
				if(mat[x-2][y] == '+' || mat[x-2][y] == '#') {
					break;
				} /*else if(mat[x+2][y]=='*') {
					mat[x+1][y] = 'W';
					mat[x+2][y] = '#';
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
		
		for (GoalPosition g : gs) {
			goal = (mat[g.getX()][g.getY()] == '.');
			mat[g.getX()][g.getY()] = goal ? '*' : mat[g.getX()][g.getY()];
		}
		
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
