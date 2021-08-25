package compare;

import java.util.Scanner;
// 

public class Poker {
	
	public static String valueToString(int v) {
		if(v <11) {
			return ""+v;
		}else if(v==11) {
			return "Jack";
		}else if(v==12) {
			return "Queen";
		}else if(v==13) {
			return "King";
		}else if(v==14) {
			return "Ace";
		}else {
			return "";
		}
	}
	
	public static String results(Hand win) {
		String send="";
		
		switch(win.getStrength()){
		case 8: send = "straight flush: "+ valueToString(win.getStrongest());
		break;
		case 7: send = "four of a kind: "+ valueToString(win.getStrongest()); // two players cannot get 4 of a kind of the same value, therefore kicker does not matter
		break;
		case 6: send = "full house: "+ valueToString(win.getStrongest())+ " over " + valueToString(win.getWeaker()); 
		break;
		case 5: send = "flush: "+ valueToString(win.getStrongest()) + " high";
		break;
		case 4: send = "straight: To "+ valueToString(win.getStrongest());
		break;
		case 3: send = "three of a kind: "+ valueToString(win.getStrongest()); // two players cannot get 3 of a kind of the same value either, therefore no kicker
		break;
		case 2: send = "two pair: " + valueToString(win.getStrongest()) + " and " + valueToString(win.getWeaker());
		break;
		case 1: send = "pair: "+ valueToString(win.getStrongest());
		break;
		case 0: send = "high card: " +valueToString(win.getSorted()[4]);
		break;
		}
		
		return send;
	}
	
	public static String tie(Hand w, Hand b) {
		if(w.getStrongest() > b.getStrongest()) { // checks the how high the Strongest variable is. This represents the 3 of a kind in a full house, the value of a 2,3, or 4 of a kind etc.
			return "White wins. - with "+ results(w); 			
		}else if(w.getStrongest() < b.getStrength()) {
			return "Black wins. - with "+ results(b); 
		}else {
			if(w.getWeaker() > b.getWeaker()) { // checks the how high the Weaker variable is. This represents the 2 of a kind in a full house, or the lower pair in 2 pair
				return "White wins. - with "+ results(w); 			
			}else if(w.getWeaker() < b.getWeaker()) {
				return "Black wins. - with "+ results(b); 
			}else {
				if(w.getKicker() > b.getKicker()) { // this check which player has a higher kicker. This is only used for a pair or 2 pair results. if kicker is the tie breaker will results will reflect it
					return "White wins. - with "+ results(w) + " "+ valueToString(w.getKicker()) + " kicker"; 			
				}else if(w.getKicker() < b.getKicker()) {
					return "Black wins. - with "+ results(b) + " "+  valueToString(b.getKicker()) + " kicker"; 
				}else {
					if(w.getKicker1() > b.getKicker1()) { // this check which player has a higher 2nd kicker. This is only used for a pair 
						return "White wins. - with "+ results(w) +  " "+ valueToString(w.getKicker1()) + " kicker"; 			
					}else if(w.getKicker1() < b.getKicker1()) {
						return "Black wins. - with "+ results(b) +  " "+ valueToString(b.getKicker1()) + " kicker"; 
					}else {
						if(w.getKicker2() > b.getKicker2()) { // this check which player has a higher 3rd kicker. This is only used for a pair 
							return "White wins. - with "+ results(w) +  " "+ valueToString(w.getKicker2()) + " kicker"; 			
						}else if(w.getKicker2() < b.getKicker2()) {
							return "Black wins. - with "+ results(b) +  " "+ valueToString(b.getKicker2()) + " kicker"; 
						}else {
							for(int i=4;i>=0;i--) { // because the kickers are all 0 for high card we look at the sorted values of the hands to see which hand wins. If they are all the same it must be a tie
								if(w.getSorted()[i] > b.getSorted()[i]) {
									return "White wins. - with high card: " + valueToString(w.getSorted()[i]); 
								}else if(w.getSorted()[i] < b.getSorted()[i]) {
									return "Black wins. - with high card: " + valueToString(b.getSorted()[i]); 
								}
							}
							return "Tie."; // This will only occur if the number values of each card are the same in both hands. If there is a flush in one and not the other it will be returned as such 
											// and never even enter this function, unless both hands are flushes in which case it will be a tie.
						}
					}
				}
			}
		}
	}
	
	public static String compareHands(Hand w, Hand b) {
		b.strength();
		w.strength();
		if(w.getStrength() > b.getStrength()) {
			return "White wins. - with "+ results(w); 
		}else if(w.getStrength() < b.getStrength()) {
			return "Black wins. - with "+ results(b); 
		}else {
			return tie(w,b);
		} 
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		String input = sc.nextLine();
		
		sc.close();
		
		String[] split = input.split(" ");
		String[] vs = new String[10]; // value and suits
		int j=0;
		
		for( int i=0; i< split.length; i ++) { // breaks the input down into 10 individual pairs of values and suits. Ignores split 0,6,7 because they are blank or "white" and "black"
			if(i != 0 && i!= 6 && i!=7) { 
				vs[j] = split[i];
				j++;
			}
		}
		
		Card[] b = {new Card(vs[0]), new Card(vs[1]),new Card(vs[2]),new Card(vs[3]),new Card(vs[4])}; 
		Card[] w = {new Card(vs[5]), new Card(vs[6]),new Card(vs[7]),new Card(vs[8]),new Card(vs[9])};
		
		Hand black = new Hand(b);
		Hand white = new Hand(w);
		
		System.out.println(compareHands(white, black));
		
		
	}

}
