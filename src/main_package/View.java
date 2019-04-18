package main_package;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main_package.Controller.CustomKeyListener;
// author Sicheng Tian
public class View extends JPanel{
	//
	HashMap<String, BufferedImage> imgs;
	final int frameHeight = 500;
	final int frameWidth = 600;
	final int imageH = 32;
	final int imageW = 32;
	JFrame frame;
	ArrayList<Button> list;
	
	JButton OPButton;
	JButton NHButton;
	JButton backButton;
	
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
		String[] imgName = {"bird", "hitItem", "collectedItem"};
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
		
		
		frame = new JFrame();
        frame.getContentPane().add(this);
		frame.setBackground(Color.gray);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameWidth,frameHeight);
    	frame.setVisible(true);
    	
    	
    	
    	
		
       
	}
	
	
	// consume a Model and update the image according to the Model
	// and call the repaint method
	public void update(Model model) {
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
		else if (model.getCurState() == Type.GAMEOVER) {
			System.out.println("game over image");
		}
		this.model = model;
		frame.repaint();
	}
	
	public void paintComponent(Graphics g) {
		try {
			if (model.getCurState() == Type.OP) {
				g.drawImage(curImg, x, y, Color.gray, this);
			
				if (model.getList().size() != 0) {
					for(Element each: model.getList()) {
						g.drawImage(imgs.get("hitItem"), each.getX(), each.getY(), Color.gray,this);
					}
				}
			}
			else if (model.getCurState() == Type.NH1) {
				g.drawImage(curImg, x, y, Color.gray, this);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
				g.drawString(String.valueOf(model.getBird().getLife()), 500, 20);
				
				if (model.getList().size() != 0) {
					for(Element each: model.getList()) {
						g.drawImage(imgs.get("collectedItem"), each.getX(), each.getY(), Color.gray,this);
					}
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
