package sokoban;

import java.util.Stack;

import javax.swing.Action;

public class ActionsManager{
	private Stack<Action> actions;
	ActionsFactory aFactory;
	
	public ActionsManager() {
		actions = new Stack<Action>();
		aFactory = new ActionsFactory();
	}
	
	public Stack<Action> getActions() {
		return actions;
	}
	
	public void newAction(String direction, WarehouseMan w, char[][] mat) {
		actions.push(aFactory.createAction(direction, w, mat));
	}

	public void undo() {
		actions.pop();
	}
}