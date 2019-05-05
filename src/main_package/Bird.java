package main_package;

//author: Sicheng Tian
public class Bird extends Element{
	// represent how many lives left
	// could also represent the NUMBER OF EGG in NH2 !!!!
	int life;
	// represent the type of bird
	BirdType bt;
	int itemsCollected;
	int xVector;
	int yVector;
	
	public Bird (int x, int y, int l, BirdType b) {
		super(x,y);
		life = l;
		bt = b;
		xVector = 0;
		yVector = 0;
	}
	
	public BirdType getBType() {
		return bt;
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
	public void move() {
		//System.out.println(x + " + " + xVector);
		//System.out.println(y + " + " + yVector);
		 x += xVector;
		 y += yVector;
		 //System.out.println("Bird Position(" + x + ", " + y + ")");
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
	
	public int getItemsCollected() {
		return itemsCollected;
	}
	
	public void setItemsCollected(int ic) {
		this.itemsCollected = ic;
	}
	
	public int getXVector() {
		return xVector;
	}
	
	public int getYVector() {
		return yVector;
	}
	
	public void setXVector(int x) {
		xVector = x;
	}
	
	public void setYVector(int y) {
		yVector = y;
	}
}
