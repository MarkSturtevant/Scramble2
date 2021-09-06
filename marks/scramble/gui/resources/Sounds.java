package marks.scramble.gui.resources;

import javafx.scene.media.AudioClip;
import marks.scramble.gui.GUIHandler;

public enum Sounds {
    CORRECT("eff_correct.mp3", false),
    TIME("eff_time.mp3", false),
    TICK("eff_tick.mp3", false),
    WIN2("eff_win2.mp3", false),
    WIN3("eff_win3.mp3", false),
    WIN4("eff_win4.mp3", false),
    WIN5("eff_win5.mp3", false),
    WIN6("eff_win6.mp3", false),
    LOST("eff_lose.mp3", false);

    private AudioClip clip;
    private final String url;

    private Sounds(String url, boolean fastInitialization) {
        if (fastInitialization) {
            this.clip = new AudioClip(Sounds.class.getResource("/resource/sfx/gui/" + url).toExternalForm());
            this.url = null;
        } else {
            this.url = url;
        }

    }

    public static void init() {
        Sounds[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            Sounds i = var0[var2];
            if (i.url != null) {
                i.clip = new AudioClip(Sounds.class.getResource("/resource/sfx/gui/" + i.url).toExternalForm());
            }
        }

        System.out.println("Game: Loaded SFX");
    }

    public void play() {
        GUIHandler.playAudio(this.clip, false);
    }

    public void stop() {
        GUIHandler.stopAudio(this.clip, false);
    }
}
