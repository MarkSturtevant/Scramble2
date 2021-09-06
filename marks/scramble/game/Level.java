package marks.scramble.game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import marks.scramble.gui.resources.Images;
import marks.scramble.gui.resources.Music;

import java.io.*;

public enum Level {

	WORD_JAMMER(),
	REDWOOD(),
	SURPRISE(),
	DUBSTEP(),
	SMOKE(),
	INVASION(),
	BRICK(),
	MANOR(),
	BUTTERFLY(),
	FINALE();

	public static int DIFFICULTY = 0;

	private String name, descr, bg, mus, icon;
	private Color boxColor, letterColor;
	private int dictID, levelID;
	private int[] wordCount;
	private int[] letterCount;
	private int[] minWordLength;
	private int[] maxWordLength;
	private int[] numWild;
	private int[] numExtra;
	private double[][] spinningData;
	private int[] autoHints;
	private double[] timeBonusA;
	private int time;

	private Level() {
		this.wordCount = new int[3];
		this.letterCount = new int[3];
		this.maxWordLength = new int[3];
		this.minWordLength = new int[3];
		this.numWild = new int[3];
		this.numExtra = new int[3];
	}

	public static void initLevels() {
		Level[] levels = Level.values();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(
				Level.class.getResourceAsStream("/resource/leveldata.txt")))) {
			for (int i = 0; i < levels.length; ++i) {
				levels[i].levelID = i+1;
				String line = br.readLine();
				levels[i].name = line.substring(line.indexOf(" ") + 1);
				line = br.readLine();
				levels[i].descr = line.substring(line.indexOf(" ") + 1);
				levels[i].bg = br.readLine().split(" ")[1];
				levels[i].icon = br.readLine().split(" ")[1];
				levels[i].mus = br.readLine().split(" ")[1];
				String[] split = br.readLine().split(" ");
				levels[i].boxColor = Color.rgb(Integer.parseInt(split[1]), Integer.parseInt(split[2]),Integer.parseInt(split[3]));
				split = br.readLine().split(" ");
				levels[i].letterColor = Color.rgb(Integer.parseInt(split[1]), Integer.parseInt(split[2]),Integer.parseInt(split[3]));
				levels[i].dictID = Integer.parseInt(br.readLine().split(" ")[1]);
				levels[i].time = Integer.parseInt(br.readLine().split(" ")[1]);
				split = br.readLine().split(" ")[1].split("/");
				String[] split2 = br.readLine().split(" ")[1].split("/");
				String[] split3 = br.readLine().split(" ")[1].split("/");
				String[] split4 = br.readLine().split(" ")[1].split("/");
				String[] split5 = br.readLine().split(" ")[1].split("/");
				String[] split6 = br.readLine().split(" ")[1].split("/");
				for (int j = 0; j < 3; ++j) {
					levels[i].wordCount[j] = Integer.parseInt(split[j]);
					levels[i].letterCount[j] = Integer.parseInt(split2[j]);
					levels[i].minWordLength[j] = Integer.parseInt(split3[j]);
					levels[i].maxWordLength[j] = Integer.parseInt(split4[j]);
					levels[i].numWild[j] = Integer.parseInt(split5[j]);
					levels[i].numExtra[j] = Integer.parseInt(split6[j]);
				}
				levels[i].spinningData = new double[][] {{0, 0, 0}, {0, 0, 0}};
				levels[i].autoHints = new int[] {0, 0, 0};
				levels[i].timeBonusA = new double[] {0, 0, 0};
				String next;
				while (! (next = br.readLine()).equals("-")) {
					if (next.equals("Spinning")) {
						levels[i].spinningData = new double[][] {{0.00000004235983, 0.00000009235983, 0.0000003235983}, {0.00214159, 0.00474159, 0.00614159}};
					} else if (next.equals("Auto-Hints")) {
						levels[i].autoHints = new int[] {2500, 2500, 2500};
					} else if (next.equals("Time Bonuses")) {
						levels[i].timeBonusA = new double[] {303.752, 256.014, 159.111};
					}
					else System.out.println("Read unknown data for level " + levels[i].name + ": " + next);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getDict() {
		return this.dictID;
	}
	
	public int getWordCount() { return wordCount[DIFFICULTY]; }
	
	public int getLetterCount() {
		return letterCount[DIFFICULTY];
	}

	public int getMinLength() { return minWordLength[DIFFICULTY]; }
	
	public int getMaxLength() {
		return maxWordLength[DIFFICULTY];
	}

	public int getNumWilds() {
		return numWild[DIFFICULTY];
	}

	public int getNumExtras() {
		return numExtra[DIFFICULTY];
	}
	
	public int getTime() {
		return time;
	}

	public int getLevelID() {
		return levelID;
	}

	public double getSpinAcc() { return spinningData[0][DIFFICULTY]; }

	public double getSpinMax() { return spinningData[1][DIFFICULTY]; }

	public double getTimeBonus() { return timeBonusA[DIFFICULTY]; }

	public int getAutoHints() { return autoHints[DIFFICULTY]; }

	public Color getBoxColor() { return boxColor; }

	public void setBoxColor(Color c) { boxColor = c; }

	public Color getLetterColor() { return letterColor; }

	public void setLetterColor(Color c) { letterColor = c; }

	public String getTitle() { return name; }

	public String getDescr() { return descr; }

	public Image getBG() { return Images.get(bg); }

	public Image getIcon() { return Images.get(icon); }

	public void playMusic() { Music.play(mus); }

	public void stopMusic() { Music.stop(mus); }

	public String getDictionaryTitle() {
		switch(dictID) {
			case 1:
				return "Basic";
			case 2:
				return "Easy";
			case 3:
				return "Common";
			case 4:
				return "Moderate";
			case 5:
				return "Difficult";
			case 6:
				return "Crazy";
			default:
				return "Unknown";
		}
	}
	
}
