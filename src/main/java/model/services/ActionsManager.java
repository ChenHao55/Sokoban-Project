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
	
	public void setActions(Stack<ActionI> actions) {
		this.actions = actions;
	}
	
	public void newAction(ActionI a) {
		actions.push(a);
	}

	public void deleteAction(ActionI a) {
		actions.remove(a);
	}
	
	public Action undo() {
		if(actions.isEmpty())
			return null;
		else
			return (Action) actions.pop();
	}

	public void clearActions() {
		actions.clear();
	}
}
