package model.services;

public class Action implements ActionI {
	
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private char[][] mat;
	private boolean lastBox;
	
	public Action(int x, int y, char[][] level) {
		this.x = x;
		this.y = y;
		this.mat = level;
		this.lastBox = false;
	}
	
	public Action() {}

	public char[][] getMat() {
		return mat;
	}

	public void setMat(char[][] mat) {
		this.mat = mat;
	}
	
	public boolean isLastBox() {
		return lastBox;
	}
	
	public void setLastBox(boolean lastBox) {
		this.lastBox = lastBox;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
