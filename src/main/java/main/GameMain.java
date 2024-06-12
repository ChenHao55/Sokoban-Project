package main;

import java.awt.Window;

import controller.GameController;
import model.exceptions.IlegalPositionException;
import view.MainFrame;

public class GameMain {
	
	public static void main(String[] args) throws IlegalPositionException {
		
		MainFrame mf = new MainFrame();
		GameController gc = new GameController(mf);
		
		mf.setGc(gc);
		((Window) mf).setVisible(true);
	}
}
