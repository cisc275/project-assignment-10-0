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
	static Type curState;
	static int count = 0;
	static final String backUpFile = "backup.ser";
	
	// initialize the model and view
	public Controller() {
		
		view = new View();
		curState = Type.MAINMENU;
		view.OPButton.addActionListener(new OPButtonListener());
		view.NHButton.addActionListener(new NHButtonListener());
		view.backButton.addActionListener(new RestartButtonListener());
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
					((NHModel) model).setNest(new CollectedItem((model.getFrameW()-model.imgW)/2, (model.getFrameH()-model.imgH)/2, ItemType.NEST));
					model.getList().add(new CollectedItem(4*(model.getFrameW()-model.imgW)/5, 2*(model.getFrameH()-model.imgH)/3, ItemType.STICK));
					model.getList().add(new CollectedItem(4*(model.getFrameW()-model.imgW)/5, (model.getFrameH()-model.imgH)/2, ItemType.RAT));
				}
				model.tutor++;
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
			model.checkQuiz();
			view.requestFocusInWindow();
		}
		
	}
	
	class ChoiceButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.delayTimer == null && model.getQuiz() != null) {
				model.getQuiz().setChosenAnser(e.getActionCommand());
				model.checkQuiz();
			}else if (model.getCurState() == Type.TUTORIALOP) {
				model.checkQuiz();
			}
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

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			
		}
		
	 }
	 
	class SerializeButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
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
		drawAction = new AbstractAction(){
    		public void actionPerformed(ActionEvent e){
    			switch(curState) {
    			case MAINMENU:
    				//view.update(model);
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
    				
    				if (!model.getQuizing()) {
    					model.updatePosition();
    					if (model.quizCount > 2 && model.eggs > 0) {
        					model = new NH2Model(view.frameWidth, view.frameHeight, view.imageW, view.imageH, view.imgsSize);
        				}
    				}
    				curState = model.getCurState();
    				view.update(model);
    				break;
    			case TUTORIALNH2:
    				view.update(model);
    				model.tutorial();
    				curState = model.getCurState();
    				break;
    			case NH2:
    				if (!model.getQuizing()) {
    					model.updatePosition();
    				}
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
		view.requestFocusInWindow();
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
		model = tmp;
		view.requestFocusInWindow();
		curState = tmp.getCurState();
		switch (curState) {
		case OP:
			view.OPButton.setVisible(false);
			view.NHButton.setVisible(false);
			view.backButton.setVisible(true);
			view.next.setVisible(false);
			hideChoiceButtons();
			break;
		case GAMEOVER:
			view.OPButton.setVisible(false);
			view.NHButton.setVisible(false);
			view.backButton.setVisible(true);
			view.next.setVisible(false);
			hideChoiceButtons();
			break;
		case MAINMENU:
			view.OPButton.setVisible(true);
			view.NHButton.setVisible(true);
			view.backButton.setVisible(false);
			view.next.setVisible(false);
			hideChoiceButtons();
			break;
		case NH1:
			view.OPButton.setVisible(false);
			view.NHButton.setVisible(false);
			view.backButton.setVisible(true);
			view.next.setVisible(false);
			hideChoiceButtons();
			break;
		case NH2:
			view.OPButton.setVisible(false);
			view.NHButton.setVisible(false);
			view.backButton.setVisible(true);
			view.next.setVisible(false);
			hideChoiceButtons();
			break;
		case NHREVIEW:
			view.OPButton.setVisible(false);
			view.NHButton.setVisible(false);
			view.backButton.setVisible(false);
			view.next.setVisible(false);
			showChoiceButtons();
			break;
		case OPREVIEW:
			view.OPButton.setVisible(false);
			view.NHButton.setVisible(false);
			view.backButton.setVisible(false);
			view.next.setVisible(false);
			showChoiceButtons();
			break;
		case TUTORIALNH1:
			view.OPButton.setVisible(false);
			view.NHButton.setVisible(false);
			view.backButton.setVisible(true);
			view.next.setVisible(true);
			hideChoiceButtons();
			break;
		case TUTORIALNH2:
			view.OPButton.setVisible(false);
			view.NHButton.setVisible(false);
			view.backButton.setVisible(true);
			view.next.setVisible(true);
			hideChoiceButtons();
			break;
		case TUTORIALOP:
			view.OPButton.setVisible(false);
			view.NHButton.setVisible(false);
			view.backButton.setVisible(true);
			view.next.setVisible(true);
			hideChoiceButtons();
			break;
		case WIN:
			view.OPButton.setVisible(false);
			view.NHButton.setVisible(false);
			view.backButton.setVisible(true);
			view.next.setVisible(false);
			hideChoiceButtons();
			break;
		default:
			break;
		}
	}
	
	public static void hideChoiceButtons() {
		view.choice1.setVisible(false);
		view.choice2.setVisible(false);
		view.choice3.setVisible(false);
		view.choice4.setVisible(false);
	}
	
	public static void showChoiceButtons() {
		view.choice1.setVisible(true);
		view.choice2.setVisible(true);
		view.choice3.setVisible(true);
		view.choice4.setVisible(true);
	}
	
	public static void main(String[] args) {
		Controller c = new Controller();
		c.start();
	}
}
