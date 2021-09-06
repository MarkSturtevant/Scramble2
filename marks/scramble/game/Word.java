package marks.scramble.game;

public class Word {
	
	private int x, y;
	private String word;
	private boolean horizontal;

	public Word(int xPos, int yPos, String word, boolean horizontal) {
		this.x = xPos;
		this.y = yPos;
		this.word = word;
		this.horizontal = horizontal;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public char[] getCharArray() {
		return word.toCharArray();
	}
	
	public boolean isHorizontal() {
		return horizontal;
	}
	
}
