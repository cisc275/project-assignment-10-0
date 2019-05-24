package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main_package.HitItem;
import main_package.ItemType;

class HitItemTest {
	HitItem h1 = new HitItem(0,0,ItemType.FOX,1,1);
	HitItem h2 = new HitItem(0,0,ItemType.AIRPLANE,1,1);
	HitItem h3 = new HitItem(0,0,ItemType.SHIP,1,1);
	HitItem h4 = new HitItem(0,0,ItemType.FISH,1,1);
	HitItem h5 = new HitItem(0,0,ItemType.FOX,1,1);
	HitItem h6 = new HitItem(0,0,ItemType.WINFLAG,1,1);
	@Test
	void test() {
		h1.move();
		assertEquals(1,h1.getX());
		assertEquals(1,h1.getY());
		assertEquals(ItemType.FOX,h1.getType());
		h1.setxVector(1);
		h1.setyVector(1);
		assertEquals(1,h1.getxVector());
		assertEquals(1,h1.getyVector());
		h1.changeDirection();
		assertEquals(true,h1.getDirectionChange());
		assertTrue(h1.getDirectionChange());
		h1.changeDirection();
		assertEquals(false,h1.getDirectionChange());

		
	}

}
