package model.services;

public interface ActionI {
	
	char[][] getMat();
	void setMat(char[][] level);
	int getX();
	int getY();
	void setX(int x);
	void setY(int y);
	
	boolean isLastBox();
	void setLastBox(boolean lastBox);
}
