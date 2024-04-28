package model.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;

public class Options implements OptionsI{
	
	private final char GOAL = '*';
	private final char PLAYER = 'W';
	
	public Options() {}
	
	public char[][] newGame(String fileName, WarehouseMan w, ArrayList<GoalPosition> gs) {
		char[][] map = null;
		try {
			File file = new File(fileName);
			Scanner s = new Scanner(file);
			
			String levelName = s.nextLine();
			int rows = s.nextInt();
			int colums = s.nextInt();
			
			map = new char[rows][colums];
			
			s.nextLine();
			
			for(int i = 0; i<rows; i++) {
				String row = s.nextLine();
				for(int j = 0; j<colums; j++) {
					map[i][j] = row.charAt(j);
					if (row.charAt(j) == PLAYER) {
						w.setX(i);
						w.setY(j);
					} else if (row.charAt(j) == GOAL) {
						GoalPosition g = new GoalPosition();
						g.setX(i);
						g.setY(j);
						gs.add(g);
					}
				}
			}
			
			s.close();
		} catch (FileNotFoundException | IlegalPositionException e) {
			e.getMessage();
		}
		return map;
	}
	
	public void saveGame(char[][] map) {
		
		File file = new File("/home/pproject/eclipse-workspace/sokoban/maps/saved_map.txt");
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
			writer.write(String.valueOf(map.length) + " ");
			writer.write(String.valueOf(map[0].length));
			writer.newLine();
			for(int i = 0; i<map.length; i++) {
				for(int j = 0; j<map[0].length; j++) {
					writer.write(map[i][j]);
				}
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public char[][] loadGame(WarehouseMan w, ArrayList<GoalPosition> gs){
		
		char[][] map = null;
		
		try (BufferedReader reader = new BufferedReader(new FileReader("/home/pproject/eclipse-workspace/sokoban/maps/saved_map.txt"))){
			
			String dimensions = reader.readLine();
			String[] dimensionsArray = dimensions.split(" ");
			int rows = Integer.parseInt(dimensionsArray[0]);
			int cols = Integer.parseInt(dimensionsArray[1]);
			
			map = new char[rows][cols];
			
			for(int i = 0; i<rows; i++) {
				String line = reader.readLine();
				for(int j = 0; j<cols; j++) {
					map[i][j] = line.charAt(j);
					if(line.charAt(j) == PLAYER) {
						try {
							w.setX(i);
							w.setY(j);
						} catch (IlegalPositionException e) {
							e.printStackTrace();
						}
					} else if(line.charAt(j) == GOAL) {
						GoalPosition g = new GoalPosition();
						try {
							g.setX(i);
							g.setY(j);
							gs.add(g);
						} catch (IlegalPositionException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
