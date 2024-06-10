package sokoban;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.io.IOException;

import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.beans.Counter;
import model.beans.DownAction;
import model.beans.GenericCounter;
import model.beans.GoalPosition;
import model.beans.LeftAction;
import model.beans.RightAction;
import model.beans.UpAction;
import model.beans.WarehouseMan;
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.exceptions.WallException;
import model.services.ActionI;
import model.services.ActionsFactory;
import model.services.ActionsFactoryI;
import model.services.ActionsManager;
import model.services.ActionsManagerI;
import model.services.GameObjectI;
import model.services.GameService;
import model.services.ObjectFactory;
import model.services.ObjectFactoryI;
import model.services.Options;
import model.services.OptionsI;

public class AppTest {
	
	private static String fileSeparator = File.separator;
	
	private static final Logger log = LoggerFactory.getLogger(AppTest.class);
	
	@DisplayName("Test to check the incorrect creation of WarehouseMan objects")
	@Nested
	class IlegalObjectCreation{
		
		@Test
		void invalidWarehouseManCreation() {
			log.info("Executing test to check the incorrect creation of a WarehouseMan Object");
			assertThrows(IlegalPositionException.class, () -> new WarehouseMan(-1, -1));
			log.info("Test passed");
		}
		
		@Test
		void invalidGoalCreation() {
			log.info("Executing test to check the incorrect creation of a GoalPosition Object");
			assertThrows(IlegalPositionException.class, () -> new GoalPosition(-1, -1));
			log.info("Test passed");
		}
	}
	
	@DisplayName("Correct creation of WarehouseMan and Movement objects")
	@Nested
	class CorrectObjectCreation{
		
		@Test
		void correctWarehouseManCreation() {
			log.info("Executing test to check the correct creation of a WarehouseMan Object");
			assertDoesNotThrow(() -> new WarehouseMan(3, 4));
			log.info("Test passed");
		}
		
		@Test
		void correctGoalCreation() {
			log.info("Executing test to check the incorrect creation of a GoalPosition Object");
			assertDoesNotThrow (() -> new GoalPosition(1, 1));
			log.info("Test passed");
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
			log.info("Test passed");
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
			log.info("Test passed");
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
			log.info("Test passed");
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
			log.info("Test passed");
		}
	}
	
	@DisplayName("Test for map creation")
	@Nested
	class MapTest {
		
		private boolean equals = true;
		
		@Test
		void correctMapCreation() throws IOException, IlegalMap {
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
			
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath()));
			
			for (int i = 0; i < matriz.length; i++) {
	            for (int j = 0; j < matriz[0].length; j++) {
	                if (matriz[i][j] != level[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			
			assertEquals(true, equals);
			log.info("Test passed");
		}
	}
	
	@DisplayName("Test for object factory")
	@Nested
	class ObjectFactoryTest {
		
		@Test
		void WarehouseManCreation() throws IOException, IlegalPositionException, IlegalMap {
			log.info("Trying to create a WarehouseMan");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath()));
			
			ObjectFactory of = new ObjectFactory();
			assertDoesNotThrow(() -> of.createWarehouseMan(level));
			log.info("Test passed");
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
			assertThrows(IlegalPositionException.class, () -> of.createWarehouseMan(level));
			log.info("Test passed");
		}
		
		@Test
		void GoalsCreation() throws IOException, IlegalPositionException, IlegalMap {
			log.info("Trying to create a Box");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath()));
			
