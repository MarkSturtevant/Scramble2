package marks.scramble.gui.resources;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.scene.media.AudioClip;
import marks.scramble.gui.GUIHandler;

public enum Music {
    INTRO("eff_intro.mp3", true),
    WIN("eff_win1.mp3", false);

    private static final List<String> directoryLevel = Arrays.asList("mus1.mp3", "mus2.mp3", "mus3.mp3", "mus4.mp3", "mus5.mp3", "mus6.mp3", "mus7.mp3", "mus8.mp3", "mus9.mp3", "mus10.mp3");
    private static Map<String, AudioClip> fileData;
    private AudioClip clip;
    private final String url;

    private Music(String url, boolean fastInitialization) {
        if (fastInitialization) {
            this.clip = new AudioClip(Sounds.class.getResource("/resource/music/gui/" + url).toExternalForm());
            this.url = null;
        } else {
            this.url = url;
        }

    }

    public static void init() {
        Music[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            Music i = var0[var2];
            if (i.url != null) {
                i.clip = new AudioClip(Sounds.class.getResource("/resource/music/gui/" + i.url).toExternalForm());
            }
        }

        fileData = new HashMap();
        Iterator var4 = directoryLevel.iterator();

        while(var4.hasNext()) {
            String file = (String)var4.next();
            fileData.put(file, new AudioClip(Sounds.class.getResource("/resource/music/levels/" + file).toExternalForm()));
        }

        System.out.println("Game: Loaded Music");
    }

    public void play() {
        GUIHandler.playAudio(this.clip, true);
    }

    public void stop() {
        GUIHandler.stopAudio(this.clip, true);
    }

    public static void play(String path) {
        GUIHandler.playAudio((AudioClip)fileData.get(path), true);
    }

    public static void stop(String path) {
        GUIHandler.stopAudio((AudioClip)fileData.get(path), true);
    }
}
