package marks.scramble.gui.sprites;

import javafx.scene.paint.Color;
import marks.scramble.game.Level;
import marks.scramble.gui.CanvasUtils;

class Letter {
    public static final double USE_RATIO = 0.8D;
    public int gridX;
    public int gridY;
    public char letter;
    public Color tileColor;
    public Color letterColor;
    private long appTime;
    private long dieTime;

    public Letter(int gridX, int gridY, char letter, Color tileColor, Color letterColor, int appTime) {
        this.gridX = gridX;
        this.gridY = gridY;
        if (letter == '-') {
            this.letter = ' ';
        } else {
            this.letter = letter;
        }

        this.tileColor = tileColor;
        this.letterColor = letterColor;
        this.appTime = (long)appTime + System.currentTimeMillis();
        this.dieTime = 9223372036854775807L;
    }

    public void setDieTime(int newTime) {
        this.dieTime = (long)newTime + System.currentTimeMillis();
    }

    public boolean here(int x, int y) {
        return this.gridX == x && this.gridY == y && this.dieTime == 9223372036854775807L;
    }

    public void setChar(char newChar) {
        if (newChar == '-') {
            this.letter = ' ';
        } else {
            this.letter = newChar;
        }

    }

    public boolean display(SpriteGame transform, Level colorData) {
        long time = System.currentTimeMillis();
        if (time >= this.dieTime) {
            return false;
        } else if (time < this.appTime) {
            return true;
        } else {
            CanvasUtils.drawRect2(transform.xTransform((double)this.gridX), transform.yTransform((double)this.gridY), transform.wTransform(0.8D), transform.hTransform(0.8D), colorData.getBoxColor());
            CanvasUtils.drawTextCentered(transform.xTransform((double)this.gridX + 0.4D), transform.yTransform((double)this.gridY + 0.4D), transform.hTransform(0.6666666666666666D), "" + this.letter, colorData.getLetterColor());
            return true;
        }
    }
}
