package sokoban;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.beans.Box;
import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;
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
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(-1, -1));
		}
		
		@Test
		void invalidXWarehouseManCoord() {
			log.info("Trying to create a WarehouseMan Object with invalid X coordinate");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(-1, 1));
		}
		
		@Test
		void invalidYWarehouseManCoord() {
			log.info("Trying to create a WarehouseMan Object with invalid Y coordinate");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(1, -1));
		}
		
		@Test
		void invalidGoalCreation() {
			log.info("Executing test to check the incorrect creation of a GoalPosition Object");
			assertThrows(IlegalPositionException.class, () -> new GoalPosition(-1, -1));
		}
		
		@Test
		void invalidLeftActionCreation() {
			log.info("Executing test to check the incorrect creation of a LeftAction Object");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(-1, -1));
		}
		
		@Test
		void invalidRightActionCreation() {
			log.info("Executing test to check the incorrect creation of a RightAction Object");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(-1, -1));
		}
		
		@Test
		void invalidUpActionCreation() {
			log.info("Executing test to check the incorrect creation of a UpAction Object");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(-1, -1));
		}
		
		@Test
		void invalidDownActionCreation() {
			log.info("Executing test to check the incorrect creation of a DownAction Object");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(-1, -1));
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
			assertDoesNotThrow(() -> new WarehouseMan(3, 4));
		}
		
		@Test
		void correctGoalCreation() {
			log.info("Executing test to check the incorrect creation of a GoalPosition Object");
			assertThrows(IlegalPositionException.class, () -> new GoalPosition(1, 1));
		}
		
		@Test
		void correctLeftActionCreation() {
			log.info("Executing test to check the incorrect creation of a LeftAction Object");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(1, 1));
		}
		
		@Test
		void correctRightActionCreation() {
			log.info("Executing test to check the incorrect creation of a RightAction Object");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(1, 1));
		}
		
		@Test
		void correctUpActionCreation() {
			log.info("Executing test to check the incorrect creation of a UpAction Object");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(1, 1));
		}
		
		@Test
		void correctDownActionCreation() {
			log.info("Executing test to check the incorrect creation of a DownAction Object");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(1, 1));
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
	
}
