package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import main_package.Bird;
import main_package.BirdType;
import main_package.CollectedItem;
import main_package.ItemType;
import main_package.Model;
import main_package.NHModel;
import main_package.Quiz;
import main_package.Type;

class NHModelTest {

	
	@Test
	void testSetUpGame() {
		Model m = new NHModel(1550,838,150,150,null);
		m.setUpGame();
	}
	
	@Test
	void testCreateTimer() {
		Model m = new NHModel(1550,838,150,150,null);
		m.setTimeCount(0);
		m.createTimer(0);
	}
	
	@Test 
	void testUpdatePosition() {
		HashMap<String, int[]> size = new HashMap<>();
		size.put("nh", new int[] {150,150});
		size.put("stick", new int[] {100,100});
		size.put("rat", new int[] {120,80});
		size.put("nestgold", new int[] {225, 150});
		Model m = new NHModel(1550,838,150,150,size);
		((NHModel) m).setNest(new CollectedItem(500,500, ItemType.NEST));
		m.setBird(new Bird(450,450, 3, BirdType.NH));
		m.setList(new ArrayList<>());
		m.getList().add(new CollectedItem(300,300, ItemType.STICK));
		m.setCurState(Type.NH1);
		m.updatePosition();
		m.getBird().setItemsCollected(5);
		m.updatePosition();
		assertEquals("nest5", ((NHModel) m).getNestBuild());
		m.getBird().setItemsCollected(10);
		m.updatePosition();
		assertEquals("nestgold", ((NHModel) m).getNestBuild());
		m.setBird(new Bird(450,450, 3, BirdType.NH));
		m.setList(new ArrayList<>());
		//m.getBird().setItemsCollected(10);
		m.setCurState(Type.NH1);
		m.setQuizzes(new ArrayList<>());
		m.getQuizzes().add(new Quiz("1","2",new String[4]));
		m.updatePosition();
		assertEquals(true, m.getQuizing());
		m.setTimeCount(0);
		m.updatePosition();
		assertEquals(Type.GAMEOVER, m.getCurState());
		
	}
	
	@Test
	void testCheckCollision() {
		HashMap<String, int[]> size = new HashMap<>();
		size.put("nh", new int[] {150,150});
		size.put("stick", new int[] {100,100});
		Model m = new NHModel(1550,838,150,150,size);
		m.setBird(new Bird(300,300, 3, BirdType.NH));
		m.setList(new ArrayList<>());
		m.getList().add(new CollectedItem(300,300, ItemType.STICK));
		assertEquals(true, m.checkCollision(new CollectedItem(0,0,ItemType.RAT)));
	}
	
	@Test
	void testOutOfFrame() {
		HashMap<String, int[]> size = new HashMap<>();
		size.put("nh", new int[] {150,150});
		Model m = new NHModel(1550,838,150,150,size);
		m.setBird(new Bird(2000,2000, 3, BirdType.NH));
		assertEquals(true, m.outOfFrame());
	}
	
	@Test
	void testCheckQuiz() {
		Model m = new NHModel(1550,838,150,150,null);
		Model.setEggs(0);
		m.setQuizzes(new ArrayList<>());
		m.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		m.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		m.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		m.startQuiz();
		m.getQuiz().setChosenAnser("2");
		m.checkQuiz();
		m.getQuiz().setChosenAnser("3");
		m.checkQuiz();
		
		Model m2 = new NHModel(1550,838,150,150,null);
		Model.setEggs(0);
		m2.setQuizzes(new ArrayList<>());
		m2.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		m2.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		m2.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		m2.startQuiz();
		m2.getQuiz().setChosenAnser("3");
		m2.checkQuiz();
		m2.getQuiz().setChosenAnser("3");
		m2.checkQuiz();
		m2.getQuiz().setChosenAnser("3");
		m2.checkQuiz();
	}
	
	@Test
	void testTutorial() {
		HashMap<String, int[]> size = new HashMap<>();
		size.put("nh", new int[] {150,150});
		size.put("stick", new int[] {100,100});
		Model m = new NHModel(1550,838,150,150,size);
		m.setList(new ArrayList<>());
		m.getList().add(new CollectedItem(300,300, ItemType.STICK));
		m.setBird(new Bird(300,300, 3, BirdType.NH));
		m.setTimeCount(0);
		m.tutorial();
		assertEquals(false, ((NHModel) m).drawDE());
	}

}
