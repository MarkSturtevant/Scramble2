package marks.scramble.gui.setups;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import marks.scramble.gui.GUIHandler;
import marks.scramble.gui.resources.Images;
import marks.scramble.gui.sprites.Bound;
import marks.scramble.gui.sprites.SpriteBackground;
import marks.scramble.gui.sprites.SpriteGear;
import marks.scramble.gui.sprites.SpriteLine;
import marks.scramble.gui.sprites.SpriteSelector;

public class SetupTitle extends Setup {
    private SpriteSelector selBox;
    private SpriteLine[] lines = new SpriteLine[2];
    private Bound[] buttons;
    private boolean musicOff;
    private boolean soundOff;

    public SetupTitle() {
        this.addSprite(new SpriteBackground(Images.TITLE.get()));
        this.addSprite(this.selBox = new SpriteSelector(2, 4.0D));
        this.addSprite(this.lines[0] = new SpriteLine(1, 1034.0D, 344.0D, 1150.0D, 457.0D, 10.0D, Color.TRANSPARENT));
        this.addSprite(this.lines[1] = new SpriteLine(1, 896.0D, 344.0D, 1009.0D, 457.0D, 10.0D, Color.TRANSPARENT));
        this.addSprite(new SpriteGear(3, 600.0D, 800.0D, 100.0D, -45.0D));
        this.buttons = new Bound[]{new Bound(53.0D, 624.0D, 248.0D, 124.0D), new Bound(53.0D, 474.0D, 248.0D, 124.0D), new Bound(1034.0D, 344.0D, 114.0D, 114.0D), new Bound(896.0D, 344.0D, 114.0D, 114.0D)};
        this.musicOff = !GUIHandler.isMusicOn();
        this.soundOff = !GUIHandler.isSEOn();
    }

    public void onClick(double mouseX, double mouseY) {
        if (this.buttons[0].overlaps(mouseX, mouseY)) {
            GUIHandler.setSetup(new SetupSelect());
        } else if (this.buttons[1].overlaps(mouseX, mouseY)) {
            GUIHandler.setSetup(new SetupTutorial());
        } else if (this.buttons[2].overlaps(mouseX, mouseY)) {
            if (this.musicOff = !this.musicOff) {
                this.lines[0].setColor(Color.RED);
            } else {
                this.lines[0].setColor(Color.TRANSPARENT);
            }

            GUIHandler.toggleAudio(true);
        } else if (this.buttons[3].overlaps(mouseX, mouseY)) {
            if (this.soundOff = !this.soundOff) {
                this.lines[1].setColor(Color.RED);
            } else {
                this.lines[1].setColor(Color.TRANSPARENT);
            }

            GUIHandler.toggleAudio(false);
        }

    }

    public void update(double mouseX, double mouseY, boolean mousePressed, int frameSpeed) {
        this.selBox.updateBound(this.buttons, mouseX, mouseY);
    }

    public void onKeyPress(KeyCode key) {
    }
}
