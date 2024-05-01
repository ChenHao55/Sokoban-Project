package model.beans;

import java.util.ArrayList;

import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;
import model.services.Action;
import model.services.GameObjectI;

public class UpAction extends Action {
	boolean lastBox;
	
	public UpAction(WarehouseMan w, char[][] mat) {
		super(w, mat);
		this.lastBox = false;
	}

	@Override
	public boolean isLastBox() {
		return lastBox;
	}

	@Override
	public void setLastBox(boolean lastBox) {
		this.lastBox = lastBox;
	}

	public char[][] move(WarehouseMan w, ArrayList<GameObjectI> gs, char[][] mat) throws WallException, IlegalPositionException {
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
					lastBox = true;
					//return false;
					break;
				}
			default:
				mat[x-1][y] = 'W';
				mat[x][y] = '.';
				w.setX(x-1);
				w.setCount(w.getCount() + 1);
				lastBox = false;
		}			
		
		w.setGlobalCount(w.getGlobalCount() + 1);
		for (GameObjectI g : gs) {
			goal = (mat[g.getX()][g.getY()] == '.');
			mat[g.getX()][g.getY()] = goal ? '*' : mat[g.getX()][g.getY()];
		}
		
		return mat;
	}
}
