package model.services;

import java.util.Stack;

public class ActionsManager{
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

	public ActionI undo() {
		if(this.actions.empty()) {return null;}
		ActionI a = actions.pop();
		return a;
	}
}
