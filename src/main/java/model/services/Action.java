package model.services;

public class Action implements ActionI {
	
	private int x;
	private int y;
	private char[][] mat;
	private boolean lastBox;
	
	public Action(int x, int y, char[][] level) {
		this.setMat(level);
		this.setX(x);
		this.setY(y);
		this.setLastBox(false);
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
