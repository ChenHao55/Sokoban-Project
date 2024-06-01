package sokoban;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.List;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
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
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.WallException;
import model.services.ActionI;
import model.services.ActionsFactory;
import model.services.ActionsFactoryI;
import model.services.ActionsManager;
import model.services.ActionsManagerI;
import model.services.GameObjectI;
import model.services.ObjectFactory;
import model.services.ObjectFactoryI;
import model.services.Options;
import model.services.OptionsI;

public class AppTest {
	
	private String fileSeparator = File.separator;
	
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
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};;
			WarehouseMan w = new WarehouseMan(1, 1);
			assertDoesNotThrow (() -> new LeftAction(w.getX(), w.getY(), mat));
		}
		
		@Test
		void correctRightActionCreation() throws IlegalPositionException {
			log.info("Executing test to check the incorrect creation of a RightAction Object");
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};;
			WarehouseMan w = new WarehouseMan(1, 1);
			assertDoesNotThrow (() -> new RightAction(w.getX(), w.getY(), mat));
		}
		
		@Test
		void correctUpActionCreation() throws IlegalPositionException {
			log.info("Executing test to check the incorrect creation of a UpAction Object");
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};;
			WarehouseMan w = new WarehouseMan(1, 1);
			assertDoesNotThrow (() -> new UpAction(w.getX(), w.getY(), mat));
		}
		
		@Test
		void correctDownActionCreation() throws IlegalPositionException {
			log.info("Executing test to check the incorrect creation of a DownAction Object");
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};;
			WarehouseMan w = new WarehouseMan(1, 1);
			assertDoesNotThrow (() -> new DownAction(w.getX(), w.getY(), mat));
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
	
	@DisplayName("Test for object factory")
	@Nested
	class ObjectFactoryTest {
		
		@Test
		void WarehouseManCreation() throws IOException, IlegalPositionException {
			log.info("Trying to create a WarehouseMan");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "map_level_1.txt").getAbsolutePath()));
			
			ObjectFactory of = new ObjectFactory();
			assertDoesNotThrow(() -> of.createWarehouseMan(level));
		}
		
		@Test
		void WarehouseManCreationNull() throws IOException, IlegalPositionException, IlegalMap {
			log.info("Trying to create a WarehouseMan");
			char[][] level =  {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', '.', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			
			ObjectFactory of = new ObjectFactory();
			assertNull(of.createWarehouseMan(level));
		}
		
		@Test
		void BoxCreation() throws IOException, IlegalPositionException {
			log.info("Trying to create a Box");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "map_level_1.txt").getAbsolutePath()));
			
			ObjectFactory of = new ObjectFactory();
			assertDoesNotThrow(() -> of.createBox(level));
		}
		
		@Test
		void BoxCreationNull() throws IOException, IlegalPositionException, IlegalMap {
			log.info("Trying to create a Box");
			char[][] level =  {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			
			ObjectFactory of = new ObjectFactory();
			assertNull(of.createBox(level));
		}
		
		@Test
		void GoalsCreation() throws IOException, IlegalPositionException {
			log.info("Trying to create a Box");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "map_level_1.txt").getAbsolutePath()));
			
			ObjectFactory of = new ObjectFactory();
			assertDoesNotThrow(() -> of.createGoals(level));
		}
		
		@Test
		void GoalsCreationNull() throws IOException, IlegalPositionException, IlegalMap {
			log.info("Trying to create a Box");
			char[][] level =  {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '.', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			
			ObjectFactory of = new ObjectFactory();
			List<Object> exp = new ArrayList<>();
			assertEquals(exp, of.createGoals(level));
		}
	}
	
	@DisplayName("Incorrect use of move")
	@Nested
	class IncorrectMove{
		
		private ArrayList<GameObjectI> gs = new ArrayList<>();
		private final ObjectFactoryI of = new ObjectFactory();
		private final OptionsI o = new Options(); 
		
		@Test
		void incorrectLeftActionMove() throws IlegalPositionException, FileNotFoundException, WallException, IlegalMap {
			log.info("Executing test to check the incorrect creation of a LeftAction Object");
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
				
			char[][] matExp = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', 'W', '.', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			boolean equals = true;
			WarehouseMan w = new WarehouseMan(1, 1);
			LeftAction action = new LeftAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "map_level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(false, equals);
		}
		
		@Test
		void incorrectRightActionMove() throws IlegalPositionException, FileNotFoundException, WallException, IlegalMap {
			log.info("Executing test to check the incorrect creation of a RightAction Object");
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
				
			char[][] matExp = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', 'W', '.', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			boolean equals = true;
			WarehouseMan w = new WarehouseMan(1, 1);
			RightAction action = new RightAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "map_level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(false, equals);
		}
		
		@Test
		void incorrectUpActionMove() throws IlegalPositionException, FileNotFoundException, WallException, IlegalMap {
			log.info("Executing test to check the incorrect creation of a UpAction Object");
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
				
			char[][] matExp = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', 'W', '.', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			boolean equals = true;
			WarehouseMan w = new WarehouseMan(1, 1);
			UpAction action = new UpAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "map_level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(false, equals);
		}
		
		@Test
		void incorrectDownActionMove() throws IlegalPositionException, FileNotFoundException, WallException, IlegalMap {
			log.info("Executing test to check the incorrect creation of a DownAction Object");
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
				
			char[][] matExp = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', 'W', '.', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			boolean equals = true;
			WarehouseMan w = new WarehouseMan(1, 1);
			DownAction action = new DownAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "map_level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(false, equals);
		}
	}
	
	@DisplayName("Correct use of move")
	@Nested
	class CorrectMove{
		
		@Test
		void correctLeftActionMove() throws IlegalPositionException, FileNotFoundException, WallException, IlegalMap {
			log.info("Executing test to check the incorrect creation of a LeftAction Object");
			ArrayList<GameObjectI> gs = new ArrayList<>();
			ObjectFactoryI of = new ObjectFactory();
			OptionsI o = new Options(); 
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};;
				
			char[][] matExp = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};;
			boolean equals = true;
			WarehouseMan w = new WarehouseMan(4, 2);
			LeftAction action = new LeftAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "map_level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(true, equals);
		}
		
		@Test
		void correctRightActionMove() throws IlegalPositionException, FileNotFoundException, WallException, IlegalMap {
			log.info("Executing test to check the incorrect creation of a RightAction Object");
			ArrayList<GameObjectI> gs = new ArrayList<>();
			ObjectFactoryI of = new ObjectFactory();
			OptionsI o = new Options(); 
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
				
			char[][] matExp = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', '.', 'W', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			boolean equals = true;
			WarehouseMan w = new WarehouseMan(4, 2);
			RightAction action = new RightAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "map_level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(true, equals);
		}

		@Test
		void correctUpActionMove() throws IlegalPositionException, FileNotFoundException, WallException, IlegalMap {
			log.info("Executing test to check the incorrect creation of a UpAction Object");
			ArrayList<GameObjectI> gs = new ArrayList<>();
			ObjectFactoryI of = new ObjectFactory();
			OptionsI o = new Options(); 
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			char[][] matExp = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', 'W', '.', '.', '.', '.', '+'},
				    {'+', '+', '.', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			boolean equals = true;
			WarehouseMan w = new WarehouseMan(4, 2);
			UpAction action = new UpAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "map_level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(true, equals);
			}
		
		@Test
		void correctDownActionMove() throws IlegalPositionException, FileNotFoundException, WallException, IlegalMap {
			log.info("Executing test to check the incorrect creation of a DownAction Object");
			ArrayList<GameObjectI> gs = new ArrayList<>();
			ObjectFactoryI of = new ObjectFactory();
			OptionsI o = new Options(); 
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			char[][] matExp = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', '.', '*', '+', '#', '.', '+'},
				    {'+', '.', 'W', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			boolean equals = true;
			WarehouseMan w = new WarehouseMan(4, 2);
			DownAction action = new DownAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "map_level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
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
		ActionsManagerI am;
		ActionsFactoryI af;
		ObjectFactoryI of;
		char[][] mat = {
			    {'+', '+', '+', '+', '.', '.', '.', '.'},
			    {'+', '.', '.', '+', '.', '.', '.', '.'},
			    {'+', '.', '.', '+', '+', '+', '+', '+'},
			    {'+', '.', '.', '.', '.', '.', '.', '+'},
			    {'+', '+', 'W', '*', '+', '#', '.', '+'},
			    {'+', '.', '.', '.', '+', '.', '.', '+'},
			    {'+', '.', '.', '.', '+', '+', '+', '+'},
			    {'+', '+', '+', '+', '+', '.', '.', '.'}
			};
		WarehouseMan w;
		
		ActionI down_a;
		ActionI up_a;
		ActionI left_a;
		ActionI right_a;
		@BeforeEach
		void createActions() throws IlegalPositionException, IlegalMap {
			am = new ActionsManager();
			af = new ActionsFactory();
			of = new ObjectFactory();
			w = (WarehouseMan) of.createWarehouseMan(mat);
			
			down_a = af.createAction('d', w.getX(), w.getY(), mat);
			up_a = af.createAction('u', w.getX(), w.getY(), mat);
			left_a = af.createAction('l', w.getX(), w.getY(), mat);
			right_a = af.createAction('r', w.getX(), w.getY(), mat);
		}
		
		@Test
		void correctGetActionsFunction() throws IlegalPositionException{
			log.info("Executing test to check the correct funtion of a get actions in Actions Manager");
			
			am.newAction(down_a);
			am.newAction(up_a);
			am.newAction(left_a);
			am.newAction(right_a);
			
			Stack<ActionI> as = am.getActions();
			
			Stack<ActionI> as2 = new Stack<>();
			
			as2.add(down_a);
			as2.add(up_a);
			as2.add(left_a);
			as2.add(right_a);
			
			assertEquals(as, as2);
		}
		@Test
		void correctUndoFunction() throws IlegalPositionException, FileNotFoundException, WallException {
			log.info("Executing test to check the correct funtion of a undo in Actions Manager");
			
			am.newAction(down_a);
			am.newAction(up_a);
			am.newAction(left_a);
			am.newAction(right_a);
			
			Stack<ActionI> as = am.getActions();
			
			assertEquals(4, as.size());
			ActionI a = am.undo();
	        assertTrue(a instanceof RightAction);

			assertEquals(3, as.size());
			a = am.undo();
	        assertTrue(a instanceof LeftAction);
			
			assertEquals(2, as.size());
			a = am.undo();
	        assertTrue(a instanceof UpAction);

			assertEquals(1, as.size());
			a = am.undo();
	        assertTrue(a instanceof DownAction);
	        
	        assertEquals(0, as.size());
			a = am.undo();
	        assertEquals(a, null);
		}
		
		@Test
		void correctClearActionsFunction() throws IlegalPositionException {
			log.info("Executing test to check the correct funtion of a clear actions in Actions Manager");	
			
			am.newAction(down_a);
			am.newAction(up_a);
			am.newAction(left_a);
			am.newAction(right_a);
			
			Stack<ActionI> as = am.getActions();
			assertEquals(4, as.size());
			
			as = am.getActions();
			am.clearActions();
			assertEquals(0, as.size());
			
			am.clearActions();
			assertEquals(0, as.size());
		}
	}
	
	@DisplayName("Test for actions factory")
	@Nested
	class ActionsFactoryTest {
		
		@Test
		void RightActionCreation() throws IOException, IlegalPositionException {
			log.info("Trying to create a RightAction");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "map_level_1.txt").getAbsolutePath()));
			WarehouseMan w = new WarehouseMan(4, 2);
			ActionsFactory af = new ActionsFactory();	
			RightAction resExp = new RightAction(w.getX(), w.getY(), level);
			RightAction res =  (RightAction) af.createAction('r', w.getX(), w.getY(), level);
			assertEquals(resExp.getMat(), res.getMat());
			assertEquals(resExp.getX(), res.getX());
			assertEquals(resExp.getY(), res.getY());
		}
		
		@Test
		void LeftActionCreation() throws IOException, IlegalPositionException {
			log.info("Trying to create a LeftAction");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "map_level_1.txt").getAbsolutePath()));
			WarehouseMan w = new WarehouseMan(4, 2);
			ActionsFactory af = new ActionsFactory();	
			LeftAction resExp = new LeftAction(w.getX(), w.getY(), level);
			LeftAction res =  (LeftAction) af.createAction('l', w.getX(), w.getY(), level);
			assertEquals(resExp.getMat(), res.getMat());
			assertEquals(resExp.getX(), res.getX());
			assertEquals(resExp.getY(), res.getY());
		}
		
		@Test
		void UpActionCreation() throws IOException, IlegalPositionException {
			log.info("Trying to create a UpAction");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "map_level_1.txt").getAbsolutePath()));
			WarehouseMan w = new WarehouseMan(4, 2);
			ActionsFactory af = new ActionsFactory();	
			UpAction resExp = new UpAction(w.getX(), w.getY(), level);
			UpAction res =  (UpAction) af.createAction('u', w.getX(), w.getY(), level);
			assertEquals(resExp.getMat(), res.getMat());
			assertEquals(resExp.getX(), res.getX());
			assertEquals(resExp.getY(), res.getY());
		}
		
		@Test
		void DownActionCreation() throws IOException, IlegalPositionException {
			log.info("Trying to create a DownAction");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "map_level_1.txt").getAbsolutePath()));
			WarehouseMan w = new WarehouseMan(4, 2);
			ActionsFactory af = new ActionsFactory();	
			DownAction resExp = new DownAction(w.getX(), w.getY(), level);
			DownAction res =  (DownAction) af.createAction('d', w.getX(), w.getY(), level);
			assertEquals(resExp.getMat(), res.getMat());
			assertEquals(resExp.getX(), res.getX());
			assertEquals(resExp.getY(), res.getY());
		}
		
	}
}
