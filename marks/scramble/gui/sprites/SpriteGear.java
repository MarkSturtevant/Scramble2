package marks.scramble.gui.sprites;

import javafx.scene.image.Image;
import marks.scramble.gui.CanvasUtils;
import marks.scramble.gui.resources.Images;

public class SpriteGear extends Sprite {
    private final Image IMG;
    private double cenX;
    private double cenY;
    private double rad;
    private double rotSpeed;
    private double theta;

    public SpriteGear(int depth, double cenX, double cenY, double rad, double rotSpeed) {
        super(depth);
        this.cenX = cenX;
        this.cenY = cenY;
        this.rad = rad * 2.5374149659863945D;
        this.rotSpeed = rotSpeed * 3.141592653589793D / 180.0D / 1000.0D;
        this.theta = 0.0D;
        this.IMG = Images.GEAR.get();
    }

    public SpriteGear(int depth, double cenX, double cenY, double rad, double rotSpeed, int vers) {
        super(depth);
        this.cenX = cenX;
        this.cenY = cenY;
        this.rad = rad * 1.8514851485148514D;
        this.rotSpeed = rotSpeed * 3.141592653589793D / 180.0D / 1000.0D;
        this.theta = 0.0D;
        this.IMG = Images.GEAR2.get();
    }

    public boolean overlaps(double x, double y) {
        return false;
    }

    public void render(int frameSpeed) {
        this.theta += this.rotSpeed * (double)frameSpeed;
        double strX = CanvasUtils.getWindowX(1.0D);
        double strY = CanvasUtils.getWindowY(1.0D);
        if (strX > strY) {
            CanvasUtils.drawImageRotated(this.IMG, this.cenX, this.cenY, this.rad * 2.0D, this.rad * 2.0D * strX / strY, this.theta);
        } else {
            CanvasUtils.drawImageRotated(this.IMG, this.cenX, this.cenY, this.rad * 2.0D * strY / strX, this.rad * 2.0D, this.theta);
        }

    }

    public void translate(double x, double y) {
        this.cenX += x;
        this.cenY += y;
    }

    public void move(double x, double y) {
        this.cenX = x;
        this.cenY = y;
    }
}
