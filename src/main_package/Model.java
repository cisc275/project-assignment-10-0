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

import main_package.Type;

public abstract class Model {
	Timer myTimer;
	Timer delayTimer;
	int defaultTime;
	int timeCount;
	int delayCount;
	ArrayList<Element> list;
	Bird bird;
	boolean quizing;
	ArrayList<Quiz> quizzes;
	int quizCount;
	String quizOutcomeInfo = "";
	Quiz quiz;
	Type curState;
	int frameW;
	int frameH;
	int imgH;
	int imgW;
	HashMap<String, int[]>imgsSize;
	boolean tutorial;
	
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
	
	public abstract void createTimer();
	public abstract void updatePosition();
	public abstract void checkCollision();
	public abstract void outOfFrame();
	public abstract void startQuiz();
	public abstract void checkQuiz();
	public abstract void tutorial();
	public abstract void createQuizzes();
}
