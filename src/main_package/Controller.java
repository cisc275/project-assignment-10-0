package main_package;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random; 


import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

//author Sicheng Tian
public class Controller implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Model model;
	View view;
	int drawDelay = 30;
	Action drawAction;
	transient Timer t;
	static int count = 0;
	static String backUpFile = "backup.ser";
	
	// initialize the model and view
	public Controller() {
		//System.out.println("controll");
		
		view = new View();
		model = new Model(view.frameWidth, view.frameHeight, view.imageW, view.imageH, view.imgsSize);
		System.out.println("model constructed");
		//view.setModel(model);
		//view.frame.setVisible(true);
		//
		view.OPButton.addActionListener(new OPButtonListener());
		view.NHButton.addActionListener(new NHButtonListener());
		view.backButton.addActionListener(new RestartButtonListener());
		view.submitButton.addActionListener(new QuizButtonListener());
		view.choice1.addActionListener(new ChoiceButtonListener());
		view.choice2.addActionListener(new ChoiceButtonListener());
		view.choice3.addActionListener(new ChoiceButtonListener());
		view.choice4.addActionListener(new ChoiceButtonListener());
		view.serialize.addActionListener(new SerializeButtonListener());
	//	view.deserialize.addActionListener(new SerializeButtonListener());		
		view.addKeyListener(new CustomKeyListener());
		
		
		
	}
	
	// if the restart button is clicked, set the curState in model to be MainMenu
	class RestartButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			model.setCurState(Type.MAINMENU);
			model.myTimer.cancel();
			System.out.println("mainmenu");
			view.backButton.setVisible(false);
			view.OPButton.setVisible(true);
			view.NHButton.setVisible(true);
		}
		
	}
	
	// if the NH button is clicked, set the curState in the model to be NH1
	class NHButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				Random rand = new Random(); 
				model.setCurState(Type.NH1);
				model.setList(new ArrayList<>());
				//model.getList().add(new CollectedItem(250, 100, ItemType.STICK));
				//model.getList().add(new CollectedItem(400, 300, ItemType.STICK));
				model.nest = new CollectedItem((model.getFrameW()-model.imgW)/2, (model.getFrameH()-model.imgH)/2, ItemType.NEST);
				for(int i = 0; i<5; i++) {
					model.getList().add(new CollectedItem(rand.nextInt(model.getFrameW()-model.imgW), rand.nextInt(model.getFrameH()-model.imgH), ItemType.STICK));
				}
				// Created rats for NH Game but don't know how to show them in the view
				for(int i = 0; i<5; i++) {
					model.getList().add(new CollectedItem(rand.nextInt(model.getFrameW()-model.imgW), rand.nextInt(model.getFrameH()-model.imgH), ItemType.RAT));
				}
				model.setUpdateL();
				model.setBird(new Bird((model.getFrameW()-model.imgW)/2, (model.getFrameH()-model.imgH)/2,3,BirdType.NH));
				try {
					model.createQuizs();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				model.createTimer();
				
				System.out.println(model.getCurState());
				view.backButton.setVisible(true);
				view.OPButton.setVisible(false);
				view.NHButton.setVisible(false);
				view.requestFocusInWindow();
				
			
		}
		
	}
	
	// if the OP button is clicked, set the curState in the model to be OP
	class OPButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
				model.groundX = 0;
				model.groundY = 0;
				model.setCurState(Type.OP);
				model.setBird(new Bird(0,250,3,BirdType.OSPREY));
				model.setList(new ArrayList<>());
				model.getList().add(new HitItem(model.getFrameW(), 100, ItemType.AIRPLANE, -10, 0));
				//model.getList().add(new HitItem(model.getFrameW(), 300, ItemType.AIRPLANE, -10, 0));
				model.setUpdateL();
				try {
					model.createQuizs();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				model.createTimer();
			
				System.out.println(model.getCurState());
				view.backButton.setVisible(true);
				view.OPButton.setVisible(false);
				view.NHButton.setVisible(false);
				view.requestFocusInWindow();
				
			
		}
		
	}
	
	
	// if the submit quiz button is clicked, 
	// if the curState is OP, call the method chechQuiz() in the model
	// if the curState is NH1, call the method submitQuiz() in model
	class QuizButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch (model.getCurState()) {
			case OP:
				// call checkQuiz
				model.checkQuiz();
				//model.quizing = false;
				view.submitButton.setVisible(false);
				break;
			case NH1:
				// call submitQuiz
				//model.quizing = false;
				//model.curState = Type.NH2;
				model.checkQuiz();
				System.out.println(model.getCurState());
				view.submitButton.setVisible(false);
				
				break;
				
			}
			view.choice1.setVisible(false);
			view.choice2.setVisible(false);
			view.choice3.setVisible(false);
			view.choice4.setVisible(false);
			view.requestFocusInWindow();
		}
		
	}
	
	class ChoiceButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			model.getQuiz().setChosenAnser(e.getActionCommand());
		}
		
	}
	
	// call the updateBirdPosition method according to the input key
	// if it is "up" updateBirdPosition(0,-1)
	// "down" updateBirdPosition(0,1)
	// "left" updateBirdPosition(-1.0)
	// "right" updateBirdPosition(1,0)
	 class CustomKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
			switch (model.getCurState()) {
			case OP:
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					model.getBird().setYVector(-10);
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					model.getBird().setYVector(10);
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					//model.updateBirdPosition(-10, 0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					//model.updateBirdPosition(10, 0);
				}
				break;
			case NH1:
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					model.getBird().setYVector(-10);
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					model.getBird().setYVector(10);
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					model.getBird().setXVector(-10);
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					model.getBird().setXVector(10);
				}
				break;
			case NH2:
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					model.getBird().setYVector(-10);
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					model.getBird().setYVector(10);
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					model.getBird().setXVector(-10);
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					model.getBird().setXVector(10);
				}
				break;
			default:
				break;
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			switch (model.getCurState()) {
			case OP:
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					model.getBird().setYVector(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					model.getBird().setYVector(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					//model.updateBirdPosition(-10, 0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					//model.updateBirdPosition(10, 0);
				}
				break;
			case NH1:
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					model.getBird().setYVector(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					model.getBird().setYVector(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					model.getBird().setXVector(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					model.getBird().setXVector(0);
				}
				break;
			case NH2:
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					model.getBird().setYVector(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					model.getBird().setYVector(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					model.getBird().setXVector(0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					model.getBird().setXVector(0);
				}
				break;
			default:
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class SerializeButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch(e.getActionCommand()) {
			case "s":
				serialize();
				break;
			case "d":
				deserialize();
				break;
			}
		}
		
	}
	
	// use EventQueue to create a timer and call start() by the timer
	// initialize the drawAction to be a AbstractAction and implements the actionPerforemed method
	// in the implemented method, check
	// if the curState in model is MainMenu, call update(model) by view
	// if the quizing boolean in model is true, call update(model) by view
	// if the curState in model is NH, call update by view
	// if the curState in model is NH2, call updateTimer(), checkTimer() and updatePosition by model, update by view
	// if the curState in model is OP, call updatePosition() by model and update(model) by view
	// if the CurState in model is End, call update(model) by view
	// if the curState in model is Win, call update(model) by view
	public void start() {
		EventQueue.invokeLater(new Runnable(){
			public void run() {
				t = new Timer(drawDelay, drawAction);
				t.start();
				
			}
		});
		//System.out.println("drawAction");
		drawAction = new AbstractAction(){
    		public void actionPerformed(ActionEvent e){
    			//System.out.println("draw");
    			//System.out.println(++count);
    			switch(model.getCurState()) {
    			case MAINMENU:
    				view.update(model);
    				break;
    			case OP:
    				//System.out.println("OP Controller");
    				if (!model.getQuizing()) {
    				model.updatePosition();
    				model.updateBirdPosition();
    				}
    				view.update(model);
    				break;
    			case NH1:
    				//System.out.println("NH1 controlller");
    				view.update(model);
    				if (!model.getQuizing()) {
    					model.updateBirdPosition();
    				}
    				break;
    			case NH2:
    				//System.out.println("NH2 controller");
    				if (!model.getQuizing()) {
    					model.updateBirdPosition();
    				}
    				view.update(model);
    				model.updatePositionNH2();
    				break;
    			case GAMEOVER:
    				view.update(model);
    				break;
    			case WIN:
    				view.update(model);
    				break;
				default:
					break;
    				
    			}
    			
    			
    				
    		}
    	};
	}
	
	public void serialize() {
		try {
			FileOutputStream file = new FileOutputStream(backUpFile);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(this);
			out.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Controller deserialize() {
		Controller tmp = null;
		try {
			FileInputStream file = new FileInputStream(backUpFile);
			ObjectInputStream in = new ObjectInputStream(file);
			tmp = (Controller) in.readObject();
			in.close();
			file.close();
		//	changeToController(tmp);

	//		EventQueue. TODO:: fix exception
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return tmp;
	}
	
	public void changeToController(Controller c) {
		this.model = c.model;
		this.view = c.view;
		this.drawAction = c.drawAction;
		this.drawDelay = c.drawDelay;
		this.t = c.t;
	//	this.count = c.count;  TODO::Needed to be fixed
	}
	
	public static void main(String[] args) {
		Controller c = new Controller();

	//	Controller d = deserialize();  // uncomment these two lines for deserialization.
	//	c.model = d.model;
		
		//System.out.println("call start");
		c.start();
		//System.out.println("hello");
	}
}
