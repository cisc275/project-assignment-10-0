package main_package;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


// author Sicheng Tian, Yufan Wang£¬ Rubai Bian
public class Model {
	Timer myTimer;
	Timer delayTimer;
	final int defaultTime = 30;
	int timeCount;
	int delayCount;
	ArrayList<Element> list;
	Bird bird;
	boolean quizing;
	int quizCount;
	String quizOutcomeInfo = "";
	Quiz quiz;
	ArrayList<Quiz> quizs;
	Type curState;
	int eggs;
	int numTrueAns;
	public static int xIncr = 5;
	public static int xDec = -10;
	public static int xDec2 = -8;
	public static int yIncr = 5;
	int frameW;
	int frameH;
	int imgH;
	int imgW;
	boolean updateL;
	// for move background
	int groundX;
	int groundY; 
	
	//Boolean for NH1 Game
	boolean moreCollectedItems;
	
	// initialize the timer and all the element in the Collection and bird
	// initializing the quizing to be false
	// set curState to be the main menu
	// initialize the egg 
	public Model(int fW, int fH, int iW, int iH) {
		frameW = fW;
		frameH = fH;
		imgW = iW;
		imgH = iH;
		curState = Type.MAINMENU;
	}
	
	//getter for eggs
	public int getEggs() {return this.eggs;};
	//getter for numofTrueAns
	public int getNumAns() {return this.numTrueAns;};
	//getter for quizing
	public boolean getQuizing() {return this.quizing;};
	
