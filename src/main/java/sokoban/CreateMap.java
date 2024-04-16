package sokoban;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CreateMap implements CreateMapI {
	
	public CreateMap() {}

	@Override
	public char[][] createMap(String fileName) throws FileNotFoundException {
		char[][] map = null;
		try {
			Scanner s = new Scanner(new File(fileName));
			
			String levelName = s.nextLine();
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
}
