package marks.scramble.game;

import java.io.*;
import java.util.List;

public class DictionaryHandler {

    public static final int NUM_DICS = 7;

    private static String[] dictionaries;

    public static void init() {
        dictionaries = new String[NUM_DICS];
        for (int i = 0; i < NUM_DICS; ++i) {
            try (/*BufferedReader reader = new BufferedReader(new FileReader(new File(
                    "src/resource/text/words" + (i+1) + ".txt")))*/
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                DictionaryHandler.class.getResourceAsStream("/resource/text/words" + (i+1) + ".txt")))) {
                String dict = "", next = "";
                while ((next = reader.readLine()) != null)
                    dict += next;
                dictionaries[i] = dict;
                System.out.println("Game: Loaded Dictionary " + (i+1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getDictionary(int id) {
        return dictionaries[id-1];
    }

}
