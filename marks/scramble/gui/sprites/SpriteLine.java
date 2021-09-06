package marks.scramble.gui.sprites;

import javafx.scene.paint.Color;
import marks.scramble.gui.CanvasUtils;

public class SpriteLine extends Sprite {
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double thickness;
    private Color color;

    public SpriteLine(int depth, double x1, double y1, double x2, double y2, double thickness, Color c) {
        super(depth);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.thickness = thickness;
        this.color = c;
    }

    public boolean overlaps(double x, double y) {
        return false;
    }

    public void render(int frameSpeed) {
        CanvasUtils.drawLine(this.x1, this.y1, this.x2, this.y2, this.thickness, this.color);
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public void translate(double x, double y) {
        this.x1 += x;
        this.x2 += x;
        this.y1 += y;
        this.y2 += y;
    }
}
