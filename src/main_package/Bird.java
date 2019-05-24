package main_package;

import java.io.Serializable;

//author: Sicheng Tian
public class Bird extends Element implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// represent how many lives left
	// could also represent the NUMBER OF EGG in NH2 !!!!
	int life;
	// represent the type of bird
	BirdType bt;
	int itemsCollected;
	int xVector;
	int yVector;
	int xMove;
	int yMove;
	
	public Bird (int x, int y, int l, BirdType b) {
		super(x,y);
		life = l;
		bt = b;
		xVector = 0;
		yVector = 0;
		switch(b) {
		case OSPREY:
			xMove = 0;
			yMove = 10;
			xHitSize1 = 0; 
			xHitSize2 = 150;
			yHitSize1 = 0;
			yHitSize2 = 150;
			break;
		case NH:
			xMove = 7;
			yMove = 7;
			xHitSize1 = 0;
			xHitSize2 = 150;
			yHitSize1 = 0;
			yHitSize2 = 150;
			break;
		}
	}
	//getter for xmove
	public int getxMove() {
		return xMove;
	}
	//getter for ymove
	public int getyMove() {
		return yMove;
	}
	//getter for bird type
	public BirdType getBType() {
		return bt;
	}
	//getter for x
	public int getX() {	return this.x;}
	// getter for y
	public int getY() {	return this.y;}
	
	//getter for life;
	public int getLife() {return this.life;};
	
	//setter for life
	public void setLife(int l) {
		life = l;
	}
	
	// move the bird, change x and y by adding incX and incY on x and y
	public void move() {
		 x += xVector;
		 y += yVector;
	}
	
	/// losing life after collision with planes, fox
	public void collision() {
		System.out.println("lose life");
		life--;
	}
	
	// add one to the life
	public void eat() {
		life++;
	}
	// getter for itemsCollected
	public int getItemsCollected() {
		return itemsCollected;
	}
	// setter for itemsCollected
	public void setItemsCollected(int ic) {
		this.itemsCollected = ic;
	}
	// getter for XVector
	public int getXVector() {
		return xVector;
	}
	// getter for YVector
	public int getYVector() {
		return yVector;
	}
	// setter for XVector
	public void setXVector(int x) {
		xVector = x;
	}
	// setter for YVector
	public void setYVector(int y) {
		yVector = y;
	}
}
