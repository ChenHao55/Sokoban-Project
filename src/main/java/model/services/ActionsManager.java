package model.services;

import java.util.Stack;

public class ActionsManager implements ActionsManagerI {
	private Stack<ActionI> actions;
	ActionsFactory aFactory;
	
	public ActionsManager() {
		actions = new Stack<ActionI>();
		aFactory = new ActionsFactory();
	}
	
	public Stack<ActionI> getActions() {
		return actions;
	}
	
	public void newAction(ActionI a) {
		actions.push(a);
	}

	public Action undo() {
		return (Action) actions.pop();
	}
}
