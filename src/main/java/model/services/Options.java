package model.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;

public class Options implements OptionsI{
	
	private String fileSeparator = File.separator;
	
	public Options() {}
	
	public char[][] newGame(String fileName) {
		char[][] map = null;
		try {
			File file = new File(fileName);
			Scanner s = new Scanner(file);
			
			//String levelName = s.nextLine();
			s.nextLine();
			int rows = s.nextInt();
			int colums = s.nextInt();
			
			map = new char[rows][colums];
			
			s.nextLine();
			
			for(int i = 0; i<rows; i++) {
				String row = s.nextLine();
				for(int j = 0; j<colums; j++) {
					map[i][j] = row.charAt(j);
				}
			}
			
			s.close();
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
		return map;

	}
	
	public void saveGame(char[][] map, WarehouseMan w, ArrayList<GameObjectI> gs, Stack<ActionI> s, int levelNumber, File file) {
			
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
			
			//Escribir el número del nivel
			writer.write(String.valueOf(levelNumber));
			writer.newLine();

			//Escribir las dimensiones de la matriz
			writer.write(String.valueOf(map.length) + " ");
			writer.write(String.valueOf(map[0].length));
			writer.newLine();
			
			//Escribir la posicion del jugador
			writer.write(String.valueOf(w.getX()) + " ");
			writer.write(String.valueOf(w.getY()));
			writer.newLine();
			
			//Escribir las posiciones de las metas
			Iterator<GameObjectI> it = gs.iterator();
			//Ecribir el tamaño del ArrayList
			writer.write(String.valueOf(gs.size()));
			writer.newLine();
			while(it.hasNext()) {
				GoalPosition g = (GoalPosition) it.next();
				//Cada linea tiene la posicion de una meta
				writer.write(String.valueOf(g.getX()) + " ");
				writer.write(String.valueOf(g.getY()));
				writer.newLine();
			}
			
			//Escribir el mapa
			for(int i = 0; i<map.length; i++) {
				for(int j = 0; j<map[0].length; j++) {
					writer.write(map[i][j]);
				}
				writer.newLine();
			}
			
			//Escribir contadores
			writer.write(String.valueOf(w.getBoxCount()));
			writer.newLine();
			writer.write(String.valueOf(w.getCount()));
			writer.newLine();
			writer.write(String.valueOf(w.getGlobalCount()));
			writer.newLine();
			
			//Escribir pila de acciones
			String fileNameWithoutExtension = file.getName().substring(0, file.getName().lastIndexOf("."));
			File stackFile = new File("games_saved" + fileSeparator + "stack_info" + fileSeparator + fileNameWithoutExtension + "_stack.dat");
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(stackFile))) {
	            oos.writeObject(s);
	        } catch (IOException e) {
	            e.getMessage();
	        }
			
		} catch (IOException e) {
			e.getMessage();
		}
	}
	
	public char[][] loadGame(WarehouseMan w, ArrayList<GameObjectI> gs, ActionsManagerI am, int levelNumber, File file) throws NumberFormatException, IlegalPositionException{
		
        char[][] map = null;

			
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			
			//Obtener el número del nivel
			String ln = reader.readLine();
			levelNumber = Integer.parseInt(ln);

			//Obtener las dimensiones del mapa
			String pos = reader.readLine();
			String cont;
			String[] posSplit = pos.split(" ");
			
			map = new char[Integer.parseInt(posSplit[0])][Integer.parseInt(posSplit[1])];
			
			//Falta leer las metas y la pos del jugador
			pos = reader.readLine();
			posSplit = pos.split(" ");
			w.setX(Integer.parseInt(posSplit[0]));
			w.setY(Integer.parseInt(posSplit[1]));
			
			pos = reader.readLine();
			int n = Integer.parseInt(pos);
			
			gs = new ArrayList<GameObjectI>();
			while(n>0) {
				pos = reader.readLine();
				posSplit = pos.split(" ");
				
				GoalPosition g = new GoalPosition(Integer.parseInt(posSplit[0]), Integer.parseInt(posSplit[1]));
				gs.add((GameObjectI) g);
				
				n--;
			}
			
			//Obtener el mapa
			for(int i = 0; i<map.length; i++) {
				String line = reader.readLine();
				for(int j = 0; j<map[0].length; j++) {
					map[i][j] = line.charAt(j);
				}
			}
			
			//leer contadores
			cont = reader.readLine();
			w.setBoxCount(Integer.parseInt(cont));
			
			cont = reader.readLine();
			w.setCount(Integer.parseInt(cont));
			
			cont = reader.readLine();
			w.setGlobalCount(Integer.parseInt(cont));
			
			//leer stack
			String fileNameWithoutExtension = file.getName().substring(0, file.getName().lastIndexOf("."));
			File stackFile = new File("games_saved" + fileSeparator + "stack_info" + fileSeparator + fileNameWithoutExtension + "_stack.dat");
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(stackFile))) {
	            Stack<ActionI> restoredStack = (Stack<ActionI>) ois.readObject();
	            am.setActions(restoredStack);
	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
