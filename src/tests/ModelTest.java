package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import main_package.Bird;
import main_package.BirdType;
import main_package.CollectedItem;
import main_package.Element;
import main_package.HitItem;
import main_package.ItemType;
import main_package.Model;
import main_package.OPModel;
import main_package.Type;

class ModelTest {

	@Test
	void testWinGame() {
		Model m = new OPModel(600, 500, 32, 32, null);
		m.winGame();
		assertEquals(m.getCurState(), Type.MAINMENU);
	}

	@Test
	void testGameOver() {
		Model m = new OPModel(600, 500, 32, 32, null);
		m.gameOver();
		assertEquals(m.getCurState(), Type.GAMEOVER);
	}
	
	@Test
	void testCollision() {
		HashMap<String, int[]> size = new HashMap<>();
		size.put("osprey", new int[] {150,150});
		size.put("airplane", new int[] {300,200});
		Model m = new OPModel(1550, 838, 150,150, size);
		m.setBird(new Bird(450,450, 3, BirdType.OSPREY));
		assertEquals(true, m.collisionF(new HitItem(500,500, ItemType.AIRPLANE,0,0)));
		assertEquals(true, m.collisionF(new HitItem(400,450, ItemType.WINFLAG,0,0)));
		assertEquals(false, m.collisionF(new HitItem(600,350, ItemType.AIRPLANE,0,0)));
		assertEquals(true, m.collisionF(new HitItem(300,350, ItemType.AIRPLANE,0,0)));
		
		
		
	}
//
//	@Test
//	void testUpdatePositionOP() {
//		Model m = new Model(600, 500, 32, 32, null);
//		m.setCurState(Type.OP);
//		ArrayList<Element> e = new ArrayList<Element>();
//		Element e1 = new HitItem(-12, 50, ItemType.FISH,-10,0);
//		Element e2 = new HitItem(21,100, ItemType.FISH,0,0);
//		e.add(e1);
//		e.add(e2);
//		m.setList(e);
//		m.setBird(16,400,3,BirdType.OSPREY);
//		m.updatePosition();
//		//assertEquals(-17, m.getList().get(0).getX());
//		//assertEquals(50, m.getList().get(0).getY());
//		//assertEquals(0, m.getList().size());
//		assertEquals(true,m.getUpdateL());
//		m.updatePosition();
//		assertEquals(false,m.getUpdateL());
//	}
//	
//	@Test
//	void updateList() {
//		Model m = new Model(600, 500, 32, 32, null);
//		m.setUpdateL();
//		ArrayList<Element> e = new ArrayList<Element>();
//		m.setList(e);
//		m.updateList();
//		assertEquals(false, m.getUpdateL());
//		assertEquals(1, e.size());
//	}
//	
//	@Test
//	void testUpdateBirdPositionOP() {
//		Model m = new Model(600, 500, 32, 32, null);
//		m.setCurState(Type.OP);
//		m.setBird(0,0,3,BirdType.OSPREY);
//		m.getBird().setXVector(5);
//		m.getBird().setYVector(5);
//		m.updateBirdPosition();
//		assertEquals(Model.xIncr, m.getBird().getX());
//		assertEquals(Model.yIncr, m.getBird().getY());
//		
//		m.setCurState(Type.NH1);
//		m.setBird(0,0,3,BirdType.NH);
//		m.getBird().setXVector(5);
//		m.getBird().setYVector(5);
//		ArrayList<Element> e = new ArrayList<Element>();
//		Element e1 = new CollectedItem(12, 50, ItemType.STICK);
//		Element e2 = new CollectedItem(21,100, ItemType.STICK);
//		e.add(e1);
//		e.add(e2);
//		m.setList(e);
//		m.updateBirdPosition();
//	}
//	
//	@Test
//	void testSubmitQuizofNH() {
//		Model m = new Model(600, 500, 32, 32, null);
//		m.submitQuiz();
//		
//		assertEquals(m.getEggs(),m.getNumAns()); // for eggs
//		assertFalse(m.getQuizing());
//		//assertEquals(BirdType.NH,m.getCurState());
//		
//	}
//	
//	@Test
//	void testCheckQuiz() {
//		Model m = new Model(600, 500, 32, 32, null);
//		m.setQuiz("1111?", "yes", new String[4]);
//		m.getQuiz().setChosenAnser("yes");
//		m.checkQuiz();
//		assertEquals(false, m.getQuizing());
//		
//		m.setBird(0, 0, 3, BirdType.OSPREY);
//		m.getQuiz().setChosenAnser("no");
//		m.checkQuiz();
//		assertEquals(false,m.getQuizing());
//		//System.out.println(m.getBird().getLife() + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		assertEquals(3, m.getBird().getLife());
//	}
//	
//	@Test
//	void testStartQuiz() {
//		Model m = new Model(600, 500, 32, 32,null);
//		m.startQuiz();
//		assertEquals(true, m.getQuizing());
//	}
//	
//	@Test
//	void testCheckCollision() {
//		Model m = new Model(600, 500, 32, 32,null);
//		m.setBird(50, 50, 3, BirdType.OSPREY);
//		m.checkCollision(new HitItem(50,50,ItemType.AIRPLANE,0,0));
//		assertEquals(2, m.getBird().getLife());
//		assertEquals(true, m.getQuizing());
//		
//		m.checkCollision(new HitItem(50,50,ItemType.FISH,0,0));
//		assertEquals(3, m.getBird().getLife());
//		
//		m.checkCollision(new HitItem(50,50,ItemType.WINFLAG,0,0));
//		assertEquals(Type.WIN, m.getCurState());
//		
//	}
//	
//	@Test
//	void testNoCollision() {
//		Model m = new Model(600, 500, 32, 32, null);
//		m.setBird(50, 50, 2, BirdType.OSPREY);
//		assertEquals(false, m.checkCollision(new HitItem(80,100,ItemType.AIRPLANE,0,0)));
//		assertEquals(false, m.checkCollision(new HitItem(80,100,ItemType.WINFLAG,0,0)));
//	}
//	
//	@Test
//	void testCollisionNH1() {
//		Model m = new Model(600, 500, 32, 32, null);
//		m.setBird(50, 50, -1, BirdType.NH);
//		ArrayList<Element> list = new ArrayList<>();
//		CollectedItem item = new CollectedItem(50,50,ItemType.STICK);
//		CollectedItem item2 = new CollectedItem(10,10,ItemType.STICK);
//		list.add(item);
//		list.add(item2);
//		m.setList(list);
//		m.collisionNH1();
//		assertEquals(true, item.getCollected());
//		
//		ArrayList<Element> list2 = new ArrayList<>();
//		list2.add(item);
//		m.setList(list2);
//		m.collisionNH1();
//		//assertEquals(true, m.getQuizing());
//	}
//	
//	@Test
//	void testCollisionNH2() {
//		Model m = new Model(600, 500, 32, 32,null);
//		m.setBird(50, 50, -1, BirdType.NH);
//		HitItem fox = new HitItem(80,80,ItemType.FOX,0,0);
//		CollectedItem egg = new CollectedItem(80,80,ItemType.EGG);
//		ArrayList<Element> list = new ArrayList<>();
//		list.add(egg);
//		m.setList(list);
//		assertEquals(list, m.getList());
//		m.setEgg(1);
//		m.collisionNH2(fox);
//		assertEquals(0, m.getEggs());
//		assertEquals(Type.GAMEOVER, m.getCurState());
//		
//		
//	}
//	
//	@Test
//	void testGettersAndSetters() {
//		Model m = new Model(600, 500, 32, 32, null);
//		m.setBird(50, 50, -1, BirdType.NH);
//		Bird b = new Bird(66,64,0,BirdType.OSPREY);
//		m.setBird(b);
//		assertEquals(b,m.getBird());
//		assertEquals(600,m.getFrameW());
//		assertEquals(500,m.getFrameH());
//	}

}
