package main_package;
// author Sicheng Tian, Yixiong Wu
public class Element {
	// everything exists in the game besides the background is an element
	
	// x-axis location
	int x;
	// y-axis location
	int y;
	ItemType it;

	public Element(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// x-position getter
	public int getX() {
		return x;
	}
	// y-position getter
	public int getY() {
		return y;
	}
	// x-position setter
	public void setX(int xChange) {
		x += xChange;
	}
	// y-position setter
	public void setY(int yChange) {
		y+= yChange;
	}

	public void move() {
		
	}
	// type getter
	public ItemType getType() {
		return it;
	}
	
}
