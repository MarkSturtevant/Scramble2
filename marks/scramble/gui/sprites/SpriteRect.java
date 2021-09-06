//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package marks.scramble.gui.sprites;

import javafx.scene.paint.Color;
import marks.scramble.gui.CanvasUtils;

public class SpriteRect extends Sprite {
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private Color color;
    private boolean outlined;

    public SpriteRect(int depth, double x1, double y1, double x2, double y2, Color c, boolean outlined) {
        super(depth);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = c;
        this.outlined = outlined;
    }

    public boolean overlaps(double x, double y) {
        return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2;
    }

    public void render(int frameSpeed) {
        CanvasUtils.drawRect1(this.x1, this.y1, this.x2, this.y2, this.color);
        if (this.outlined) {
            CanvasUtils.drawRectOutline2(this.x1, this.y1, this.x2 - this.x1, this.y2 - this.y1, Color.BLACK, 15.0D);
        }

    }

    public void translate(double x, double y) {
        this.x1 += x;
        this.x2 += x;
        this.y1 += y;
        this.y2 += y;
    }

    public void relocate(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void changeColor(Color newColor) {
        this.color = newColor;
    }
}
