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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import main_package.Controller.CustomKeyListener;
// author Sicheng Tian
public class View extends JPanel{
	//
	HashMap<String, Image> imgs;
	Image[] opmaps;
	Image[] nhmaps;
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
	JButton OPButton, NHButton, backButton,submitButton, choice1, choice2, choice3, choice4, serialize, deserialize, next;
	Image curImg, opmap, nhmap;
	int pic = 0;
	int pic2 = 0;
	final int fcOpmap = 5;
	final int fcNhmap = 4;
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
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 
    	frame.setVisible(false);
		
    	imgsSize = new HashMap<>();
    	imgsSize.put("fish", new int[] {scaleW(115), scaleH(75)});
    	imgsSize.put("airplane", new int[] {scaleW(300), scaleH(200)});
    	imgsSize.put("ship", new int[] {scaleW(300), scaleH(200)});
    	imgsSize.put("fox", new int[] {scaleW(100), scaleH(150)});
    	imgsSize.put("osprey", new int[] {scaleW(150), scaleH(150)});
    	imgsSize.put("osprey2", new int[] {scaleW(150), scaleH(150)});
    	imgsSize.put("nh", new int[] {scaleW(150), scaleH(150)});
    	imgsSize.put("winflag", new int[] {scaleW(200), scaleH(150)});
    	imgsSize.put("collectedItem", new int[] {scaleW(100), scaleH(150)});
    	imgsSize.put("nest10", new int[] {scaleW(225), scaleH(150)});
    	imgsSize.put("nest5", new int[] {scaleW(225), scaleH(150)});
    	imgsSize.put("nest1", new int[] {scaleW(225), scaleH(150)});
    	imgsSize.put("fox", new int[] {scaleW(100), scaleH(150)});
    	imgsSize.put("rat", new int[] {scaleW(120), scaleH(80)});
    	imgsSize.put("stick", new int[] {scaleW(100), scaleH(100)});
    	imgsSize.put("egg", new int[] {scaleW(30), scaleH(50)});
    	imgsSize.put("ospreyReal", new int[] {scaleW(400), scaleH(500)});
    	imgsSize.put("NorthernHarrierReal", new int[] {scaleW(400), scaleH(500)});
    	imgsSize.put("quizpanel", new int[] {scaleW(1000), scaleH(600)});
    	imgsSize.put("nextbutton", new int[] {scaleW(150), scaleH(70)});
    	

		String[] imgName = {"osprey", "osprey2", "nh", "airplane", "fox","ship", "fish", "winflag", "rat", "nest1","nest5","nest10", 
				"stick", "egg", "bgland", "bgwater", "nhbg", "ospreyReal", "NorthernHarrierReal", "opmapbg","opmapbg2"
				,"opmapbg3","opmapbg4","opmapbg5" ,"nhmapbg","nhmapbg2","nhmapbg3","nhmapbg4" ,"mainmenubg", "quizpanel",
				"NHtutorial1bg","NHtutorial2bg","NHtutorial3bg","nextbutton"};

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
		opmap = imgs.get("opmapbg");
		nhmap = imgs.get("nhmapbg");
		nhmaps = new Image[]{imgs.get("nhmapbg"),imgs.get("nhmapbg2"),imgs.get("nhmapbg3"),imgs.get("nhmapbg4") };
		opmaps = new Image[]{imgs.get("opmapbg"), imgs.get("opmapbg2"),imgs.get("opmapbg3"), imgs.get("opmapbg4"),imgs.get("opmapbg5")};
		
		
		setLayout(null);
		OPButton = new JButton(new ImageIcon(imgs.get("ospreyReal")));
		OPButton.setOpaque(true);
		OPButton.setBounds(frameWidth/5, frameHeight/8, imgsSize.get("ospreyReal")[0], imgsSize.get("ospreyReal")[1]);
		//OPButton.setVisible(false);
		add(OPButton);
		
		NHButton = new JButton(new ImageIcon(imgs.get("NorthernHarrierReal")));
		NHButton.setOpaque(true);
		NHButton.setBounds(frameWidth/2,frameHeight/8,imgsSize.get("NorthernHarrierReal")[0],imgsSize.get("NorthernHarrierReal")[1]);
		//NHButton.setVisible(false);
		add(NHButton);
		
		backButton = new JButton("back");
		backButton.setOpaque(true);
		backButton.setBounds(frameWidth/2, scaleH(5), scaleW(100), scaleH(30));
		backButton.setVisible(false);
		add(backButton);
		
		submitButton = new JButton("submit");
		submitButton.setOpaque(true);
		submitButton.setBounds(scaleW(795), scaleH(600) , scaleW(100), scaleH(30));
		submitButton.setVisible(false);
		add(submitButton);
		
