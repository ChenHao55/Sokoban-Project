package main;

import java.awt.Window;

import controller.GameController;
import controller.GameControllerI;
import model.exceptions.IlegalPositionException;
import view.MainFrame;
import view.MainFrameI;

public class GameMain {
	
	public static void main(String[] args) throws IlegalPositionException {
		
		MainFrameI mf = new MainFrame();
		GameControllerI gc = new GameController(mf);
		
		mf.setGc(gc);
		((Window) mf).setVisible(true);
	}
}
