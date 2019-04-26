package main_package;

//author: Sicheng Tian
public class Bird extends Element{
	// represent how many lives left
	int life;
	// represent the type of bird
	BirdType bt;
	
	int itemsCollected;
	
	public Bird (int x, int y, int l, BirdType b) {
		super(x,y);
		life = l;
		bt = b;
	}
	
	//getter for x
	public int getX() {	return this.x;}
	// getter for y
	public int getY() {	return this.y;}
	
	//getter for life;
	public int getLife() {return this.life;};
	
	public void setLife(int l) {
		life = l;
	}
	
	// move the bird, change x and y by adding incX and incY on x and y
	public void move(int incX, int incY) {
		 x += incX;
		 y += incY;
		 //System.out.println("Bird Position(" + x + ", " + y + ")");
	}
	
	/// losing life after collision with planes, fox
	public void collision() {
		life--;
	}
	
	// add one to the life
	public void eat() {
		life++;
	}
	
	public int getItemsCollected() {
		return itemsCollected;
	}
	
	public void setItemsCollected(int ic) {
		this.itemsCollected = ic;
	}
}
