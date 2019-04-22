package main_package;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


// author Sicheng Tian, Yufan Wang£¬ Rubai Bian
public class Model {
	Timer myTimer;
	int timeCount;
	ArrayList<Element> list;
	Bird bird;
	//Bird birdNH;
	boolean quizing;
	Type curState;
	Quiz quiz;
	int eggs;
	int numTrueAns;
	public static int xIncr = 5;
	public static int xDec = -5;
	public static int xDec2 = -3;
	public static int yIncr = 5;
	int frameW;
	int frameH;
	int imgH;
	int imgW;
	boolean updateL;
	// for move background
	int groundX;
	int groundY; 
	
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
	
	public void createTimer() {
		myTimer = new Timer();
		switch(curState) {
		case OP:
			break;
		case NH1:
			timeCount = 10;
			myTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//t++;
					System.out.println("time count :" + --timeCount);
					if (timeCount == 0) {
						myTimer.cancel();
						quizing = true;
						System.out.println("start quiz");
					}
				}
				
			}, 0, 1000);
			break;
		case NH2:
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
			curE.setX(xDec);
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
		list.add(new HitItem(frameW, ranH, ItemType.AIRPLANE));
		updateL = false;
	}
	
	// update the bird position by calling the move method
	// if the curState is NH1 call collisionNH1()
	public void updateBirdPosition(int incX, int incY) {
		if (!outOfFrame(incX, incY)) {
			bird.move(incX, incY);
			System.out.println("here1");
			if (curState == Type.NH1) {
				collisionNH1();
			}
		}
	}
	
	// helper function for updateBirdPosition to prevent bird go out of screen
	public boolean outOfFrame(int incX, int incY) {
		switch (curState) {
		case OP:
			if (bird.getY() + incY < 0|| bird.getY() + imgH + incY > frameH) {
				return true;
			}
			break;
		case NH1:
			if (bird.getY() + incY < 0 || bird.getY() + imgH + incY > frameH || 
					bird.getX() + incX < 0 || bird.getX() + imgW + incX > frameW) {
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
	
	// the method will generate a quiz
	// and set quizing boolean to be true
	public void startQuiz() {
		System.out.println("quiz");
		this.quizing = true;
	}
	
	// for OP Game
	// check the answer of the quiz
	// if it is true, set the quizing boolean to be false;
	// if it is false, call the collision method in the bird and then set the quizing boolean to be false
	// and check the remaining life of bird, if it is zero call gameOver()
	public void checkQuiz() {
		this.quizing = false;
		System.out.println("submit");
		if(!quiz.checkAnswer()) {
			bird.collision();
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
				bird.setLife(bird.getLife() + 1);
				CollectedItem c = (CollectedItem)cur;
				c.isCollected();
				System.out.println("collected!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				iter.remove();
			}
		}
	}
	
	// for NH game
	// check the number of right answer in the quiz
	// set the number of egg to be the number of right answers
	// set the quizing boolean to be false
	// set curState to be NH2
	public void submitQuiz() {
		this.quizing = false;
	}
	
	// for NH game
	// check whether the hitItem fox has collision with egg
	// if it is remove the egg item and the hitItem from list, subtract one from int eggs
	// if the fox has collision with bird, call the move method of the fox
	// check the number of eggs left. if it is zero, call gameOver()
	public void collisionNH2(HitItem ht) {
		eggs--;
		if(eggs <= 0) {
			this.curState = Type.GAMEOVER;
		}
	}
	
	// getter setter for create test
	public Type getCurState() {
		return curState;
	}
	
	public void setList(ArrayList<Element> list){
		this.list = list;
	}
	
	public void setQuiz(String question, String answer) {
		quiz = new Quiz(question, answer);
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
