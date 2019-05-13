package main_package;

import java.io.Serializable;

// author Yixiong Wu, Yufan Wang
public class Quiz implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// The question for one quiz
	String question;
	// matched answer to the question
	String answer;
	// the answer which the player has chosen
	String chosenAnswer = "";
	String[] choice;
	
	public Quiz(String question, String answer, String[] choice) {
		this.question = question;
		this.answer = answer;
		this.choice = choice;
	}
	
	// record player's choice for one quiz question
	public void setChosenAnser(String chosenAnswer) {
		this.chosenAnswer = chosenAnswer;
	}
	//check if the chosenAnswer matches the correct answer
	public boolean checkAnswer() {
		return answer.equals(chosenAnswer);
	}
	// chosenAnswer getter
	public String getChosenAnswer() {
		return chosenAnswer;
	}
	// question getter
	public String getQuestion() {
		return question;
	}
	// answer getter
	public String getAnswer() {
		return answer;
	}
	// choice getter
	public String[] getChoice() {
		return choice;
	}
	// override toString 
	public String toString() {
		String x = "";
		for (int i = 0; i < choice.length;i++) {
			x += choice[i] + ", ";
		}
		return question + "; " + x + "; "+ answer;
	}
	
	
}
