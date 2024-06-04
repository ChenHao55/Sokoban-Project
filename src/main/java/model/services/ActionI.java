package model.services;

import java.io.Serializable;

public interface ActionI extends Serializable{
	
	char[][] getMat();
	void setMat(char[][] level);
	int getX();
	int getY();
	void setX(int x);
	void setY(int y);
	
	boolean isLastBox();
	void setLastBox(boolean lastBox);
}
