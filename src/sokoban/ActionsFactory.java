package sokoban;

public class ActionsFactory {
	
	Action createAction(char direction, WarehouseMan w, char[][] mat) {
		switch(direction) {
			case 'l':
				return new LeftAction(w, mat);
			case 'r':
				return new RightAction(w, mat);
			case 'u':
				return new UpAction(w, mat);
			case 'd':
				return new DownAction(w, mat);
		}
	}
}
