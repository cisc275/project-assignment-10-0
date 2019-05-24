package main_package;

import java.io.Serializable;

// author Sicheng Tian
public class HitItem extends Element implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//represents which kind of item it is
	//ItemType it;
	int xVector;
	int yVector;
	boolean directionChange = false;
	
	public HitItem(int x, int y, ItemType i, int xV, int yV) {
		super(x,y);
		it = i;
		xVector = xV;
		yVector = yV;
		switch(i) {
		case AIRPLANE:
			xHitSize1 = 50; 
			xHitSize2 = 300;
			yHitSize1 = 50;
			yHitSize2 = 150;
			break;
		case SHIP:
			xHitSize1 = 80;
			xHitSize2 = 270;
			yHitSize1 = 80;
			yHitSize2 = 200;
			break;
		case FISH:
			xHitSize1 = 0;
			xHitSize2 = 115;
			yHitSize1 = 0;
			yHitSize2 = 75;
			break;
		case FOX:
			xHitSize1 = 0;
			xHitSize2 = 100;
			yHitSize1 = 0;
			yHitSize2 = 150;
			break;
		case WINFLAG:
			xHitSize1 = 0;
			xHitSize2 = 200;
			yHitSize1 = 0;
			yHitSize2 = 150;
			break;
		}
	}
	

	// getter for x
	public int getX() {
		return this.x;
	}
	
	//getter for y
	public int getY() {
		return this.y;
	}
	// type getter
	public ItemType getType() {
		return it;
	}
	
	
	// move the item, change x and y by adding or subtracting incX and incY on x and y.
	public void move() {
		x += xVector;
		y += yVector;
	}
	//x-vector getter
	public int getxVector() {
		return xVector;
	}
	//x-vector setter
	public void setxVector(int xVector) {
		this.xVector = xVector;
	}
	//y-vector getter 
	public int getyVector() {
		return yVector;
	}
	//x-vector setter
	public void setyVector(int yVector) {
		this.yVector = yVector;
	}
	//reverse direction
	public void changeDirection() {
		directionChange = !directionChange;
	}
	// direction change getter
	public boolean getDirectionChange() {
		return directionChange;
	}
	
}
