package marks.scramble.gui.sprites;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import marks.scramble.gui.CanvasUtils;

public class SpriteBackground extends Sprite {
    private Sprite obj;

    public SpriteBackground(Image img) {
        super(100000);
        this.obj = new SpriteImage(img, 0.0D, 0.0D, CanvasUtils.getReferenceWidth(), CanvasUtils.getReferenceHeight(), 1000000);
    }

    public SpriteBackground(Color color) {
        super(100000);
        this.obj = new SpriteRect(1000000, 0.0D, 0.0D, CanvasUtils.getReferenceWidth(), CanvasUtils.getReferenceHeight(), color, false);
    }

    public boolean overlaps(double x, double y) {
        return true;
    }

    public void render(int frameSpeed) {
        this.obj.render(frameSpeed);
    }

    public void translate(double x, double y) {
        System.out.println("Warning: Attempting to move a Background Sprite.  Operation cancelled");
    }
}
