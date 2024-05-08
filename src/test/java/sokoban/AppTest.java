package sokoban;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Stack;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.beans.Box;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
import model.services.Action;
import model.services.ActionI;
import model.services.ActionsFactory;
import model.services.ActionsFactoryI;
import model.services.ActionsManager;
import model.services.ActionsManagerI;
import model.services.ObjectFactory;
import model.services.ObjectFactoryI;
import model.services.Options;
import model.services.OptionsI;
import model.services.Options;

public class AppTest {
	
	private String fileSeparator = File.separator;
	
	private static final Logger log = LoggerFactory.getLogger(AppTest.class);
	
	@DisplayName("Test to check the incorrect creation of a Box and WarehouseMan objects")
	@Nested
	class IlegalObjectCreation{
		
		@Test
		void invalidBoxCreation() {
			log.info("Executing test to check the incorrect creation of a Box Object");
			assertThrows(IlegalPositionException.class, () -> new Box(-1, -3));
		}
		
		@Test
		void invalidXBoxCoord() {
			log.info("Trying to create a Box Object with invalid X coordinate");
			assertThrows(IlegalPositionException.class, () -> new Box(-1, 6));
		}
		
		@Test
		void invalidYBoxCoord() {
			log.info("Trying to create a Box Object with invalid Y coordinate");
			assertThrows(IlegalPositionException.class, () -> new Box(6, -3));
		}
		
		@Test
		void invalidWarehouseManCreation() {
			log.info("Executing test to check the incorrect creation of a WarehouseMan Object");
			assertThrows(IlegalPositionException.class, () -> new Box(-1, -3));
		}
		
		@Test
		void invalidXWarehouseManCoord() {
			log.info("Trying to create a WarehouseMan Object with invalid X coordinate");
			assertThrows(IlegalPositionException.class, () -> new Box(-1, 6));
		}
		
		@Test
		void invalidYWarehouseManCoord() {
			log.info("Trying to create a WarehouseMan Object with invalid Y coordinate");
			assertThrows(IlegalPositionException.class, () -> new Box(6, -3));
		}
	}
	
	@DisplayName("Correct creation of WarehouseMan and Box objects")
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
			assertDoesNotThrow(() -> new Box(3, 4));
		}
	}
	
			
			
	@DisplayName("Test for map creation")
	@Nested
	class MapTest {
		
		private boolean equals = true;
		
		@Test
		void correctMapCreation() throws IOException {
			log.info("Trying to create a Map");
			char[][] matriz = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			
			Options o = new Options();
			
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "map_level_1.txt").getAbsolutePath()));
			
			for (int i = 0; i < matriz.length; i++) {
	            for (int j = 0; j < matriz[0].length; j++) {
	                if (matriz[i][j] != level[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			
			assertEquals(true, equals);
		}
	}

		
		
	@DisplayName("Correct function of Actions Manager")
	@Nested
	class CorrectActionsManager{
		
		@Test
		void correctActionsManagerCreation() {
			log.info("Executing test to check the correct creation of a Action Manager");
			assertDoesNotThrow(() -> new ActionsManager());
		}
		
		@Test
		void correctGetActionsFunction() throws IlegalPositionException {
			log.info("Executing test to check the correct creation of a WarehouseMan Object");
			ActionsManagerI am = new ActionsManager();
			ActionsFactoryI af = new ActionsFactory();
			ObjectFactoryI of = new ObjectFactory();
			OptionsI o = new Options(); 
		
		}
		@Test
		void correctUndoFunction() throws IlegalPositionException {
			log.info("Executing test to check the correct creation of a WarehouseMan Object");
			ActionsManagerI am = new ActionsManager();
			ActionsFactoryI af = new ActionsFactory();
			ObjectFactoryI of = new ObjectFactory();
			OptionsI o = new Options(); 
			char[][] matriz = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			WarehouseMan w = new WarehouseMan(0,0);
			ActionI left_a = af.createAction('l', w, matriz);
			am.newAction(left_a);
			am.newAction(left_a);
			Stack<ActionI> as = am.getActions();
			assertEquals(2, as.size());
			am.undo();
			assertEquals(1, as.size());
		}
		
		@Test
		void correctClearActionsFunction() throws IlegalPositionException {
			log.info("Executing test to check the correct creation of a WarehouseMan Object");
			ActionsManagerI am = new ActionsManager();
			ActionsFactoryI af = new ActionsFactory();
			ObjectFactoryI of = new ObjectFactory();
			OptionsI o = new Options(); 
			char[][] matriz = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			WarehouseMan w = new WarehouseMan(0,0);
			ActionI left_a = af.createAction('l', w, matriz);
			am.newAction(left_a);
			am.newAction(left_a);
			Stack<ActionI> as = am.getActions();
			assertEquals(2, as.size());
			am.clearActions();
			assertEquals(0, as.size());
		}
	}
}
