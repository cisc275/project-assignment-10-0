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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//static CollectedItem nest;
	boolean moreCollectedItems;
	boolean drawDE;
	Random rand = new Random(); 
	String nestBuild;
	//static int eggs;
	
	// Constructor sets up the NH1 game and determines the locations of all elements in the game
	public NHModel(int fW, int fH, int iW, int iH, HashMap<String, int[]> map) {
		super(fW, fH, iW, iH, map);
		//setCurState(Type.NH1);
		nestBuild = "nest10";
		setCurState(Type.TUTORIALNH1);
		tutorialBg = new String[] {"NHtutorial1bg","NHtutorial2bg"};
		tutor = 0;
		setList(new ArrayList<>());
		//setNest(new CollectedItem((getFrameW()-imgW)/2, (getFrameH()-imgH)/2, ItemType.NEST));
		setBird(new Bird((getFrameW()-imgW)/2, (getFrameH()-imgH)/2,3,BirdType.NH));
		//getList().add(new CollectedItem((getFrameW()-imgW)/3, getFrameH()-imgH, ItemType.STICK));
		//getList().add(new CollectedItem(4*(getFrameW()-imgW)/5, (getFrameH()-imgH)/2, ItemType.RAT));
		createTimer(5);
		//getList().add(new CollectedItem(2*(getFrameW()-imgW)/3, getFrameH()-imgH, ItemType.RAT));
	}
	
	public void setUpGame() {
		nestBuild = "nest1";
		setList(new ArrayList<>());
		setNest(new CollectedItem((getFrameW()-imgW)/2, (getFrameH()-imgH)/2, ItemType.NEST));
		createCollectedItems(5, ItemType.STICK);
		createCollectedItems(5, ItemType.RAT);
		setBird(new Bird((getFrameW()-imgW)/2, (getFrameH()-imgH)/2,3,BirdType.NH));
		
		try {
			createQuizzes();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		createTimer(40);
	}
	
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
				
				//System.out.println("time count :" + --timeCount);
				--timeCount;
				if (timeCount == 0) {
					myTimer.cancel();
					//drawDE = false;
				} 
			}
			
		}, 0, 1000);
	}
	
	// This method updates the position of the bird based and controls the functionality of the game
	@Override
	public void updatePosition() {
		//System.out.println("Updating position");
		if(timeCount <= 0) {
			gameOver();
		}
		if (bird.getItemsCollected() == 5) {
			nestBuild = "nest5";
		}
		else if (bird.getItemsCollected() == 10) {
			nestBuild = "nestgold";
		}
		
		if (!outOfFrame()) {
			bird.move();
			//System.out.println(bird.getX() + ", " + bird.getY());
			//System.out.println("here1");
			if (curState == Type.NH1) {
				/*if(timeCount > 30) {
					drawDE = true;
				} else {
					drawDE = false;*/
					bird.move();
					checkCollision(bird);
				
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
					//eggs = 1;
					//myTimer.cancel();
					//curState= Type.NH2;
				}
				
				//}
			}
		} 
		
	}
	
	// This method checks to see if the bird has collided with a collected item
	@Override
	public boolean checkCollision(Element e) {
		boolean collision = false;
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
				//System.out.println("collected!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
		if(!quiz.checkAnswer()) {
			// here represent reduce number of egg in NH2
			bird.collision();
			quizOutcomeInfo = "You Lose a Northern Harrier Egg";
			/*if(eggs > 1) {
				eggs--;
			}*/
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
				// TODO Auto-generated method stub
				//System.out.println("delayCount: " + ++delayCount);
				++delayCount;
				if (delayCount >=2) {
					if (quizCount < 3) {
						quiz = quizzes.get(quizCount);
						
					}
					else {
						quizing = false;
						if(eggs <=0) {
							gameOver();
							//System.out.println("gameover");
						} else {
							curState = Type.NH2;
							
							//System.out.println(curState);
							//resetModelNH2();
						}
					}
					quizOutcomeInfo = "";
					delayTimer.cancel();
					delayTimer = null;
				}
			}
			
		}, 0, 1000);
		
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
//			if (!moreCollectedItems && collisionF(nest)) {
//				System.out.println("NH1 Complete");
//				bird.setItemsCollected(0);
//				setUpGame();
//				curState = Type.NH1;
//			}
			
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
//			System.out.println(infos.length);
//			for(int i = 0; i < infos.length; i++) {
//				System.out.println(infos[i]);
//			}
			String[] choices = {infos[1],infos[2],infos[3],infos[4]};
			quizzes.add(new Quiz(infos[0], infos[5], choices));
		}
		scan.close();
	}
	
	// Setter for the nest object
	public void setNest(CollectedItem n) {
		nest  = n;
	}
	
	public boolean drawDE() {
		return drawDE;
	}

}
