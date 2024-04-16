package sokoban;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.EmptyFileException;
import exceptions.FileException;
import exceptions.IlegalPositionException;

public class AppTest {
	
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
	
/*	@DisplayName("Correct creation of a Map")
	@Nested
	class CorrectMapCreation{
		
		@Test
		void correctMap() throws EmptyFileException, FileException {
			CreateMap cm = new CreateMap();
			char[][] map = cm.createMap("map_level_1.txt");
			
			for(int i = 0; i<map.length; i++) {
				for(int j = 0; j<map[0].length; j++) {
					System.out.println(map[i][j]);
				}
			}
			System.out.println();
		}
	}*/
	
}