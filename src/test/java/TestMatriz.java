package test.java;

import sokoban.WarehouseMan;
import sokoban.RightAction;
import sokoban.WallException;

public class TestMatriz {
	
	static char[][] mat = {
			{'+','+','+','+'},
			{'+','.','.','+'},
			{'+','W','.','+'},
			{'+','+','+','+'}
	};
	
	static WarehouseMan w = new WarehouseMan(2,1);
	static RightAction r = new RightAction(w, mat);
	
	public static void prueba(WarehouseMan w, RightAction r, char[][] mat) throws WallException {
		r.move(w, mat);
		
		for(int i = 0; i<mat.length; i++) {
			for(int j = 0; j<mat[0].length; j++) {
				System.out.println(mat[i][j] + "\t");
			}
			System.out.println();
		}
	} 
	
	public static void main(String[] args) {
		try {
			prueba(w,r,mat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