		//GridBagConstraints gbc = new GridBagConstraints();
		choice1 = new JButton("A");
		choice1.setOpaque(true);
		choice1.setBounds(scaleW(730), scaleH(550), scaleW(50),scaleH(30));
		choice1.setVisible(false);
		choice1.setActionCommand("A");
		add(choice1);
		
		choice2 = new JButton("B");
		choice2.setOpaque(true);
		choice2.setBounds(scaleW(780), scaleH(550), scaleW(50),scaleH(30));
		choice2.setVisible(false);
		choice2.setActionCommand("B");
		add(choice2);
		
		choice3 = new JButton("C");
		choice3.setOpaque(true);
		choice3.setBounds(scaleW(830), scaleH(550), scaleW(50),scaleH(30));
		choice3.setVisible(false);
		choice3.setActionCommand("C");
		add(choice3);
		
		choice4 = new JButton("D");
		choice4.setOpaque(true);
		choice4.setBounds(scaleW(880), scaleH(550), scaleW(50),scaleH(30));
		choice4.setVisible(false);
		choice4.setActionCommand("D");
		add(choice4);
		
		next = new JButton(new ImageIcon(imgs.get("nextbutton")));
		next.setOpaque(true);
		next.setContentAreaFilled(false);
		next.setBounds(scaleW(1300), scaleH(700), scaleW(150),scaleH(70));
		next.setVisible(false);
		add(next);
		
		serialize = new JButton("Serialize");
		serialize.setOpaque(true);
		serialize.setVisible(true);
		serialize.setBounds(scaleW(1200), scaleH(5), scaleW(100), scaleH(30));
		serialize.setActionCommand("s");
		add(serialize);
		
		deserialize = new JButton("Deserialize");
		deserialize.setOpaque(true);
		deserialize.setVisible(true);
		deserialize.setBounds(scaleW(1300), scaleH(5), scaleW(100), scaleH(30));
		deserialize.setActionCommand("d");
		add(deserialize);
		
		frame.getContentPane().add(this);
		
