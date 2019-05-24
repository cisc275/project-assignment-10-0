package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main_package.CollectedItem;
import main_package.ItemType;

class CollectedItemTest {
	CollectedItem a = new CollectedItem(0,0,ItemType.EGG);
	CollectedItem b = new CollectedItem(0,0,ItemType.STICK);
	CollectedItem c = new CollectedItem(0,0,ItemType.RAT);
	CollectedItem d = new CollectedItem(0,0,ItemType.NEST);
	
	@Test
	void test() {
		a.isCollected();
		assertTrue(a.getCollected());
		assertEquals(ItemType.EGG,a.getType());
	}
}
