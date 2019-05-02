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
	HashMap<String, BufferedImage> imgs;
	//final int frameHeight = 643;    //500
	//final int frameWidth = 1024;     //600
	int frameHeight = 643;    //500
	int frameWidth = 1024;     //600
	final int imageH = 32;
	final int imageW = 32;
	JFrame frame;
	ArrayList<Button> list;
	// for operation
	JButton OPButton;
	JButton NHButton;
	JButton backButton;
	JButton submitButton;
	// for quiz
	JButton choice1;
	JButton choice2;
	JButton choice3;
	JButton choice4;
	
	BufferedImage curImg;
	Model model;
	int x;
	int y;
	
	
	
	
	// initialize the frameHeight, frameWidth
	// initialize the images by calling createImage method
	// initialize the frame and button
	// add button to the JPanel
	public View() {
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		//super(new GridBagLayout());
		String[] imgName = {"bird", "hitItem", "collectedItem", "nest", "background", "background2"};
		imgs = new HashMap<>();
		for(int i = 0; i < imgName.length; i++) {
			BufferedImage img = createImage(imgName[i]);
			imgs.put(imgName[i], img);
		}
		
		//setLayout(null);
		
		OPButton = new JButton("start Osprey Game");
		OPButton.setOpaque(true);
		//OPButton.setBounds(100, 100, 400, 50);
		//OPButton.setVisible(false);
		add(OPButton);
		
		NHButton = new JButton("start Northen Harrier Game");
		NHButton.setOpaque(true);
		//NHButton.setBounds(600,100,400,50);
		//NHButton.setVisible(false);
		add(NHButton);
		
		backButton = new JButton("back");
		backButton.setOpaque(true);
		backButton.setVisible(false);
		add(backButton);
		
		submitButton = new JButton("submit");
		submitButton.setOpaque(true);
		submitButton.setVisible(false);
		add(submitButton);
		
		//GridBagConstraints gbc = new GridBagConstraints();
		choice1 = new JButton("A");
		choice1.setOpaque(true);
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
		
		
		frame = new JFrame();
        frame.getContentPane().add(this);
		frame.setBackground(Color.gray);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	frame.setVisible(true);
    	frameWidth = frame.getWidth();
    	frameHeight = frame.getHeight()-40;
    	System.out.printf("%d, %d\n", frame.getWidth(), frame.getHeight());
    	frame.setVisible(true);
    	
	}
	
	
	// consume a Model and update the image according to the Model
	// and call the repaint method
	public void update(Model model) {
		if (model.getQuizing() && model.delayTimer == null) {
			submitButton.setVisible(true);
			choice1.setVisible(true);
			choice2.setVisible(true);
			choice3.setVisible(true);
			choice4.setVisible(true);
		}
		
		if (model.getCurState() == Type.MAINMENU) {
			
		}
		else if (model.getCurState() == Type.OP) {
			//remove(OPButton);
			x = model.getBird().getX();
			y = model.getBird().getY();
			curImg = imgs.get("bird");
		}
		else if (model.getCurState() == Type.NH1) {
			x = model.getBird().getX();
			y = model.getBird().getY();
			curImg = imgs.get("bird");
		}
		else if (model.getCurState() == Type.NH2) {
			x = model.getBird().getX();
			y = model.getBird().getY();
			curImg = imgs.get("bird");
		}
		else if (model.getCurState() == Type.GAMEOVER) {
			
		}
		this.model = model;
		frame.repaint();
	}
	
	public void paintComponent(Graphics g) {
		try {
			if (model.getQuizing()) {
				g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
				g.drawString("Your chose: " + model.getQuiz().getChosenAnswer(), frameWidth/2, 100);
				g.drawString(model.getQuiz().getQuestion(), frameWidth/2, 260);
				g.drawString("A: " + model.getQuiz().getChoice()[0], frameWidth/2, 300);
				g.drawString("B: " + model.getQuiz().getChoice()[1], frameWidth/2, 340);
				g.drawString("C: " + model.getQuiz().getChoice()[2], frameWidth/2, 380);
				g.drawString("D: " + model.getQuiz().getChoice()[3], frameWidth/2, 420);
				if (model.getCurState() == Type.NH1) {
					g.drawString(model.quizCount + "/" + 3 + " Quizs", 30, 30);
				}
				g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
				g.setColor(Color.red);
				g.drawString(model.quizOutcomeInfo, frameWidth/2, 200);
				
				
			}
			else {
				switch (model.getCurState()) {
				case OP:
					//g.drawImage(imgs.get("background").getScaledInstance(frameWidth, frameHeight, Image.SCALE_FAST), model.groundX % frameWidth, model.groundY, Color.gray, this);
					//System.out.println("first: " + model.groundX % frameWidth);
					//g.drawImage(imgs.get("background").getScaledInstance(frameWidth, frameHeight, Image.SCALE_FAST), (model.groundX % frameWidth) + frameWidth, model.groundY, Color.gray, this);
					//System.out.println("second: " + (model.groundX + frameWidth)% frameWidth);
					
					//g.drawImage(imgs.get("background2"), (model.groundX % frameWidth) + 3 * frameWidth, model.groundY, Color.gray, this);
					//System.out.println("first: " + model.groundX % frameWidth);
					//g.drawImage(imgs.get("background2"), (model.groundX % (2* frameWidth)) + 3 * frameWidth, model.groundY, Color.gray, this);
					g.drawImage(curImg, x, y, Color.gray, this);
					//life bar
					g.setColor(Color.black);
					g.drawRect(10, 2, model.defaultTime*10, 20);
					g.setColor(Color.yellow);
					g.fillRect(10, 2, model.getTimeCount()*10, 20);
			
					if (model.getList().size() != 0) {
						for(Element each: model.getList()) {
							g.drawImage(imgs.get("hitItem"), each.getX(), each.getY(), Color.gray,this);
						}
					}
					break;
				case NH1:
					g.drawImage(curImg, x, y, Color.gray, this);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.drawString("Items Collected: " + String.valueOf(model.getBird().getItemsCollected()), 1000, 20);
					g.drawString("Time Remaining: " + String.valueOf(model.getTimeCount()), 100, 20);
					g.drawImage(imgs.get("nest"), this.frameWidth/2, this.frameHeight/2, Color.gray,this);
				
					if (model.getList().size() != 0) {
						for(Element each: model.getList()) {
							g.drawImage(imgs.get("collectedItem"), each.getX(), each.getY(), Color.gray,this);
						}
					}
					break;
				case NH2:
					g.drawImage(curImg, x, y, Color.gray, this);
					g.drawString("Time Remaining: " + String.valueOf(model.getTimeCount()), 100, 20);
					//g.drawString("You Win NH1", 1000, 20);
					g.drawImage(imgs.get("nest"), this.frameWidth/2, this.frameHeight/2, Color.gray,this);
					//System.out.println(model.getList().size());
					if (model.getList().size() != 0) {
						//System.out.println("times drawn");
						for(Element each: model.getList()) {
							//System.out.println("drawing hit item"+ each.getX());
							g.drawImage(imgs.get("hitItem"), each.getX(), each.getY(), Color.gray,this);
						}
					}
					break;
				case GAMEOVER:
					break;
				}
			}
		}catch(NullPointerException n) {
			
		}
		
		
	}
	
	// read the image and return it
	public BufferedImage createImage(String x) {
		BufferedImage bi;
		try {
			if (x.equals("bird")) {
				bi = ImageIO.read(new File("imgs/IMG_0690.png"));
			}
			else if (x.equals("hitItem")) {
				bi = ImageIO.read(new File("imgs/IMG_0692.png"));
			}
			else if (x.equals("collectedItem")) {
				bi = ImageIO.read(new File("imgs/IMG_0691.png"));
			}
			else if (x.equals("nest")) {
				bi = ImageIO.read(new File("imgs/IMG_0692.png"));
			}
			else if (x.equals("background")) {
				bi = ImageIO.read(new File("imgs/background.jpg"));
			}
			else if (x.equals("background2")) {
				bi = ImageIO.read(new File("imgs/background2.jpg"));
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
}
