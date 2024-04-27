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

	public char[][] undo() {
		if(this.actions.empty()) {return null;}
		ActionI a = actions.pop();
		if(a instanceof DownAction) {
			return ((DownAction) a).getMat();
		} else if(a instanceof UpAction) {
			return ((UpAction) a).getMat();
		} else if(a instanceof LeftAction) {
			return ((LeftAction) a).getMat();
		} else if(a instanceof RightAction) {
			return ((RightAction) a).getMat();
		}
		return null;
	}
}
