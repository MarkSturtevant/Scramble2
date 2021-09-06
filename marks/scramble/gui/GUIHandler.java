package marks.scramble.gui;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import marks.scramble.Main;
import marks.scramble.gui.resources.Music;
import marks.scramble.gui.setups.Setup;
import marks.scramble.gui.setups.SetupLoading;
import marks.scramble.gui.setups.SetupTitle;

public class GUIHandler {
    public static double stageW;
    public static double stageH;
    public static double mouseX;
    public static double mouseY;
    private static boolean mousePressed;
    public static GraphicsContext gc;
    private static Setup currentSetup;
    private static AnimationTimer loop;
    private static List<AudioClip> activeMusic;
    private static List<AudioClip> activeSE;
    private static boolean musicOn;
    private static boolean soundEffectsOn;

    public GUIHandler() {
    }

    public static void init(double width, double height, double awidth, double aheight) {
        mouseX = 1.0D;
        mouseY = 1.0D;
        mousePressed = false;
        stageW = awidth;
        stageH = aheight;
        activeMusic = new ArrayList();
        activeSE = new ArrayList();
        soundEffectsOn = true;
        musicOn = true;
        CanvasUtils.setReferences(width, height);
    }

    protected static void initCanvasAndLoop(Stage stage) {
        Pane root = new Pane();
        Scene sc = new Scene(root, stageW, stageH);
        sc.setOnMousePressed((e) -> {
            mousePressed = true;
        });
        sc.setOnMouseReleased((e) -> {
            mousePressed = false;
            currentSetup.onClick(mouseX, mouseY);
        });
        sc.setOnMouseMoved((e) -> {
            mouseX = e.getSceneX() * CanvasUtils.getReferenceWidth() / stageW;
            mouseY = e.getSceneY() * CanvasUtils.getReferenceHeight() / stageH;
        });
        sc.setOnMouseDragged((e) -> {
            mouseX = e.getSceneX() * CanvasUtils.getReferenceWidth() / stageW;
            mouseY = e.getSceneY() * CanvasUtils.getReferenceHeight() / stageH;
        });
        sc.setOnKeyReleased((e) -> {
            currentSetup.onKeyPress(e.getCode());
        });
        GUIHandler.ResizableCanvas canvas = new GUIHandler.ResizableCanvas(stageW, stageH);
        gc = canvas.getGraphicsContext2D();
        sc.widthProperty().addListener((obs, oldVal, newVal) -> {
            stageW = sc.getWidth();
            canvas.setWidth(stage.getWidth());
        });
        sc.heightProperty().addListener((obs, oldVal, newVal) -> {
            stageH = sc.getHeight();
            canvas.setHeight(stage.getHeight());
        });
        root.getChildren().add(canvas);
        currentSetup = new SetupLoading();
        stage.setScene(sc);
        stage.show();
        initTimerLoop();
        (new Thread(GUIHandler::runInitialization)).start();
    }

    public static void runInitialization() {
        Main.initResources();
        currentSetup = new SetupTitle();
        Music.INTRO.play();
    }

    public static void setSetup(Setup csc) {
        currentSetup = csc;
    }

    public static void stop() {
        loop.stop();
    }

    public static void playAudio(AudioClip ac, boolean isMusic) {
        if (isMusic) {
            if (musicOn && !ac.isPlaying()) {
                ac.play();
                if (!activeMusic.contains(ac)) {
                    activeMusic.add(ac);
                }
            }
        } else if (soundEffectsOn) {
            ac.play();
            if (!activeSE.contains(ac)) {
                activeSE.add(ac);
            }
        }

    }

    public static void stopAudio(AudioClip ac, boolean isMusic) {
        if (ac.isPlaying()) {
            ac.stop();
        }

        if (isMusic) {
            if (activeMusic.contains(ac)) {
                activeMusic.remove(ac);
            }
        } else if (activeSE.contains(ac)) {
            activeSE.remove(ac);
        }

    }

    public static void toggleAudio(boolean isMusic) {
        if (isMusic) {
            if (musicOn) {
                while(activeMusic.size() > 0) {
                    stopAudio((AudioClip)activeMusic.get(0), true);
                }
            }

            musicOn = !musicOn;
        } else {
            if (soundEffectsOn) {
                while(activeSE.size() > 0) {
                    stopAudio((AudioClip)activeSE.get(0), false);
                }
            }

            soundEffectsOn = !soundEffectsOn;
        }

    }

    public static boolean isMusicOn() {
        return musicOn;
    }

    public static boolean isSEOn() {
        return soundEffectsOn;
    }

    private static void initTimerLoop() {
        loop = new AnimationTimer() {
            long timein = System.currentTimeMillis();
            int frameSpeed = 1;

            public void handle(long now) {
                this.frameSpeed = (int)(System.currentTimeMillis() - this.timein);
                this.timein = System.currentTimeMillis();
                GUIHandler.currentSetup.onUpdate(GUIHandler.mouseX, GUIHandler.mouseY, GUIHandler.mousePressed, this.frameSpeed);
            }
        };
        loop.start();
    }

    private static class ResizableCanvas extends Canvas {
        private ResizableCanvas(double width, double height) {
            super(width, height);
        }

        public boolean isResizable() {
            return true;
        }

        public double prefWidth(double height) {
            return this.getWidth();
        }

        public double prefHeight(double width) {
            return this.getHeight();
        }
    }
}
