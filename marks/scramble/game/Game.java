package marks.scramble.game;

import java.util.*;

public class Game {
	
	public static final int DEFAULT_HINTS = 3;
	
	private WordFinder wf;
	private Word activeWord, previousWord;
	private char[] activeLetters;
	private int remWords;
	private int remHints;

	public Game(Level l) {
		this.remHints = l.getAutoHints() == 0 ? DEFAULT_HINTS : 0;
		this.wf = new WordFinder(l);
		this.remWords = l.getWordCount() + 1;
		this.startUp();
	}
	
	private void startUp() {
		activeWord = wf.getStarter();
		this.nextWord();
	}
	
	private void nextWord() {
		if (--this.remWords == 0)
			return;
		previousWord = activeWord;
		activeWord = wf.getNextPattern(activeWord);
		activeLetters = wf.getLetters(activeWord);
		wf.decrementCounters(activeWord);
	}
	
	public boolean testWord(String input) {
		// test if viable
		if (input.length() != activeWord.getWord().length() || ! wf.isInDictionary(input))
			return false;
		for (int i = 0; i < input.length(); i++)
			if (activeWord.getCharArray()[i] != '-' && input.charAt(i) != activeWord.getCharArray()[i])
				return false;
		List<Character> letterList = new ArrayList<>();
		for (int i = 0; i < activeLetters.length; ++i)
			letterList.add(activeLetters[i]);
		for (char c : input.toCharArray()) {
			if (letterList.remove(new Character(c)));
			else if (letterList.remove(new Character('?')));
			else return false;
		}

		activeWord.setWord(input);
		nextWord();
		return true;
	}
	
	public boolean useHint(int index) {
		if (index == -1) {
			int endIndex = new Random().nextInt(activeLetters.length);
			index = endIndex;
			while (activeWord.getCharArray()[index] != '-') {
				if (++index >= activeLetters.length)
					index = 0;
				if (index == endIndex)
					return false;
			}
		}
		else if (index < 0 || index >= activeWord.getWord().length() ||
				activeWord.getCharArray()[index] != '-' ||--remHints < 0)
			return false;

		String w = activeWord.getWord();
		if (activeLetters[index] == '?')
			activeLetters[index] = wf.getWithWildcards(Arrays.copyOfRange(activeLetters, 0, w.length())).charAt(index);
		activeWord.setWord(w.substring(0, index) + activeLetters[index] + w.substring(index + 1));
		return true;
	}
	
	public boolean ended() {
		return this.remWords <= 0;
	}
	
	public Word getNextWord(boolean front) {
		if (front)
			return activeWord;
		else return previousWord;
	}
	
	public char[] getLetters() {
		return Arrays.copyOf(activeLetters, activeLetters.length);
	}
	
	public int getNumHints() {
		return remHints;
	}
	
	public int getWordsLeft() {
		return remWords;
	}
	
	public String getLatestWord() {
		String w = activeWord.getWord();
		return wf.getWithWildcards(Arrays.copyOfRange(activeLetters, 0, w.length())).substring(0, activeWord.getWord().length());
	}
	
}