			ObjectFactory of = new ObjectFactory();
			assertDoesNotThrow(() -> of.createGoals(level));
			log.info("Test passed");
		}
		
		@Test
		void GoalsCreationNull() throws IOException, IlegalPositionException, IlegalMap {
			log.info("Trying to create a Box");
			char[][] level =  {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			
			ObjectFactory of = new ObjectFactory();
			List<Object> exp = new ArrayList<>();
			assertEquals(exp, of.createGoals(level));
			log.info("Test passed");
		}
		
		@Test
		void creationOfAWarehouseManFromAnEmptyMap() throws IlegalPositionException, IlegalMap {
			log.info("Trying to create a Box");
			char[][] level = new char[0][];
			ObjectFactory of = new ObjectFactory();
			assertThrows(IlegalMap.class, () -> of.createWarehouseMan(level));
			log.info("Test passed");
		}
		
		@Test
		void creationOfAGoalsFromAnEmptyMap() throws IlegalPositionException, IlegalMap {
			log.info("Trying to create a Box");
			char[][] level = new char[0][];
			ObjectFactory of = new ObjectFactory();
			assertThrows(IlegalMap.class, () -> of.createGoals(level));
			log.info("Test passed");
		}
		
		@Test
		void creationOfAWarehouseManFromAnEmptyMap2() throws IlegalPositionException, IlegalMap {
			log.info("Trying to create a Box");
			char[][] level = new char[1][0];
			ObjectFactory of = new ObjectFactory();
			assertThrows(IlegalMap.class, () -> of.createWarehouseMan(level));
			log.info("Test passed");
		}
		
		@Test
		void creationOfAGoalsFromAnEmptyMap2() throws IlegalPositionException, IlegalMap {
			log.info("Trying to create a Box");
			char[][] level = new char[1][0];
			ObjectFactory of = new ObjectFactory();
			assertThrows(IlegalMap.class, () -> of.createGoals(level));
			log.info("Test passed");
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
			Counter c = new Counter();
			LeftAction action = new LeftAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat, c);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(false, equals);
			log.info("Test passed");
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
			Counter c = new Counter();
			RightAction action = new RightAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat, c);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(false, equals);
			log.info("Test passed");
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
			Counter c = new Counter();
			UpAction action = new UpAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat, c);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(false, equals);
			log.info("Test passed");
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
			Counter c = new Counter();
			DownAction action = new DownAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(mat);
			char[][] matRes = action.move(w, gs, mat, c);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(false, equals);
			log.info("Test passed");
		}
		
		@Test
		void tryToMoveBlockBox() throws IlegalPositionException, FileNotFoundException, IlegalMap, WallException {
			log.info("Executing test to check the incorrect creation of a DownAction Object");
			char[][] mat = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', '.', '*', '+', '.', 'W', '+'},
				    {'+', '.', '.', '.', '+', '.', '#', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
				
			boolean equals = true;
			WarehouseMan w = new WarehouseMan(6, 4);
			Counter c = new Counter();
			DownAction action = new DownAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(mat);
			char[][] matRes = action.move(w, gs, mat, c);
			for (int i = 0; i < mat.length; i++) {
	            for (int j = 0; j < mat[0].length; j++) {
	                if (mat[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(true, equals);
			log.info("Test passed");
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
			Counter c = new Counter();
			LeftAction action = new LeftAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat, c);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(true, equals);
			log.info("Test passed");
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
			Counter c = new Counter();
			RightAction action = new RightAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat, c);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(true, equals);
			log.info("Test passed");
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
			Counter c = new Counter();
			UpAction action = new UpAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat, c);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(true, equals);
			log.info("Test passed");
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
			Counter c = new Counter();
			DownAction action = new DownAction(w.getX(), w.getY(), mat);
			gs = of.createGoals(o.newGame(new File("maps" + File.separator + "level_" + 1 + ".txt").getAbsolutePath()));
			char[][] matRes = action.move(w, gs, mat, c);
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != matRes[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			assertEquals(true, equals);
			log.info("Test passed");
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
		Counter c;

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
			c = new Counter();
			
			down_a = af.createAction('d', w.getX(), w.getY(), mat);
			up_a = af.createAction('u', w.getX(), w.getY(), mat);
			left_a = af.createAction('l', w.getX(), w.getY(), mat);
			right_a = af.createAction('r', w.getX(), w.getY(), mat);
		}
		
		@Test
		void correctSetAction() {
			Deque<ActionI> stack = new ArrayDeque<ActionI>();
			stack.add(left_a);
			stack.add(down_a);
			stack.add(right_a);
			stack.add(up_a);
			am.setActions(stack);
			assertEquals(4, am.getActions().size());
			log.info("Test passed");
		}
		
		@Test
		void correctDeleteAction() {
			Deque<ActionI> stack = new ArrayDeque<ActionI>();
			stack.add(left_a);
			stack.add(down_a);
			stack.add(right_a);
			stack.add(up_a);
			am.setActions(stack);
			am.deleteAction(down_a);
			assertEquals(3, am.getActions().size());
			log.info("Test passed");
		}
		
		@Test
		void correctGetActionsFunction() throws IlegalPositionException{
			log.info("Executing test to check the correct funtion of a get actions in Actions Manager");
			
			am.newAction(down_a);
			am.newAction(up_a);
			am.newAction(left_a);
			am.newAction(right_a);
			
			Deque<ActionI> as = am.getActions();
			
			Deque<ActionI> as2 = new ArrayDeque<>();
			as2.push(down_a);
			as2.push(up_a);
			as2.push(left_a);
			as2.push(right_a);
			
			assertEquals(4, as.size());
			
			while(!as.isEmpty() && !as2.isEmpty()) {
				assertEquals(as.poll(), as2.poll());
			}
			log.info("Test passed");
		}
		@Test
		void correctUndoFunction() throws IlegalPositionException, FileNotFoundException, WallException {
			log.info("Executing test to check the correct funtion of a undo in Actions Manager");
			
			am.newAction(down_a);
			am.newAction(up_a);
			am.newAction(left_a);
			am.newAction(right_a);
			
			Deque<ActionI> as = am.getActions();
			
			assertEquals(4, as.size());
			ActionI a = am.undo();
	        assertTrue(a instanceof RightAction);
	        
	        //Lo comento para que valoreis si quitarlo o no, en las diapositivas pone que deberia haber mas de 2 asserts por test
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
	        assertEquals(null, a);
	        log.info("Test passed");
		}
		
		@Test
		void correctClearActionsFunction() throws IlegalPositionException {
			log.info("Executing test to check the correct funtion of a clear actions in Actions Manager");	
			
			am.newAction(down_a);
			am.newAction(up_a);
			am.newAction(left_a);
			am.newAction(right_a);
			
			Deque<ActionI> as = am.getActions();
			assertEquals(4, as.size());
			
			as = am.getActions();
			am.clearActions();
			assertEquals(0, as.size());
			
			am.clearActions();
			assertEquals(0, as.size());
			log.info("Test passed");
		}
	}
	
	@DisplayName("Test for actions factory")
	@Nested
	class ActionsFactoryTest {
		
		@Test
		void RightActionCreation() throws IOException, IlegalPositionException, IlegalMap {
			log.info("Trying to create a RightAction");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath()));
			WarehouseMan w = new WarehouseMan(4, 2);
			ActionsFactory af = new ActionsFactory();	
			RightAction resExp = new RightAction(w.getX(), w.getY(), level);
			RightAction res =  (RightAction) af.createAction('r', w.getX(), w.getY(), level);
			assertEquals(resExp.getMat(), res.getMat());
			log.info("Test passed");
		}
		
		@Test
		void LeftActionCreation() throws IOException, IlegalPositionException, IlegalMap {
			log.info("Trying to create a LeftAction");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath()));
			WarehouseMan w = new WarehouseMan(4, 2);
			ActionsFactory af = new ActionsFactory();	
			LeftAction resExp = new LeftAction(w.getX(), w.getY(), level);
			LeftAction res =  (LeftAction) af.createAction('l', w.getX(), w.getY(), level);
			assertEquals(resExp.getMat(), res.getMat());
			log.info("Test passed");
		}
		
		@Test
		void UpActionCreation() throws IOException, IlegalPositionException, IlegalMap {
			log.info("Trying to create a UpAction");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath()));
			WarehouseMan w = new WarehouseMan(4, 2);
			ActionsFactory af = new ActionsFactory();	
			UpAction resExp = new UpAction(w.getX(), w.getY(), level);
			UpAction res =  (UpAction) af.createAction('u', w.getX(), w.getY(), level);
			assertEquals(resExp.getMat(), res.getMat());
			log.info("Test passed");
		}
		
		@Test
		void DownActionCreation() throws IOException, IlegalPositionException, IlegalMap {
			log.info("Trying to create a DownAction");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath()));
			WarehouseMan w = new WarehouseMan(4, 2);
			ActionsFactory af = new ActionsFactory();	
			DownAction resExp = new DownAction(w.getX(), w.getY(), level);
			DownAction res =  (DownAction) af.createAction('d', w.getX(), w.getY(), level);
			assertEquals(resExp.getMat(), res.getMat());
			log.info("Test passed");
		}
		
		@Test
		void NullActionCreation() throws IlegalPositionException, FileNotFoundException, IlegalMap {
			log.info("Trying to create a non existing action");
			Options o = new Options();
			char[][] level =  o.newGame((new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath()));
			WarehouseMan w = new WarehouseMan(4, 2);
			ActionsFactory af = new ActionsFactory();	
			DownAction res =  (DownAction) af.createAction('x', w.getX(), w.getY(), level);
			assertNull(res);
			log.info("Test passed");
		}
		
	}
	
	@DisplayName("Test to check Options")
	@Nested
	class OptionsTest {
		
		private Options o = new Options();
		private ObjectFactory of = new ObjectFactory();
		String path = new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath();
		
		@Test
		void newGame() throws IlegalPositionException, ObjectPositionNotFoundException {
			log.info("Trying to use correctly newGame method");
			assertDoesNotThrow(() -> o.newGame(path));
			log.info("Test passed");
		}
		
		@Test
		void saveGame() throws IlegalPositionException, IlegalMap, FileNotFoundException {
			log.info("Trying to save correctly a game");
			char[][] map = o.newGame(path);
			WarehouseMan w = (WarehouseMan) of.createWarehouseMan(map);
			Counter c = new Counter();
			Counter c2 = new Counter();
			GenericCounter g = new GenericCounter();
			g.setCurrentCount(c);
			g.setLevelCount(c2);
			ArrayList<GameObjectI> gs = of.createGoals(map);
			Deque<ActionI> s = new ArrayDeque<>();
			File f = new File("saved_map.txt");
			assertDoesNotThrow(() -> o.saveGame(map, w, gs, s, 1, f, g));
			log.info("Test passed");
		}
		
		@Test
		void loadGame() throws IlegalPositionException, IlegalMap {
			log.info("Trying to load correctly a game");
			WarehouseMan w = new WarehouseMan(0,0);
			Counter c = new Counter();
			Counter c2 = new Counter();
			GenericCounter g = new GenericCounter();
			g.setCurrentCount(c);
			g.setLevelCount(c2);
			ArrayList<GameObjectI> gs = new ArrayList<>();
			File f = new File("saved_map.txt");
			ActionsManager am = new ActionsManager();
			
			char[][] matExp = {
				    {'+', '+', '+', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '.', '.', '.', '.'},
				    {'+', '.', '.', '+', '+', '+', '+', '+'},
				    {'+', '.', '.', '.', '.', '.', '.', '+'},
				    {'+', '+', 'W', '*', '+', '#', '.', '+'},
				    {'+', '.', '.', '.', '+', '.', '.', '+'},
				    {'+', '.', '.', '.', '+', '+', '+', '+'},
				    {'+', '+', '+', '+', '+', '.', '.', '.'}
				};
			boolean equals = true;
			Pair<Integer, char[][]> p = o.loadGame(w, gs, am, f, g);
			char[][] res = p.getValue1();
			for (int i = 0; i < matExp.length; i++) {
	            for (int j = 0; j < matExp[0].length; j++) {
	                if (matExp[i][j] != res[i][j]) {
	                    equals = false;
	                }
	            }
	        }
			
			assertEquals(true, equals);
			log.info("Test passed");
		}
	}
	
	@DisplayName("Test for GameService")
	@Nested
	class GameServiceTest {	
		
		private GameService gs;
		private GenericCounter c;
		private Counter c1;
		
		@BeforeEach
		void init() throws IlegalPositionException {
			log.info("Initializating global variables");
			gs = new GameService();
			c1 = new Counter();
			c = new GenericCounter();
			c1.setBoxCount(5);
			c1.setCount(10);
			c1.setGlobalCount(15);
			c.setCurrentCount(c1);
			gs.setGenericCounter(c);
			log.info("Global variables initializated");
		}
		
		@Test
		void correctNewGameTest() throws IlegalPositionException, IlegalMap {
			log.info("Testing a correct use of newGame method");
			assertDoesNotThrow(() -> gs.newGame(new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath()));
			log.info("Test passed");
		}
		
		@Test
		void FileNotFoundNewGameTest() {
			log.info("Testing incorrect use of newGame method");
			assertThrows(FileNotFoundException.class, () -> gs.newGame(new File("maps" + fileSeparator + "level_99.txt").getAbsolutePath()));
			log.info("Test passed");
		}
		
		@Test
		void IlegalMapNewGameTest() {
			log.info("Testing incorrect use of newGame method");
			assertThrows(IlegalMap.class, () -> gs.newGame(new File("testMaps" + fileSeparator + "emptyMap.txt").getAbsolutePath()));
			log.info("Test passed");
		}
		
		@Test 
		void IlegalPositionExceptionNewGameTest1(){
			log.info("Testing incorrect use of newGame method");
			assertThrows(IlegalPositionException.class, () -> gs.newGame(new File("testMaps" + fileSeparator + "noWarehouseManMap.txt").getAbsolutePath()));
			log.info("Test passed");
		}
		
		@Test 
		void IlegalPositionExceptionNewGameTest2(){
			log.info("Testing incorrect use of newGame method");
			assertThrows(IlegalPositionException.class, () -> gs.newGame(new File("testMaps" + fileSeparator + "moreThanOneWMap.txt").getAbsolutePath()));
			log.info("Test passed");
		}
		
		@Test
		void correctGameTest() {
			log.info("Testing correct use of game method");
			assertDoesNotThrow(() -> gs.game(new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath()));
			log.info("Test passed");
		}
		
		@Test
		void FileNotFoundGameTest() {
			log.info("Testing incorrect use of newGame method");
			assertThrows(FileNotFoundException.class, () -> gs.game(new File("maps" + fileSeparator + "level_99.txt").getAbsolutePath()));
			log.info("Test passed");
		}
		
		@Test
		void IlegalMapGameTest() {
			log.info("Testing incorrect use of newGame method");
			assertThrows(IlegalMap.class, () -> gs.game(new File("testMaps" + fileSeparator + "emptyMap.txt").getAbsolutePath()));
			log.info("Test passed");
		}
		
		@Test 
		void IlegalPositionExceptionGameTest1(){
			log.info("Testing incorrect use of newGame method");
			assertThrows(IlegalPositionException.class, () -> gs.game(new File("testMaps" + fileSeparator + "noWarehouseManMap.txt").getAbsolutePath()));
			log.info("Test passed");
		}
		
		@Test 
		void IlegalPositionExceptionGameTest2(){
			log.info("Testing incorrect use of newGame method");
			assertThrows(IlegalPositionException.class, () -> gs.game(new File("testMaps" + fileSeparator + "moreThanOneWMap.txt").getAbsolutePath()));
			log.info("Test passed");
		}
		
		@Test
		void saveGameTest() throws IlegalPositionException, IlegalMap, IOException {
			log.info("Testing correct use of saveGame method");
			gs.newGame(new File("maps" + fileSeparator + "level_1.txt").getAbsolutePath());
			String path = new File("savedTestMaps" + fileSeparator + "savedTestMap.txt").getAbsolutePath();
			File f = new File(path);
			assertEquals(true, gs.saveGame(f));
			log.info("Test passed");
		}
		
		@Test
		void saveGameNullFileTest() throws FileNotFoundException {
			log.info("Testin incorrect use os saveGame method");
			assertEquals(false, gs.saveGame(null));
			log.info("Test passed");
		}
		
		@Test
		void loadGameTest() {
			log.info("Testing correct use of loadGame method");
			String path = new File("savedTestMaps" + fileSeparator + "savedTestMap.txt").getAbsolutePath();
			File f = new File(path);
			assertDoesNotThrow(() -> gs.loadGame(f));
			log.info("Test passed");
		}
		
		@Test
		void ilegalPositionWLoadGameTest() {
			log.info("Testing incorrect use of loadGame method");
			String path = new File("savedTestMaps" + fileSeparator + "IlegalPositionWMap.txt").getAbsolutePath();
			File f = new File(path);
			assertThrows(IlegalPositionException.class, () -> gs.loadGame(f));
			log.info("Test passed");
		}
		
		@Test
		void loadGameNullFile() throws NumberFormatException, IlegalPositionException {
			log.info("Testing incorrect use of loadGame method");
			assertEquals(false, gs.loadGame(null));
			log.info("Test passed");
		}
		
		@Test
		void numberFormatExceptionLoadGame() {
			log.info("Testing incorrect use of loadGame method");
			String path = new File("savedTestMaps" + fileSeparator + "NumberFormatExceptionSavedMap.txt").getAbsolutePath();
			File f = new File(path);
			assertThrows(NumberFormatException.class, () -> gs.loadGame(f));
			log.info("Test passed");
		}
		
		@Test
		void loadGameMFTest() {
			log.info("Testing correct use of loadGame method");
			String path = new File("savedTestMaps" + fileSeparator + "savedTestMap.txt").getAbsolutePath();
			File f = new File(path);
			assertDoesNotThrow(() -> gs.loadGameMF(f));
			log.info("Test passed");
		}
		
		@Test
		void ilegalPositionWLoadGameMFTest() {
			log.info("Testing incorrect use of loadGame method");
			String path = new File("savedTestMaps" + fileSeparator + "IlegalPositionWMap.txt").getAbsolutePath();
			File f = new File(path);
			assertThrows(IlegalPositionException.class, () -> gs.loadGameMF(f));
			log.info("Test passed");
		}
		
		@Test
		void loadGameMFNullFile() throws NumberFormatException, IlegalPositionException {
			log.info("Testing incorrect use of loadGame method");
			assertEquals(false, gs.loadGameMF(null));
			log.info("Test passed");
		}
		
		@Test
		void numberFormatExceptionLoadGameMF() {
			log.info("Testing incorrect use of loadGame method");
			String path = new File("savedTestMaps" + fileSeparator + "NumberFormatExceptionSavedMap.txt").getAbsolutePath();
			File f = new File(path);
			assertThrows(NumberFormatException.class, () -> gs.loadGameMF(f));
			log.info("Test passed");
		}
		
		@Test
		void decrementBoxCounter() {
			log.info("Testing correct use of decrementBoxCounter method");
			gs.decrementBoxCounter();
			assertEquals(4, gs.getGenericCounter().getCurrentCount().getBoxCount());
			log.info("Test passed");
		}
		
		@Test
		void decrementWMCounter() {
			log.info("Testing correct use of decrementBoxCounter method");
			gs.decrementWMCounter();
			assertEquals(9, gs.getGenericCounter().getCurrentCount().getCount());
			log.info("Test passed");
		}
		
		@Test
		void decrementGlobalCounter() {
			log.info("Testing correct use of decrementBoxCounter method");
			gs.decrementGlobalCounter();
			assertEquals(14, gs.getGenericCounter().getCurrentCount().getGlobalCount());
			log.info("Test passed");
		}
	}
}
