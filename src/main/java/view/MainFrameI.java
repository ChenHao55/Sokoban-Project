package view;

import java.io.File;

import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;

public interface MainFrameI {
	
	public void createBottonsFromExternalClasses();
	
	public void createMap(char[][] level) throws IlegalPositionException, ObjectPositionNotFoundException;
	
	public void updateCounters(int countBox, int count, int globalCount);
	
	public void updateLevelName(String levelName);
	
	public void paintMap();
	
	public void showCongrats(int punctuation);
	
	public void showError();
	
	public File saveGame(char c);
}
