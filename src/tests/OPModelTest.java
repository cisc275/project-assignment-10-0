package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import main_package.Bird;
import main_package.BirdType;
import main_package.HitItem;
import main_package.ItemType;
import main_package.OPModel;
import main_package.Quiz;
import main_package.Type;

class OPModelTest {

	@Test
	void createTimerTest1() {
		// test when drawNA is true
		int time = 5;
		OPModel op1 = new OPModel(1, 1, 1, 1, new HashMap<String, int[]>());
		op1.createTimer(time);
		//Assert.assertEquals(time, op1.getTimeCount());
	}
	@Test
	void createTimerTest2() {
		int time = 5;
		OPModel op1 = new OPModel(1, 1, 1, 1, new HashMap<String, int[]>());
		op1.setDrawNA(false);
		System.out.println(op1.getDrawNA());
		OPModel.setDefaultTime(9);
		op1.createTimer(time);
		//assertEquals(Type.GAMEOVER, op1.getCurState());
		OPModel.setDefaultTime(60);
		
	}
	
	@Test
	void updatePosition() {
		
		OPModel op1 = new OPModel(1, 1, 1, 1, new HashMap<String, int[]>());
		op1.updatePosition();
		HashMap<String, int[]> size = new HashMap<>();
		size.put("osprey", new int[] {150,150});
		size.put("airplane", new int[] {300,200});
		size.put("ship", new int[] {300,200});
		size.put("fish", new int[] {115,75});
		size.put("winflag", new int[] {200,150});
		OPModel op2 = new OPModel(10000, 10000, 300, 300, size);
		op2.setBird(new Bird(450,450, 3, BirdType.OSPREY));
		op2.setList(new ArrayList<>());
		op2.getList().add(new HitItem(500,500, ItemType.AIRPLANE,0,0));
		op2.getList().add(new HitItem(-400,-200, ItemType.AIRPLANE,0,0));
		op2.getList().add(new HitItem(500,500, ItemType.FISH,0,0));
		op2.updatePosition();
		assertEquals(1, op2.getList().size());
		op2.setUpdateL(true);
		op2.updatePosition();
		op2.setTimeCount(op2.getFlagTime());
		op2.setWinFlag(true);
		op2.updatePosition();
	}
	
	@Test
	void checkCollisionTest() {
		HashMap<String, int[]> size = new HashMap<>();
		size.put("osprey", new int[] {150,150});
		size.put("airplane", new int[] {300,200});
		OPModel op1 = new OPModel(300, 300, 1, 1, size);
		op1.setCurState(Type.OP);
		op1.setBird(new Bird(1,1, 3, BirdType.OSPREY));
		HitItem e1 = new HitItem(1, 1, ItemType.AIRPLANE, 1, 1);
		HitItem e2 = new HitItem(1, 1, ItemType.WINFLAG, 1, 1);
		op1.setQuizzes(new ArrayList<>());
		op1.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op1.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op1.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op1.checkCollision(e1);
		op1.checkCollision(e2);
	}
	
	@Test
	void createQuizesTest() throws Exception {
		OPModel op1 = new OPModel(300, 300, 1, 1, new HashMap<String, int[]>());

	}
	
	@Test
	void tutorialTest() {
		OPModel op1 = new OPModel(1550, 838, 1, 1, new HashMap<String, int[]>());
		op1.setBird(new Bird(1,1, 3, BirdType.OSPREY));
		op1.setTimeCount(0);
		op1.tutorial();
		HashMap<String, int[]> size = new HashMap<>();
		size.put("osprey", new int[] {150,150});
		size.put("airplane", new int[] {300,200});
		size.put("ship", new int[] {300,200});
		size.put("fish", new int[] {115,75});
		OPModel op2 = new OPModel(1550, 838, 1, 1, size);
		op2.setBird(new Bird(1550,320, 3, BirdType.OSPREY));
		op2.setTimeCount(0);
		op2.setTutor(1);
		op2.setQuizing(false);
		op2.setList(new ArrayList<>());
		op2.tutorial();
	}
	@Test
	void startQuizTest() throws Exception {
		OPModel op1 = new OPModel(300, 300, 1, 1, new HashMap<String, int[]>());
		HashMap<String, int[]> size = new HashMap<>();
		size.put("osprey", new int[] {150,150});
		size.put("airplane", new int[] {300,200});
		size.put("ship", new int[] {300,200});
		size.put("fish", new int[] {115,75});
		OPModel op2 = new OPModel(300, 300, 1, 1, size);
		op1.startQuiz();
		op1.setCurState(Type.OP);
		op1.setQuizzes(new ArrayList<>());
		op1.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op1.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op1.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op1.startQuiz();
		op1.createQuizzes();
		op1.setCurState(Type.OPREVIEW);
		op1.startQuiz();
	}
	@Test
	void setUpGameTest() {
		OPModel op1 = new OPModel(300, 300, 1, 1, new HashMap<String, int[]>());
		op1.setBird(new Bird(1,1, 3, BirdType.OSPREY));
		op1.setCurState(Type.OP);
		op1.setUpGame();
	}
	@Test
	void checkQuizTest() {
		OPModel op1 = new OPModel(300, 300, 1, 1, new HashMap<String, int[]>());
		op1.setBird(new Bird(1,1, 3, BirdType.OSPREY));
		op1.setQuizzes(new ArrayList<>());
		op1.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op1.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op1.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op1.setCurState(Type.OP);
		op1.startQuiz();
		op1.getQuiz().setChosenAnser("2");
		op1.checkQuiz();
		
		op1.startQuiz();
		op1.setDelayT(3);
		op1.getQuiz().setChosenAnser("1");
		op1.checkQuiz();
		
		OPModel op2 = new OPModel(300, 300, 1, 1, new HashMap<String, int[]>());
		op2.setBird(new Bird(1,1, 3, BirdType.OSPREY));
		op2.setCurState(Type.OPREVIEW);
		op2.setQuizzes(new ArrayList<>());
		op2.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op2.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op2.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		op2.startQuiz();
		op2.getQuiz().setChosenAnser("2");
		op2.checkQuiz();
		
		op2.setDelayT(3);
		op2.getQuiz().setChosenAnser("3");
		op2.checkQuiz();
		
		OPModel op3 = new OPModel(300, 300, 1, 1, new HashMap<String, int[]>());
		op3.checkQuiz();
	}
	@Test
	void gettersTest() {
		OPModel op1 = new OPModel(300, 300, 1, 1, new HashMap<String, int[]>());
		op1.getEnergy();
		op1.isWinFlag();
		op1.setEnergy(10);
		op1.isWaterbg();
		op1.setWaterbg(true);
		op1.getFlagTime();
		op1.setWinFlag(true);
		op1.gameOver();
	}
	

}
