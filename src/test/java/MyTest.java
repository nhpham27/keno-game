import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	KenoGame game;
	
	@BeforeEach
	void setup() {
		game = new KenoGame();
	}
	
	@Test
	void constructorTest() {
		assertEquals(0, game.getNumDraws(), "constructor test failed");
		assertEquals(0, game.getNumSpots(), "constructor test failed");
		assertEquals(0, game.getMatchedNumbers().size(), "constructor test failed");
		assertEquals(0, game.getDrawNumbers().size(), "constructor test failed");
		assertEquals(0, game.getPrize(), "constructor test failed");
		assertEquals(0, game.getTotalPrize(), "constructor test failed");
		assertEquals(0, game.getUserNumbers().size(), "constructor test failed");
	}
	
	@Test
	void setNumDrawTest() {
		assertEquals(0, game.getNumDraws(), "setNumDraw() test failed");
		game.setNumDraws(1);
		assertEquals(1, game.getNumDraws(), "setNumDraw() test failed");
	}
	
	@Test
	void getNumDrawTest() {
		game.setNumDraws(1);
		assertEquals(1, game.getNumDraws(), "setNumDraw() test failed");
	}
	
	@Test
	void setNumSpotTest() {
		assertEquals(0, game.getNumSpots(), "setNumSpot() test failed");
		game.setNumSpots(1);
		assertEquals(1, game.getNumSpots(), "setNumSpot() test failed");
	}
	
	@Test
	void getNumSpotTest() {
		game.setNumSpots(1);
		assertEquals(1, game.getNumSpots(), "getNumSpot() test failed");
	}
	
	@Test
	void getPrizeTest() {
		game.setNumDraws(1);
		game.setNumSpots(10);
		game.quickPick();
		game.draw();
		while(game.getNumMatched() != 0) {
			game.draw();
		}
		assertEquals(5, game.getPrize(), "getPrize() test failed");
	}
	
	@Test
	void getTotalPrizeTest1() {
		assertEquals(0, game.getTotalPrize(), "getTotalPrize() test 1 failed");
	}
	
	@Test
	void getTotalPrizeTest2() {
		game.setNumDraws(1);
		game.setNumSpots(1);
		game.quickPick();
		game.draw();
		while(game.getNumMatched() != 1) {
			game.draw();
		}
		assertEquals(2, game.getTotalPrize(), "getTotalPrize() test 2 failed");
		game.draw();
		while(game.getNumMatched() != 1) {
			game.draw();
		}
		assertEquals(4, game.getTotalPrize(), "getTotalPrize() test 2 failed");
	}
	
	@Test
	void getDrawnNumbersTest() {
		game.setNumDraws(1);
		game.setNumSpots(4);
		game.draw();
		ArrayList<Integer> list = game.getDrawNumbers();
		assertEquals(20, list.size(), "getDrawnNumbers() test failed");
	}
	
	@Test
	void getMatchedNumbersTest() {
		game.setNumDraws(1);
		game.setNumSpots(4);
		game.quickPick();
		game.draw();
		ArrayList<Integer> list = game.getMatchedNumbers();
		assertEquals(game.getNumMatched(), list.size(), "getMatchedNumbers() test failed");
	}
	
	@Test
	void addUserNumberTest1() { 
		// adding failed because numSopt is not set 
		game.addUserNumber(1);
		ArrayList<Integer> list = game.getUserNumbers();
		assertEquals(0, list.size(), "addUserNumbers() test failed");
	}
	
	@Test
	void addUserNumberTest2() { 
		// adding failed because the same number is add to the list 
		game.setNumDraws(1);
		game.setNumSpots(1);
		game.addUserNumber(1);
		game.addUserNumber(1);
		ArrayList<Integer> list = game.getUserNumbers();
		assertEquals(1, list.get(0), "addUserNumbers() test failed");
		assertEquals(1, list.size(), "addUserNumbers() test failed");
	}
	
	@Test
	void addUserNumberTest3() { 
		// successfully added
		game.setNumDraws(1);
		game.setNumSpots(1);
		game.addUserNumber(1);
		ArrayList<Integer> list = game.getUserNumbers();
		assertEquals(1, list.size(), "addUserNumbers() test failed");
		assertEquals(1, list.get(0), "addUserNumbers() test failed");
	}
	
	@Test
	void getUserNumbersTest() {
		game.setNumDraws(1);
		game.setNumSpots(4);
		game.addUserNumber(1);
		game.addUserNumber(2);
		game.addUserNumber(3);
		game.addUserNumber(4);
		ArrayList<Integer> list = game.getUserNumbers();
		assertEquals(4, list.size(), "getUserNumbers() test failed");
		assertEquals(1, list.get(0), "getUserNumbers() test failed");
		assertEquals(2, list.get(1), "getUserNumbers() test failed");
		assertEquals(3, list.get(2), "getUserNumbers() test failed");
		assertEquals(4, list.get(3), "getUserNumbers() test failed");
	}
	
	@Test
	void removeUserNumberTest() {
		game.setNumDraws(1);
		game.setNumSpots(4);
		game.addUserNumber(1);
		game.addUserNumber(2);
		game.addUserNumber(3);
		game.addUserNumber(4);
		game.removeUserNumber(2);
		assertEquals(false, game.isInUserNumbers(2), "removeUserNumbers() test failed");
	}
	
	@Test
	void clearUserNumbers() {
		game.setNumDraws(1);
		game.setNumSpots(4);
		game.addUserNumber(1);
		game.addUserNumber(2);
		game.addUserNumber(3);
		game.addUserNumber(4);
		game.clearUserNumbers();
		assertEquals(false, game.isInUserNumbers(2), "clearUserNumbers() test failed");
		assertEquals(0, game.getUserNumbers().size(), "clearUserNumbers() test failed");
	}
	
	@Test
	void matchTest() {
		game.setNumDraws(1);
		game.setNumSpots(8);
		game.quickPick();
		game.draw();
		ArrayList<Integer> list = game.getMatchedNumbers();
		assertEquals(game.getNumMatched(), list.size(), "match() test failed");
	}
	
	@Test
	void quickPickTest1() {
		// quick pick 8 numbers
		game.setNumDraws(1);
		game.setNumSpots(8);
		game.quickPick();
		assertEquals(8, game.getUserNumbers().size(), "quickPick() test1 failed");
		assertEquals(true, allDifferent(game.getUserNumbers()), "quickPick() test1 failed");
	}
	
	@Test
	void quickPickTest2() {
		// quick pick 10 numbers
		game.setNumDraws(1);
		game.setNumSpots(10);
		game.quickPick();
		assertEquals(10, game.getUserNumbers().size(), "quickPick() test2 failed");
		assertEquals(true, allDifferent(game.getUserNumbers()), "quickPick() test2 failed");
	}
	
	@Test
	void drawTest() {
		game.setNumDraws(1);
		game.setNumSpots(8);
		game.quickPick();
		game.draw();
		assertEquals(20, game.getDrawNumbers().size(), "draw() test failed");
		assertEquals(true, allDifferent(game.getDrawNumbers()), "draw() test failed");
		assertEquals(game.getNumMatched(), game.getMatchedNumbers().size(), "draw() test failed");
	}
	
	@Test
	void resetTest() {
		game.setNumDraws(1);
		game.setNumSpots(8);
		game.quickPick();
		game.draw();
		game.reset();
		assertEquals(0, game.getNumDraws(), "reset() test failed");
		assertEquals(0, game.getNumSpots(), "reset() test failed");
		assertEquals(0, game.getMatchedNumbers().size(), "reset() test failed");
		assertEquals(0, game.getDrawNumbers().size(), "reset test failed");
		assertEquals(0, game.getPrize(), "reset test failed");
		assertEquals(0, game.getTotalPrize(), "reset test failed");
		assertEquals(0, game.getUserNumbers().size(), "reset test failed");
	}
	
	@Test
	void prizeTest1() {
		// spot = 1
		String s = "prize() test1 failed";
		game.setNumDraws(1);
		game.setNumSpots(1);
		game.quickPick();
		game.draw();
		while(game.getNumMatched() != 1) {
			game.draw();
		}
		assertEquals(2, game.getPrize(), s);
	}
	
	@Test
	void prizeTest2() {
		// spot = 4
		String s = "prize() test2 failed";
		boolean list[] = {false, false, false};
		game.setNumDraws(1);
		game.setNumSpots(4);
		game.quickPick();
		game.draw();
		int temp;
		while(allTrue(list) == false) {
			game.draw();
			temp = game.getNumMatched() - 2;
			if(temp == 0 && list[temp] == false) {
				list[temp] = true;
				assertEquals(1, game.getPrize(), s);
			}
			if(temp == 1 && list[temp] == false) {
				list[temp] = true;
				assertEquals(5, game.getPrize(), s);
			}
			if(temp == 2 && list[temp] == false) {
				list[temp] = true;
				assertEquals(75, game.getPrize(), s);
			}
		}
	}
	
	@Test
	void prizeTest3() {
		// spot = 8
		String s = "prize() test3 failed";
		boolean list[] = {false, false, false, false, false};
		game.setNumDraws(1);
		game.setNumSpots(8);
		game.quickPick();
		game.draw();
		int temp;
		while(allTrue(list) == false) {
			game.draw();
			temp = game.getNumMatched() - 4;
			if(temp == 0) {
				list[temp] = true;
				assertEquals(2, game.getPrize(), s);
			}
			if(temp == 1  && list[temp] == false) {
				list[temp] = true;
				assertEquals(12, game.getPrize(), s);
			}
			if(temp == 2  && list[temp] == false) {
				list[temp] = true;
				assertEquals(50, game.getPrize(), s);
			}
			if(temp == 3  && list[temp] == false) {
				list[temp] = true;
				assertEquals(750, game.getPrize(), s);
			}
			if(temp == 4  && list[temp] == false) {
				list[temp] = true;
				assertEquals(10000, game.getPrize(), s);
			}
		}
	}
	
	@Test // It will take pretty long time for this test to run
	// since it will find a ten-matched-numbers draw
	void prizeTest4() {
		// spot = 10
		String s = "prize() test4 failed";
		boolean list[] = {false, false, false, false, false, false};
		game.setNumDraws(1);
		game.setNumSpots(10);
		game.quickPick();
		game.draw();
		int temp;
		while(allTrue(list) == false) {
			game.draw();
			temp = game.getNumMatched() - 5;
			if(temp == 0) {
				list[temp] = true;
				assertEquals(2, game.getPrize(), s);
			}
			if(temp == 1  && list[temp] == false) {
				list[temp] = true;
				assertEquals(15, game.getPrize(), s);
			}
			if(temp == 2  && list[temp] == false) {
				list[temp] = true;
				assertEquals(40, game.getPrize(), s);
			}
			if(temp == 3  && list[temp] == false) {
				list[temp] = true;
				assertEquals(450, game.getPrize(), s);
			}
			if(temp == 4  && list[temp] == false) {
				list[temp] = true;
				assertEquals(4250, game.getPrize(), s);
			}
			if(temp == 5  && list[temp] == false) {
				list[temp] = true;
				assertEquals(100000, game.getPrize(), s);
			}
		}
		while(game.getNumMatched() != 0) {
			game.draw();
		}
		assertEquals(5, game.getPrize(), s);
	}
	
	// check that all numbers in a list are different
	private boolean allDifferent(ArrayList<Integer> list) {
		for(int i = 0; i < list.size(); i++)
			for(int j = 0; i < list.size(); i++) {
				if(i != j) {
					if(list.get(i) == list.get(j))
						return false;
				}
			}
		return true;
	}
	
	// check that all elements are true
	private boolean allTrue(boolean list[]) {
		for(boolean x : list)
			if(x == false)
				return false;
		return true;
		
	} 
}
