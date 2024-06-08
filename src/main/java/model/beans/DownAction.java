package model.beans;

import java.util.List;

import model.exceptions.IlegalPositionException;
import model.services.Action;
import model.services.GameObjectI;

public class DownAction extends Action {
	
	private static final long serialVersionUID = 1L;

	public DownAction(int x, int y, char[][] mat) {
		super(x, y, mat);
	}

	public char[][] move(WarehouseMan w, List<GameObjectI> gs, char[][] mat, Counter c) throws IlegalPositionException {
		int x = w.getX();
		int y = w.getY();
		boolean goal = false;
		switch(mat[x+1][y]) {
			case '+':
				// Bloqueado por un muro
				break;
			case '#':
			case '@': // Añadido para manejar el caso de que una caja esté actualmente en una meta
				if(mat[x+2][y] == '+' || mat[x+2][y] == '#' || mat[x+2][y] == '@') {
					// No se puede mover la caja porque está bloqueada
					break;
				} else {
					// Mover WarehouseMan a la posición de la caja
					mat[x+1][y] = 'W';
					// Restablecer la posición original del WarehouseMan
					mat[x][y] = '.';
					// Mover la caja a la nueva posición
					if(mat[x+2][y] == '*') {
						mat[x+2][y] = '@'; // La caja se mueve a otra meta
					} else {
						mat[x+2][y] = '#'; // La caja se mueve a un espacio vacío
					}
					// Si la caja se movió desde una meta, restablecer la antigua posición de la caja a meta
					if(mat[x+1][y] == '@') {
						mat[x+1][y] = '*';
					}
					// Actualizar la posición del WarehouseMan
					w.setX(x+1);
					c.setBoxCount(c.getBoxCount() + 1);
					this.setLastBox(true);
				}
				c.setGlobalCount(c.getGlobalCount() + 1);
				break;
			default:
				// Mover WarehouseMan si no hay caja
				mat[x+1][y] = 'W';
				mat[x][y] = '.';
				w.setX(x+1);
				c.setCount(c.getCount() + 1);
				this.setLastBox(false);
				c.setGlobalCount(c.getGlobalCount() + 1);
		}

		// Asegurar que todas las metas estén correctamente marcadas después del movimiento
		for (GameObjectI g : gs) {
			goal = (mat[g.getX()][g.getY()] == '.');
			mat[g.getX()][g.getY()] = goal ? '*' : mat[g.getX()][g.getY()];
		}

		return mat;
	}

}
