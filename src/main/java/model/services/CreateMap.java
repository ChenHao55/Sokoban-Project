package model.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import model.beans.GoalPosition;
import model.beans.WarehouseMan;
import model.exceptions.IlegalPositionException;


public class CreateMap implements CreateMapI {
	
	private final char GOAL = '*';
	private final char PLAYER = 'W';
	
	public CreateMap() {}

	@Override
	public char[][] createMap(String fileName, WarehouseMan w, ArrayList<GoalPosition> gs) throws FileNotFoundException {
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
	
	public char[][] createMapByMat(char[][] map, WarehouseMan w, GoalPosition g) throws FileNotFoundException, IlegalPositionException {
			
		for(int i = 0; i<map.length; i++) {
			for(int j = 0; j<map[0].length; j++) {
				if (map[i][j] == PLAYER) {
					w.setX(i);
					w.setY(j);
				} else if (map[i][j] == GOAL) {
					g.setX(i);
					g.setY(j);
				}
			}
		}
			
		return map;
	}
}
