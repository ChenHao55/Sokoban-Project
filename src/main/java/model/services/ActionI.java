package model.services;

import model.beans.WarehouseMan;

public interface ActionI {
	
	char[][] getMat();
	WarehouseMan getW();
	
	void setMat(char[][] level);
	void setW(WarehouseMan w);
	boolean isLastBox();
	void setLastBox(boolean lastBox);
}
