import java.util.ArrayList;
import java.util.Random;

public class KenoGame {
	private int numDraws;
	private int numSpots;
	private int numMatched;
	private ArrayList<Integer> userNumbers;
	private ArrayList<Integer> drawNumbers;
	private ArrayList<Integer> matchedNumbers;
	private int totalPrize, prize;
	
	public KenoGame() {
		// TODO Auto-generated constructor stub
		numDraws = 0;
		numSpots = 0;
		userNumbers = new ArrayList<>();
		drawNumbers = new ArrayList<>();
		matchedNumbers = new ArrayList<>();
		totalPrize = 0;
		prize = 0;
		numMatched = 0;
	}
	
	void draw() {
		// clear the list
		drawNumbers.clear();
		
		// generate 20 random numbers in range 1-80
		Random random = new Random();
		int temp = 0;
		for(int i = 0; i < 20; i++) {
			// random number from 1-80
			temp = random.nextInt(79) + 1;
			
			// get another random number if the number is already in the list
			while(drawNumbers.contains(temp)) {
				temp = random.nextInt(79) + 1;
			}
			
			drawNumbers.add(temp);
		}
		
		// match the user's numbers with drawn numbers
		match();
		prize();
		//printResult();
	}
	
	// set numDraws
	void setNumDraws(int n) {
		numDraws = n;
	}
	
	// set NumSpots
	void setNumSpots(int n) {
		numSpots = n;
	}
	
	// get numDraws
	int getNumDraws() {
		return numDraws;
	}
	
	// get numSpots
	int getNumSpots() {
		return numSpots;
	}
	
	// get total won so far
	int getTotalPrize() {
		return totalPrize;
	}
	
	// get prize
	int getPrize() {
		return prize;
	}
	
	// get drawn numbers
	ArrayList<Integer> getDrawNumbers(){
		return drawNumbers;
	}
	
	// get matched numbers
	ArrayList<Integer> getMatchedNumbers() {
		return matchedNumbers;
	}
	
	// get numbers of matches
	int getNumMatched() {
		return numMatched;
	}
	
	// add a number to userNumbers list
	boolean addUserNumber(int n) {
		// add to the list if the number is not in the list
		if(!userNumbers.contains(n) && userNumbers.size() < numSpots) {
			userNumbers.add(n);
			return true;
		}
		
		return false;
	}
	
	// check if a number is in user numbers list
	boolean isInUserNumbers(int n) {
		return userNumbers.contains(n);
	}
	
	// get user numbers list
	ArrayList<Integer> getUserNumbers() {
		return userNumbers;
	}
	
	// remove a number from user list
	void removeUserNumber(Integer n) {
		if(userNumbers.contains(n) == true){
			userNumbers.remove(n);
		}
	}
	
	// clear userNumbers List
	void clearUserNumbers() {
		userNumbers.clear();
	}
	
	// match the numbers that user chose to the drawn numbers
	private void match() {
		// clear matched numbers list
		matchedNumbers.clear();
		
		// look for matched numbers
		for(int x : userNumbers) {
			for(int y : drawNumbers) {
				if(x == y) {
					matchedNumbers.add(x);
				}
			}
		}
		
		numMatched = matchedNumbers.size();
	}
	
	// Print result
	private void printResult() {
		System.out.println("Number of draws: " + numDraws);
		System.out.println("Number of spots: " + numSpots);
		System.out.println("Drawn numbers: ");
		printList(drawNumbers);
		System.out.println("User numbers: ");
		printList(userNumbers);
		System.out.println("Matched numbers: ");
		printList(matchedNumbers);
		System.out.println("Number of matches: " + matchedNumbers.size());
		System.out.println("prize: " + prize);
		System.out.println("Total prize: " + totalPrize);
	}
	
	// Quick Pick, generate the numbers matched the number of spots
	void quickPick() {
		// clear the list
		userNumbers.clear();
		
		// generate 20 random numbers in range 1-80
		Random random = new Random();
		int temp = 0;
		for(int i = 0; i < numSpots; i++) {
			// random number from 1-80
			temp = random.nextInt(79) + 1;
			
			// get another random number if the number is already in the list
			while(userNumbers.contains(temp)) {
				temp = random.nextInt(79) + 1;
			}
			
			userNumbers.add(temp);
		}
	}
	
	// Print all values in the array
	private void printList(ArrayList<Integer> list) {
		for(Integer x : list) {
			System.out.print(x + " ");
		}
		System.out.print("\n");
	}
	
	// reset all the data of the game
	void reset() {
		numDraws = 0;
		numSpots = 0;
		numMatched = 0;
		userNumbers.clear();
		drawNumbers.clear();
		matchedNumbers.clear();
		prize = 0;
	}

	// the prize of current round
	private void prize() {
		int matched = 0;
		prize = 0;
		switch(numSpots) {
			case 1:
				if(matchedNumbers.size() == 1)
					prize = 2;
				break;
			case 4:
				int list1[] = {1,5,75};
				matched = matchedNumbers.size() - 2;
				for(int i = 0; i < 3; i++)
					if(matched == i)
						prize = list1[i];
				break;
			case 8:
				int list2[] = {2,12,50,750,10000};
				matched = matchedNumbers.size() - 4;
				for(int i = 0; i < 5; i++)
					if(matched == i)
						prize = list2[i];
				break;
			case 10:
				int list3[] = {2,15,40,450,4250,100000};
				matched = matchedNumbers.size() - 5;
				if(matched == -5)
					prize = 5;
				else {
					for(int i = 0; i < 6; i++)
						if(matched == i)
							prize = list3[i];
				}
				break;
		}
		
		totalPrize += prize; 
	}
}
