package main_package;

import java.io.Serializable;

// author:¡¡Sicheng Tian, Yixiong Wu
public class CollectedItem extends Element implements Serializable{
	//represents which kind of item it is
	//ItemType it;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//represents whether the item is collected or not, every item is initialized with a false value
	boolean collected;
	
	public CollectedItem(int x, int y, ItemType i) {
		super(x,y);
		it = i;
		collected = false;
		switch(i) {
		case STICK:
			xHitSize1 = 0; 
			xHitSize2 = 100;
			yHitSize1 = 0;
			yHitSize2 = 100;
			break;
		case RAT:
			xHitSize1 = 0;
			xHitSize2 = 120;
			yHitSize1 = 0;
			yHitSize2 = 80;
			break;
		case EGG:
			xHitSize1 = 0;
			xHitSize2 = 30;
			yHitSize1 = 0;
			yHitSize2 = 50;
			break;
		case NEST:
			xHitSize1 = 0;
			xHitSize2 = 225;
			yHitSize1 = 0;
			yHitSize2 = 150;
			break;
		}
	}
	
	// call when the item is collected, change boolean collected to true
	public void isCollected() {
		collected = true;
	}
	// collected getter
	public boolean getCollected() {
		return collected;
	}
	// getType getter
	public ItemType getType() {
		return it;
	}
}
