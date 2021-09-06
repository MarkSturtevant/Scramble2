package marks.scramble.gui.setups;

import java.util.Random;
import marks.scramble.gui.CanvasUtils;
import marks.scramble.gui.resources.Images;

class StarSprite {
    private int SIZE;
    double posX;
    double posY;
    double velX;
    double velY;
    double accY;
    double rot;
    double rotV;
    int variant;

    public StarSprite(boolean right, double x, double y) {
        Random rand = new Random();
        this.SIZE = 64;
        this.posX = x;
        this.posY = y;
        this.accY = 0.007D;
        this.velX = (rand.nextDouble() * 0.6D + 0.2D) * (double)(right ? 1 : -1);
        this.velY = -1.0D * (rand.nextDouble() * 0.2D + 0.05D) - 1.0D;
        this.variant = rand.nextInt(4);
        this.rot = rand.nextDouble() * 2.0D * 3.141592653589793D;
        this.rotV = (rand.nextDouble() * 3.5D * 3.141592653589793D + 1.5707963267948966D) / 1000.0D * (double)(rand.nextBoolean() ? -1 : 1);
    }

    public StarSprite(boolean right, double x, double y, int SIZE) {
        this(right, x, y);
        this.SIZE = SIZE;
    }

    public boolean drawAndUpdate(int frameSpeed) {
        this.velY += this.accY * (double)frameSpeed;
        this.posX += this.velX * (double)frameSpeed;
        this.posY += this.velY * (double)frameSpeed;
        this.rot += this.rotV * (double)frameSpeed;
        if (this.posX >= (double)(-this.SIZE / 2) && this.posX <= (double)(1200 + this.SIZE / 2) && this.posY <= (double)(800 + this.SIZE / 2)) {
            Images img = Images.STAR1;
            switch(this.variant) {
            case 1:
                img = Images.STAR2;
                break;
            case 2:
                img = Images.STAR3;
                break;
            case 3:
                img = Images.STAR4;
            }

            CanvasUtils.drawImageRotated(img.get(), this.posX, this.posY, (double)this.SIZE, (double)this.SIZE, this.rot);
            return true;
        } else {
            return false;
        }
    }
}
