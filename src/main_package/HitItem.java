package main_package;

// author Sicheng Tian
public class HitItem extends Element{
	//represents which kind of item it is
	ItemType it;
	int xVector;
	int yVector;
	
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
}
