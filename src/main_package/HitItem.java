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
	}
	

	// getter for x
	public int getX() {
		return this.x;
	}
	
	//getter for y
	public int getY() {
		return this.y;
	}
	
	public ItemType getType() {
		return it;
	}
	
	
	// move the item, change x and y by adding or subtracting incX and incY on x and y.
	public void move() {
		x += xVector;
		y += yVector;
	}
	
	public int getxVector() {
		return xVector;
	}

	public void setxVector(int xVector) {
		this.xVector = xVector;
	}

	public int getyVector() {
		return yVector;
	}

	public void setyVector(int yVector) {
		this.yVector = yVector;
	}
	
	public void changeDirection() {
		directionChange = !directionChange;
	}
	public boolean getDirectionChange() {
		return directionChange;
	}
	
}
