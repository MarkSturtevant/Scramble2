package marks.scramble.gui.sprites;

import javafx.scene.paint.Color;
import marks.scramble.gui.CanvasUtils;

public class SpritePoly extends Sprite {
    private Color c;
    private double[] points;

    public SpritePoly(int depth, double[] points, Color c) {
        super(depth);
        this.points = points;
        this.c = c;
    }

    public void setColor(Color c) {
        this.c = c;
    }

    public boolean overlaps(double x, double y) {
        return false;
    }

    public void render(int frameSpeed) {
        CanvasUtils.drawPolygon(this.c, this.points);
    }

    public void translate(double x, double y) {
    }
}
