package main_package;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import main_package.Type;

public abstract class Model implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient Timer myTimer;
	transient Timer delayTimer;
	static int defaultTime = 60;
//	private static final long serialVersionUID = 1L;
//	transient Timer myTimer;
//	transient Timer delayTimer;
	int timeCount;
	int delayCount;
	ArrayList<Element> list;
	Bird bird;
	boolean quizing, updateL;
	ArrayList<Quiz> quizzes;
	int quizCount;
	String quizOutcomeInfo = "";
	Quiz quiz;
	Type curState;
	int frameW;
	int frameH;
	final int scaleWidth = 1550;
	final int scaleHeight = 838;
	int imgH;
	int imgW;
	HashMap<String, int[]>imgsSize;
	boolean tutorial;
	private int groundX, groundY;
	static int eggs;
	static CollectedItem nest;
	
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
		groundX = 0;
		groundY = 0;
	}
	
	public int getImgH() {
		return imgH;
	}
	
	public int getImgW() {
		return imgW;
	}
	
	// those abstract method will be implemented in OPModel, NHModel, and NH2Model
	public abstract void createTimer(int time);
	public abstract void updatePosition();
	public abstract boolean checkCollision(Element e);
	public abstract boolean outOfFrame();
	public abstract void startQuiz();
	public abstract void checkQuiz();
	public abstract void tutorial();
	public abstract void createQuizzes() throws Exception;
	
	public abstract void recreateTimer();
	
	//
	// check if collision
	public boolean collisionF(Element e) {
		int birdW = imgsSize.get(bird.getBType().getName())[0];
		int birdH = imgsSize.get(bird.getBType().getName())[1];
		//int elementW = imgsSize.get(e.getType().getName())[0];
		//int elementH = imgsSize.get(e.getType().getName())[1];
		int xhit1 = scaleW(e.getxHitSize1());
		int xhit2 = scaleW(e.getxHitSize2());
		int yhit1 = scaleH(e.getyHitSize1());
		int yhit2 = scaleH(e.getyHitSize2());
//		System.out.println(birdW + ", " + birdH + "; " + elementW + ", " + elementH
//				);
		
		if (e.getType().equals(ItemType.WINFLAG)) {
			return bird.getX() + birdW > e.getX();
		}
		
//		boolean xC = e.getX() <= bird.getX() + birdW 
//				&& e.getX() >= bird.getX();
//		boolean xC2 = e.getX() + elementW <= bird.getX() + birdW 
//				&& e.getX() + elementW >= bird.getX();
//		boolean yC1 = e.getY() <= bird.getY() + birdH 
//				&& e.getY() >= bird.getY();
//		boolean yC2 = e.getY() + elementH <= bird.getY() + birdH 
//				&& e.getY() + elementH >= bird.getY();
		
		boolean xC = e.getX() + xhit1<= bird.getX() + birdW 
				&& e.getX() + xhit1 >= bird.getX();
		boolean xC2 = e.getX() + xhit2 <= bird.getX() + birdW 
				&& e.getX() + xhit2 >= bird.getX();
		boolean yC1 = e.getY() + yhit1 <= bird.getY() + birdH 
				&& e.getY() + yhit1>= bird.getY();
		boolean yC2 = e.getY() + yhit2 <= bird.getY() + birdH 
				&& e.getY() + yhit2 >= bird.getY();
				
		boolean result1 = xC && yC1 || xC && yC2 || xC2 && yC1 || xC2 && yC2;
		
//		boolean x2C = bird.getX() <= e.getX() + elementW
//				&& bird.getX() >= e.getX();
//		boolean x2C2 = bird.getX() + birdW <=e.getX() + elementW
//				&& bird.getX() + birdW >= e.getX();
//		boolean y2C1 = bird.getY() <= e.getY() + elementH 
//				&& bird.getY() >= e.getY();
//		boolean y2C2 = bird.getY() + birdH <= e.getY() + elementH 
//				&& bird.getY() + birdH >= e.getY();
		
		boolean x2C = bird.getX() <= e.getX() + xhit2
				&& bird.getX() >= e.getX() + xhit1;
		boolean x2C2 = bird.getX() + birdW <=e.getX() + xhit2
				&& bird.getX() + birdW >= e.getX() + xhit1;
		boolean y2C1 = bird.getY() <= e.getY() + yhit2 
				&& bird.getY() >= e.getY() + yhit1;
		boolean y2C2 = bird.getY() + birdH <= e.getY() + yhit2 
				&& bird.getY() + birdH >= e.getY() + yhit1;
				
		boolean result2 = x2C && y2C1 || x2C && y2C2 || x2C2 && y2C1 || x2C2 && y2C2;
				
		return result1 || result2;
	}
	
	// set curState to be End
	public void gameOver() {
		   curState = Type.GAMEOVER;
	}
		
	// set curState to be Win 
	public void winGame() {
		curState = Type.WIN;
	}
	
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
	
	public boolean inTutorial() {
		return tutorial;
	}
	
	public boolean getQuizing() {
		return quizing;
	}
	
	public int getGroundY() {
		return groundY;
	}
	public int getGroundX() {
		return groundX;
	}

	public void setGroundX(int groundX) {
		this.groundX = groundX;
	}

	public void setGroundY(int groundY) {
		this.groundY = groundY;
	}
	
	public int scaleW(int size) {
		return frameW * size / scaleWidth;
	}
	public int scaleH(int size) {
		return frameH * size / scaleHeight;
	}
	
}
