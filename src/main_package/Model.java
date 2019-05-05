package main_package;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


// author Sicheng Tian, Yufan Wang£¬ Rubai Bian
public class Model {
	Timer myTimer;
	Timer delayTimer;
	final int defaultTime = 60;
	int timeCount;
	int delayCount;
	int energy;
	ArrayList<Element> list;
	Bird bird;
	CollectedItem nest;
	boolean quizing = false;
	int quizCount;
	String quizOutcomeInfo = "";
	Quiz quiz;
	ArrayList<Quiz> quizs;
	Type curState;
	int eggs =0;
	int numTrueAns;
	public static int xIncr = 5;
	public static int xDec = -10;
	public static int xDec2 = -5;
	public static int yIncr = 5;
	int frameW;
	int frameH;
	int imgH;
	int imgW;
	boolean updateL;
	// for move background
	int groundX;
	int groundY;
	HashMap<String, int[]> imgsSize;
	
	//Boolean for NH1 Game
	boolean moreCollectedItems;
	
	// initialize the timer and all the element in the Collection and bird
	// initializing the quizing to be false
	// set curState to be the main menu
	// initialize the egg 
	public Model(int fW, int fH, int iW, int iH, HashMap<String, int[]> map) {
		frameW = fW;
		frameH = fH;
		imgW = iW;
		imgH = iH;
		curState = Type.MAINMENU;
		imgsSize = map;
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
			energy = defaultTime - 10;
			myTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//t++;
					if (!quizing) {
						// update time and energy
						System.out.println("time count :" + --timeCount);
						energy --;
					}
					if (timeCount % 4 == 0) {
						// every 4 second update list
						updateL = true;
					}
					if (energy <= 0) {
						// lose all energy game over
						gameOver();
						System.out.println(curState);
						myTimer.cancel();
					}
					if (timeCount < 0) {
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
						winGame();
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
		// if timeCount(not energy) is out win flag
		if (timeCount == 0) {
			// flag has same speed as background
			list.add(new HitItem(frameW, frameH / 2, ItemType.WINFLAG, xDec2,0));
		}
		
		
		Iterator<Element> iter = list.iterator();
		while(iter.hasNext()) {
			Element curE = iter.next();
			//curE.setX(xDec);
			curE.move();
			if (curE.getX() + imgsSize.get(curE.getType().getName())[0] <= 0 ) { // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
				System.out.println("remove");
				iter.remove();
				//updateL = true;
			}
			else if (checkCollision(curE)) {
				System.out.println("remove");
				iter.remove();
				//updateL = true;
			}
		}
	}
	
	//helper for updatePosition()
	public void updateList() {
		/*
		Random ran = new Random();
		int ranH = ran.nextInt(frameH - imgH / 2);
		//System.out.println(ranH);
		list.add(new HitItem(frameW, ranH, ItemType.AIRPLANE, -10, 0));
		updateL = false;
		*/
		System.out.println("update list");
		Random ran = new Random();
		int ranType = ran.nextInt(3);
		int ranSpeed = ran.nextInt(6) + 8;
		System.out.println("random type: " + ranType);
		System.out.println("random speed: " + ranSpeed);
		switch(ranType) {
		// 0 is airplane
		case 0:
			list.add(new HitItem(frameW, frameH / 4, ItemType.AIRPLANE, 0 - ranSpeed,0));
		break;
		// 1 is ship
		case 1:
			list.add(new HitItem(frameW, frameH * 4 / 7, ItemType.SHIP, 0 - ranSpeed,0));
		break;
		// 2 is fish
		case 2:
			list.add(new HitItem(frameW, frameH * 5 / 7, ItemType.FISH, 0 - ranSpeed,0));
		break;
		}
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
			
			int nestX = this.getFrameW()/2;
			int nestY = this.getFrameH()/2;
			//int width = imgsSize.get("bird")[0];
			//int height = imgsSize.get("bird")[1];
//			boolean xC = nestX - imgW/2 <= bird.getX() + imgW/2 && nestX - imgW/2 >= bird.getX() - imgW/2;
//			boolean xC2 = nestX + imgW/2 <= bird.getX() + imgW/2 && nestX + imgW/2 >= bird.getX() - imgW/2;
//			boolean yC1 = nestY - imgH/2 <= bird.getY() + imgH/2 && nestY - imgH/2 >= bird.getY() - imgH/2;
//			boolean yC2 = nestY + imgH/2 <= bird.getY() + imgH/2 && nestY + imgH/2 >= bird.getY() - imgH/2;
			//(xC && yC1 || xC && yC2 || xC2 && yC1 || xC2 && yC2)
			if (!moreCollectedItems && collisionF(nest) && curState == Type.NH1) {
				System.out.println("NH1 Complete");
				myTimer.cancel();
				startQuiz();
				//myTimer.cancel();
				//curState= Type.NH2;
			}
			
			}
		}
	}
	
	public boolean collisionF(Element e) {
		int birdW = imgsSize.get(bird.getBType().getName())[0];
		int birdH = imgsSize.get(bird.getBType().getName())[1];
		int elementW = imgsSize.get(e.getType().getName())[0];
		int elementH = imgsSize.get(e.getType().getName())[1];
//		System.out.println(birdW + ", " + birdH + "; " + elementW + ", " + elementH
//				);
		
		boolean xC = e.getX() <= bird.getX() + birdW 
				&& e.getX() >= bird.getX();
		boolean xC2 = e.getX() + elementW <= bird.getX() + birdW 
				&& e.getX() + elementW >= bird.getX();
		boolean yC1 = e.getY() <= bird.getY() + birdH 
				&& e.getY() >= bird.getY();
		boolean yC2 = e.getY() + elementH <= bird.getY() + birdH 
				&& e.getY() + elementH >= bird.getY();
		boolean result1 = xC && yC1 || xC && yC2 || xC2 && yC1 || xC2 && yC2;
		
		boolean x2C = bird.getX() <= e.getX() + elementW
				&& bird.getX() >= e.getX();
		boolean x2C2 = bird.getX() + birdW <=e.getX() + elementW
				&& bird.getX() + birdW >= e.getX();
		boolean y2C1 = bird.getY() <= e.getY() + elementH 
				&& bird.getY() >= e.getY();
		boolean y2C2 = bird.getY() + birdH <= e.getY() + elementH 
				&& bird.getY() + birdH >= e.getY();
		boolean result2 = x2C && y2C1 || x2C && y2C2 || x2C2 && y2C1 || x2C2 && y2C2;
				
		return result1 || result2;
	}
	
	public void resetModelNH2() {
		System.out.println("here");
		setBird(new Bird(getFrameW()/2, getFrameH()/2,0,BirdType.NH));
		//setBird(new Bird(0, 0,0,BirdType.NH));
		nest = new CollectedItem(getFrameW()/2, getFrameH()/2, ItemType.NEST);
		setList(new ArrayList<>());
		//getList().add(new HitItem(getFrameW(), 100, ItemType.FOX, -10, 0));
		//getList().add(new HitItem(getFrameW(), 100, ItemType.AIRPLANE, -10, 0));
		setUpdateL();
		createTimer();
	}
	
	public void updatePositionNH2() {
		// for background
		if (!outOfFrame()) {
			bird.move();
			if (curState == Type.NH2) {
				//collisionNH2();
				if (timeCount % 2 == 0 && updateL) {
					//System.out.println(timeCount);
					updateListNH2();
				}
				Iterator<Element> iter = list.iterator();
				//System.out.println(list.size());
				while(iter.hasNext()) {
					Element curE = iter.next();
					curE.move();
					//System.out.println(curE.getY());
					//System.out.println((frameH)/2);
//					int nestX = this.getFrameW()/2;
//					int nestY = this.getFrameH()/2;
//					boolean xC = nestX - imgW/2 <= curE.getX() + imgW/2 && nestX - imgW/2 >= curE.getX() - imgW/2;
//					boolean xC2 = nestX + imgW/2 <= curE.getX() + imgW/2 && nestX + imgW/2 >= curE.getX() - imgW/2;
//					boolean yC1 = nestY - imgH/2 <= curE.getY() + imgH/2 && nestY - imgH/2 >= curE.getY() - imgH/2;
//					boolean yC2 = nestY + imgH/2 <= curE.getY() + imgH/2 && nestY + imgH/2 >= curE.getY() - imgH/2;
					int nestW = imgsSize.get(nest.getType().getName())[0];
					int nestH = imgsSize.get(nest.getType().getName())[1];
					int elementW = imgsSize.get(curE.getType().getName())[0];
					int elementH = imgsSize.get(curE.getType().getName())[1];
					
					boolean xC = curE.getX() <= nest.getX() + nestW
							&& curE.getX() >= nest.getX();
					boolean xC2 = curE.getX() + elementW <=nest.getX() + nestW
							&& curE.getX() + elementW >= nest.getX();
					boolean yC1 = curE.getY() <= nest.getY() + nestH 
							&& curE.getY() >= nest.getY();
					boolean yC2 = curE.getY() + elementH <= nest.getY() + nestH 
							&& curE.getY() + elementH >= nest.getY();
							
					if(xC && yC1 || xC && yC2 || xC2 && yC1 || xC2 && yC2) {
						System.out.println("removeNH2");
						eggs--;
						if(eggs <= 0) {
							this.curState = Type.GAMEOVER;
						}
						iter.remove();
					}
					/*if (curE.getX() + imgW <= 0 ) { // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
						System.out.println("removeNH2");
						iter.remove();
						//updateL = true;
						//list.add(new HitItem(frameW, 100, ItemType.AIRPLANE));
					}*/
					else if (collisionNH2(curE) && curE.getType() != ItemType.NEST) {
						System.out.println("remove");
						HitItem h = (HitItem)curE;
						/*if(h.getxVector() == 10 && !h.getDirectionChange()) {
							h.setxVector(-10);
							h.changeDirection();
						} else if(h.getxVector() == -10 && !h.getDirectionChange()) {
							h.setxVector(10);
							h.changeDirection();
						}
						if(h.getyVector() == 10 && !h.getDirectionChange()) {
							h.setyVector(-10);
							h.changeDirection();
						} else if(h.getyVector() == -10 && !h.getDirectionChange()) {
							h.setyVector(10);
							h.changeDirection();
						}*/
						
						if(!h.getDirectionChange()) {
							int newX = h.getxVector()*-1;
							int newY = h.getyVector()*-1;
							h.setyVector(newY);
							h.setxVector(newX);
							h.changeDirection();
						}
						//iter.remove();
						//updateL = true;
					}
				}
			}
			
		}
	}
	
	//helper for updatePositionNH2()
	public double calculateUnitVectorMag(int xpos, int ypos){
		double mag = Math.pow(xpos, 2) + Math.pow(ypos, 2);
		mag = Math.sqrt(mag);
		System.out.println(mag);
		return mag;
		
	}
	
	//helper for updatePosition()
		public void updateListNH2() {
			System.out.println("updateLIst");
			System.out.println(list.size());
			Random ran = new Random();
			int ranSide = ran.nextInt(8);
			int height = 0;
			int width = 0;
			// Create vector in the right direction
			double unitVectorMag = this.calculateUnitVectorMag(frameW/2, frameH/2);
			double vX = 10*((frameW/2)/unitVectorMag);
			double vY = 10*((frameH/2)/unitVectorMag);
			int vectorX = (int) vX;
			int vectorY = (int) vY;
			System.out.println(vectorX);
			System.out.println(vectorY);
			switch(ranSide) {
			case 0:
				height = (frameH-imgH)/2;
				width = 0;
				//direction = 'e';
				list.add(new HitItem(width, height, ItemType.FOX, 10, 0));
				//list.add(new HitItem(width, height, ItemType.FOX, 10, 0));
				System.out.println("move east");
				// Moving East
				break;
			case 1:
				height = (frameH - imgH)/2;
				width = frameW;
				//direction = 'w';
				list.add(new HitItem(width, height, ItemType.FOX, -10, 0));
//				list.add(new HitItem(width, height, ItemType.FOX, -10, 0));
				System.out.println("move west");
				// Moving West
				break;
			case 2:
				height = 0;
				width = (frameW - imgW)/2;
				//direction = 's';
				//list.add(new HitItem(width, height, ItemType.FOX, 0, 10));

				list.add(new HitItem(width, height, ItemType.FOX, 0, 10));
				System.out.println("move south");
				// Moving South
				break;
			case 3:
				height = frameH;
				width = (frameW - imgW)/2;

				//direction = 'n';
				//list.add(new HitItem(width, height, ItemType.FOX, 0, -10));
				list.add(new HitItem(width, height, ItemType.FOX, 0, -10));
				System.out.println("move north");
				// Moving North
				break;
			case 4:
				height = 0;
				width = 0;
				//list.add(new HitItem(width, height, ItemType.AIRPLANE, 19, 10));
				list.add(new HitItem(width, height, ItemType.FOX, vectorX, vectorY));
				System.out.println("move southeast");
				// Moving Southeast
				break;
			case 5:
				height = frameH;
				width = 0;
				list.add(new HitItem(width, height, ItemType.FOX, vectorX, -vectorY));
				System.out.println("move northeast");
				// Moving Southeast
				break;
			case 6:
				height = 0;
				width = frameW;
				list.add(new HitItem(width, height, ItemType.FOX, -vectorX, vectorY));
				System.out.println("move Southwest");
				// Moving Southwest
				break;
			case 7:
				height = frameH;
				width = frameW;
				list.add(new HitItem(width, height, ItemType.FOX, -vectorX, -vectorY));
				System.out.println("move Northwest");
				// Moving Southeast
				break;
			}
			
			System.out.println(list.size());
			//System.out.println(height);
			//System.out.println(width);
			
			
			//list.add(new HitItem(frameW-imgW, ran.nextInt((frameH - imgH)), ItemType.AIRPLANE, -10, 0));
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
//		boolean xC = ht.getX() - imgW/2 <= bird.getX() + imgW/2 && ht.getX() - imgW/2 >= bird.getX() - imgW/2;
//		boolean yC1 = ht.getY() - imgH/2 <= bird.getY() + imgH/2 && ht.getY() - imgH/2 >= bird.getY() - imgH/2;
//		boolean yC2 = ht.getY() + imgH/2 <= bird.getY() + imgH/2 && ht.getY() + imgH/2 >= bird.getY() - imgH/2;
		if (collisionF(ht)) {
			System.out.println("collsion!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			if(ht instanceof HitItem) {
				HitItem h = (HitItem)ht;
				if(h.getType().equals(ItemType.FISH)) {
					System.out.println(bird.getLife());
					bird.eat();
					//get energy
					if (defaultTime - 10 - energy <= 10) {
						energy = defaultTime - 10;
					}
					else {
						energy += 10;
					}
					
				} else if(h.getType().equals(ItemType.AIRPLANE) || h.getType().equals(ItemType.FOX) || h.getType().equals(ItemType.SHIP)) {
					System.out.println(bird.getLife());
					bird.collision();
					startQuiz();
				} else if(h.getType().equals(ItemType.WINFLAG)) {
					winGame();
				}
			}
			//gameOver();
			return true;
		}
		return false;
	}
	
	public boolean collisionNH2(Element cur) {
//		boolean xC = cur.getX() - imgW/2 <= bird.getX() + imgW/2 && cur.getX() - imgW/2 >= bird.getX() - imgW/2;
//		boolean xC2 = cur.getX() + imgW/2 <= bird.getX() + imgW/2 && cur.getX() + imgW/2 >= bird.getX() - imgW/2;
//		boolean yC1 = cur.getY() - imgH/2 <= bird.getY() + imgH/2 && cur.getY() - imgH/2 >= bird.getY() - imgH/2;
//		boolean yC2 = cur.getY() + imgH/2 <= bird.getY() + imgH/2 && cur.getY() + imgH/2 >= bird.getY() - imgH/2;
		if (collisionF(cur)) {
			System.out.println("Collision");
			return true;
			
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
				energy -= 10;
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
				eggs++;
			}
			quizCount++;
			delayTimer = new Timer();
			delayCount = 0;
			delayTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("delayCount: " + ++delayCount);
					if (delayCount >=2) {
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
			//System.out.println(bird.getBType().getName());
			//System.out.println(cur.getType().getName());
//			boolean xC = cur.getX() - imgW/2 <= bird.getX() + imgW/2 && cur.getX() - imgW/2 >= bird.getX() - imgW/2;
//			boolean xC2 = cur.getX() + imgW/2 <= bird.getX() + imgW/2 && cur.getX() + imgW/2 >= bird.getX() - imgW/2;
//			boolean yC1 = cur.getY() - imgH/2 <= bird.getY() + imgH/2 && cur.getY() - imgH/2 >= bird.getY() - imgH/2;
//			boolean yC2 = cur.getY() + imgH/2 <= bird.getY() + imgH/2 && cur.getY() + imgH/2 >= bird.getY() - imgH/2;
			if (collisionF(cur) && !cur.getType().equals(ItemType.NEST)) {  //xC && yC1 || xC && yC2 || xC2 && yC1 || xC2 && yC2
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
	/*public void collisionNH2(HitItem ht) {
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
	}*/
	
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