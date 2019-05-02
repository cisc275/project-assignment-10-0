package main_package;
// author Yixiong Wu, Yufan Wang
public class Quiz {
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
	
	public String getChosenAnswer() {
		return chosenAnswer;
	}
	
	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

	public String[] getChoice() {
		return choice;
	}

	public String toString() {
		String x = "";
		for (int i = 0; i < choice.length;i++) {
			x += choice[i] + ", ";
		}
		return question + "; " + x + "; "+ answer;
	}
	
	
}
