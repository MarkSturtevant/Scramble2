package marks.scramble.gui.sprites;

import javafx.scene.image.Image;
import marks.scramble.gui.CanvasUtils;

public class SpriteImage extends Sprite {
    protected Image img;
    protected double centerX;
    protected double centerY;
    protected double width;
    protected double height;

    public SpriteImage(int depth, Image img, double centerX, double centerY) {
        super(depth);
        this.img = img;
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    public SpriteImage(int depth, Image img, double centerX, double centerY, double width, double height) {
        super(depth);
        this.img = img;
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
    }

    public SpriteImage(Image img, double x1, double y1, double x2, double y2, int depth) {
        super(depth);
        this.img = img;
        this.centerX = (x1 + x2) / 2.0D;
        this.centerY = (y1 + y2) / 2.0D;
        this.width = x2 - x1;
        this.height = y2 - y1;
    }

    public void setImage(Image newImage) {
        this.img = newImage;
    }

    public void render(int frameSpeed) {
        CanvasUtils.drawImage3(this.img, this.centerX, this.centerY, this.width, this.height);
    }

    public boolean overlaps(double x, double y) {
        return x >= this.centerX - this.width / 2.0D && x <= this.centerX + this.width / 2.0D && y >= this.centerY - this.height / 2.0D && y <= this.centerY + this.height / 2.0D;
    }

    public void translate(double x, double y) {
        this.centerX += x;
        this.centerY += y;
    }
}
