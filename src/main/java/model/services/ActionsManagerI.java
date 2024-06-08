package model.services;

import java.util.Deque;

public interface ActionsManagerI {
	
	//Metodo para obtener la pila de acciones realizadas
	public Deque<ActionI> getActions();
	
	//Metodo para  la pila de acciones realizadas
	public void setActions(Deque<ActionI> actions);

	//Metodo para insertar una nueva accion en la pila
	public void newAction(ActionI a);

	//Metodo para borrar una acción
	public void deleteAction(ActionI a);

	//Metodo para sacar una accion de la pila
	public Action undo();
	
	//Empty the stack of actions
	public void clearActions();
}
