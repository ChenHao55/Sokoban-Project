package sokoban;

public class WarehouseMan {
	private int x;
	private int y;
	
	public WarehouseMan(int x, int y){
		if (x < 0 || y < 0){
			//throw new InvalidPreConditionException("ID, name or email have a blank value");
		}
		this.x = x;
		this.y = y;
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
