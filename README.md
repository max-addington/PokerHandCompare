# PokerHandCompare
Josh Moscoe 8-25-2021

Manifest technologies coding Kata- http://codingdojo.org/kata/PokerHands/


<i>All input must be in the format  of: Black: 5H AD AC 5D 7H  White: 5C 5D AH AS 6D  (note the two spaces between the last card in black's hand and white's hand)</i>


For this problem I figured the simplest and most elegant solution involved sorting the values of each hand by size. This made checking what the hand was a lot easier. For example for a straight all I need to do is start at i=1 and check to see if int[i] == int[i-1]+1. It also helped a lot with the pairing functions. If a number occurs many times in a sorted array then they will appear next to each other, so instead of iterating over the whole hand to look for a pair I only needed to look at a hand’s neighbours. So for Four of a Kind, I looked at the 4th card and checked if the 3 previous values were the same, and then did the same for the 5th value. Similarly, for the 3 of a Kind I started from the 3rd value and only had to check two more possibilities.

For the full house the first two and the last two digits must be the same, so all I need to check is if the middle value is the same as either the 1st or the 5th value. If all of those are true then we have a full house! For 2 pair there were only 3 possible occurrences; AABBC, AABCC or ABBCC.
And of course, to check high card I just iterated over the sorted list to see whose card was lower first. If both had aces but one had a king then the king would be the high card.

Once the hand checking logic had been completed, I needed to compare the hands, but because of the Hand class’s strength variable that was as simple as comparing two integers. If they tied then I checked the strongest variable which represented the value of the pair, 3 of a kind or 4 of a kind. Strongest also represented the highest card in a flush or straight, as well as the pair of higher value in a 2 pair, or the three of a kind in a full house.


From there we only needed to add the kicker if both players had the same pair which I saved in the Hand’s checkPair() function.
