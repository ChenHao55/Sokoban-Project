package model.services;

import java.util.Stack;

public interface ActionsManagerI {
	
	//Metodo para obtener la pila de acciones realizadas
	public Stack<ActionI> getActions();
	
	//Metodo para insertar una nueva accion en la pila
	public void newAction(ActionI a);
	
	//Metodo para sacar una accion de la pila
	public Action undo();
}