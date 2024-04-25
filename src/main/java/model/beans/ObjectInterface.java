package model.beans;

import model.exceptions.IlegalPositionException;

public interface ObjectInterface {
	public int getX();
	public int getY();
	public void setX(int x) throws IlegalPositionException;
	public void setY(int y) throws IlegalPositionException;
}
