package marks.scramble.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFinder {
	
	public static final String[] startingWords = new String[] {"BEGIN", "START", "COMMENCE", "EMBARK", "SCRAMBLE"};
	
	private String dictionary;
	private String allWordDictionary;
	
	private int wordsLeft, lettersLeft, minLength, maxLength, wildsLeft, extrasLeft;

	public WordFinder(Level l) {
		this.minLength = l.getMinLength();
		this.maxLength = l.getMaxLength();
		this.dictionary = DictionaryHandler.getDictionary(l.getDict());
		this.allWordDictionary = DictionaryHandler.getDictionary(DictionaryHandler.NUM_DICS);
		this.initWord(l);
	}
	
	private void initWord(Level l) {
		this.wordsLeft = l.getWordCount();
		this.lettersLeft = l.getLetterCount();
		this.wildsLeft = l.getNumWilds();
		this.extrasLeft = l.getNumExtras();
	}
	
	private int getNextLength() {
		int lowBound = lettersLeft - maxLength * (this.wordsLeft - 1);
		int highBound = lettersLeft - minLength * (this.wordsLeft - 1);
		if (lowBound < minLength)
			lowBound = minLength;
		if (highBound > maxLength)
			highBound = maxLength;
		while (9 - highBound < extrasLeft - ((wordsLeft - 1) * 3 - Math.max((lettersLeft-highBound) - 6 * (wordsLeft-1), 0))) {
			highBound--;
			System.out.println("wassap");
		}

		int nextLength = (int) (Math.random() * (highBound - lowBound + 1)) + lowBound;
		return nextLength;
	}
	
	public Word getStarter() {
		Random rand = new Random();
		return new Word(0, 0, startingWords[rand.nextInt(startingWords.length)], rand.nextBoolean());
	}
	
	public Word getNextPattern(Word previous) {
		Random rand = new Random();
		String prevStr = previous.getWord();
		int length = this.getNextLength();
		
		// I DO WHILE LOOP
		int endInI = rand.nextInt(prevStr.length()), i = endInI;
		do {
			if (++i >= prevStr.length())
				i = 0;
			
			// J DO WHILE LOOP
			int endInJ = rand.nextInt(prevStr.length()), j = endInJ;
			do {
				if (++j >= length)
					j = 0;
				// Pattern Building
				String pattern = "[" + length + "]";
				for (int ii = 0; ii < length; ii++) {
					if (ii == j)
						pattern += "[" + prevStr.charAt(i) + "]";
					else pattern += ".";
				}
				// End Pattern Building
				// Select Word if Applicable
				Matcher m = Pattern.compile(pattern).matcher(dictionary);
				
				if (m.find()) {
					String output = "";
					for (int jj = 0; jj < length; jj++) {
						if (jj == j)
							output += prevStr.charAt(i);
						else output += '-';
					}
					if (previous.isHorizontal())
						return new Word(previous.getX() + i, previous.getY() - j, output, false);
					return new Word(previous.getX() - j, previous.getY() + i, output, true);
				}
				// End Word Select
			} while (j != endInJ);
			// END J DO WHILE LOOP
		} while (i != endInI);
		// END I DO WHILE LOOP

		System.out.println("Fatal Error: Couldn't find a word!");
		return null;
	}
	
	public char[] getLetters(Word patternWord) {
		String word = patternWord.getWord();
		String pattern = "[" + word.length() + "]";
		for (char c : word.toCharArray()) {
			if (c == '-')
				pattern += ".";
			else pattern += "[" + c + "]";
		}
		Matcher m = Pattern.compile(pattern).matcher(dictionary);
		List<String> found = new ArrayList<>();
		while (m.find())
			found.add(dictionary.substring(m.start() + 1, m.end()));
		char[] output = found.get(new Random().nextInt(found.size())).toCharArray();
		output = addWildCards(patternWord, output);
		output = addExtras(output);
		return output;
	}
	
	public boolean isInDictionary(String word) {
		String pattern = "[" + word.length() + "]";
		for (char c : word.toCharArray())
			pattern += "[" + c + "]";
		return Pattern.compile(pattern).matcher(allWordDictionary).find();
	}

	public String getWithWildcards(char[] arr) {
		String pattern = "[" + arr.length + "]";
		for (char c : arr) {
			if (c == '?')
				pattern += ".";
			else pattern += "[" + c + "]";
		}
		Matcher m = Pattern.compile(pattern).matcher(dictionary);
		List<String> found = new ArrayList<>();
		while (m.find())
			found.add(dictionary.substring(m.start() + 1, m.end()));
		return found.get(new Random().nextInt(found.size()));
	}

	private char[] addWildCards(Word w, char[] arr) {

		Random rand = new Random();
		int deepMin = wildsLeft - ((lettersLeft - arr.length) - (wordsLeft - 1));

		int min = Math.max(0, deepMin);
		int max = Math.min(wildsLeft, arr.length - 1);

		if (deepMin >= 0)
			deepMin = -1;
		double prob = 1 - Math.pow(rand.nextDouble(), 1 / Math.pow(-deepMin, 0.25));
		int numWilds = (int) (prob * (max - min + 1)) + min;
		wildsLeft -= numWilds;

		List<Integer> indexes = new ArrayList<>();
		for (int i = 0; i < arr.length; ++i)
			indexes.add(i);
		for (; numWilds > 0; --numWilds) {
			int next = indexes.remove(rand.nextInt(indexes.size()));
			if (w.getCharArray()[next] == '-')
				arr[next] = '?';
			else numWilds++; // retry
		}
		return arr;
	}

	private char[] addExtras(char[] arr) {

		Random rand = new Random();
		int deepMin = extrasLeft - ((wordsLeft - 1) * 3 - Math.max((lettersLeft-arr.length) - 6 * (wordsLeft-1), 0));

		int min = Math.max(0, deepMin);
		int max = Math.min(extrasLeft, 3);

		if (deepMin >= 0)
			deepMin = -1;
		double prob = 1 - Math.pow(rand.nextDouble(), 1 / Math.sqrt(-deepMin));
		int numExtras = (int) (prob * (max - min + 1)) + min;
		extrasLeft -= numExtras;

		char[] output = new char[arr.length + numExtras];
		for (int i = 0; i < arr.length; ++i)
			output[i] = arr[i];
		for (int i = arr.length; i < output.length; ++i)
			output[i] = (char) (rand.nextInt(26) + 'A');

		return output;
	}

	public void decrementCounters(Word nextWord) {
		this.wordsLeft--;
		this.lettersLeft -= nextWord.getWord().length();
	}
	
}
