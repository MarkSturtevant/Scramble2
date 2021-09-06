package marks.scramble.gui.resources;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;

public enum Images {
    LOADING(new Image(Images.class.getClassLoader().getResourceAsStream("resource/images/gui/scr_loading.png"))),
    TITLE("scr_title.png"),
    TUTORIAL("scr_tutorial.png"),
    LEVELSEL1("scr_levelsel1.png"),
    LEVELSEL2("scr_levelsel2.png"),
    GEAR(new Image(Images.class.getClassLoader().getResourceAsStream("resource/images/gui/gear.png"))),
    GEAR2("gear2.png"),
    TIMERS("timers.png"),
    SEL("selection.png"),
    TIMEUP("timeup.png"),
    FINISH("finish.png"),
    STAR1("star1.png"),
    STAR2("star2.png"),
    STAR3("star3.png"),
    STAR4("star4.png");

    private static final List<String> directoryIcon = Arrays.asList("icon1.png", "icon2.png", "icon3.png", "icon4.png", "icon5.png", "icon6.png", "icon7.png", "icon8.png", "icon9.png", "icon10.png");
    private static final List<String> directoryBG = Arrays.asList("bg1.png", "bg2.png", "bg3.png", "bg4.png", "bg5.png", "bg6.png", "bg7.png", "bg8.png", "bg9.png", "bg10.png", "extra1.png", "extra2.png", "extra3.png", "extra4.png", "extra5.png", "extra6.png", "extra7.png", "extra8.png");
    private static Map<String, Image> fileData;
    private final String path;
    private Image img;

    private Images(String path) {
        this.path = path;
    }

    private Images(Image i) {
        this.img = i;
        this.path = null;
    }

    public static void init() {
        Images[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            Images i = var0[var2];
            if (i.path != null) {
                i.img = new Image(Images.class.getClassLoader().getResourceAsStream("resource/images/gui/" + i.path));
            }
        }

        fileData = new HashMap();
        Iterator var4 = directoryBG.iterator();

        String file;
        while(var4.hasNext()) {
            file = (String)var4.next();
            fileData.put(file, new Image(Images.class.getClassLoader().getResourceAsStream("resource/images/bgs/" + file)));
        }

        var4 = directoryIcon.iterator();

        while(var4.hasNext()) {
            file = (String)var4.next();
            fileData.put(file, new Image(Images.class.getClassLoader().getResourceAsStream("resource/images/icons/" + file)));
        }

        System.out.println("Game: Loaded Images");
    }

    public Image get() {
        return this.img;
    }

    public static Image get(String path) {
        return (Image)fileData.get(path);
    }
}
