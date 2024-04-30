package model.services;

import model.exceptions.IlegalPositionException;

public interface GameObjectI {
	
	//Getters y Setters
	int getX();
	
	int getY();
	
	void setX(int x) throws IlegalPositionException;
	
	void setY(int y) throws IlegalPositionException;

	GameObjectI clone();
}
