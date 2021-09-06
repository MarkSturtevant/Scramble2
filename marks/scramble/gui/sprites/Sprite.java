package marks.scramble.gui.sprites;

public abstract class Sprite {
    private int depth;
    protected double x;
    protected double y;
    protected double w;
    protected double h;

    public Sprite(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return this.depth;
    }

    public abstract boolean overlaps(double var1, double var3);

    public abstract void render(int var1);

    public abstract void translate(double var1, double var3);
}