	// create timer and task depends on curState
	public void createTimer() {
		myTimer = new Timer();
		switch(curState) {
		case OP:
			timeCount = defaultTime;
			myTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//t++;
					if (!quizing) {
						System.out.println("time count :" + --timeCount);
					}
					if (timeCount <= 0) {
						gameOver();
						System.out.println(curState);
						myTimer.cancel();
					}
				}
				
			}, 0, 1000);
			break;
		case NH1:
			timeCount = 40;
			myTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//t++;
					System.out.println("time count :" + --timeCount);
					if (timeCount == 0) {
						myTimer.cancel();
						startQuiz();
					} 
				}
				
			}, 0, 1000);
			break;
		case NH2:
			timeCount = 40;
			myTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//t++;
					System.out.println("time count :" + --timeCount);
					updateL = true;
					if (timeCount == 0) {
						myTimer.cancel();
						gameOver();
					} 
				}
				
			}, 0, 1000);
			break;
		}
	}
	
	// loop through the collection list update their position by calling the move method
	// if the curState is NH2 call collisionNH2()
	// if the curState is OP call checkCollision
	public void updatePosition() {
		// for background
		groundX = groundX + xDec2;
		// for element
		if (updateL) {
			updateList();
		}
		Iterator<Element> iter = list.iterator();
		while(iter.hasNext()) {
			Element curE = iter.next();
			//curE.setX(xDec);
			curE.move();
			if (curE.getX() + imgW / 2 <= 0 ) { // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
				System.out.println("remove");
				iter.remove();
				updateL = true;
				//list.add(new HitItem(frameW, 100, ItemType.AIRPLANE));
			}
			else if (checkCollision(curE)) {
				System.out.println("remove");
				iter.remove();
				updateL = true;
			}
		}
	}
	
	//helper for updatePosition()
	public void updateList() {
		Random ran = new Random();
		int ranH = ran.nextInt(frameH - imgH / 2);
		//System.out.println(ranH);
		list.add(new HitItem(frameW, ranH, ItemType.AIRPLANE, -10, 0));
		updateL = false;
	}
	
	// update the bird position by calling the move method
	// if the curState is NH1 call collisionNH1()
	public void updateBirdPosition() {
		if (!outOfFrame()) {
			bird.move();
			//System.out.println(bird.getX() + ", " + bird.getY());
			//System.out.println("here1");
			if (curState == Type.NH1) {
				collisionNH1();
			}
			int nestX = this.getFrameW()/2;
			int nestY = this.getFrameH()/2;
			boolean xC = nestX - imgW/2 <= bird.getX() + imgW/2 && nestX - imgW/2 >= bird.getX() - imgW/2;
			boolean xC2 = nestX + imgW/2 <= bird.getX() + imgW/2 && nestX + imgW/2 >= bird.getX() - imgW/2;
			boolean yC1 = nestY - imgH/2 <= bird.getY() + imgH/2 && nestY - imgH/2 >= bird.getY() - imgH/2;
			boolean yC2 = nestY + imgH/2 <= bird.getY() + imgH/2 && nestY + imgH/2 >= bird.getY() - imgH/2;
			if (!moreCollectedItems && (xC && yC1 || xC && yC2 || xC2 && yC1 || xC2 && yC2) && curState == Type.NH1) {
				System.out.println("NH1 Complete");
				myTimer.cancel();
				startQuiz();
				//myTimer.cancel();
				//curState= Type.NH2;
			}
		}
	}
	
	public void resetModelNH2() {
		System.out.println("here");
		setBird(new Bird(getFrameW()/2, getFrameH()/2,0,BirdType.NH));
		//setBird(new Bird(0, 0,0,BirdType.NH));
		setList(new ArrayList<>());
		getList().add(new HitItem(getFrameW(), 100, ItemType.AIRPLANE, -10, 0));
		setUpdateL();
		createTimer();
	}
	
	public void updatePositionNH2() {
		// for background
		if (!outOfFrame()) {
			bird.move();
			if (curState == Type.NH2) {
				//collisionNH2();
				if (timeCount % 4 == 0 && updateL) {
					//System.out.println(timeCount);
					updateListNH2();
				}
				Iterator<Element> iter = list.iterator();
				//System.out.println(list.size());
				while(iter.hasNext()) {
					Element curE = iter.next();
					curE.move();
					if (curE.getX() + imgW <= 0 ) { // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
						System.out.println("removeNH2");
						iter.remove();
						//updateL = true;
						//list.add(new HitItem(frameW, 100, ItemType.AIRPLANE));
					}
					else if (collisionNH2(curE)) {
						System.out.println("remove");
						//iter.remove();
						//updateL = true;
					}
				}
			}
			
		}
	}
	
	//helper for updatePosition()
		public void updateListNH2() {
			System.out.println("updateLIst");
			Random ran = new Random();
			int ranSide = ran.nextInt(3)+1;
			int height = 0;
			int width = 0;
			switch(ranSide) {
			case 1:
				height = (frameH-imgH)/2;
				width = 0;
				// Moving East
			case 2:
				height = (frameH - imgH)/2;
				width = frameW;
				// Moving West
			case 3:
				height = 0;
				width = (frameW - imgW)/2;
				// Moving South
			case 4:
				height = frameH;
				width = (frameW - imgW)/2;
				// Moving North
			}
			//System.out.println(height);
			//System.out.println(width);
			list.add(new HitItem(frameW-imgW, ran.nextInt((frameH - imgH)), ItemType.AIRPLANE, -10, 0));
			updateL = false;
		}
	
	// helper function for updateBirdPosition to prevent bird go out of screen
	public boolean outOfFrame() {
		switch (curState) {
		case OP:
			if (bird.getY() + bird.getYVector() < 0|| bird.getY() + imgH + bird.getYVector() > frameH) {
				return true;
			}
			break;
		case NH1:
			if (bird.getY() + bird.getYVector() < 0 || bird.getY() + imgH + bird.getYVector() > frameH || 
					bird.getX() + bird.getXVector() < 0 || bird.getX() + imgW + bird.getXVector() > frameW) {
				return true;
			}
			break;
		case NH2:
			if (bird.getY() + bird.getYVector() < 0 || bird.getY() + imgH + bird.getYVector() > frameH || 
					bird.getX() + bird.getXVector() < 0 || bird.getX() + imgW + bird.getXVector() > frameW) {
				return true;
			}
			break;
		}
		return false;
	}
	
	//for OP game
	// check if the bird position has collision with other hitItem except fish
	// if it is call the startQize method
	// if has collision with fish call eat() in the bird
	// remove the hitItem that has collision from the Element list
	// check if the bird has collision with the final flag
	// if it is call winGame()
	public boolean checkCollision(Element ht) {
		boolean xC = ht.getX() - imgW/2 <= bird.getX() + imgW/2 && ht.getX() - imgW/2 >= bird.getX() - imgW/2;
		boolean yC1 = ht.getY() - imgH/2 <= bird.getY() + imgH/2 && ht.getY() - imgH/2 >= bird.getY() - imgH/2;
		boolean yC2 = ht.getY() + imgH/2 <= bird.getY() + imgH/2 && ht.getY() + imgH/2 >= bird.getY() - imgH/2;
		if (xC && yC1 || xC && yC2) {
			System.out.println("collsion!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			if(ht instanceof HitItem) {
				HitItem h = (HitItem)ht;
				if(h.getType().equals(ItemType.FISH)) {
					System.out.println(bird.getLife());
					bird.eat();
					
				} else if(h.getType().equals(ItemType.AIRPLANE)) {
					System.out.println(bird.getLife());
					bird.collision();
				} else if(h.getType().equals(ItemType.WINFLAG)) {
					this.curState = Type.WIN;
				}
			}
			//gameOver();
			startQuiz();
			return true;
		}
		return false;
	}
	
	public boolean collisionNH2(Element cur) {
		boolean xC = cur.getX() - imgW/2 <= bird.getX() + imgW/2 && cur.getX() - imgW/2 >= bird.getX() - imgW/2;
		boolean xC2 = cur.getX() + imgW/2 <= bird.getX() + imgW/2 && cur.getX() + imgW/2 >= bird.getX() - imgW/2;
		boolean yC1 = cur.getY() - imgH/2 <= bird.getY() + imgH/2 && cur.getY() - imgH/2 >= bird.getY() - imgH/2;
		boolean yC2 = cur.getY() + imgH/2 <= bird.getY() + imgH/2 && cur.getY() + imgH/2 >= bird.getY() - imgH/2;
		if (xC && yC1 || xC && yC2 || xC2 && yC1 || xC2 && yC2) {
			System.out.println("Collision");
			return true;
				/*if(cur instanceof HitItem) {
					HitItem h = (HitItem)cur;
					int temp = h.getX();
					h.setX(h.getY());
					h.setY(temp);
				}*/
		}
			
		
		return false;
		
	}
	
	// the method will generate a quiz
	// and set quizing boolean to be true
	public void startQuiz() {
		System.out.println("start quiz");
		this.quizing = true;
		switch (curState) {
		case OP:
			Random r = new Random();
			if (quizs.size() != 0) {
				quiz = quizs.get(r.nextInt(quizs.size()));
				System.out.println(quiz);
			}
			break;
		case NH1:
			quizCount = 0;
			quiz = quizs.get(quizCount);
		}
	}
	
	// for OP and NH Game
	// check the answer of the quiz
	// if it is true, set the quizing boolean to be false;
	// if it is false, call the collision method in the bird and then set the quizing boolean to be false
	// and check the remaining life of bird, if it is zero call gameOver()
	public void checkQuiz() {
		
		System.out.println("submit");
		switch(curState) {
		case OP:
			if(!quiz.checkAnswer()) {
				bird.collision();
				timeCount -= 10;
				quizOutcomeInfo = "Oh No!!!  You Are Wrong, Lose Energy!!";
			}
			else {
				quizOutcomeInfo = "Correct!!!   You Saved the Bird!!";
			}
			delayTimer = new Timer();
			delayCount = 0;
			delayTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("delayCount: " + ++delayCount);
					if (delayCount >=3) {
						quizing = false;
						delayTimer.cancel();
						delayTimer = null;
						quizOutcomeInfo = "";
					}
				}
				
			}, 0, 1000);
			break;
		case NH1:
			if(!quiz.checkAnswer()) {
				// here represent reduce number of egg in NH2
				bird.collision();
				quizOutcomeInfo = "Oh No!!!  You Lose an egg in next game";
			}
			else {
				quizOutcomeInfo = "Correct!!!!!";
			}
			quizCount++;
			delayTimer = new Timer();
			delayCount = 0;
			delayTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("delayCount: " + ++delayCount);
					if (delayCount >=3) {
						if (quizCount < 3) {
							quiz = quizs.get(quizCount);
							
						}
						else {
							quizing = false;
							curState = Type.NH2;
							resetModelNH2();
						}
						quizOutcomeInfo = "";
						delayTimer.cancel();
						delayTimer = null;
					}
				}
				
			}, 0, 1000);
