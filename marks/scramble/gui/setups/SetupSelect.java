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

public class SetupSelect extends Setup {
    private static final double STARTX = 94.0D;
    private static final double STARTY = 203.0D;
    private static final double LENGTH = 139.0D;
    private static final double JUMP = 218.0D;
    private SpriteSelector selBox;
    private SpriteText levelTitle;
    private Bound[] buttons;

    public SetupSelect() {
        this.addSprite(new SpriteBackground(Images.LEVELSEL1.get()));
        this.addSprite(new SpriteGear(3, 0.0D, 0.0D, 80.0D, -120.0D));
        this.addSprite(new SpriteGear(3, 1200.0D, 0.0D, 80.0D, 120.0D));
        this.addSprite(this.selBox = new SpriteSelector(2, 4.0D));
        this.addSprite(this.levelTitle = new SpriteText(1, "", 600.0D, 758.0D, 50.0D, Color.WHITE));
        this.buttons = new Bound[11];

        for(int i = 0; i < Level.values().length; ++i) {
            double lowX = 94.0D + 218.0D * (double)(i / 2);
            double lowY = 203.0D + 218.0D * (double)(i % 2);
            this.buttons[i] = new Bound(lowX, lowY, 139.0D, 139.0D);
            this.addSprite(new SpriteImage(Level.values()[i].getIcon(), lowX, lowY, lowX + 139.0D, lowY + 139.0D, 3));
        }

        this.buttons[10] = new Bound(19.0D, 609.0D, 161.0D, 80.0D);
    }

    public void onClick(double mouseX, double mouseY) {
        for(int i = 0; i < Level.values().length; ++i) {
            if (this.buttons[i].overlaps(mouseX, mouseY)) {
                GUIHandler.setSetup(new SetupSelect2(i + 1));
                break;
            }
        }

        if (this.buttons[10].overlaps(mouseX, mouseY)) {
            GUIHandler.setSetup(new SetupTitle());
        }

    }

    public void update(double mouseX, double mouseY, boolean mousePressed, int frameSpeed) {
        this.selBox.updateBound(this.buttons, mouseX, mouseY);
        boolean hovering = false;

        for(int i = 0; i < Level.values().length; ++i) {
            if (this.buttons[i].overlaps(mouseX, mouseY)) {
                this.levelTitle.setText(Level.values()[i].getTitle());
                hovering = true;
            }
        }

        if (!hovering) {
            this.levelTitle.setText("");
        }

    }

    public void onKeyPress(KeyCode key) {
    }
}
