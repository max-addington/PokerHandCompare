package compare;
import java.util.Arrays;


class Hand {
	Card[] cards;
	String handType=""; // this is used for to make printing results easier
	int sorted[] = {0,0,0,0,0}; //This allows me to check for pairs and straights without analyzing every value and instead only looking at a value's neighbours.
	int strength=0;
	int strongest=0; // this is the high card in a straight or flush, the 3 of a kind in a full house, the higher of two pairs, or the value of the pair, 3 or 4 of a kind
	int weaker=0;    // this is the pair in a full house or the weaker of two pairs in the two pair hand
	// Might be worth explaining what a kicker is here, or in the documentation (if you didn't already - might have missed it)
	int kicker =0; // the first kicker will only be needed for two pair and for a pair
	int kicker1 = 0;
	int kicker2 =0; // three kickers are only needed for pairs, for high card the sorted array itself will be used
	
	Hand(Card[] c){
		cards = c;
		for(int i=0; i<5; i++) {
			sorted[i] = c[i].getValue();
		}
		Arrays.sort(sorted);
	}
	
	String getHandtype() {
		return handType;
	}
	
	int[] getSorted() {
		return sorted;
	}
	
	int getStrength() {
		return strength;
	}
	
	int getStrongest() {
		return strongest;
	}
	
	int getWeaker() {
		return weaker;
	}
	
	int getKicker() {
		return kicker;
	}
	
	int getKicker1() {
		return kicker1;
	}
	
	int getKicker2() {
		return kicker2;
	}
	
	void strength() { // because this function only goes downstream when a previous hand is false we don't need to confirm a pair is not in fact four of a kind etc.
		if(checkStraightFlush()) {
			handType= "straight flush";
			strength=8;
		}else if(checkFour()) {
			handType= "four of a kind";
			strength=7;
		}else if(checkFullHouse()) {
			handType= "full house";
			strength=6;
		}else if(checkFlush()) {
			handType= "flush";
			strength=5;
		}else if(checkStraight()) {
			handType= "straight";
			strength=4;
		}else if(checkThree()) {
			handType= "three of a kind";
			strength=3;
		}else if(checkTwoPair()) {
			handType= "two pair";
			strength=2;
		}else if(checkPair()) {
			handType= "pair";
			strength=1;
		}else {
			handType= "high card";
			strength=0;
		}
	}
	
	
	boolean checkPair() {
		int[] kickers = {0,0,0,0,0,};
		for(int i = 1; i<5; i++) {
			if(sorted[i] == sorted[i-1]) {
				strongest = sorted[i]; 
				for(int j = 0; j < 5 ; j++) {
					if(j != i && j != i-1) {
						kickers[j] = sorted[j]; // to find the kicker, iterates through the sorted list to save all of the values that aren't of the pair in the kickers array
												// then the array is sorted and they are saved as kickers 0, 1 and 2, 0 being the highest
					}
				}
				Arrays.sort(kickers);
				kicker = kickers[4];
				kicker1= kickers[3];
				kicker2= kickers[2];
				return true;
			}
		}
		return false;
	}// because values is sorted if the adjacent values aren't the same, then they will not appear again, 
	 // checks starting from position 1 and then checks position 2, 3 and 4
	
	
	boolean checkTwoPair() { // the kicker in this function will always be the number not used in either pair

		if(sorted[0] == sorted[1] && sorted[2] == sorted[3]) {
			strongest = sorted[3];  // because the values are already in order the higher pair will always be the 2nd to appear and the weaker will be the first
			weaker = sorted[1];
			kicker = sorted[4];
			return true;
		}else if(sorted[0] == sorted[1] && sorted[3] == sorted[4]) {
			strongest = sorted[3];
			weaker = sorted[1];
			kicker = sorted[2];
			return true;
		}else if(sorted[1] == sorted[2] && sorted[3] == sorted[4]) {
			strongest = sorted[3];
			weaker = sorted[1];
			kicker = sorted[0];
			return true;
		}else {
			return false;
		}
	} // because of the sorted list, only 3 possible scenarios could yield 2 pair, AABBC, AABCC, and ABBCC.
	
	
	boolean checkThree() { // no kicker is needed because two players cannot get the same 3 of a kind
		for(int i = 2; i < 5; i++) {
			if(sorted[i] == sorted[i-1] && sorted[i] == sorted[i-2]) {
				strongest = sorted[i];
				return true;
			}
		}
		return false;
	}// because values is sorted if the adjacent values aren't the same, then they will not appear again, 
	 // checks starting from position 2 and then checks position 3 and 4
	
	
	boolean checkStraight() {	
		
		if(sorted[4] == 14 && sorted[0] == 2 && sorted[1] ==3 && sorted[2] == 4 && sorted[3] ==5) {
			strongest = sorted[3];
			return true;
		} // because aces can be low for a straight checks for that specific case
		
		for(int i = 1; i < 5;i++) {
			if(sorted[i-1] != sorted[i]-1) {
				return false; // starts at 1 and checks to see if the previous value is exactly 1 smaller than the current one, if it's ever not true then returns false
			}
		}
		strongest = sorted[4];
		return true;
		
	} // checks if all of the values form a straight 
	
	boolean checkFlush() {
		
		if(cards[0].getSuit().equals(cards[1].getSuit()) 
		   && cards[0].getSuit().equals(cards[2].getSuit())
		   && cards[0].getSuit().equals(cards[3].getSuit())
		   && cards[0].getSuit().equals(cards[4].getSuit())) {
			strongest= sorted[4];
			return true;
		}
		else {
			return false;
		}
	}  // checks if all cards are of the same suit
	
	boolean checkFullHouse() {
		if(sorted[0] == sorted[1] && sorted[3] == sorted[4]) {
			if(sorted[0] == sorted[2]) {
				strongest= sorted[0]; // the 3 of a kind is considered stronger in a full house even if the pair's value is higher
				weaker = sorted[3];
				return true;
			}else if(sorted[3] == sorted[2]) {
				strongest = sorted[3];
				weaker = sorted[0];
				return true;
			}
		}
		return false;
	} // because values are sorted, checks if both the first and last two numbers are pairs, if they are then it checks if the middle
	  // number matches either the first or second pair
	
	boolean checkFour() { // no kicker is needed because 2 players cannot get the same 4 of a kind
		for(int i = 3; i < 5; i++) {
			if(sorted[i] == sorted[i-1] && sorted[i] == sorted[i-2] && sorted[i] == sorted[i-3]) {
				strongest = sorted[i];
				return true;
			}
		}
		return false;
	}// because values is sorted if the adjacent values aren't the same, then they will not appear again, 
	 // checks starting from position 3 and then checks position 4
	
	
	boolean checkStraightFlush() {
		if(checkFlush()&& checkStraight()) {
			return true;
		}
		else {
			return false;
		}
	} // if both flush and straight are true then the hand is a straight flush
	
	
}
