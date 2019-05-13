package main_package;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import main_package.Controller.CustomKeyListener;
// author Sicheng Tian
public class View extends JPanel{
	//
	HashMap<String, Image> imgs;
	final int scaleHeight = 838;    //500
	final int scaleWidth = 1550;     //600
	int frameHeight = 643;    //500
	int frameWidth = 1024;     //600
	final int imageH = 150;
	final int imageW = 100;
	HashMap<String, int[]> imgsSize;
	JFrame frame;
	ArrayList<Button> list;
	// for operation
	JButton OPButton, NHButton, backButton,submitButton, choice1, choice2, choice3, choice4, serialize, deserialize;
	Image curImg;
	Model model;
	int x;
	int y;
	boolean drawDE;
	boolean first = true;
	
	
	
	
	// initialize the frameHeight, frameWidth
	// initialize the images by calling createImage method
	// initialize the frame and button
	// add button to the JPanel
	public View() {
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		//super(new GridBagLayout());
		
		frame = new JFrame();
		System.out.println("frame");
//		frame.getContentPane().add(this);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBackground(Color.gray);
		frame.setVisible(true);
		frameWidth = frame.getWidth();
    	frameHeight = frame.getHeight()-40;
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	System.out.printf("%d, %d\n", frame.getWidth(), frame.getHeight());
    	
    	//setLayout(null);
		
    			OPButton = new JButton("start Osprey Game");
    			OPButton.setOpaque(true);
    			OPButton.setBounds(300, frameHeight/3, 300, 300);
    			//OPButton.setVisible(false);
    			add(OPButton);
    			
    			NHButton = new JButton("start Northen Harrier Game");
    			NHButton.setOpaque(true);
    			NHButton.setBounds(1000,frameHeight/3,300,300);
    			//NHButton.setVisible(false);
    			add(NHButton);
    			
    			backButton = new JButton("back");
    			backButton.setOpaque(true);
    			backButton.setBounds(frameWidth/2, 10, 100, 30);
    			backButton.setVisible(false);
    			add(backButton);
    			
    			submitButton = new JButton("submit");
    			submitButton.setOpaque(true);
    			submitButton.setBounds(frameWidth/2, (frameHeight / 2) + 50 , 100, 30);
    			submitButton.setVisible(false);
    			add(submitButton);
    			
    			//GridBagConstraints gbc = new GridBagConstraints();
    			choice1 = new JButton("A");
    			choice1.setOpaque(true);
    			choice1.setBounds(frameWidth/3, 300, 50,30);
    			choice1.setVisible(false);
    			choice1.setActionCommand("A");
    			add(choice1);
    			
    			choice2 = new JButton("B");
    			choice2.setOpaque(true);
    			choice2.setVisible(false);
    			choice2.setActionCommand("B");
    			add(choice2);
    			
    			choice3 = new JButton("C");
    			choice3.setOpaque(true);
    			choice3.setVisible(false);
    			choice3.setActionCommand("C");
    			add(choice3);
    			
    			choice4 = new JButton("D");
    			choice4.setOpaque(true);
    			choice4.setVisible(false);
    			choice4.setActionCommand("D");
    			add(choice4);
    			
    			serialize = new JButton("Serialize");
    			serialize.setOpaque(true);
    			serialize.setVisible(true);
    			serialize.setBounds(frameWidth/4, (frameHeight / 4) + 50 , 100, 30);
    			serialize.setActionCommand("s");
    			add(serialize);
    			
    			deserialize = new JButton("Deserialize");
    			deserialize.setOpaque(true);
    			deserialize.setVisible(true);
    			deserialize.setBounds(frameWidth/4, (frameHeight / 4) + 50 , 100, 30);
    			deserialize.setActionCommand("d");
    			add(deserialize);
    			
    	frame.getContentPane().add(this);
    	
    	frame.setVisible(false);
		
    	imgsSize = new HashMap<>();
    	imgsSize.put("fish", new int[] {frameWidth * 115 / scaleWidth, frameHeight * 75 / scaleHeight});
    	imgsSize.put("airplane", new int[] {frameWidth * 300 / scaleWidth, frameHeight * 200 / 838});
    	imgsSize.put("ship", new int[] {frameWidth * 300 / scaleWidth, frameHeight * 200 / 838});
    	imgsSize.put("fox", new int[] {frameWidth * 100 / scaleWidth, frameHeight * 150 / 838});
    	imgsSize.put("osprey", new int[] {frameWidth * 150 / scaleWidth, frameHeight * 150 / 838});
    	imgsSize.put("osprey2", new int[] {frameWidth * 150 / scaleWidth, frameHeight * 150 / 838});
    	imgsSize.put("nh", new int[] {frameWidth * 150 / scaleWidth, frameHeight * 150 / 838});
    	imgsSize.put("winflag", new int[] {frameWidth * 200 / scaleWidth, frameHeight * 150 / 838});
    	imgsSize.put("collectedItem", new int[] {frameWidth * 100 / scaleWidth, frameHeight * 150 / 838});
    	imgsSize.put("nest", new int[] {frameWidth * 225 / 1550, frameHeight * 150 / 838});
    	imgsSize.put("fox", new int[] {frameWidth * 100 / scaleWidth, frameHeight * 150 / 838});
    	imgsSize.put("rat", new int[] {frameWidth * 120 / scaleWidth, frameHeight * 80 / 838});
    	imgsSize.put("stick", new int[] {frameWidth * 100 / scaleWidth, frameHeight * 100 / 838});
    	imgsSize.put("egg", new int[] {frameWidth * 30 / 1550, frameHeight * 50 / 838});
    	imgsSize.put("ospreyReal", new int[] {frameWidth * 400 / scaleWidth, frameHeight * 500 / 838});
    	imgsSize.put("NorthernHarrierReal", new int[] {frameWidth * 400 / scaleWidth, frameHeight * 500 / 838});
    	imgsSize.put("quizpanel", new int[] {frameWidth * 1000 / scaleWidth, frameHeight * 600 / 838});
    	
    	
    	
    	

		String[] imgName = {"osprey", "osprey2", "nh", "airplane", "fox","ship", "fish", "winflag", "rat", "nest", 
				"stick", "egg", "bgland", "bgwater", "nhbg", "ospreyReal", "NorthernHarrierReal", "opmapbg", "bgdelaware", 
				"mainmenubg", "quizpanel"};

    	imgs = new HashMap<>();
		for(int i = 0; i < imgName.length; i++) {
			BufferedImage img = createImage(imgName[i]);
			if (imgName[i].contains("bg")) {
				imgs.put(imgName[i], img.getScaledInstance(frameWidth, frameHeight, Image.SCALE_FAST));
			}
			else if(imgsSize.containsKey(imgName[i])){
				imgs.put(imgName[i], img.getScaledInstance(imgsSize.get(imgName[i])[0], imgsSize.get(imgName[i])[1], Image.SCALE_FAST));
			}
		}
		
		curImg = imgs.get("osprey");
		
		frame.setVisible(true);
    	
    	
	}
	
	
	// consume a Model and update the image according to the Model
	// and call the repaint method
	public void update(Model model) {
		//System.out.println("update model");
		if ((model.getQuizing() || model.getCurState().equals(Type.OPREVIEW) ||
				model.getCurState().equals(Type.NHREVIEW)) && model.delayTimer == null) {
			submitButton.setVisible(true);
			choice1.setVisible(true);
			choice2.setVisible(true);
			choice3.setVisible(true);
			choice4.setVisible(true);
			backButton.setVisible(false);
		}
		else {
		if (model.getCurState() == Type.MAINMENU) {
			
		}
		else if (model.getCurState() == Type.OP) {
			//remove(OPButton);
			//x = model.getBird().getX();
			//y = model.getBird().getY();
			//curImg = imgs.get("bird");
			
			if (curImg == imgs.get("osprey")) {
				curImg = imgs.get("osprey2");
			}
			else {
				curImg = imgs.get("osprey");
			}
			backButton.setVisible(true);
		}
		else if (model.getCurState() == Type.NH1) {
			//x = model.getBird().getX();
			//y = model.getBird().getY();
			drawDE = ((NHModel) model).drawDE();
			//curImg = imgs.get("bird");
			//backButton.setVisible(true);
		}
		else if (model.getCurState() == Type.NH2) {
			//x = model.getBird().getX();
			//y = model.getBird().getY();
			//curImg = imgs.get("bird");
			backButton.setVisible(true);
		}
		else if (model.getCurState() == Type.GAMEOVER) {
			backButton.setVisible(true);
		}
		else if (model.getCurState() == Type.WIN) {
			backButton.setVisible(true);
		}
		
		}
		this.model = model;
		frame.repaint();
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
//	public void animation() {
//		if (curImg == imgs.get("osprey")) {
//			curImg = imgs.get("osprey2");
//		}
//		else {
//			curImg = imgs.get("osprey");
//		}
//		
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
	// draw image and game content on the panel according to model class
	public void paintComponent(Graphics g) {
		//System.out.println("paint");
		if (model == null) {
			System.out.println("null model");
//			System.out.println(g.drawImage(imgs.get("ospreyReal"), 400, 400, Color.gray, this));
//			g.drawString("Time Remaining: ", 100, 20);
			g.drawImage(imgs.get("mainmenubg"), 0, 0, null, this);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString("Ospey:", this.frameWidth/5, this.frameHeight/9);
			g.drawString("Northern Harrier:", this.frameWidth/2, this.frameHeight/9);
			g.drawImage(imgs.get("ospreyReal"), this.frameWidth/5, this.frameHeight/8, Color.gray, this);
			g.drawImage(imgs.get("NorthernHarrierReal"), this.frameWidth/2, this.frameHeight/8, Color.gray, this);
			return;
		}
		try {

			//else {
				switch (model.getCurState()) {
				case MAINMENU:
					g.drawImage(imgs.get("mainmenubg"), 0, 0, null, this);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.drawString("Ospey:", this.frameWidth/5, this.frameHeight/9);
					g.drawString("Northern Harrier:", this.frameWidth/2, this.frameHeight/9);
					g.drawImage(imgs.get("ospreyReal"), this.frameWidth/5, this.frameHeight/8, Color.gray, this);
					g.drawImage(imgs.get("NorthernHarrierReal"), this.frameWidth/2, this.frameHeight/8, Color.gray, this);
					break;
				case OP:
//					String s = "bgwater";
//					if (model.timeCount == 55) {
//						s ="bgland";
//					}
					if(((OPModel) model).getDrawNA()) {
						g.drawImage(imgs.get("opmapbg"),0, 0, Color.gray, this);
						g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
						g.setColor(Color.red);
						g.drawString("The Osprey is a migratory bird that migrates from Delaware to Mexico.", frameWidth/7, frameHeight/3);
					} else {
						//if(model.getWaterbg()) {
							g.drawImage(imgs.get("bgwater"), model.getGroundX() % frameWidth, model.getGroundY(), Color.gray, this);
							//System.out.println("first: " + model.groundX % frameWidth);
							g.drawImage(imgs.get("bgwater"), (model.getGroundX() % frameWidth) + frameWidth, model.getGroundY(), Color.gray, this);
						/*} else if(!model.getWaterbg()) {
							/*if(frameWidth >= 0 && first) {
								if(frameWidth == 0) {
									first = false;
								}
								g.drawImage(imgs.get("bgwater"), model.groundX % frameWidth, model.groundY, Color.gray, this);
							//System.out.println("first: " + model.groundX % frameWidth);
								g.drawImage(imgs.get("bgland"), (model.groundX % frameWidth) + frameWidth, model.groundY, Color.gray, this);
							} else {
								first = false;
								g.drawImage(imgs.get("bgland"), model.groundX % frameWidth, model.groundY, Color.gray, this);
								//System.out.println("first: " + model.groundX % frameWidth);
								g.drawImage(imgs.get("bgland"), (model.groundX % frameWidth) + frameWidth, model.groundY, Color.gray, this);
							//}
						}*/
						//System.out.println("second: " + (model.groundX + frameWidth)% frameWidth);
						
						//g.drawImage(imgs.get("bgland"), (model.groundX % frameWidth) + 3 * frameWidth, model.groundY, Color.gray, this);
						//System.out.println("first: " + model.groundX % frameWidth);
						//g.drawImage(imgs.get("bgland"), (model.groundX % (2* frameWidth)) + 3 * frameWidth, model.groundY, Color.gray, this);
						g.drawImage(curImg, model.getBird().getX(), model.getBird().getY(), null, this);
						//life bar
						g.setColor(Color.black);
						g.drawRect(10, 2, (model.defaultTime - 10)*10, 20);
						g.setColor(Color.yellow);
						g.fillRect(10, 2, ((OPModel) model).getEnergy()*10, 20);
				
						if (model.getList().size() != 0) {
							for(Element each: model.getList()) {
								g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
							}
						}
					}
					break;
				case NH1:
					
//					
//					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
//					g.drawString("Items Collected: " + String.valueOf(model.getBird().getItemsCollected()), 1000, 20);
//					g.drawString("Time Remaining: " + String.valueOf(model.getTimeCount()), 100, 20);
//					g.drawImage(imgs.get("nest"), model.nest.getX(), model.nest.getY(), null,this);
					/*g.drawImage(imgs.get("nhbg"), 0, 0, null, this);
					
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.drawString("Items Collected: " + String.valueOf(model.getBird().getItemsCollected()), 1000, 20);
					g.drawString("Time Remaining: " + String.valueOf(model.getTimeCount()), 100, 20);
					g.drawImage(imgs.get("nest"), model.nest.getX(), model.nest.getY(), null,this);*/
				
//					g.drawImage(imgs.get(model.getBird().getBType().getName()), x, y, null, this);
					if(drawDE) {
						g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
						g.drawImage(imgs.get("bgdelaware"),0, 0, Color.gray, this);
						g.drawString("Time Remaining: " + String.valueOf(model.getTimeCount()), 100, 20);
						//g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
						//g.setColor(Color.red);
						//g.drawString("The Northern Harrier is a non-migratory bird that resides in Delaware.", frameWidth/7, frameHeight/3);
					} else {
						g.drawImage(imgs.get("nhbg"), 0, 0, null, this);
						if (model.getList().size() != 0) {
							for(Element each: model.getList()) {
								g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
							}
						}
						g.drawImage(imgs.get("nest"), model.nest.getX(), model.nest.getY(), null,this);
						//g.drawImage(imgs.get("osprey"), model.getBird().getX(), model.getBird().getY(), null, this);
						g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
						g.setColor(Color.red);
						g.drawString("Items Collected: " + String.valueOf(model.getBird().getItemsCollected())+ "/10", 1000, 20);
						g.drawString("Time Remaining: " + String.valueOf(model.getTimeCount()), 100, 20);
						
						g.drawImage(imgs.get(model.getBird().getBType().getName()), model.getBird().getX(), model.getBird().getY(), null, this);
					
						/*if (model.getList().size() != 0) {
							for(Element each: model.getList()) {
								g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
							}
						}*/
					}
//					if (model.getList().size() != 0) {
//						//System.out.println("times drawn");
//						for(Element each: model.getList()) {
//							//System.out.println("drawing hit item"+ each.getX());
//							g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
//						}
//					}
					break;
				case NH2:
					g.drawImage(imgs.get("nhbg"), 0, 0, null, this);		
					
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.setColor(Color.red);
					g.drawString("Time Remaining: " + String.valueOf(model.getTimeCount()), 100, 20);
					g.drawString("Eggs: " + String.valueOf(((NH2Model) model).eggs), 1000, 20);
					//g.drawString("You Win NH1", 1000, 20);
					g.drawImage(imgs.get("nest"), (this.frameWidth-this.imageW)/2, (this.frameHeight-this.imageH)/2, null,this);
					//System.out.println(model.getList().size());
					
					//g.drawImage(imgs.get("osprey"), model.getBird().getX(), model.getBird().getY(), null, this);
					if (model.getList().size() != 0) {
						//System.out.println("times drawn");
						for(Element each: model.getList()) {
							//System.out.println("drawing hit item"+ each.getX());
							g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
						}
					}
					
					if (((NH2Model) model).eggList.size() != 0) {
						for(CollectedItem each: ((NH2Model) model).eggList) {
							g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
						}
					}
					g.drawImage(imgs.get(model.getBird().getBType().getName()), model.getBird().getX(), model.getBird().getY(), null, this);
					
					break;
				case GAMEOVER:
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.drawString("Gameover", this.frameWidth/2, this.frameHeight/2);
					break;
				case OPREVIEW:
					g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
					g.drawString("You Win the osprey game, Review what you learned", frameWidth/3, frameHeight * 70/838);
					g.drawString("Your chose: " + model.getQuiz().getChosenAnswer(), frameWidth/3, frameHeight * 100/838);
					g.drawString(model.getQuiz().getQuestion(), frameWidth/3, frameHeight * 260/838);
					g.drawString("A: " + model.getQuiz().getChoice()[0], frameWidth/3, frameHeight * 300 / 838);
					g.drawString("B: " + model.getQuiz().getChoice()[1], frameWidth/3, frameHeight * 340 / 838);
					g.drawString("C: " + model.getQuiz().getChoice()[2], frameWidth/3, frameHeight * 380 / 838);
					g.drawString("D: " + model.getQuiz().getChoice()[3], frameWidth/3, frameHeight * 420 / 838);
					g.setColor(Color.red);
					g.drawString(model.quizOutcomeInfo, frameWidth/3, frameHeight * 200/838);
					break;
				case NHREVIEW:
					g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
					g.drawString("You Win the Northern Harrier game, Review what you learned", frameWidth/3, frameHeight * 70/838);
					g.drawString("Your chose: " + model.getQuiz().getChosenAnswer(), frameWidth/3, frameHeight * 100/838);
					g.drawString(model.getQuiz().getQuestion(), frameWidth/3, frameHeight * 260/838);
					g.drawString("A: " + model.getQuiz().getChoice()[0], frameWidth/3, frameHeight * 300 / 838);
					g.drawString("B: " + model.getQuiz().getChoice()[1], frameWidth/3, frameHeight * 340 / 838);
					g.drawString("C: " + model.getQuiz().getChoice()[2], frameWidth/3, frameHeight * 380 / 838);
					g.drawString("D: " + model.getQuiz().getChoice()[3], frameWidth/3, frameHeight * 420 / 838);
					g.setColor(Color.red);
					g.drawString(model.quizOutcomeInfo, frameWidth/3, frameHeight * 200/838);
					break;
				case WIN:
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.drawString("You finished the review quiz", this.frameWidth/3, this.frameHeight/2);
					break;
				}
		//	}
				if (model.getQuizing()) {
					g.drawImage(imgs.get("quizpanel"), frameWidth/4, frameHeight * 100/838, null,this);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 29));
					g.setColor(Color.black);
					g.drawString("Your chose: " + model.getQuiz().getChosenAnswer(), frameWidth/3, frameHeight * 220/838);
					//Don't delete this, this is the front changing attempt
					/*
					String question = model.getQuiz().getQuestion();
					String[] questions = question.split(".", -1);
					for(int i = 0; i < questions.length; i++) {
						if(i != (questions.length -1)) {
							g.drawString(model.getQuiz().getQuestion(), frameWidth/2, 260);
						}
					}
					*/
					g.drawString(model.getQuiz().getQuestion(), frameWidth/3, frameHeight * 360/838);
					g.drawString("A: " + model.getQuiz().getChoice()[0], frameWidth/3, frameHeight * 400 / 838);
					g.drawString("B: " + model.getQuiz().getChoice()[1], frameWidth/3, frameHeight * 440 / 838);
					g.drawString("C: " + model.getQuiz().getChoice()[2], frameWidth/3, frameHeight * 480 / 838);
					g.drawString("D: " + model.getQuiz().getChoice()[3], frameWidth/3, frameHeight * 520 / 838);
					if (model.getCurState() == Type.NH1) {
						//g.drawString(model.quizCount + "/" + 3 + " Quizs", frameWidth * 30 / 1550, frameHeight * 30/838);
						//g.drawString(model.quizCount + "/" + 3 + " Quizzes", 30, 30);
						g.drawString(model.quizCount + "/" + 3 + " Quizzes", frameWidth/2, frameHeight * 600/838);

					}
					g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
					g.setColor(Color.red);
					g.drawString(model.quizOutcomeInfo, frameWidth/3, frameHeight * 300/838);
					
					
				}
		}catch(NullPointerException n) {
			n.printStackTrace();
		}
		
		
	}
	
	// read the image and return it
	public BufferedImage createImage(String x) {
		BufferedImage bi;
		try {
			if (x.equals("osprey")) {
				bi = ImageIO.read(new File("imgs/Osprey1.png"));
			}
			else if (x.equals("osprey2")) {
				bi = ImageIO.read(new File("imgs/Osprey2.png"));
			}
			else if (x.equals("nh")) {
				bi = ImageIO.read(new File("imgs/nh1.png"));
			}
			else if (x.equals("mainmenubg")) {
				bi = ImageIO.read(new File("imgs/mainmenu.png"));
			}
			else if (x.equals("quizpanel")) {
				bi = ImageIO.read(new File("imgs/quizpanel.png"));
			}
			else if (x.equals("airplane")) {
				bi = ImageIO.read(new File("imgs/airplane.png"));
			}
			else if (x.equals("fox")) {
				bi = ImageIO.read(new File("imgs/fox.png"));
			}
			else if (x.equals("ship")) {
				bi = ImageIO.read(new File("imgs/ship.png"));
			}
			else if (x.equals("fish")) {
				bi = ImageIO.read(new File("imgs/fish.png"));
			}
			else if (x.equals("winflag")) {
				bi = ImageIO.read(new File("imgs/flag.png"));
			}
			else if (x.equals("rat")) {
				bi = ImageIO.read(new File("imgs/rat.png"));
			}
			else if (x.equals("egg")) {
				bi = ImageIO.read(new File("imgs/egg.png"));
			}
			else if (x.equals("stick")) {
				bi = ImageIO.read(new File("imgs/stick.png"));
			}
			else if (x.equals("nest")) {
				bi = ImageIO.read(new File("imgs/nest.png"));
			}
			else if (x.equals("bgland")) {
				bi = ImageIO.read(new File("imgs/bgland.png"));
			}
			else if (x.equals("bgwater")) {
				bi = ImageIO.read(new File("imgs/bgwater.png"));
			}
			else if (x.equals("nhbg")) {
				bi = ImageIO.read(new File("imgs/nhbg.png"));
			}
			else if (x.equals("opmapbg")) {
				bi = ImageIO.read(new File("imgs/opmapbg.png"));
			}
			else if (x.equals("ospreyReal")) {
				bi = ImageIO.read(new File("imgs/ospreyReal.jpg"));
			}
			else if (x.equals("NorthernHarrierReal")) {
				bi = ImageIO.read(new File("imgs/NHReal.jpg"));
			}
			else if (x.equals("bgdelaware")) {
				bi = ImageIO.read(new File("imgs/Delaware.png"));
			}
			else {
				bi = null;
			}
			return bi;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
}
