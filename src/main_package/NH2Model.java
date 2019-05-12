package main_package;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class NH2Model extends Model{
	// Instantiate Variables
	ArrayList<CollectedItem> eggList;
	boolean drawDE;
	
	// Constructor sets up the NH2 game and determines the locations of all elements in the game
	public NH2Model(int fW, int fH, int iW, int iH, HashMap<String, int[]> map) {
		
		super(fW, fH, iW, iH, map);
		System.out.println("egg: " + eggs);
		System.out.println("here");
		curState = Type.NH2;
		eggList = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < eggs; i++) {
			int ranX = r.nextInt((nest.getX() + imgsSize.get("nest")[0]) - nest.getX());
			int ranY = r.nextInt((nest.getY() + imgsSize.get("nest")[1]) - nest.getY()); 
			System.out.println(nest.getX() + ranX + ", " + nest.getY() + ranY);
			eggList.add(new CollectedItem(nest.getX() + ranX, nest.getY() + ranY, ItemType.EGG));
		}
		setBird(new Bird((getFrameW()-imgW)/2, (getFrameH()-imgH)/2,0,BirdType.NH));
		//setBird(new Bird(0, 0,0,BirdType.NH));
		nest = new CollectedItem((getFrameW()-imgW)/2, (getFrameH()-imgH)/2, ItemType.NEST);
		setList(new ArrayList<>());
		setUpdateL();
		createTimer();		
	}

	// This method creates a timer for 40 seconds for the NH1 game 
	@Override
	public void createTimer() {
		myTimer = new Timer();
		timeCount = 40;
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Time count : " + -- timeCount);
				updateL = true;
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
			//System.out.println("egg < 0  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
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
					System.out.println("remove eggs from lists");
					eggList.remove(0);
					if(eggs <= 0)
						this.curState = Type.GAMEOVER;
					iter.remove();
					// if the fox gets to the nest, it steals and egg
				} else if (checkCollision(curE) && curE.getType() != ItemType.NEST) {
					System.out.println("remove");
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
//		int birdW = imgsSize.get(bird.getBType().getName())[0], birdH = imgsSize.get(bird.getBType().getName())[1];
//		int itemW = imgsSize.get(e.getType().getName())[0], itemH = imgsSize.get(e.getType().getName())[1];
		return collisionF(e);
	}
	
	// This method checks if the bird has moved out of the frame and prevents this from happening
	@Override
	public boolean outOfFrame() {
		return (bird.getY() + bird.getYVector() < 0 || bird.getY() + imgH + bird.getYVector() > frameH || 
				bird.getX() + bird.getXVector() < 0 || bird.getX() + imgW + bird.getXVector() > frameW);
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
				// TODO Auto-generated method stub
				System.out.println("delayCount: " + ++delayCount);
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

	@Override
	public void tutorial() {
		// TODO Auto-generated method stub
		
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
//		Random random = new Random();
//		int ranNum = random.nextInt(8);
//		double unitVectorMag = this.calculateUnitVectorMag((frameW-imgW)/2, (frameH-imgH)/2);
//		double vX = 10*(((frameW-imgW)/2)/unitVectorMag), vY = 0*(((frameW-imgW)/2)/unitVectorMag);
//		int vectorX =(int) vX, vectorY = (int) vY;
//		int[][] direction = new int[][] {{10, 0, (frameH-imgH)/2, 0}, 
//										{-10, 0, (frameH - imgH)/2, frameW}, 
//										{0, 5, 0, (frameH - imgH)/2}, 
//										{0, -10, frameH, (frameW - imgW)/2}, 
//										{vectorX, vectorY, 0, 0}, 
//										{vectorX, -vectorY, frameH-imgH, 0}, 
//										{-vectorX, vectorY, 0, frameW}, 
//										{-vectorX, -vectorY, frameH-imgH, frameW-imgW}};
//		list.add(new HitItem(direction[ranNum][2], direction[ranNum][3], ItemType.FOX, direction[ranNum][0], direction[ranNum][1]));
//		updateL = false;
		// Generate random number
		Random ran = new Random();
		int ranSide = ran.nextInt(8);
		int height = 0;
		int width = 0;
		// Create vector in the right direction
		double unitVectorMag = this.calculateUnitVectorMag((frameW-imgW)/2, (frameH-imgH)/2);
		double vX = 5*(((frameW-imgW)/2)/unitVectorMag);
		double vY = 5*(((frameH-imgH)/2)/unitVectorMag);
		int vectorX = (int) vX;
		int vectorY = (int) vY;
		
		// Switch statement to determine create a fox spawn location.
		switch(ranSide) {
		case 0:
			height = (frameH-imgH)/2;
			width = 0;
			//direction = 'e';
			list.add(new HitItem(width, height, ItemType.FOX, 5, 0));
			//list.add(new HitItem(width, height, ItemType.FOX, 10, 0));
			System.out.println("move east");
			// Moving East
			break;
		case 1:
			height = (frameH - imgH)/2;
			width = frameW;
			//direction = 'w';
			list.add(new HitItem(width, height, ItemType.FOX, -5, 0));
//			list.add(new HitItem(width, height, ItemType.FOX, -10, 0));
			System.out.println("move west");
			// Moving West
			break;
		case 2:
			height = 0;
			width = (frameW - imgW)/2;
			//direction = 's';
			//list.add(new HitItem(width, height, ItemType.FOX, 0, 10));

			list.add(new HitItem(width, height, ItemType.FOX, 0, 5));
			System.out.println("move south");
			// Moving South
			break;
		case 3:
			height = frameH;
			width = (frameW - imgW)/2;

			//direction = 'n';
			//list.add(new HitItem(width, height, ItemType.FOX, 0, -10));
			list.add(new HitItem(width, height, ItemType.FOX, 0, -5));
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
			height = frameH-imgH;
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
			height = frameH-imgH;
			width = frameW-imgW;
			list.add(new HitItem(width, height, ItemType.FOX, -vectorX, -vectorY));
			System.out.println("move Northwest");
			// Moving Southeast
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
