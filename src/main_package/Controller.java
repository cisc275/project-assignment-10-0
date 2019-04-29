package main_package;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

//author Sicheng Tian
public class Controller {
	Model model;
	View view;
	final int drawDelay = 30;
	Action drawAction;
	
	// initialize the model and view
	public Controller() {
		//System.out.println("controll");
		view = new View();
		model = new Model(view.frameWidth, view.frameHeight, view.imageW, view.imageH);
		//
		view.OPButton.addActionListener(new OPButtonListener());
		view.NHButton.addActionListener(new NHButtonListener());
		view.backButton.addActionListener(new RestartButtonListener());
		view.submitButton.addActionListener(new QuizButtonListener());
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
				model.setCurState(Type.NH1);
				model.setList(new ArrayList<>());
				model.getList().add(new CollectedItem(250, 100, ItemType.STICK));
				model.getList().add(new CollectedItem(400, 300, ItemType.STICK));
				model.setUpdateL();
				model.setBird(new Bird(300, 400,0,BirdType.NH));
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
				model.getList().add(new HitItem(model.getFrameW(), 100, ItemType.AIRPLANE));
				model.getList().add(new HitItem(model.getFrameW(), 300, ItemType.AIRPLANE));
				model.setUpdateL();
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
				model.quizing = false;
				view.submitButton.setVisible(false);
				break;
			case NH1:
				// call submitQuiz
				model.quizing = false;
				System.out.println("check answer and go to NH2");
				model.curState = Type.NH2;
				System.out.println(model.getCurState());
				view.submitButton.setVisible(false);
				break;
				
			}
			view.requestFocusInWindow();
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
					model.updateBirdPosition(0, -10);
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					model.updateBirdPosition(0, 10);
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
					model.updateBirdPosition(0, -10);
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					model.updateBirdPosition(0, 10);
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					model.updateBirdPosition(-10, 0);
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					model.updateBirdPosition(10, 0);
				}
			default:
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
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
				Timer t = new Timer(drawDelay, drawAction);
				t.start();
			}
		});
		
		drawAction = new AbstractAction(){
    		public void actionPerformed(ActionEvent e){
    			//System.out.println("draw");
    			
    			switch(model.getCurState()) {
    			case MAINMENU:
    				view.update(model);
    				break;
    			case OP:
    				if (!model.getQuizing()) {
    				model.updatePosition();
    				}
    				view.update(model);
    				break;
    			case NH1:
    				view.update(model);
    			case GAMEOVER:
    				break;
				default:
					break;
    				
    			}
    			
    			
    				
    		}
    	};
	}
	
	public static void main(String[] args) {
		Controller c = new Controller();
		c.start();
		//System.out.println("hello");
	}
}
