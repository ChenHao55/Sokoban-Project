import controller.GameController;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.services.ActionsFactory;
import model.services.ActionsFactoryI;
import model.services.ActionsManager;
import model.services.ActionsManagerI;
import view.MainFrame;
import view.MapPanel;

public class GameMain {
	
	public static void main(String[] args) throws IlegalPositionException, ObjectPositionNotFoundException {
		
		MainFrame mf = new MainFrame();
		MapPanel mp = new MapPanel();
		ActionsFactory af;
		ActionsManager am;
		GameController gc = new GameController(mf, mp);
		
		mf.setGc(gc);
		mp.setGc(gc);
		mf.setVisible(true);
	}
}
