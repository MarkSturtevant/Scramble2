package marks.scramble.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class GUIWindow extends Application {
    private static Stage stg;
    private static String title;
    private static double width;
    private static double height;
    private static double awidth;
    private static double aheight;

    public GUIWindow() {
    }

    public static void launchGUI(String stageTitle) {
        title = stageTitle;
        width = 1200.0D;
        height = 800.0D;
        awidth = 900.0D;
        aheight = 600.0D;
        launch(new String[0]);
    }

    public static void launchGUI(String stageTitle, double width_, double height_, double appWidth, double appHeight) {
        title = stageTitle;
        width = width_;
        height = height_;
        awidth = appWidth;
        aheight = appHeight;
        launch(new String[0]);
    }

    public void start(Stage arg0) throws Exception {
        stg = arg0;
        stg.setTitle(title);
        GUIHandler.init(width, height, awidth, aheight);
        GUIHandler.initCanvasAndLoop(stg);
    }

    public void stop() {
        System.out.println("Shutting Down.");
        GUIHandler.stop();
    }
}
