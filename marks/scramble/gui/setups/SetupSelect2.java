package marks.scramble.gui.setups;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import marks.scramble.game.Level;
import marks.scramble.gui.GUIHandler;
import marks.scramble.gui.resources.Images;
import marks.scramble.gui.sprites.Bound;
import marks.scramble.gui.sprites.SpriteBackground;
import marks.scramble.gui.sprites.SpriteGear;
import marks.scramble.gui.sprites.SpriteImage;
import marks.scramble.gui.sprites.SpriteSelector;
import marks.scramble.gui.sprites.SpriteText;

public class SetupSelect2 extends Setup {
    private Level level;
    private SpriteSelector selBox;
    private SpriteGear diffGear;
    private Bound[] buttons;
    private SpriteText[] ldd;

    public SetupSelect2(int l) {
        this.level = Level.values()[l - 1];
        this.addSprite(new SpriteBackground(Images.LEVELSEL2.get()));
        this.addSprite(this.selBox = new SpriteSelector(2, 4.0D));
        this.addSprite(new SpriteGear(3, 0.0D, 0.0D, 80.0D, -120.0D));
        this.addSprite(new SpriteGear(3, 1200.0D, 0.0D, 80.0D, 120.0D));
        this.addSprite(new SpriteGear(3, 600.0D, 381.5D, 110.0D, 360.0D, 2));
        this.addSprite(this.diffGear = new SpriteGear(1, 0.0D, 0.0D, 23.0D, 10.0D, 2));
        this.addSprite(new SpriteImage(this.level.getIcon(), 530.0D, 312.0D, 669.0D, 451.0D, 3));
        this.buttons = new Bound[]{new Bound(21.0D, 709.0D, 147.0D, 73.0D), new Bound(1030.0D, 709.0D, 147.0D, 73.0D), new Bound(47.0D, 638.0D, 31.0D, 31.0D), new Bound(186.0D, 638.0D, 31.0D, 31.0D), new Bound(325.0D, 638.0D, 31.0D, 31.0D)};
        this.ldd = new SpriteText[]{new SpriteText(3, "", 600.0D, 746.0D, 48.0D, Color.WHITE), new SpriteText("Words", 430.0D, 587.0D, 28.0D, Color.WHITE, 3), new SpriteText("Length", 430.0D, 625.0D, 28.0D, Color.WHITE, 3), new SpriteText("Avg. Length", 430.0D, 663.0D, 28.0D, Color.WHITE, 3), new SpriteText("Dictionary", 810.0D, 587.0D, 28.0D, Color.WHITE, 3), new SpriteText("Seconds", 810.0D, 625.0D, 28.0D, Color.WHITE, 3), new SpriteText("", 810.0D, 663.0D, 28.0D, Color.WHITE, 3)};
        SpriteText[] var2 = this.ldd;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            SpriteText st = var2[var4];
            this.addSprite(st);
        }

        this.setData();
        this.setDifficulty(Level.DIFFICULTY);
    }

    private void setData() {
        this.ldd[0].setText(this.level.getTitle());
        this.ldd[1].setText(this.level.getWordCount() + " Words");
        this.ldd[2].setText(this.level.getMinLength() + "-" + this.level.getMaxLength() + " Length");
        this.ldd[3].setText(String.format("%.1f Avg. Length", (double)this.level.getLetterCount() / (double)this.level.getWordCount()));
        this.ldd[4].setText(this.level.getDictionaryTitle() + " Words");
        this.ldd[5].setText(this.level.getTime() + " Seconds");
        this.ldd[6].setText(this.level.getDescr());
    }

    public void onClick(double mouseX, double mouseY) {
        if (this.buttons[0].overlaps(mouseX, mouseY)) {
            GUIHandler.setSetup(new SetupSelect());
        } else if (this.buttons[1].overlaps(mouseX, mouseY)) {
            GUIHandler.setSetup(new SetupGame(this.level));
        } else {
            for(int i = 2; i <= 4; ++i) {
                if (this.buttons[i].overlaps(mouseX, mouseY)) {
                    this.setDifficulty(i - 2);
                    break;
                }
            }
        }

    }

    private void setDifficulty(int newDiff) {
        Level.DIFFICULTY = newDiff;
        this.diffGear.move(this.buttons[newDiff + 2].getCenterX(), this.buttons[newDiff + 2].getCenterY());
        this.setData();
    }

    public void update(double mouseX, double mouseY, boolean mousePressed, int frameSpeed) {
        this.selBox.updateBound(this.buttons, mouseX, mouseY);
    }

    public void onKeyPress(KeyCode key) {
    }
}
