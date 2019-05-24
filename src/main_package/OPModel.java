package main_package;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class OPModel extends Model implements Serializable{
	private int energy;
	private boolean waterbg;
	private boolean drawNA, winFlag;
	private final int flagTime = 30;
	private int delayT = 0;
	
	public static int xbg = -6;
	Color color;
	
	// create OPModel for osprey game
	public OPModel(int fW, int fH, int iW, int iH, HashMap<String, int[]> map) {
		super(fW, fH, iW, iH, map);
		drawNA = true;
		quizing = false;
		setCurState(Type.TUTORIALOP);
		tutorialBg = new String[] {"OPtutorial1bg", "OPtutorial2bg"};
		tutor = 0;
		energy = 50;
		setBird(new Bird(0,250,3,BirdType.OSPREY));
		setList(new ArrayList<>());
		createTimer(5);
		color = Color.yellow;
		
	}
	
	// set a timer and task on osprey game from 60 to 0, every second count one
	@Override
	public void createTimer(int time) {
		if (drawNA) {
			timeCount = time;
			myTimer = new Timer();
			myTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					--timeCount;
					if (timeCount <= 0) {
						myTimer.cancel();
						myTimer = null;
					}
				}
				
			}, 0,1000);
		}else {
		timeCount = defaultTime;
		energy = defaultTime - 10;
		myTimer = new Timer();
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!quizing) {
					--timeCount;
					energy --;
				}
				if (timeCount % 2 == 0) {
					// every 4 second update list
					updateL = true;
				}
				if (energy <= 0) {
					// lose all energy game over
					gameOver();
					myTimer.cancel();
				}
				if (timeCount < flagTime || curState == Type.OPREVIEW) {
					updateL = false;
					myTimer.cancel();
				}
			}
			
		}, 0, 1000);
		}
	}
	
	// loop through the collection list update their position by calling the move method
	// call checkCollision
	@Override
	public void updatePosition() {
		color = Color.yellow;
		// for bird
		if (!(outOfFrame())) {
			bird.move();
		}
			setGroundX(getGroundX() + xbg);
			// for element
			if (updateL) {
				updateList();
			}
			
			// if timeCount(not energy) is out win flag
			if (timeCount == flagTime && winFlag) {
				// flag has same speed as background
				list.add(new HitItem(frameW, frameH / 3, ItemType.WINFLAG, xbg,0));
				winFlag = false;
			}
			
			Iterator<Element> iter = list.iterator();
			while(iter.hasNext()) {
				Element curE = iter.next();
				curE.move();
				if (curE.getX() + imgsSize.get(curE.getType().getName())[0] <= 0 ) { 
					iter.remove();
				}
				else if (checkCollision(curE)) {
					iter.remove();
				}
			}
		
	}
	
	//helper for updatePosition() to update the list by adding obiect in it 
		public void updateList() {
			Random ran = new Random();
			int ranType = ran.nextInt(3);
			int ranSpeed = scaleW(ran.nextInt(14) + 10);
			int ranPosition1 = scaleH(ran.nextInt(frameH/2));
			int ranPosition2 = scaleH(ran.nextInt(frameH/2 - 200) + frameH/2);
			switch(ranType) {
			// 0 is airplane
			case 0:
				list.add(new HitItem(frameW, ranPosition1, ItemType.AIRPLANE, 0 - ranSpeed,0));  //    1/4
			break;
			// 1 is ship
			case 1:
				list.add(new HitItem(frameW, ranPosition2, ItemType.SHIP, 0 - ranSpeed,0));  //     4/7
			break;
			// 2 is fish
			case 2:
				list.add(new HitItem(frameW, frameH*5/7, ItemType.FISH, 0 - ranSpeed,0));   //   5/7
			break;
			}
			updateL = false;
		}

	// check if the bird position has collision with other hitItem except fish
	// if it is call the startQize method
	// if has collision with fish call eat() in the bird
	// remove the hitItem that has collision from the Element list
	// check if the bird has collision with the final flag
	// if it is call winGame()
	@Override
	public boolean checkCollision(Element ht) {
		if (collisionF(ht)) {
			if(ht instanceof HitItem) {
				HitItem h = (HitItem)ht;
				if(h.getType().equals(ItemType.FISH)) {
					color = Color.green;
					bird.eat();
					//get energy
					if (defaultTime - 10 - energy <= 10) {
						energy = defaultTime - 10;
					}
					else {
						energy += 10;
					}
					
					
				} else if(h.getType().equals(ItemType.AIRPLANE) || h.getType().equals(ItemType.FOX) || h.getType().equals(ItemType.SHIP)) {
					bird.collision();
					startQuiz();
				} else if(h.getType().equals(ItemType.WINFLAG)) {
					curState = Type.OPREVIEW;
					try {
					createQuizzes();
					startQuiz();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			return true;
		}
		return false;
	}
	
	// check if the bird go out of the frame
	@Override
	public boolean outOfFrame() {
		if (bird.getY() + bird.getYVector() < 0|| bird.getY() + imgH + bird.getYVector() > frameH) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// start quiz set quizzing to be true 
	@Override
	public void startQuiz() {
		switch(curState) {
		case OP:
		color = Color.red;
		this.quizing = true;
		Random r = new Random();
		if (quizzes.size() != 0) {
			quiz = quizzes.get(r.nextInt(quizzes.size()));
		}
		break;
		case TUTORIALOP:
			quizing = true;
			color = Color.red;
			if (energy - 20 >=0) {
				energy -= 20;
			}else {
				energy = 0;
			}
			break;
		case OPREVIEW:
			quizCount = 0;
			quiz = quizzes.get(quizCount);
			break;
		}
	}
	
	// check if the play chose a correct answer for the quiz
	// and give a message
	@Override
	public void checkQuiz() {
		switch(curState) {
		case OP:
		if(!quiz.checkAnswer()) {
			bird.collision();
			energy -= 20;
			quizOutcomeInfo = "You Are Wrong, Lose Energy!!";
		}
		else {
			color = Color.green;
			quizOutcomeInfo = "Congratulations! You Saved the Bird!!";
		}
		delayTimer = new Timer();
		delayCount = delayT;
		delayTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				++delayCount;
				if (delayCount >=3) {
					quizing = false;
					delayTimer.cancel();
					delayTimer = null;
					quizOutcomeInfo = "";
				}
			}
			
		}, 0, 1000);
		break;
		case OPREVIEW:
			if(!quiz.checkAnswer()) {
				quizOutcomeInfo = "Oh No!!!  The Answer is: " + quiz.getAnswer();
			}
			else {
				quizOutcomeInfo = "Congratulations!!";
			}
			quizCount++;
			delayTimer = new Timer();
			delayCount = delayT;
			delayTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					++delayCount;
					if (delayCount >=2) {
						if (quizCount < 3) {
							quiz = quizzes.get(quizCount);
							
						}
						else {
							winGame();
						}
						quizOutcomeInfo = "";
						delayTimer.cancel();
						delayTimer = null;
					}
				}
				
			}, 0, 1000);
			break;
		case TUTORIALOP:
			quizing = false;
			break;
		}
	}
	
	// create a tutorial for player to learn how to paly the game
	@Override
	public void tutorial() {
		if (!outOfFrame() && timeCount == 0) {
			drawNA = false;
			bird.move();
			if (list.isEmpty() && tutor == 1 && !quizing) {
				color = Color.yellow;
				list.add(new HitItem(frameW, scaleH(100), ItemType.AIRPLANE, 0 - 10,0));
				list.add(new HitItem(frameW, scaleH(320), ItemType.SHIP, 0 - 10,0));
				list.add(new HitItem(frameW, 3*frameH/4, ItemType.FISH, 0 - 10,0));
			}
			Iterator<Element> iter = list.iterator();
			while(iter.hasNext()) {
				Element curE = iter.next();
				curE.move();
				if (curE.getX() + imgsSize.get(curE.getType().getName())[0] <= 0 ) {
					iter.remove();
				}
				else if (checkCollision(curE)) {
					iter.remove();
				}
			}
		}
	}
	
	public void setUpGame() {
		setBird(new Bird(0,250,3,BirdType.OSPREY));
		setList(new ArrayList<>());
		getList().add(new HitItem(getFrameW(), 100, ItemType.AIRPLANE, -10, 0));
		setUpdateL();
		winFlag = true;
		try {
			createQuizzes();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		createTimer(defaultTime);
		color = Color.yellow;
	}
	
	// create quizzes for osprey game and osprey review quiz by reading file
	@Override
	public void createQuizzes() throws Exception{
		Scanner scan;
		quizzes = new ArrayList<>();
		File file = null;
		switch(curState) {
		case OPREVIEW:
			file = new File("OPReviewQuiz.txt");
			break;
		case OP:
			file = new File("OPquiz.txt");
			break;
		}
		
		scan = new Scanner(file);
		while(scan.hasNextLine()) {
			String[] infos = scan.nextLine().split(";", -1);
			String[] choices = {infos[1],infos[2],infos[3],infos[4]};
			quizzes.add(new Quiz(infos[0], infos[5], choices));
		}
		scan.close();
	}
	
	// getter and setter
	public int getEnergy() {
		return energy;
	}

	public boolean isWinFlag() {
		return winFlag;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public boolean isWaterbg() {
		return waterbg;
	}

	public void setWaterbg(boolean waterbg) {
		this.waterbg = waterbg;
	}
	
	public boolean getDrawNA() {
		return drawNA;
	}

	public int getFlagTime() {
		return flagTime;
	}

	public void setDrawNA(boolean drawNA) {
		this.drawNA = drawNA;
	}

	public void setWinFlag(boolean winFlag) {
		this.winFlag = winFlag;
	}

	public void setDelayT(int delayT) {
		this.delayT = delayT;
	}
	
	
}
