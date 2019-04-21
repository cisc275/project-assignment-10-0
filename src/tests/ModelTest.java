package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main_package.Bird;
import main_package.BirdType;
import main_package.CollectedItem;
import main_package.Element;
import main_package.HitItem;
import main_package.ItemType;
import main_package.Model;
import main_package.Type;

class ModelTest {

	@Test
	void testWinGame() {
		Model m = new Model(600, 500, 32, 32);
		m.winGame();
		assertEquals(m.getCurState(), Type.WIN);
	}

	@Test
	void testGameOver() {
		Model m = new Model(600, 500, 32, 32);
		m.gameOver();
		assertEquals(m.getCurState(), Type.GAMEOVER);
	}

	@Test
	void testUpdatePositionOP() {
		Model m = new Model(600, 500, 32, 32);
		m.setCurState(Type.OP);
		ArrayList<Element> e = new ArrayList<Element>();
		Element e1 = new HitItem(50, 50, ItemType.FISH);
		e.add(e1);
		m.setList(e);
		m.updatePosition();
		assertEquals(50 - Math.abs(Model.xIncr), m.getList().get(0).getX());
		assertEquals(50, m.getList().get(0).getY());
		assertEquals(true,m.getUpdateL());
	}
	
	@Test
	void updateList() {
		
	}
	
	@Test
	void testUpdateBirdPositionOP() {
		Model m = new Model(600, 500, 32, 32);
		m.setCurState(Type.OP);
		m.setBird(0,0,3,BirdType.OSPREY);
		m.updateBirdPosition(Model.xIncr, Model.yIncr);
		assertEquals(Model.xIncr, m.getBird().getX());
		assertEquals(Model.yIncr, m.getBird().getY());
	}
	
	@Test
	void testSubmitQuizofNH() {
		Model m = new Model(600, 500, 32, 32);
		m.submitQuiz();
		
		assertEquals(m.getEggs(),m.getNumAns()); // for eggs
		assertFalse(m.getQuizing());
		assertEquals(BirdType.NH,m.getCurState());
		
	}
	
	@Test
	void testCheckQuiz() {
		Model m = new Model(600, 500, 32, 32);
		m.setQuiz("1111?", "yes");
		m.getQuiz().setChosenAnser("yes");
		m.checkQuiz();
		assertEquals(false, m.getQuizing());
		
		m.setBird(0, 0, 3, BirdType.NH);
		m.getQuiz().setChosenAnser("no");
		m.checkQuiz();
		assertEquals(false,m.getQuizing());
		assertEquals(2, m.getBird().getLife());
	}
	
	@Test
	void testStartQuiz() {
		Model m = new Model(600, 500, 32, 32);
		m.startQuiz();
		assertEquals(true, m.getQuizing());
	}
	
	@Test
	void testCheckCollision() {
		Model m = new Model(600, 500, 32, 32);
		m.setBird(50, 50, 3, BirdType.OSPREY);
		m.checkCollision(new HitItem(50,50,ItemType.AIRPLANE));
		assertEquals(2, m.getBird().getLife());
		assertEquals(true, m.getQuizing());
		
		m.checkCollision(new HitItem(50,50,ItemType.FISH));
		assertEquals(3, m.getBird().getLife());
		
		m.checkCollision(new HitItem(50,50,ItemType.WINFLAG));
		assertEquals(Type.WIN, m.getCurState());
		
	}
	
	@Test
	void testNoCollision() {
		Model m = new Model(600, 500, 32, 32);
		m.setBird(50, 50, 2, BirdType.OSPREY);
		assertEquals(false, m.checkCollision(new HitItem(80,100,ItemType.AIRPLANE)));
		assertEquals(false, m.checkCollision(new HitItem(80,100,ItemType.WINFLAG)));
	}
	
	@Test
	void testCollisionNH1() {
		Model m = new Model(600, 500, 32, 32);
		m.setBird(50, 50, -1, BirdType.NH);
		ArrayList<Element> list = new ArrayList<>();
		CollectedItem item = new CollectedItem(50,50,ItemType.STICK);
		CollectedItem item2 = new CollectedItem(10,10,ItemType.STICK);
		list.add(item);
		list.add(item2);
		m.setList(list);
		m.collisionNH1();
		assertEquals(true, item.getCollected());
		
		ArrayList<Element> list2 = new ArrayList<>();
		list2.add(item);
		m.setList(list2);
		m.collisionNH1();
		assertEquals(true, m.getQuizing());
	}
	
	@Test
	void testCollisionNH2() {
		Model m = new Model(600, 500, 32, 32);
		m.setBird(50, 50, -1, BirdType.NH);
		HitItem fox = new HitItem(80,80,ItemType.FOX);
		CollectedItem egg = new CollectedItem(80,80,ItemType.EGG);
		ArrayList<Element> list = new ArrayList<>();
		list.add(egg);
		m.setList(list);
		assertEquals(list, m.getList());
		m.setEgg(1);
		m.collisionNH2(fox);
		assertEquals(0, m.getEggs());
		assertEquals(Type.GAMEOVER, m.getCurState());
		
		
	}
	
	@Test
	void testGettersAndSetters() {
		Model m = new Model(600, 500, 32, 32);
		m.setBird(50, 50, -1, BirdType.NH);
		Bird b = new Bird(66,64,0,BirdType.OSPREY);
		m.setBird(b);
		assertEquals(b,m.getBird());
		assertEquals(600,m.getFrameW());
		assertEquals(500,m.getFrameH());
	}

}
