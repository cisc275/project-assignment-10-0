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
import java.util.ArrayList;
import java.util.Random; 


import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

//author Sicheng Tian
public class Controller {
	static Model model;
	static View view;
	int drawDelay = 30;
	Action drawAction;
	Timer t;
	Type curState;
	static int count = 0;
	static final String backUpFile = "backup.ser";
	
	// initialize the model and view
	public Controller() {
		//System.out.println("controll");
		
		view = new View();
		curState = Type.MAINMENU;
		//model = new Model(view.frameWidth, view.frameHeight, view.imageW, view.imageH, view.imgsSize);
		//System.out.println("model constructed");
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
		view.next.addActionListener(new NextListener());
		view.serialize.addActionListener(new SerializeButtonListener());
		view.deserialize.addActionListener(new SerializeButtonListener());	
		
		view.addKeyListener(new CustomKeyListener());
		
		
		
	}
	
	// if the restart button is clicked, set the curState in model to be MainMenu
	class RestartButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			model.setCurState(Type.MAINMENU);
			curState = Type.MAINMENU;
			if(model.myTimer != null) {
				model.myTimer.cancel();
			}
			System.out.println("mainmenu");
			view.backButton.setVisible(false);
			view.next.setVisible(false);
			view.OPButton.setVisible(true);
			view.NHButton.setVisible(true);
		}
		
	}
	
	// if the NH button is clicked, set the curState in the model to be NH1
	class NHButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Create a model for the NHGame
			model = new NHModel(view.frameWidth, view.frameHeight, view.imageW, view.imageH, view.imgsSize);
			model.setCurState(Type.TUTORIALNH1);
			curState = model.getCurState();
			System.out.println(model.getCurState());
			((NHModel) model).drawDE = true;
			// Set the button views for the game
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
			model = new OPModel(view.frameWidth, view.frameHeight, view.imageW, view.imageH, view.imgsSize);
			curState = model.getCurState();
				System.out.println(model.getCurState());
				view.backButton.setVisible(true);
				view.OPButton.setVisible(false);
				view.NHButton.setVisible(false);
				view.requestFocusInWindow();
				
			
		}
		
	}
	
	class NextListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (!model.getQuizing()) {
			if (model.tutor < model.tutorialBg.length - 1) {
				if (model.tutor == 0 && model instanceof NHModel) {
					System.out.println("add element");
					((NHModel) model).setNest(new CollectedItem((model.getFrameW()-model.imgW)/2, (model.getFrameH()-model.imgH)/2, ItemType.NEST));
					model.getList().add(new CollectedItem(4*(model.getFrameW()-model.imgW)/5, 2*(model.getFrameH()-model.imgH)/3, ItemType.STICK));
					model.getList().add(new CollectedItem(4*(model.getFrameW()-model.imgW)/5, (model.getFrameH()-model.imgH)/2, ItemType.RAT));
				}
				model.tutor++;
				System.out.println(model.tutor);
			}else {
				switch(model.getCurState()) {
				case TUTORIALOP:
					curState = Type.OP;
					model.setCurState(Type.OP);
					model.setUpGame();
					break;
				case TUTORIALNH1:
					((NHModel) model).setUpGame();
					curState = Type.NH1;
					model.setCurState(Type.NH1);
					break;
				case TUTORIALNH2:
					((NH2Model) model).setUpGame();
					curState = Type.NH2;
					model.setCurState(Type.NH2);
					break;
				}
				view.next.setVisible(false);
			}
			view.requestFocusInWindow();
			
		}
		}
		
	}
	
	
	// if the submit quiz button is clicked, 
	// if the curState is OP, call the method chechQuiz() in the model
	// if the curState is NH1, call the method submitQuiz() in model
	class QuizButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
//			switch (model.getCurState()) {
//			case OP:
//				// call checkQuiz
//				model.checkQuiz();
//				//model.quizing = false;
//				view.submitButton.setVisible(false);
//				break;
//			case NH1:
//				// call submitQuiz
//				//model.quizing = false;
//				//model.curState = Type.NH2;
//				model.checkQuiz();
//				System.out.println(model.getCurState());
//				view.submitButton.setVisible(false);
//				break;
//			case OPREVIEW:
//				break;
//			case NHREVIEW:
//				break;
//				
//			}
			model.checkQuiz();
			System.out.println(model.getCurState());
//			view.submitButton.setVisible(false);
//			view.choice1.setVisible(false);
//			view.choice2.setVisible(false);
//			view.choice3.setVisible(false);
//			view.choice4.setVisible(false);
			view.requestFocusInWindow();
		}
		
	}
	
	class ChoiceButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (model.delayTimer == null && model.getQuiz() != null) {
				model.getQuiz().setChosenAnser(e.getActionCommand());
				model.checkQuiz();
			}else if (model.getCurState() == Type.TUTORIALOP) {
				model.checkQuiz();
			}
			System.out.println(model.getCurState());
			view.submitButton.setVisible(false);
			view.choice1.setVisible(false);
			view.choice2.setVisible(false);
			view.choice3.setVisible(false);
			view.choice4.setVisible(false);
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
			
