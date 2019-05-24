package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main_package.Bird;
import main_package.BirdType;

class BirdTest {
	Bird osprey1 = new Bird(0,0,3,BirdType.NH);
	Bird osprey2 = new Bird(0,0,3,BirdType.OSPREY);
	
	@Test
	void testGetxMove() {
		assertEquals(0,osprey2.getxMove());
	}
	
	@Test
	void testGetyMove() {
		assertEquals(10,osprey2.getyMove());
	}
	
	@Test
	void testGetBType() {
		assertEquals(BirdType.NH,osprey1.getBType());
	}
	
	@Test
	void testSetLife() {
		osprey1.setLife(1);
		assertEquals(1,osprey1.getLife());
	}
	
	@Test
	void testMove1() {
		osprey1.setXVector(1);
		osprey1.setYVector(1);
		osprey1.move();
		assertEquals(1,osprey1.getX());
	}
	
	@Test
	void testMove2() {
		osprey1.setXVector(1);
		osprey1.setYVector(1);
		osprey1.move();
		assertEquals(1,osprey1.getY());
	}
	
	@Test
	void testCollision() {
		osprey1.collision();
		assertEquals(2,osprey1.getLife());
	}
	
	@Test
	void testEat() {
		osprey1.eat();
		assertEquals(4,osprey1.getLife());
	}
	
	@Test
	void testGetItemsCollected() {
		assertEquals(0,osprey1.getItemsCollected());
	}

	@Test
	void testSetItemsCollected() {
		osprey1.setItemsCollected(1);
		assertEquals(1,osprey1.getItemsCollected());
	}
	
	@Test
	void testXVector() {
		assertEquals(0,osprey1.getXVector());
	}
	
	@Test
	void testYVector() {
		assertEquals(0,osprey1.getYVector());
	}
}
