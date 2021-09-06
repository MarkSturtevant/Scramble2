package marks.scramble.gui.setups;

import javafx.scene.input.KeyCode;
import marks.scramble.gui.resources.Images;
import marks.scramble.gui.sprites.SpriteBackground;
import marks.scramble.gui.sprites.SpriteGear;

public class SetupLoading extends Setup {
    public SetupLoading() {
        this.addSprite(new SpriteBackground(Images.LOADING.get()));
        this.addSprite(new SpriteGear(2, 600.0D, 500.0D, 70.0D, -180.0D));
    }

    public void onClick(double mouseX, double mouseY) {
    }

    public void update(double mouseX, double mouseY, boolean mousePressed, int frameSpeed) {
    }

    public void onKeyPress(KeyCode key) {
    }
}
