package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main_package.Element;
import main_package.ItemType;

class ElementTest {
	Element a = new Element(1,1);
	@Test
	void test() {
		a.setX(1);
		a.setY(1);
		a.move();
		assertEquals(2,a.getX());
		assertEquals(2,a.getY());
		assertEquals(0,a.getxHitSize1());
		assertEquals(0,a.getxHitSize2());
		assertEquals(0,a.getyHitSize1());
		assertEquals(0,a.getyHitSize2());
	}

}
