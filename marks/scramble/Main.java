package marks.scramble;

import marks.scramble.game.DictionaryHandler;
import marks.scramble.game.Level;
import marks.scramble.gui.GUIWindow;
import marks.scramble.gui.resources.Images;
import marks.scramble.gui.resources.Music;
import marks.scramble.gui.resources.Sounds;

public class Main {

	public static void main(String[] args) {
		
		GUIWindow.launchGUI("Scramble");
		
	}

	public static void initResources() {
		Images.init();
		Music.init();
		Sounds.init();
		DictionaryHandler.init();
		Level.initLevels();
	}
	
}
