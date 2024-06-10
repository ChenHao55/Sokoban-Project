package model.beans;

public class GenericCounter {
	
	private Counter currentCount = new Counter();
	private Counter levelCount = new Counter();
	
	public Counter getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(Counter currentCount) {
		this.currentCount = currentCount;
	}
	public Counter getLevelCount() {
		return levelCount;
	}
	public void setLevelCount(Counter levelCount) {
		this.levelCount = levelCount;
	}
	
	public void cloneCounter(Counter c1, Counter c2) {
		c1.setBoxCount(c2.getBoxCount());
		c1.setCount(c2.getCount());
		c1.setGlobalCount(c2.getGlobalCount());
	}

}
