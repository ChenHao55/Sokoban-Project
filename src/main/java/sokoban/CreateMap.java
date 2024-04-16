package sokoban;

import java.io.File;
import java.util.Scanner;

import exceptions.EmptyFileException;
import exceptions.FileException;

public class CreateMap implements CreateMapI {
	
	public CreateMap() {}

	@Override
	public char[][] createMap(String fileName) throws EmptyFileException, FileException {
		
		char[][] map = null;
		File file = new File(fileName);
		
		if(file.exists() && file.canRead()) {
			if (file.length() != 0) {
				
				Scanner s = new Scanner(fileName);
				String levelName = s.nextLine();
				int rows = s.nextInt();
				int colums = s.nextInt();
				
				map = new char[rows][colums];
				
				for(int i = 0; i<rows; i++) {
					String fila = s.nextLine();
					for(int j = 0; j<colums; j++) {
						map[i][j] = fila.charAt(j);
					}
				}
				
				s.close();
			} else {
				throw new EmptyFileException("The file is empty, map can't be created");
			}
		} else {
			throw new FileException("FIle can't be read or don't exists");
		}
		
		return map;
	}

}
