package main_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.io.Serializable;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Random;

import main_package.Type;

public class NHModel extends Model implements Serializable{
	
	private static final long serialVersionUID = 1L;
	boolean moreCollectedItems;
	boolean drawDE;
	Random rand = new Random(); 
	String nestBuild;
	final int mapTime = 5;
	final int gameTime = 40;
	final int numCollectedItems = 5;
	final int totalItems = 10;
	
	// Constructor sets up the NH1 game and determines the locations of all elements in the game
	public NHModel(int fW, int fH, int iW, int iH, HashMap<String, int[]> map) {
		super(fW, fH, iW, iH, map);
		nestBuild = "nest10";
		setCurState(Type.TUTORIALNH1);
		tutorialBg = new String[] {"NHtutorial1bg","NHtutorial2bg"};
		tutor = 0;
		setList(new ArrayList<>());
		setBird(new Bird((getFrameW()-imgW)/2, (getFrameH()-imgH)/2,0,BirdType.NH));
		createTimer(mapTime);
	}
	
	// This method is called after the tutorial is over to reset the model
	public void setUpGame() {
		nestBuild = "nest1";
		setList(new ArrayList<>());
		setNest(new CollectedItem((getFrameW()-imgW)/2, (getFrameH()-imgH)/2, ItemType.NEST));
		createCollectedItems(numCollectedItems, ItemType.STICK);
		createCollectedItems(numCollectedItems, ItemType.RAT);
		setBird(new Bird((getFrameW()-imgW)/2, (getFrameH()-imgH)/2,0,BirdType.NH));
		
		try {
			createQuizzes();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		createTimer(gameTime);
	}
	
	// This method adds collected items to the ArrayList of items
	public void createCollectedItems(int num, ItemType it) {
		for(int i = 0; i<num; i++) {
			getList().add(new CollectedItem(rand.nextInt(getFrameW()-imgW), rand.nextInt(getFrameH()-imgH), it));
		}
	}

	// This method creates a timer for 35 seconds for the NH1 game 
	@Override
	public void createTimer(int time) {
		myTimer = new Timer();
		timeCount = time;
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				--timeCount;
				if (timeCount == 0) {
					myTimer.cancel();
				} 
			}
			
		}, 0, 1000);
	}
	
	// This method updates the position of the bird based and controls the functionality of the game
	@Override
	public void updatePosition() {
		if(timeCount <= 0) {
			gameOver();
		}
		if (bird.getItemsCollected() == numCollectedItems) {
			nestBuild = "nest5";
		}
		else if (bird.getItemsCollected() == totalItems) {
			nestBuild = "nestgold";
		}
		
		if (!outOfFrame()) {
			bird.move();
			if (curState == Type.NH1) {
				//bird.move();
				checkCollision(bird);
				if (!moreCollectedItems && collisionF(nest) && curState == Type.NH1) {
					System.out.println("NH1 Complete");
					myTimer.cancel();
					startQuiz();
				}
			}
		} 
		
	}
	
	// This method checks to see if the bird has collided with a collected item
	@Override
	public boolean checkCollision(Element e) {
		System.out.println("call");
		boolean collision = false;
		Iterator<Element> iter = list.iterator();
		while(iter.hasNext()) {
			Element cur = iter.next();
			if (collisionF(cur) && !cur.getType().equals(ItemType.NEST)) {  //xC && yC1 || xC && yC2 || xC2 && yC1 || xC2 && yC2
				bird.setItemsCollected(bird.getItemsCollected() + 1);
				CollectedItem c = (CollectedItem)cur;
				c.isCollected();
				iter.remove();
				collision = true;
			}
		}
		moreCollectedItems = false;
		for(Element el: list) {
			if(el instanceof CollectedItem) {
				moreCollectedItems = true;
			}
		}
		System.out.println(list.size());
		System.out.println(moreCollectedItems);
		return collision;
	}

	// This method checks if the bird has moved out of the frame and prevents this from happening
	@Override
	public boolean outOfFrame() {
		if (bird.getY() + bird.getYVector() < 0 || bird.getY() + imgH + bird.getYVector() > frameH || 
				bird.getX() + bird.getXVector() < 0 || bird.getX() + imgW + bird.getXVector() > frameW) {
			return true;
		}	
		return false;
	}
	
	// This method sets the quizzing variable to true in order to start quizzing the player
	@Override
	public void startQuiz() {
		this.quizing = true;
		quizCount = 0;
		quiz = quizzes.get(quizCount);
	}
	
	// This method checks to if the user responses for each quiz question is correct
	// If the user gets at least one question right, then they move on to the NH2 game
	@Override
	public void checkQuiz() {
		int dLimit = 2;
		int qLimit = 3;
		int thousand =1000;
		if(!quiz.checkAnswer()) {
			bird.collision();
			quizOutcomeInfo = "You Lose a Northern Harrier Egg";
		}
		else {
			quizOutcomeInfo = "Correct!!! ";
			eggs++;
		}
		System.out.println("Eggs: " + eggs);
		quizCount++;
		delayTimer = new Timer();
		delayCount = 0;
		delayTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				++delayCount;
				if (delayCount >= dLimit) {
					if (quizCount < qLimit) {
						quiz = quizzes.get(quizCount);
					}
					else {
						quizing = false;
						if(eggs <=0) {
							gameOver();
						} else {
							curState = Type.NH2;
						}
					}
					quizOutcomeInfo = "";
					try {
					delayTimer.cancel();
					}catch(Exception e) {}
					delayTimer = null;
				}
			}
			
		}, 0, thousand);
		
	}
	
	// This method runs the tutorial for the NH1 game
	@Override
	public void tutorial() {
		
		if (!outOfFrame() && timeCount == 0) {
			drawDE = false;
			bird.move();
			if(this.checkCollision(bird)) {
				System.out.println("No more");
			}
		}
	}
	
	// This method reads in the quiz questions for the NH1 game
	@Override
	public void createQuizzes() throws Exception{
		Scanner scan;
		quizzes = new ArrayList<>();
		File file = null;
		file = new File("NHquiz.txt");
		scan = new Scanner(file);
		while(scan.hasNextLine()) {
			String[] infos = scan.nextLine().split(";", -1);
			String[] choices = {infos[1],infos[2],infos[3],infos[4]};
			quizzes.add(new Quiz(infos[0], infos[5], choices));
		}
		scan.close();
	}
	
	// Setter for the nest object
	public void setNest(CollectedItem n) {
		nest  = n;
	}
	// getting for drawDE map variable
	public boolean drawDE() {
		return drawDE;
	}
	
	public String getNestBuild() {
		return nestBuild;
	}

}
