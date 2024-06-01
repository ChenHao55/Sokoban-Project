package model.services;

import model.exceptions.IlegalPositionException;

public interface GameObjectI {
	
	//Getters y Setters
	int getX();
	
	int getY();
	
	void setX(int x) throws IlegalPositionException;
	
	void setY(int y) throws IlegalPositionException;
	
	int getCount();

	void setCount(int count);

	int getBoxCount();

	void setBoxCount(int boxCount);
	
	int getGlobalCount();

	void setGlobalCount(int globalCount);
}