//			this.quizing = false;
//			curState = Type.NH2;
			
		}
		
		
		
	}
	
	// set curState to be End
	public void gameOver() {
	    curState = Type.GAMEOVER;
	}
	
	// set curState to be Win 
	public void winGame() {
		curState = Type.WIN;
	}
	
	// for NH game
	// check if current bird position has collision with CollectedItem in the Element list
	// if the item has collision call isCollected() method in Collected
	// if all the CollectedItem is collected, call the startQuiz() method
	public void collisionNH1() {
		Iterator<Element> iter = list.iterator();
		while(iter.hasNext()) {
			Element cur = iter.next();
			boolean xC = cur.getX() - imgW/2 <= bird.getX() + imgW/2 && cur.getX() - imgW/2 >= bird.getX() - imgW/2;
			boolean xC2 = cur.getX() + imgW/2 <= bird.getX() + imgW/2 && cur.getX() + imgW/2 >= bird.getX() - imgW/2;
			boolean yC1 = cur.getY() - imgH/2 <= bird.getY() + imgH/2 && cur.getY() - imgH/2 >= bird.getY() - imgH/2;
			boolean yC2 = cur.getY() + imgH/2 <= bird.getY() + imgH/2 && cur.getY() + imgH/2 >= bird.getY() - imgH/2;
			if (xC && yC1 || xC && yC2 || xC2 && yC1 || xC2 && yC2) {
				bird.setItemsCollected(bird.getItemsCollected() + 1);
				CollectedItem c = (CollectedItem)cur;
				c.isCollected();
				System.out.println("collected!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				iter.remove();
			}
		}
		moreCollectedItems = false;
		for(Element e: list) {
			if(e instanceof CollectedItem) {
				moreCollectedItems = true;
			}
		}
		/*if(!moreCollectedItems) {
			System.out.println("NH1 Complete");
			curState= Type.NH2;
		}*/
	}
	
	// for NH game
	// check the number of right answer in the quiz
	// set the number of egg to be the number of right answers
	// set the quizing boolean to be false
	// set curState to be NH2
	// NO NEED FOR THIS ONE !!!!!!!!!!!!!   ALL USE CHECKQUIZ
	public void submitQuiz() {
		this.quizing = false;
	}
	
	// for NH game
	// check whether the hitItem fox has collision with egg
	// if it is remove the egg item and the hitItem from list, subtract one from int eggs
	// if the fox has collision with bird, call the move method of the fox
	// check the number of eggs left. if it is zero, call gameOver()
	public void collisionNH2(HitItem ht) {
		Iterator<Element> iter = list.iterator();
		while(iter.hasNext()) {
			Element cur = iter.next();
			boolean xC = cur.getX() - imgW/2 <= bird.getX() + imgW/2 && cur.getX() - imgW/2 >= bird.getX() - imgW/2;
			boolean xC2 = cur.getX() + imgW/2 <= bird.getX() + imgW/2 && cur.getX() + imgW/2 >= bird.getX() - imgW/2;
			boolean yC1 = cur.getY() - imgH/2 <= bird.getY() + imgH/2 && cur.getY() - imgH/2 >= bird.getY() - imgH/2;
			boolean yC2 = cur.getY() + imgH/2 <= bird.getY() + imgH/2 && cur.getY() + imgH/2 >= bird.getY() - imgH/2;
			if (xC && yC1 || xC && yC2 || xC2 && yC1 || xC2 && yC2) {
				CollectedItem c = (CollectedItem)cur;
				c.isCollected();
				System.out.println("collected!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				iter.remove();
			}
		}
		eggs--;
		if(eggs <= 0) {
			this.curState = Type.GAMEOVER;
		}
	}
	
	public void createQuizs() throws Exception{
		Scanner scan;
		quizs = new ArrayList<>();
		switch(curState) {
		case OP:
			File fileO = new File("OPquiz.txt");
			scan = new Scanner(fileO);
			while(scan.hasNextLine()) {
				String[] infos = scan.nextLine().split(";", -1);
//				System.out.println(infos.length);
//				for(int i = 0; i < infos.length; i++) {
//					System.out.println(infos[i]);
//				}
				String[] choices = {infos[1],infos[2],infos[3],infos[4]};
				quizs.add(new Quiz(infos[0], infos[5], choices));
			}
			scan.close();
			break;
		case NH1:
			File fileN = new File("NHquiz.txt");
			scan = new Scanner(fileN);
			while(scan.hasNextLine()) {
				String[] infos = scan.nextLine().split(";", -1);
//				System.out.println(infos.length);
//				for(int i = 0; i < infos.length; i++) {
//					System.out.println(infos[i]);
//				}
				String[] choices = {infos[1],infos[2],infos[3],infos[4]};
				quizs.add(new Quiz(infos[0], infos[5], choices));
			}
			scan.close();
			break;
		}
	}
	
	// getter setter for create test
	public Type getCurState() {
		return curState;
	}
	
	public void setList(ArrayList<Element> list){
		this.list = list;
	}
	
	public void setQuiz(String question, String answer, String[] choice) {
		quiz = new Quiz(question, answer, choice);
	}
	
	public Quiz getQuiz() {
		return quiz;
	}
	
	public void setBird(int x, int y, int l, BirdType bt) {
		bird = new Bird(x,y,l,bt);
	}
	
	public void setBird(Bird b) {
		this.bird = b;
	}
	
	public Bird getBird() {
		return bird;
	}
	
	public void setCurState(Type curState) {
		this.curState = curState;
	}
	
	public ArrayList<Element> getList(){
		return list;
	}
	public void setEgg(int e) {
		eggs = e;
	}
	public boolean getUpdateL() {
		return updateL;
	}
	public void setUpdateL() {
		updateL = false;
	}
	
	public int getFrameW() {
		return frameW;
	}
	
	public int getFrameH() {
		return frameH;
	}
	
	public int getTimeCount() {
		return timeCount;
	}
	
}
// testing2