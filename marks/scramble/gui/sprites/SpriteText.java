package marks.scramble.gui.sprites;

import javafx.scene.paint.Color;
import marks.scramble.gui.CanvasUtils;

public class SpriteText extends Sprite {
    private String text;
    private Color color;
    private double cenX;
    private double cenY;
    private double h;
    private boolean centered;

    public SpriteText(int depth, String text, double cenX, double cenY, double h, Color color) {
        super(depth);
        this.cenX = cenX;
        this.cenY = cenY;
        this.color = color;
        this.text = text;
        this.h = h;
        this.centered = true;
    }

    public SpriteText(String text, double leftX, double botY, double h, Color color, int depth) {
        super(depth);
        this.cenX = leftX;
        this.cenY = botY;
        this.color = color;
        this.text = text;
        this.h = h;
        this.centered = false;
    }

    public void setText(String newText) {
        this.text = newText;
    }

    public boolean overlaps(double x, double y) {
        return false;
    }

    public void render(int frameSpeed) {
        if (this.centered) {
            CanvasUtils.drawTextCentered(this.cenX, this.cenY, this.h, this.text, this.color);
        } else {
            CanvasUtils.drawText(this.cenX, this.cenY, this.h, this.text, this.color);
        }

    }

    public void changeColor(Color c) {
        this.color = c;
    }

    public void setHeight(double newHeight) {
        this.h = newHeight;
    }

    public void translate(double x, double y) {
        this.cenX += x;
        this.cenY += y;
    }
}