//			switch (model.getCurState()) {
//			case OP:
//				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//				if (e.getKeyCode() == KeyEvent.VK_UP) {
//					//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//					model.getBird().setYVector(-10);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//					model.getBird().setYVector(10);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//					//model.updateBirdPosition(-10, 0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					//model.updateBirdPosition(10, 0);
//				}
//				break;
//			case TUTORIALNH1:
//				if (e.getKeyCode() == KeyEvent.VK_UP) {
//					model.getBird().setYVector(-10);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//					model.getBird().setYVector(10);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//					model.getBird().setXVector(-10);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					model.getBird().setXVector(10);
//				}
//				break;
//			case NH1:
//				if (e.getKeyCode() == KeyEvent.VK_UP) {
//					model.getBird().setYVector(-7);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//					model.getBird().setYVector(7);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//					model.getBird().setXVector(-7);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					model.getBird().setXVector(7);
//				}
//				break;
//			case TUTORIALNH2:
//				if (e.getKeyCode() == KeyEvent.VK_UP) {
//					model.getBird().setYVector(-10);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//					model.getBird().setYVector(10);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//					model.getBird().setXVector(-10);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					model.getBird().setXVector(10);
//				}
//				break;
//			case NH2:
//				if (e.getKeyCode() == KeyEvent.VK_UP) {
//					model.getBird().setYVector(-7);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//					model.getBird().setYVector(7);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//					model.getBird().setXVector(-7);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					model.getBird().setXVector(7);
//				}
//				break;
//			default:
//				break;
//			}
			if (model.getBird() != null) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					model.getBird().setYVector(0 - model.getBird().getyMove());
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					model.getBird().setYVector(model.getBird().getyMove());
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					model.getBird().setXVector(0 - model.getBird().getxMove());
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					model.getBird().setXVector(model.getBird().getxMove());
				}
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if (model.getBird() != null) {
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
			}
//			switch (model.getCurState()) {
//			case OP:
//				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//				if (e.getKeyCode() == KeyEvent.VK_UP) {
//					//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//					model.getBird().setYVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//					model.getBird().setYVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//					//model.updateBirdPosition(-10, 0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					//model.updateBirdPosition(10, 0);
//				}
//				break;
//			case TUTORIALNH1:
//				if (e.getKeyCode() == KeyEvent.VK_UP) {
//					model.getBird().setYVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//					model.getBird().setYVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//					model.getBird().setXVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					model.getBird().setXVector(0);
//				}
//				break;
//			case NH1:
//				if (e.getKeyCode() == KeyEvent.VK_UP) {
//					model.getBird().setYVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//					model.getBird().setYVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//					model.getBird().setXVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					model.getBird().setXVector(0);
//				}
//				break;
//			case TUTORIALNH2:
//				if (e.getKeyCode() == KeyEvent.VK_UP) {
//					model.getBird().setYVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//					model.getBird().setYVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//					model.getBird().setXVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					model.getBird().setXVector(0);
//				}
//				break;
//			case NH2:
//				if (e.getKeyCode() == KeyEvent.VK_UP) {
//					model.getBird().setYVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//					model.getBird().setYVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//					model.getBird().setXVector(0);
//				}
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					model.getBird().setXVector(0);
//				}
//				break;
//			default:
//				break;
//			}
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
    			System.out.println(curState);
    			switch(curState) {
    			case MAINMENU:
    				//view.update(model);
    				System.out.println("here");
    				view.repaint();
    				break;
    			case TUTORIALOP:
    				if (((OPModel) model).getDrawNA()) {
    					t.setDelay(500);
    				}else {
    					t.setDelay(30);
    				}
    				model.tutorial();
    				view.update(model);
    				break;
    			case OP:
    				//System.out.println("OP Controller");
    				view.update(model);
    				if (!model.getQuizing()) {
    				model.updatePosition();
    				//view.animation();
    				}
    				
    				curState = model.curState;
    				break;
    			case TUTORIALNH1:
    				if (((NHModel) model).drawDE()) {
    					t.setDelay(500);
    				}else {
    					t.setDelay(30);
    				}
    				curState = model.curState;
    				view.update(model);
    				model.tutorial();
    				//curState = model.getCurState();
    				break;
    			case NH1:
    				//System.out.println("NH1 controlller");
    				view.update(model);
    				if (!model.getQuizing()) {
    					model.updatePosition();
    					if (model.quizCount > 2 && model.eggs > 0) {
        					model = new NH2Model(view.frameWidth, view.frameHeight, view.imageW, view.imageH, view.imgsSize);
        					curState = model.getCurState();
        					System.out.println(curState + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        				}
    				}
    				break;
    			case TUTORIALNH2:
    				view.update(model);
    				model.tutorial();
    				curState = model.getCurState();
    				break;
    			case NH2:
    				//System.out.println("NH2 controller");
    				if (!model.getQuizing()) {
    					model.updatePosition();
    				}
    				//System.out.println(model instanceof NH2Model );
    				curState = model.getCurState();
    				view.update(model);
    				model.updatePosition();
    				curState = model.getCurState();
    				break;
    			case GAMEOVER:
    				view.update(model);
    				break;
    			case OPREVIEW:
    				curState = model.getCurState();
    				view.update(model);
    				break;
    			case NHREVIEW:
    				curState = model.getCurState();
    				view.update(model);
    				break;
    			case WIN:
    				curState = model.getCurState();
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
			out.writeObject(Controller.model);
			out.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deserialize() {
		Model tmp = null;
		try {
			FileInputStream file = new FileInputStream(backUpFile);
			ObjectInputStream in = new ObjectInputStream(file);
			tmp = (Model) in.readObject();
			in.close();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(tmp == null);
		model = tmp;
	//	view.update(model);
	}
	
	public static void main(String[] args) {
		Controller c = new Controller();
		
		//System.out.println("call start");
		c.start();
		//System.out.println("hello");
	}
}
