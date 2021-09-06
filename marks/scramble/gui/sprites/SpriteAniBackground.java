package marks.scramble.gui.sprites;

import java.util.function.Consumer;
import javafx.scene.image.Image;
import marks.scramble.gui.CanvasUtils;

public class SpriteAniBackground extends Sprite {
    private boolean frozen;
    private int totalTime = 0;
    private Image bgBase;
    private Consumer<Integer> method;

    public SpriteAniBackground(Image bgBase, int bgID) {
        super(99999);
        this.bgBase = bgBase;
        this.method = AniBGMethods.getMethod(bgID);
        this.frozen = false;
        AniBGMethods.counter = 0;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean overlaps(double x, double y) {
        return true;
    }

    public void render(int frameSpeed) {
        CanvasUtils.drawImage2(this.bgBase, 0.0D, 0.0D, CanvasUtils.getReferenceWidth(), CanvasUtils.getReferenceHeight());
        if (!this.frozen) {
            this.totalTime += frameSpeed;
        }

        this.method.accept(this.totalTime);
    }

    public void translate(double x, double y) {
    }
}
