package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main_package.Quiz;

class QuizTest {

	
	@Test
	void testCheckAnswer() {
		String[] choice = {"answer", "a", "b","c"};
		Quiz q = new Quiz("question", "answer", choice);
		q.setChosenAnser("231234");
		assertEquals(false, q.checkAnswer());
		q.setChosenAnser("answer");
		assertTrue(q.checkAnswer());
	}
}
