package model.services;

import java.util.ArrayDeque;
import java.util.Deque;

public class ActionsManager implements ActionsManagerI {
	private Deque<ActionI> actions;
	ActionsFactory aFactory;
	
	public ActionsManager() {
		actions = new ArrayDeque<>();
		aFactory = new ActionsFactory();
	}
	
	public Deque<ActionI> getActions() {
		return actions;
	}
	
	public void setActions(Deque<ActionI> actions) {
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
