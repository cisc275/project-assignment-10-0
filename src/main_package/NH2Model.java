package main_package;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class NH2Model extends Model implements Serializable{
	
	// Instantiate Variables
	private static final long serialVersionUID = 1L;
	final int gameTime = 20;
	final int foxSpeed1 = 3;
	final int foxSpeed2 = 2;
	final int foxSpeed3 = -5;
	ArrayList<CollectedItem> eggList;
	boolean drawDE;
	final int nestCenterX = 100;
	final int nestCenterY = 50;
	final int spawnLocations = 8;
	
	// Constructor sets up the NH2 game and determines the locations of all elements in the game
	public NH2Model(int fW, int fH, int iW, int iH, HashMap<String, int[]> map) {
		
		super(fW, fH, iW, iH, map);
		curState = Type.TUTORIALNH2;
		tutorialBg = new String[] {"NHtutorial3bg"};
		eggList = new ArrayList<>();
		//Random r = new Random();
		nest = new CollectedItem((getFrameW()-imgW)/2, (getFrameH()-imgH)/2, ItemType.NEST);
		for (int i = 0; i < eggs; i++) {
			switch(i) {
			case 0:
				//eggList.add(new CollectedItem(nest.getX() + 100, nest.getY() + 50, ItemType.EGG));
				eggList.add(new CollectedItem(nest.getX()+nestCenterX, nest.getY()+nestCenterY, ItemType.EGG));
				break;
			case 1:
				eggList.add(new CollectedItem(nest.getX() + nestCenterX-gameTime, nest.getY() + nestCenterY, ItemType.EGG));
				break;
			case 2:
				eggList.add(new CollectedItem(nest.getX() + nestCenterX/2, nest.getY() + nestCenterY, ItemType.EGG));
				break;
			}
		}
		setBird(new Bird((getFrameW()-imgW)/2, (getFrameH()-imgH)/2,0,BirdType.NH));
		//nest = new CollectedItem((getFrameW()-imgW)/2, (getFrameH()-imgH)/2, ItemType.NEST);
		setList(new ArrayList<>());
		setUpdateL();
		list.add(new HitItem(frameW/4, (frameH - imgH)/2, ItemType.FOX, 0, 0));	
	}
	
	// This method is called after the tutorial is over to reset the model
	public void setUpGame() {
		curState = Type.NH2;
		setBird(new Bird((getFrameW()-imgW)/2, (getFrameH()-imgH)/2,0,BirdType.NH));
		setList(new ArrayList<>());
		setUpdateL();
		createTimer(gameTime);
	}

	// This method creates a timer for 40 seconds for the NH1 game 
	@Override
	public void createTimer(int time) {
		myTimer = new Timer();
		timeCount = time;
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				-- timeCount;
				updateL = true;
				if (eggs <= 0) {
					myTimer.cancel();
				}
				if(timeCount == 0) {
					myTimer.cancel();
					curState = Type.NHREVIEW;
					try {
						createQuizzes();
						startQuiz();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, 0, 1000);
		
	}

	// This method updates the position of the bird based and controls the functionality of the game
	@Override
	public void updatePosition() {
		// Check if user got all questions correct
		// If the player has no eggs
		if(eggs <= 0) {
			this.curState = Type.GAMEOVER;
		}
		// If player not out off the screen, run the game.
		if(!outOfFrame()) {
			bird.move();
			// Every 2 seconds spawn a fox
			if(timeCount % 2 == 0 && updateL)
				updateList();
			
			// Iterate through all the elements in the game and check for collisions
			Iterator<Element> iter = list.iterator();
			while(iter.hasNext()) {
				Element curE = iter.next();
				curE.move();
				
				int nestW = imgsSize.get(nest.getType().getName())[0], nestH = imgsSize.get(nest.getType().getName())[1]; 
				int itemW = imgsSize.get(curE.getType().getName())[0], itemH = imgsSize.get(curE.getType().getName())[1];
				
				boolean xC1 = curE.getX() <= nest.getX() + nestW && curE.getX() >= nest.getX();
				boolean xC2 = curE.getX() + itemW <= nest.getX() + nestW && curE.getX() + itemW >= nest.getX();
				boolean yC1 = curE.getY() <= nest.getY() + nestH && curE.getY() >= nest.getY();
				boolean yC2 = curE.getY() + itemH <= nest.getY() + nestH && curE.getY() + itemH >= nest.getY();
				
				// if the bird collide with a fox, the fox changes direction
				if(xC1 && (yC1 || yC2) || xC2 && (yC1 ||yC2 )) {
					System.out.println("removeNH2");
					eggs--;
					if(eggList.size()>0) {
						eggList.remove(0);
					}
					if(eggs <= 0)
						this.curState = Type.GAMEOVER;
					iter.remove();
					// if the fox gets to the nest, it steals and egg
				} else if (checkCollision(curE) && curE.getType() != ItemType.NEST) {
					HitItem h = (HitItem)curE;
					
					if(!h.getDirectionChange()) {
						int newX = h.getxVector()*-1;
						int newY = h.getyVector()*-1;
						h.setyVector(newY);
						h.setxVector(newX);
						h.changeDirection();
					}
				}
			}
		}
	}
	
	// This method checks for a collision in the main game
	@Override
	public boolean checkCollision(Element e) {
		return collisionF(e);
	}
	
	// This method checks if the bird has moved out of the frame and prevents this from happening
	@Override
	public boolean outOfFrame() {
		return (bird.getY() + bird.getYVector() < 0 || bird.getY() + imgH + bird.getYVector() > frameH || 
				bird.getX() + bird.getXVector() < 0 || bird.getX() + imgW + bird.getXVector() > frameW);
	}
	
	public boolean foxoutOfFrame(Element e) {
		HitItem fox = ((HitItem) e);
		return (fox.getY() + fox.getyVector() < 0 || fox.getY() + imgH + fox.getyVector() > frameH || 
				fox.getX() + fox.getxVector() < 0 || fox.getX() + imgW + fox.getxVector() > frameW);
	}
	
	// This method sets the quizzing variable to true in order to start quizzing the player
	@Override
	public void startQuiz() {
		quizCount = 0;
		quiz = quizzes.get(quizCount);
	}

	// This method checks to if the user responses for each quiz question is correct
	// If the user gets at least one question right, then they move on to the NH2 game
	@Override
	public void checkQuiz() {
		if(!quiz.checkAnswer()) {
			// here represent reduce number of egg in NH2
			quizOutcomeInfo = "Oh No!!!  The Answer is: " + quiz.getAnswer();
		}
		else {
			quizOutcomeInfo = "Congratulations!!";
		}
		quizCount++;
		delayTimer = new Timer();
		delayCount = 0;
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
	}
	
	// This method runs the tutorial of the game and manages the position of the bird.
	@Override
	public void tutorial() {
		if(!outOfFrame()) {
			bird.move();
			// Iterate through all the elements in the game and check for collisions
			Iterator<Element> iter = list.iterator();
			while(iter.hasNext()) {
				Element curE = iter.next();
				curE.move();
				
				if (checkCollision(curE) && curE.getType() != ItemType.NEST) {
					HitItem h = (HitItem)curE;
					
					if(!h.getDirectionChange()) {
						int newX = foxSpeed3;
						int newY = 0;
						h.setyVector(newY);
						h.setxVector(newX);
						h.changeDirection();
						
					}
				}
			}
		}
	}

	// This method reads in the quiz questions for the NH1 game
	@Override
	public void createQuizzes() throws Exception{
		quizzes = new ArrayList<>();
		Scanner scanner = new Scanner(new File("NHReviewQuiz.txt"));
		while(scanner.hasNextLine()) {
			String[] infos = scanner.nextLine().split(";", -1);
			String[] choices = {infos[1],infos[2],infos[3],infos[4]};
			quizzes.add(new Quiz(infos[0], infos[5], choices));
		}
		scanner.close();
	}
	
	//helper method for updatePosition()
	// This method generate the random position for the foxes to spawn in NH2
	// It also determines the direction each fox should travel in to steal and egg
	public void updateList() {
		// Generate random number
		Random ran = new Random();
		int ranSide = ran.nextInt(spawnLocations);
		int height = 0;
		int width = 0;
		// Create vector in the right direction
		double unitVectorMag = this.calculateUnitVectorMag((frameW-imgW)/2, (frameH-imgH)/2);
		double vX = foxSpeed1*(((frameW-imgW)/2)/unitVectorMag);
		double vY = foxSpeed1*(((frameH-imgH)/2)/unitVectorMag);
		int vectorX = (int) vX;//scaleW((int) vX);
		int vectorY = (int) vY;//scaleH((int) vY);
		
		// Switch statement to determine create a fox spawn location.
		switch(ranSide) {
		case 0:
			height = (frameH-imgH)/2;
			width = 0;
			list.add(new HitItem(width, height, ItemType.FOX, foxSpeed1, 0));
			System.out.println("move east");
			// Moving East
			break;
		case 1:
			height = (frameH - imgH)/2;
			width = frameW;
			list.add(new HitItem(width, height, ItemType.FOX, -foxSpeed1, 0));
			System.out.println("move west");
			// Moving West
			break;
		case 2:
			height = 0;
			width = (frameW - imgW)/2;
			list.add(new HitItem(width, height, ItemType.FOX, 0, foxSpeed2));
			System.out.println("move south");
			// Moving South
			break;
		case 3:
			height = frameH;
			width = (frameW - imgW)/2;
			list.add(new HitItem(width, height, ItemType.FOX, 0, -foxSpeed2));
			System.out.println("move north");
			// Moving North
			break;
		case 4:
			height = 0;
			width = 0;
			list.add(new HitItem(width, height, ItemType.FOX, vectorX, vectorY));
			System.out.println("move southeast");
			// Moving Southeast
			break;
		case 5:
			height = frameH-imgH;
			width = 0;
			list.add(new HitItem(width, height, ItemType.FOX, vectorX, -vectorY));
			System.out.println("move northeast");
			// Moving northeast
			break;
		case 6:
			height = 0;
			width = frameW;
			list.add(new HitItem(width, height, ItemType.FOX, -vectorX, vectorY));
			System.out.println("move Southwest");
			// Moving Southwest
			break;
		case 7:
			height = frameH-imgH;
			width = frameW-imgW;
			list.add(new HitItem(width, height, ItemType.FOX, -vectorX, -vectorY));
			System.out.println("move Northwest");
			// Moving Northwest
			break;
		}
		
		updateL = false;
	}
	
	// This method calculates the magnitude of the vector the fox needs to reach the nest.
	// This magnitude will be used to calculate the correct unit vector for the fox.
	public double calculateUnitVectorMag(int x, int y) {
		return Math.sqrt(x * x + y * y);
	}
	
	
}