		frame.setVisible(true);
    	
    	
	}
	
	
	// consume a Model and update the image according to the Model
	// and call the repaint method
	public void update(Model model) {
		//System.out.println("update model");
		if ((model.getQuizing() || model.getCurState().equals(Type.OPREVIEW) ||
			model.getCurState().equals(Type.NHREVIEW))) {//&& model.delayTimer == null
			//submitButton.setVisible(true);
			choice1.setVisible(true);
			choice2.setVisible(true);
			choice3.setVisible(true);
			choice4.setVisible(true);
			backButton.setVisible(false);
		}
		else {
			submitButton.setVisible(false);
			choice1.setVisible(false);
			choice2.setVisible(false);
			choice3.setVisible(false);
			choice4.setVisible(false);
			backButton.setVisible(true);
		if (model.getCurState() == Type.MAINMENU) {
			
		}
		else if (model.getCurState() == Type.OP) {
			if (((OPModel) model).getDrawNA() && model.pic < 4) {
				model.pic = model.pic+1;
				opmap = opmaps[model.pic];
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (curImg == imgs.get("osprey")) {
				curImg = imgs.get("osprey2");
			}
			else {
				curImg = imgs.get("osprey");
			}
			backButton.setVisible(true);
		}
		else if(model.getCurState() == Type.TUTORIALNH1) {
			drawDE = ((NHModel) model).drawDE();
			if (drawDE) {
				model.pic = (model.pic + 1)%fcNhmap;
				nhmap = nhmaps[model.pic];
			}else {
				next.setVisible(true);
			}
		}
		else if (model.getCurState() == Type.NH1) {
		}
		else if (model.getCurState() == Type.TUTORIALNH2) {
			next.setVisible(true);
		}
		else if (model.getCurState() == Type.NH2) {
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
			//g.drawImage(imgs.get("ospreyReal"), this.frameWidth/5, this.frameHeight/8, Color.gray, this);
			//g.drawImage(imgs.get("NorthernHarrierReal"), this.frameWidth/2, this.frameHeight/8, Color.gray, this);
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
						g.drawImage(opmap,0, 0, Color.gray, this);
						g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
						g.setColor(Color.red);
						//g.drawString("The Osprey is a migratory bird that migrates from Delaware to Mexico.", frameWidth/7, frameHeight/3);
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
//						if(model.getQuizing()) {
//							g.setColor(Color.red);
//						}
//						else {
//							g.setColor(Color.yellow);
//						}
						g.setColor(((OPModel) model).color);
						g.fillRect(10, 2, ((OPModel) model).getEnergy()*10, 20);
				
						if (model.getList().size() != 0) {
							for(Element each: model.getList()) {
//								if (each.getType().equals(ItemType.WINFLAG)) {
//									System.out.println("land");
//									g.drawImage(imgs.get("bgland"), each.getX(), 0, null,this);
//								}
								g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
							}
						}
					}
					break;
				case TUTORIALNH1:
					if(drawDE) {
						g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
						g.drawImage(nhmap,0, 0, Color.gray, this);
						g.drawString("Time Remaining: " + String.valueOf(model.getTimeCount()), 100, 20);
					} else {
						g.drawImage(imgs.get(model.tutorialBg[model.tutor]), 0, 0, null, this);
						g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
						g.setColor(Color.red);
						g.drawString("Tutorial", this.frameWidth/2, (this.frameHeight)/4);
						if (model.getList().size() != 0) {
							for(Element each: model.getList()) {
								g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
							}
						}
						if (model.nest != null) {
						g.drawImage(imgs.get(((NHModel) model).nestBuild), model.nest.getX(), model.nest.getY(), null,this);
						}
						g.drawImage(imgs.get(model.getBird().getBType().getName()), model.getBird().getX(), model.getBird().getY(), null, this);
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
						g.drawImage(imgs.get("nhbg"), 0, 0, null, this);
						if (model.getList().size() != 0) {
							for(Element each: model.getList()) {
								g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
							}
						}
						g.drawImage(imgs.get(((NHModel) model).nestBuild), model.nest.getX(), model.nest.getY(), null,this);
						//g.drawImage(imgs.get("osprey"), model.getBird().getX(), model.getBird().getY(), null, this);
						g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
						g.setColor(Color.red);
						g.drawString("Items Collected: " + String.valueOf(model.getBird().getItemsCollected())+ "/10", 2*this.frameWidth/3, this.frameWidth/25);
						g.drawString("Time Remaining: " + String.valueOf(model.getTimeCount()), this.frameWidth/10, this.frameWidth/25);
						
						g.drawImage(imgs.get(model.getBird().getBType().getName()), model.getBird().getX(), model.getBird().getY(), null, this);
					
						/*if (model.getList().size() != 0) {
							for(Element each: model.getList()) {
								g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
							}
						}*/
					//}
//					if (model.getList().size() != 0) {
//						//System.out.println("times drawn");
//						for(Element each: model.getList()) {
//							//System.out.println("drawing hit item"+ each.getX());
//							g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
//						}
//					}
					break;
				case TUTORIALNH2:
					g.drawImage(imgs.get(model.tutorialBg[model.tutor]), 0, 0, null, this);
					g.drawImage(imgs.get("nest10"), (this.frameWidth-this.imageW)/2, (this.frameHeight-this.imageH)/2, null,this);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
					g.setColor(Color.red);
					g.drawString("Tutorial", this.frameWidth/2, (this.frameHeight)/4);
					if (model.getList().size() != 0) {
						//System.out.println("times drawn");
						for(Element each: model.getList()) {
							//System.out.println("drawing hit item"+ each.getX());
							g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
						}
					}
					for(CollectedItem each: ((NH2Model) model).eggList) {
						g.drawImage(imgs.get(each.getType().getName()), each.getX(), each.getY(), null,this);
					}
					g.drawImage(imgs.get(model.getBird().getBType().getName()), model.getBird().getX(), model.getBird().getY(), null, this);
					break;
				case NH2:
					g.drawImage(imgs.get("nhbg"), 0, 0, null, this);		
					
					g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
					g.setColor(Color.red);
					g.drawString("Time Remaining: " + String.valueOf(model.getTimeCount()), this.frameWidth/10, this.frameWidth/25);
					g.drawString("Eggs: " + String.valueOf(((NH2Model) model).eggList.size()), 3*this.frameWidth/4, this.frameWidth/25);
					//g.drawString("You Win NH1", 1000, 20);
					g.drawImage(imgs.get("nest10"), (this.frameWidth-this.imageW)/2, (this.frameHeight-this.imageH)/2, null,this);
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
					g.drawString("You Win the osprey game, Review what you learned", frameWidth/3, scaleH(70));
					g.drawString("" + model.getQuiz().getChosenAnswer(), frameWidth/3, scaleH(100));
					g.drawString(model.getQuiz().getQuestion(), frameWidth/3, scaleH(260));
					g.drawString("A: " + model.getQuiz().getChoice()[0], frameWidth/3, scaleH(300));
					g.drawString("B: " + model.getQuiz().getChoice()[1], frameWidth/3, scaleH(340));
					g.drawString("C: " + model.getQuiz().getChoice()[2], frameWidth/3, scaleH(380));
					g.drawString("D: " + model.getQuiz().getChoice()[3], frameWidth/3, scaleH(420));
					g.setColor(Color.red);
					g.drawString(model.quizOutcomeInfo, frameWidth/3, scaleH(200));
					break;
				case NHREVIEW:
					g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
					g.drawString("You Win the Northern Harrier game, Review what you learned", frameWidth/3, scaleH(70));
					g.drawString("" + model.getQuiz().getChosenAnswer(), frameWidth/3, scaleH(100));
					g.drawString(model.getQuiz().getQuestion(), frameWidth/3, scaleH(260));
					g.drawString("A: " + model.getQuiz().getChoice()[0], frameWidth/3, scaleH(300));
					g.drawString("B: " + model.getQuiz().getChoice()[1], frameWidth/3, scaleH(340));
					g.drawString("C: " + model.getQuiz().getChoice()[2], frameWidth/3, scaleH(380));
					g.drawString("D: " + model.getQuiz().getChoice()[3], frameWidth/3, scaleH(420));
					g.setColor(Color.red);
					g.drawString(model.quizOutcomeInfo, frameWidth/3, scaleH(200));
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
					g.drawString("" + model.getQuiz().getChosenAnswer(), frameWidth/3, scaleH(220));
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
					g.drawString(model.getQuiz().getQuestion(), frameWidth/3, scaleH(360));
					g.drawString("A: " + model.getQuiz().getChoice()[0], frameWidth/3, scaleH(400));
					g.drawString("B: " + model.getQuiz().getChoice()[1], frameWidth/3, scaleH(440));
					g.drawString("C: " + model.getQuiz().getChoice()[2], frameWidth/3, scaleH(480));
					g.drawString("D: " + model.getQuiz().getChoice()[3], frameWidth/3, scaleH(520));
					if (model.getCurState() == Type.NH1) {
						//g.drawString(model.quizCount + "/" + 3 + " Quizs", frameWidth * 30 / 1550, frameHeight * 30/838);
						//g.drawString(model.quizCount + "/" + 3 + " Quizzes", 30, 30);
						g.drawString(model.quizCount + "/" + 3 + " Quizzes", scaleW(1000), scaleH(600));

					}
					g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
					g.setColor(Color.red);
					g.drawString(model.quizOutcomeInfo, frameWidth/3, scaleH(300));
					
					
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
			else if (x.equals("nest1")) {
				bi = ImageIO.read(new File("imgs/nest1.png"));
			}
			else if (x.equals("nest5")) {
				bi = ImageIO.read(new File("imgs/nest5.png"));
			}
			else if (x.equals("nest10")) {
				bi = ImageIO.read(new File("imgs/nest10.png"));
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
				bi = ImageIO.read(new File("imgs/opmap1.jpg"));
			}
			else if (x.equals("opmapbg2")) {
				bi = ImageIO.read(new File("imgs/opmap2.jpg"));
			}
			else if (x.equals("opmapbg3")) {
				bi = ImageIO.read(new File("imgs/opmap3.jpg"));
			}
			else if (x.equals("opmapbg4")) {
				bi = ImageIO.read(new File("imgs/opmap4.jpg"));
			}
			else if (x.equals("opmapbg5")) {
				bi = ImageIO.read(new File("imgs/opmap5.jpg"));
			}
			else if (x.equals("ospreyReal")) {
				bi = ImageIO.read(new File("imgs/ospreyReal.jpg"));
			}
			else if (x.equals("NorthernHarrierReal")) {
				bi = ImageIO.read(new File("imgs/NHReal.jpg"));
			}
			else if (x.equals("nhmapbg")) {
				bi = ImageIO.read(new File("imgs/demap1.png"));
			}
			else if (x.equals("nhmapbg2")) {
				bi = ImageIO.read(new File("imgs/demap2.png"));
			}
			else if (x.equals("nhmapbg3")) {
				bi = ImageIO.read(new File("imgs/demap3.png"));
			}
			else if (x.equals("nhmapbg4")) {
				bi = ImageIO.read(new File("imgs/demap4.png"));
			}
			else if (x.equals("NHtutorial1bg")) {
				bi = ImageIO.read(new File("imgs/NHtutorial1.png"));
			}
			else if (x.equals("NHtutorial2bg")) {
				bi = ImageIO.read(new File("imgs/NHtutorial2.png"));
			}
			else if (x.equals("NHtutorial3bg")) {
				bi = ImageIO.read(new File("imgs/NHtutorial3.png"));
			}
			else if (x.equals("nextbutton")) {
				bi = ImageIO.read(new File("imgs/nextbutton.png"));
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
	
	public int scaleW(int size) {
		return frameWidth * size / scaleWidth;
	}
	public int scaleH(int size) {
		return frameHeight * size / scaleHeight;
	}
}
