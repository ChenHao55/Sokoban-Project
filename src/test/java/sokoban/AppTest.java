package sokoban;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.beans.Box;
import model.beans.DownAction;
import model.beans.GoalPosition;
import model.beans.LeftAction;
import model.beans.RightAction;
import model.beans.UpAction;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.services.GameObjectI;
import model.services.ObjectFactory;
import model.services.ObjectFactoryI;
import model.services.Options;
import model.services.OptionsI;

public class AppTest {
	
	private static final Logger log = LoggerFactory.getLogger(AppTest.class);
	
	@DisplayName("Test to check the incorrect creation of WarehouseMan and Box objects")
	@Nested
	class IlegalObjectCreation{
		
		@Test
		void invalidBoxCreation() {
			log.info("Executing test to check the incorrect creation of a Box Object");
			assertThrows(IlegalPositionException.class, () -> new Box(-1, -3));
		}
		
		@Test
		void invalidWarehouseManCreation() {
			log.info("Executing test to check the incorrect creation of a WarehouseMan Object");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(-1, -1));
		}
		
		@Test
		void invalidGoalCreation() {
			log.info("Executing test to check the incorrect creation of a GoalPosition Object");
			assertThrows(IlegalPositionException.class, () -> new GoalPosition(-1, -1));
		}
	}
	
	@DisplayName("Correct creation of WarehouseMan, Box and Movement objects")
	@Nested
	class CorrectObjectCreation{
		
		@Test
		void correctBoxCreation() {
			log.info("Executing test to check the correct creation of a Box Object");
			assertDoesNotThrow(() -> new Box(1, 2));
		}
		
		@Test
		void correctWarehouseManCreation() {
			log.info("Executing test to check the correct creation of a WarehouseMan Object");
			assertDoesNotThrow(() -> new WarehouseMan(3, 4));
		}
		
		@Test
		void correctGoalCreation() {
			log.info("Executing test to check the incorrect creation of a GoalPosition Object");
			assertDoesNotThrow (() -> new GoalPosition(1, 1));
		}
		
		@Test
		void correctLeftActionCreation() throws IlegalPositionException {
			log.info("Executing test to check the incorrect creation of a LeftAction Object");
			char[][] mat = new char[1][1];
			WarehouseMan w = new WarehouseMan(1, 1);
			assertDoesNotThrow (() -> new LeftAction(w, mat));
		}
		
		@Test
		void correctRightActionCreation() throws IlegalPositionException {
			log.info("Executing test to check the incorrect creation of a RightAction Object");
			char[][] mat = new char[1][1];
			WarehouseMan w = new WarehouseMan(1, 1);
			assertDoesNotThrow (() -> new RightAction(w, mat));
		}
		
		@Test
		void correctUpActionCreation() throws IlegalPositionException {
			log.info("Executing test to check the incorrect creation of a UpAction Object");
			char[][] mat = new char[1][1];
			WarehouseMan w = new WarehouseMan(1, 1);
			assertDoesNotThrow (() -> new UpAction(w, mat));
		}
		
		@Test
		void correctDownActionCreation() throws IlegalPositionException {
			log.info("Executing test to check the incorrect creation of a DownAction Object");
			char[][] mat = new char[1][1];
			WarehouseMan w = new WarehouseMan(1, 1);
			assertDoesNotThrow (() -> new DownAction(w, mat));
		}
	}
	
	@DisplayName("Correct use of move")
	@Nested
	class CorrectMove{
		
		private ArrayList<GameObjectI> gs = new ArrayList<>();
		private final ObjectFactoryI of = new ObjectFactory();
		private final OptionsI o = new Options(); 
		
		@Test
		void correctLeftActionMove() throws IlegalPositionException {
			log.info("Executing test to check the incorrect creation of a LeftAction Object");
			char[][] mat = new char[1][1];
			WarehouseMan w = new WarehouseMan(1, 1);
			LeftAction action = new LeftAction(w, mat);
			assertDoesNotThrow (() -> new DownAction(w, mat));
		}
		
		@Test
		void correctRightActionMove() throws IlegalPositionException {
			log.info("Executing test to check the incorrect creation of a RightAction Object");
			char[][] mat = new char[1][1];
			WarehouseMan w = new WarehouseMan(1, 1);
			RightAction action = new RightAction(w, mat);
			assertDoesNotThrow (() -> new DownAction(w, mat));
		}
		
		@Test
		void correctUpActionMove() throws IlegalPositionException, FileNotFoundException {
			log.info("Executing test to check the incorrect creation of a UpAction Object");
			char[][] mat = new char[1][1];
			WarehouseMan w = new WarehouseMan(1, 1);
			UpAction action = new UpAction(w, mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "map_level_" + 1 + ".txt").getAbsolutePath()));
			assertDoesNotThrow (() -> action.move(w, gs, mat));
		}
		
		@Test
		void correctDownActionMove() throws IlegalPositionException {
			log.info("Executing test to check the incorrect creation of a DownAction Object");
			char[][] mat = new char[1][1];
			WarehouseMan w = new WarehouseMan(1, 1);
			DownAction action = new DownAction(w, mat);
			assertDoesNotThrow (() -> new DownAction(w, mat));
		}
	}
	
}
