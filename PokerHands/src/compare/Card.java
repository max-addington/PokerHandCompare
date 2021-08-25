package compare;

class Card {
	int value;
	String suit;
	String name;
	
	Card(String input){
		String v = input.substring(0,1);
		String s = input.substring(1,2);
		suit = s;
		try { // This checks if the value is a face card or not, if the value is less than ten this sets the value equal to the string (as an int).
			value = Integer.parseInt(v);
			name = v;
		}catch(Exception e) { // this switch sets the value equal to the corresponding letter for ten and face cards
			switch(v.toLowerCase()) {
			case "t": value = 10;
			name = "10";
			break;
			case "j": value = 11;
			name = "Jack";
			break;
			case "q" : value = 12;
			name= "Queen";
			break;
			case "k" : value = 13;
			name = "King";
			break;
			case "a" : value= 14;
			name = "Ace";
			break;
			}
		}
	}
	
	int getValue() {
		return value;
	}
	
	String getSuit() {
		return suit;
	}
	
	String getName() {
		return name;
	}
}
