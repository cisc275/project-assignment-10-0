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
	int eggs;
	ArrayList<CollectedItem> eggList;
	CollectedItem nest;
	boolean drawDE;
	
	public NH2Model(int fW, int fH, int iW, int iH, HashMap<String, int[]> map) {
		super(fW, fH, iW, iH, map);
		this.eggs = 0;
		this.eggList = new ArrayList<>();
	}

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

	@Override
	public void updatePosition() {
		if (!(outOfFrame())) {
			bird.move();
		}
		if(eggs <= 0)
			this.curState = Type.GAMEOVER;
		if(!outOfFrame()) {
			bird.move();
			if(timeCount % 2 == 0 && updateL)
				updateList();
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
				
				if(xC1 && (yC1 || yC2) || xC2 && (yC1 ||yC2 )) {
					System.out.println("removeNH2");
					eggs--;
					System.out.println("remove eggs from lists");
					eggList.remove(0);
					if(eggs <= 0)
						this.curState = Type.GAMEOVER;
					iter.remove();
				}
			}
		}
	}

	@Override
	public boolean checkCollision(Element e) {
//		int birdW = imgsSize.get(bird.getBType().getName())[0], birdH = imgsSize.get(bird.getBType().getName())[1];
//		int itemW = imgsSize.get(e.getType().getName())[0], itemH = imgsSize.get(e.getType().getName())[1];
		return collisionF(e);
	}

	@Override
	public boolean outOfFrame() {
		return (bird.getY() + bird.getYVector() < 0 || bird.getY() + imgH + bird.getYVector() > frameH || 
				bird.getX() + bird.getXVector() < 0 || bird.getX() + imgW + bird.getXVector() > frameW);
	}

	@Override
	public void startQuiz() {
		quizCount = 0;
		quiz = quizzes.get(quizCount);
	}

	@Override
	public void checkQuiz() {
		if(!quiz.checkAnswer())
			quizOutcomeInfo = "Oh No!!!  The Answer is: " + quiz.getAnswer();
		else
			quizOutcomeInfo = "Congratulations!!";
		quizCount++;
		delayTimer = new Timer();
		delayCount = 0;
		delayTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("delayCount: " + ++delayCount);
				if (delayCount >=2) {
					if (quizCount < 3)
						quiz = quizzes.get(quizCount);
					else 
						curState = Type.WIN;
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
	
	public void updateList() {
		Random random = new Random();
		int ranNum = random.nextInt(8);
		double unitVectorMag = this.calculateUnitVectorMag((frameW-imgW)/2, (frameH-imgH)/2);
		double vX = 10*(((frameW-imgW)/2)/unitVectorMag), vY = 0*(((frameW-imgW)/2)/unitVectorMag);
		int vectorX =(int) vX, vectorY = (int) vY;
		int[][] direction = new int[][] {{10, 0, (frameH-imgH)/2, 0}, 
										{-10, 0, (frameH - imgH)/2, frameW}, 
										{0, 5, 0, (frameH - imgH)/2}, 
										{0, -10, frameH, (frameW - imgW)/2}, 
										{vectorX, vectorY, 0, 0}, 
										{vectorX, -vectorY, frameH-imgH, 0}, 
										{-vectorX, vectorY, 0, frameW}, 
										{-vectorX, -vectorY, frameH-imgH, frameW-imgW}};
		list.add(new HitItem(direction[ranNum][2], direction[ranNum][3], ItemType.FOX, direction[ranNum][0], direction[ranNum][1]));
		updateL = false;
	}
	
	public double calculateUnitVectorMag(int x, int y) {
		return Math.sqrt(x * x + y * y);
	}
	
	
}
