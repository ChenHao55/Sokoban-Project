package view;

import java.io.File;

import controller.GameControllerI;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;

public interface MainFrameI {
	
	public void createBottonsFromExternalClasses();

	public GameControllerI getGc();

	public void setGc(GameControllerI gc);
	
	public void createMap(char[][] level) throws IlegalPositionException, ObjectPositionNotFoundException;
	
	public void updateCounters(int countBox, int count, int globalCount);
	
	public void updateLevelName(int levelNumber);
	
	public void paintMap();
	
	public void showCongrats(int punctuation);
	
	public void showError();
	
	public File optionGamePanel(char c);
}
