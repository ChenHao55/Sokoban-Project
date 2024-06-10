package model.beans;

public class Counter {
	private int count;
	private int boxCount;
	private int globalCount;
	
	public Counter() {
		this.boxCount = 0;
		this.count = 0;
		this.globalCount = 0;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getBoxCount() {
		return boxCount;
	}
	
	public void setBoxCount(int boxCount) {
		this.boxCount = boxCount;
	}
	
	public int getGlobalCount() {
		return globalCount;
	}
	
	public void setGlobalCount(int globalCount) {
		this.globalCount = globalCount;
	}
}
