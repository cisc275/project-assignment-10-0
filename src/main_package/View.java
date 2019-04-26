package main_package;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
	
	JButton OPButton;
	JButton NHButton;
	JButton backButton;
	JButton submitButton;
	
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
		String[] imgName = {"bird", "hitItem", "collectedItem", "nest", "background", "background2"};
		imgs = new HashMap<>();
		for(int i = 0; i < imgName.length; i++) {
			BufferedImage img = createImage(imgName[i]);
			imgs.put(imgName[i], img);
		}
		
		
		OPButton = new JButton("start Osprey Game");
		OPButton.setOpaque(true);
		//OPButton.setVisible(false);
		add(OPButton);
		
		NHButton = new JButton("start Northen Harrier Game");
		NHButton.setOpaque(true);
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
		if (model.getQuizing()) {
			submitButton.setVisible(true);
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
			
		}
		else if (model.getCurState() == Type.GAMEOVER) {
			
		}
		this.model = model;
		frame.repaint();
	}
	
	public void paintComponent(Graphics g) {
		try {
			if (model.getQuizing()) {
				
			}
			else {
				switch (model.getCurState()) {
				case OP:
					//g.drawImage(imgs.get("background"), model.groundX % frameWidth, model.groundY, Color.gray, this);
					//System.out.println("first: " + model.groundX % frameWidth);
					//g.drawImage(imgs.get("background"), (model.groundX % frameWidth) + frameWidth, model.groundY, Color.gray, this);
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
					g.drawString("You Win NH1", 1000, 20);
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
