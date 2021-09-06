package marks.scramble.gui.sprites;

public class Bound {
    private double x;
    private double y;
    private double w;
    private double h;
    private double d;
    private boolean circular;

    public Bound(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.d = Math.sqrt(width * width + height * height);
        this.circular = false;
    }

    public Bound(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.d = this.w = this.h = radius * 2.0D;
        this.h = 0.0D;
        this.circular = true;
    }

    public boolean overlaps(double testX, double testY) {
        if (this.circular) {
            return Math.sqrt(Math.pow(testX - this.x, 2.0D) + Math.pow(testY - this.y, 2.0D)) <= this.d / 2.0D;
        } else {
            return testX >= this.x && testX <= this.x + this.w && testY >= this.y && testY <= this.y + this.h;
        }
    }

    public double getDiameter() {
        return this.d;
    }

    public double getCenterX() {
        return this.circular ? this.x : this.x + this.w / 2.0D;
    }

    public double getCenterY() {
        return this.circular ? this.y : this.y + this.h / 2.0D;
    }
}
