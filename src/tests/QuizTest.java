package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main_package.Quiz;

class QuizTest {
	String[] choice = {"answer", "a","b","c","d"};
	Quiz q = new Quiz("question", "answer", choice); 
	
	
	@Test
	void testCheckAnswer() {
		q.setChosenAnser("231234");
		assertEquals(false,q.checkAnswer());
	}
	
	@Test
	void testGetChosenAnser() {
		q.setChosenAnser("231234");
		assertEquals("231234", q.getChosenAnswer());
	}
	
	@Test
	void testGetQuestion() {
		assertEquals("question", q.getQuestion());
	}
	
	@Test
	void testGetAnswer() {
		assertEquals("answer", q.getAnswer());
	}
	
	@Test
	void testGetChoice() {
		assertEquals(choice, q.getChoice());
	}
	
	@Test
	void testToString() {
		assertEquals("question; answer, a, b, c, d, ; answer", q.toString());
	}
}
