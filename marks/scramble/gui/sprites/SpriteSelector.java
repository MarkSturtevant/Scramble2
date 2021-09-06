//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package marks.scramble.gui.sprites;

import javafx.scene.image.Image;
import marks.scramble.gui.CanvasUtils;
import marks.scramble.gui.resources.Images;

public class SpriteSelector extends Sprite {
    private static final Image IMG;
    private static final double CONV = 1.3043478260869565D;
    private Bound bound;
    private double rotation;
    private double rotSpeed;

    public SpriteSelector(int depth, Bound activeBound, double period) {
        super(depth);
        this.bound = activeBound;
        this.rotation = 0.0D;
        this.rotSpeed = 0.006283185307179587D / period;
    }

    public SpriteSelector(int depth, double period) {
        super(depth);
        this.bound = null;
        this.rotation = 0.0D;
        this.rotSpeed = 0.006283185307179587D / period;
    }

    public boolean overlaps(double x, double y) {
        return this.bound.overlaps(x, y);
    }

    public void render(int frameSpeed) {
        if (this.bound != null) {
            this.rotation -= (double)frameSpeed * this.rotSpeed;
            double r = (this.bound.getDiameter() + 10.0D) * 1.3043478260869565D;
            double strX = CanvasUtils.getWindowX(1.0D);
            double strY = CanvasUtils.getWindowY(1.0D);
            if (strX > strY) {
                CanvasUtils.drawImageRotated(IMG, this.bound.getCenterX(), this.bound.getCenterY(), r, r * strX / strY, this.rotation);
            } else {
                CanvasUtils.drawImageRotated(IMG, this.bound.getCenterX(), this.bound.getCenterY(), r * strY / strX, r, this.rotation);
            }

        }
    }

    public void updateBound(Bound[] posBounds, double mouseX, double mouseY) {
        Bound[] var6 = posBounds;
        int var7 = posBounds.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Bound b = var6[var8];
            if (b != null && b.overlaps(mouseX, mouseY)) {
                this.bound = b;
                return;
            }
        }

        this.bound = null;
    }

    public void translate(double x, double y) {
    }

    static {
        IMG = Images.SEL.get();
    }
}
