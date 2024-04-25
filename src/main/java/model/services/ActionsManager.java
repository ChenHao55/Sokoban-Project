package model.services;


import java.util.Stack;

import javax.swing.Action;

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
		return actions.pop();
	}
}