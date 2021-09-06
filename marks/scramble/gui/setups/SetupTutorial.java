package marks.scramble.gui.setups;

import javafx.scene.input.KeyCode;
import marks.scramble.gui.GUIHandler;
import marks.scramble.gui.resources.Images;
import marks.scramble.gui.sprites.Bound;
import marks.scramble.gui.sprites.SpriteBackground;
import marks.scramble.gui.sprites.SpriteGear;
import marks.scramble.gui.sprites.SpriteSelector;

public class SetupTutorial extends Setup {
    private SpriteSelector selBox;
    private Bound[] buttons;

    public SetupTutorial() {
        this.addSprite(new SpriteBackground(Images.TUTORIAL.get()));
        this.addSprite(this.selBox = new SpriteSelector(2, 4.0D));
        this.addSprite(new SpriteGear(3, 0.0D, 0.0D, 50.0D, -90.0D));
        this.addSprite(new SpriteGear(3, 1200.0D, 0.0D, 50.0D, 90.0D));
        this.buttons = new Bound[]{new Bound(919.0D, 637.0D, 229.0D, 114.0D)};
    }

    public void onClick(double mouseX, double mouseY) {
        if (this.buttons[0].overlaps(mouseX, mouseY)) {
            GUIHandler.setSetup(new SetupTitle());
        }

    }

    public void update(double mouseX, double mouseY, boolean mousePressed, int frameSpeed) {
        this.selBox.updateBound(this.buttons, mouseX, mouseY);
    }

    public void onKeyPress(KeyCode key) {
    }
}
