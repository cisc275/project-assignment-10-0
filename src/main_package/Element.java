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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int xChange) {
		x += xChange;
	}
	
	public void setY(int yChange) {
		y+= yChange;
	}
	
	public void move() {
		
	}
	
	public ItemType getType() {
		return it;
	}
	
}
