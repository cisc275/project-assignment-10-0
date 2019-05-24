package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import main_package.NH2Model;
import main_package.Quiz;
import main_package.BirdType;
import main_package.CollectedItem;
import main_package.HitItem;
import main_package.ItemType;
import main_package.Model;

class NH2ModelTest {

	@Test
	void setUpGameTest() {
		HashMap<String, int[]> map = new HashMap<>();
		Model.setEggs(3);
		NH2Model nh2 = new NH2Model(2080, 1080, 1, 1, map);
		nh2.setUpGame();	
	}
	@Test
	void createTimerTest() {
		HashMap<String, int[]> map = new HashMap<>();
		NH2Model nh2 = new NH2Model(2080, 1080, 1, 1, map);
		nh2.setTimeCount(0);
		nh2.createTimer(1);
	}
	@Test
	void updatePositionTest() {
		HashMap<String, int[]> imgs = new HashMap<>();
		imgs.put("nest1", new int[] {100, 150});
		imgs.put("nh", new int[] {150, 150});
		imgs.put("fox", new int[] {100, 150});
		NH2Model nh2 = new NH2Model(2080, 1080, 1, 1, imgs);
		nh2.getEggList().add(new CollectedItem(100, 100, ItemType.EGG));
		Model.setEggs(3);
		((NH2Model) nh2).setNest(new CollectedItem(300, 300, ItemType.NEST));
		nh2.setBird(500, 500, 1, BirdType.NH);
		nh2.setList(new ArrayList<>());
		//nh2.getList().add(new CollectedItem(300, 300, ItemType.NEST));
		nh2.getList().add(new HitItem(200, 200, ItemType.FOX, 1, 1));
		nh2.getList().add(new HitItem(500, 500, ItemType.FOX, 1, 1));
		//nh2.getList().add(new CollectedItem(210, 210, ItemType.STICK));
		nh2.updatePosition();
		nh2.setTimeCount(4);
		nh2.setUpdateL(true);
		nh2.updatePosition();
		Model.setEggs(0);
		nh2.updatePosition();
		
	}
	@Test
	void checkCollisionTest() {
		HashMap<String, int[]> map = new HashMap<>();
		NH2Model nh2 = new NH2Model(2080, 1080, 300, 300, map);
	}
	
	@Test
	void checkQuizTest() {
		HashMap<String, int[]> map = new HashMap<>();
		NH2Model nh2 = new NH2Model(2080, 1080, 300, 300, map);
		nh2.setQuizzes(new ArrayList<>());
		nh2.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		nh2.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		nh2.getQuizzes().add(new Quiz("1","2",new String[] {"1","2","3","4"}));
		nh2.startQuiz();
		nh2.getQuiz().setChosenAnser("1");
		nh2.checkQuiz();
		nh2.getQuiz().setChosenAnser("2");
		nh2.checkQuiz();
	}
	@Test
	void tutorialTest() {
		HashMap<String, int[]> map = new HashMap<>();
		map.put("nh", new int[] {150, 150});
		map.put("fox", new int[] {100, 150});
		map.put("nest1", new int[] {100, 150});
		NH2Model nh2 = new NH2Model(2080, 1080, 1, 1, map);
		nh2.setBird(300, 300, 1, BirdType.NH);
		nh2.setNest(new CollectedItem(300, 300, ItemType.NEST));
		nh2.setList(new ArrayList<>());
		nh2.getList().add(new HitItem(300, 300, ItemType.FOX, 1, 1));
		nh2.tutorial();
	}
	@Test
	void updateListTest() {
		HashMap<String, int[]> map = new HashMap<>();
		NH2Model nh2 = new NH2Model(2080, 1080, 1, 1, map);
		for(int i = 0; i < 100; i++) {
			nh2.updateList();
		}
	}
	
	@Test
	void testOutOfFrame() {
		HashMap<String, int[]> map = new HashMap<>();
		map.put("nh", new int[] {150, 150});
		NH2Model nh2 = new NH2Model(2080, 1080, 1, 1, map);
		nh2.setBird(-1000, 300, 1, BirdType.NH);
		nh2.outOfFrame();
	}
	
	@Test
	void testFoxOutOfFrame() {
		HashMap<String, int[]> map = new HashMap<>();
		map.put("fox", new int[] {100, 150});
		NH2Model nh2 = new NH2Model(2080, 1080, 1, 1, map);
		HitItem fox = new HitItem(-1000, 200, ItemType.FOX, 1, 1);
		nh2.foxoutOfFrame(fox);
	}

}
