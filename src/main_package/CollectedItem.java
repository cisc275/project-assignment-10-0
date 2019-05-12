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
