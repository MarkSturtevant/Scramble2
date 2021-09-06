package marks.scramble.gui.sprites;

import java.util.Random;
import javafx.scene.paint.Color;
import marks.scramble.gui.CanvasUtils;

public class SpriteBank extends Sprite {
    public static final double CENTERX = 1000.0D;
    public static final double CENTERY = 600.0D;
    public static final double MAX_WIDTH = 400.0D;
    public static final double MAX_HEIGHT = 400.0D;
    public static final double MAP_WIDTH = 300.0D;
    public static final double MAP_HEIGHT = 300.0D;
    public static final double R = 150.0D;
    public static final double S = 35.0D;
    private double actWidth;
    private double actHeight;
    private double aspectRatio = 1.0D;
    private char[] letterList;
    private boolean[] used;
    private boolean spinning;
    private double rotationAcc;
    private double rotationSpeed;
    private double maxRotVel;
    private double rot;
    private String input;

    public SpriteBank(int depth, double rotAcc, double maxRotVel) {
        super(depth);
        this.spinning = rotAcc != 0.0D;
        this.rot = this.rotationSpeed = 0.0D;
        this.rotationAcc = rotAcc;
        this.maxRotVel = maxRotVel;
    }

    public boolean overlaps(double x, double y) {
        double newX = this.xRevTransform(x);
        double newY = this.yRevTransform(y);
        return newX >= 0.0D && newX <= 300.0D && newY >= 0.0D && newY <= 300.0D;
    }

    public void render(int frameSpeed) {
        double winWidth = CanvasUtils.getWindowX(400.0D);
        double winHeight = CanvasUtils.getWindowY(400.0D);
        if (winWidth / winHeight > this.aspectRatio) {
            this.actHeight = 400.0D;
            this.actWidth = CanvasUtils.getReferenceX(winHeight * this.aspectRatio);
        } else {
            this.actWidth = 400.0D;
            this.actHeight = CanvasUtils.getReferenceY(winWidth / this.aspectRatio);
        }

        if (this.spinning) {
            this.rot += this.rotationSpeed * (double)frameSpeed;
            this.rotationSpeed = Math.min(this.rotationAcc * (double)frameSpeed + this.rotationSpeed, this.maxRotVel);
        }

        CanvasUtils.drawOval(Color.color(0.0D, 0.0D, 0.0D, 0.7D), this.xTransform(150.0D), this.yTransform(150.0D), this.wTransform(270.0D), this.hTransform(270.0D));
        double incr = 6.283185307179586D / (double)this.letterList.length;
        double ang = -1.5707963267948966D;

        for(int i = 0; i < this.letterList.length; ++i) {
            double l = 0.0D;
            if (Math.abs(Math.sin(ang)) > Math.abs(Math.cos(ang))) {
                l = 150.0D - Math.abs(35.0D / Math.sin(ang));
            } else {
                l = 150.0D - Math.abs(35.0D / Math.cos(ang));
            }

            l *= 0.9166666666666666D;
            if (this.spinning) {
                CanvasUtils.drawTextRotated(this.xTransform(l * Math.cos(ang + this.rot) + 150.0D), this.yTransform(l * Math.sin(ang + this.rot) + 150.0D), this.hTransform(35.0D), "" + this.letterList[i], this.used[i] ? Color.GRAY : Color.WHITE, -1.5707963267948966D - (ang + this.rot));
            } else {
                CanvasUtils.drawTextCentered(this.xTransform(l * Math.cos(ang) + 150.0D), this.yTransform(l * Math.sin(ang) + 150.0D), this.hTransform(35.0D), "" + this.letterList[i], this.used[i] ? Color.GRAY : Color.WHITE);
            }

            ang += incr;
        }

        CanvasUtils.drawRect2(this.xTransform(150.0D - 8.75D * (double)this.input.length()), this.yTransform(-35.0D), this.wTransform(17.5D * (double)this.input.length()), this.hTransform(17.5D), Color.color(0.0D, 0.0D, 0.0D, 0.7D));
        if (!this.spinning) {
            CanvasUtils.drawTextCentered(this.xTransform(150.0D), this.yTransform(-26.25D), this.hTransform(16.153846153846153D), this.input, Color.WHITE);
        } else {
            String gassed = "";

            for(int i = 0; i < this.input.length(); ++i) {
                gassed = gassed + '#';
            }

            CanvasUtils.drawTextCentered(this.xTransform(150.0D), this.yTransform(-26.25D), this.hTransform(16.153846153846153D), gassed, Color.WHITE);
        }

    }

    public double xTransform(double oldX) {
        return oldX / 300.0D * this.actWidth + 1000.0D - this.actWidth / 2.0D;
    }

    public double yTransform(double oldY) {
        return oldY / 300.0D * this.actHeight + 600.0D - this.actHeight / 2.0D;
    }

    public double xRevTransform(double oldX) {
        return 300.0D * ((oldX - 1000.0D) / this.actWidth + 0.5D);
    }

    public double yRevTransform(double oldY) {
        return 300.0D * ((oldY - 600.0D) / this.actHeight + 0.5D);
    }

    public double wTransform(double oldW) {
        return oldW / 300.0D * this.actWidth;
    }

    public double hTransform(double oldH) {
        return oldH / 300.0D * this.actHeight;
    }

    public void shuffle() {
        this.input = "";
        this.used = new boolean[this.letterList.length];
        Random rand = new Random();

        for(int i = 0; i < this.letterList.length; ++i) {
            int randomPosition = rand.nextInt(this.letterList.length);
            char temp = this.letterList[i];
            this.letterList[i] = this.letterList[randomPosition];
            this.letterList[randomPosition] = temp;
        }

    }

    public void setNewList(char[] newList) {
        this.letterList = newList;
        this.shuffle();
    }

    public void addLetter(char letter) {
        int i;
        for(i = 0; i < this.used.length; ++i) {
            if (!this.used[i] && this.letterList[i] == letter) {
                this.used[i] = true;
                this.input = this.input + letter;
                return;
            }
        }

        for(i = 0; i < this.used.length; ++i) {
            if (!this.used[i] && this.letterList[i] == '?') {
                this.used[i] = true;
                this.input = this.input + letter;
                return;
            }
        }

    }

    public void backspace() {
        if (this.input.length() != 0) {
            char c = this.input.charAt(this.input.length() - 1);
            boolean found = false;

            int i;
            for(i = 0; i < this.used.length; ++i) {
                if (this.used[i] && this.letterList[i] == c) {
                    this.used[i] = false;
                    found = true;
                    break;
                }
            }

            if (!found) {
                for(i = 0; i < this.used.length; ++i) {
                    if (this.used[i] && this.letterList[i] == '?') {
                        this.used[i] = false;
                        break;
                    }
                }
            }

            this.input = this.input.substring(0, this.input.length() - 1);
        }
    }

    public String popInput() {
        String output = this.input;
        this.input = "";
        this.used = new boolean[this.letterList.length];
        return output;
    }

    public void translate(double x, double y) {
    }
}
