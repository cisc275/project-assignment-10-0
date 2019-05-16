package main_package;

import java.io.Serializable;

// author Sicheng Tian, Yixiong Wu
public class Element implements Serializable{
	// everything exists in the game besides the background is an element
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// x-axis location
	int x;
	// y-axis location
	int y;
	ItemType it;
	int xHitSize1, xHitSize2, yHitSize1, yHitSize2;

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

	public int getxHitSize1() {
		return xHitSize1;
	}

	public int getxHitSize2() {
		return xHitSize2;
	}

	public int getyHitSize1() {
		return yHitSize1;
	}

	public int getyHitSize2() {
		return yHitSize2;
	}
	
}
