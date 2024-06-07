package model.services;

import java.util.ArrayList;

import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;

public interface ObjectFactoryI {
	
	//Metodo para crear los objetos
	GameObjectI createWarehouseMan(char[][] level) throws IlegalPositionException, IlegalMap;
	
	//Metodo para crear posiciones meta
	ArrayList<GameObjectI> createGoals(char[][] level) throws IlegalPositionException, IlegalMap;
}
