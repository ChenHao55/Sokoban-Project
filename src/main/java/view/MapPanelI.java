package view;

import javax.swing.JLabel;

import controller.GameControllerI;

public interface MapPanelI {
	
	public JLabel getTurnCount();
	
	public JLabel getTurnBox();
	
	public JLabel getTurnWarehouseMan();
	
	public JLabel getLevelName();
	
	public GameControllerI getGc();

	public void setGc(GameControllerI gc);
	
	public void createMap(char[][] level);
}
