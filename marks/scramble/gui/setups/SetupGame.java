package marks.scramble.gui.setups;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import marks.scramble.game.Level;
import marks.scramble.gui.GUIHandler;
import marks.scramble.gui.resources.Images;
import marks.scramble.gui.resources.Sounds;
import marks.scramble.gui.sprites.Bound;
import marks.scramble.gui.sprites.SpriteAniBackground;
import marks.scramble.gui.sprites.SpriteBank;
import marks.scramble.gui.sprites.SpriteGame;
import marks.scramble.gui.sprites.SpriteImage;
import marks.scramble.gui.sprites.SpriteSelector;
import marks.scramble.gui.sprites.SpriteText;

public class SetupGame extends Setup {
    private static final double TIMERX = 875.0D;
    private static final double TIMERY = 0.0D;
    private SpriteBank bank;
    private SpriteGame game;
    private SpriteAniBackground bg;
    private SpriteSelector selBox;
    private Bound[] buttons;
    private SpriteText[] infos;
    private Level level;

    public SetupGame(Level level) {
        this.level = level;
        this.addSprite(this.selBox = new SpriteSelector(5, 4.0D));
        this.buttons = new Bound[]{new Bound(889.0D, 254.0D, 130.0D, 78.0D)};
        this.addSprite(this.bg = new SpriteAniBackground(level.getBG(), level.getLevelID()));
        this.addSprite(new SpriteImage(Images.TIMERS.get(), 875.0D, 0.0D, 1175.0D, 400.0D, 4));
        this.addSprite(new SpriteText(3, "seconds\nleft", 971.0D, 196.0D, 15.0D, Color.BLACK));
        this.addSprite(new SpriteText(3, "hints", 1097.0D, 140.0D, 15.0D, Color.BLACK));
        this.addSprite(new SpriteText(3, "words\nleft", 1073.0D, 276.0D, 15.0D, Color.BLACK));
        this.infos = new SpriteText[3];
        this.addSprite(this.infos[0] = new SpriteText(2, "", 971.0D, 146.0D, 62.0D, Color.BLACK));
        this.addSprite(this.infos[1] = new SpriteText(2, "", 1097.0D, 90.0D, 62.0D, Color.BLACK));
        this.addSprite(this.infos[2] = new SpriteText(2, "", 1065.0D, 239.0D, 40.0D, Color.BLACK));
        level.playMusic();
        this.addSprite(this.bank = new SpriteBank(1, level.getSpinAcc(), level.getSpinMax()));
        this.addSprite(this.game = new SpriteGame(2, level, this.bank));
        this.infos[1].setText("" + this.game.getHintsLeft());
        this.infos[0].setText("" + this.game.getTimeLeft());
        this.infos[2].setText("" + this.game.getWordsLeft());
    }

    public void onClick(double mouseX, double mouseY) {
        if (this.buttons[0].overlaps(mouseX, mouseY)) {
            GUIHandler.setSetup(new SetupLose(this.bg, this.game, this.bank, this.infos));
            this.level.stopMusic();
            Sounds.TIME.play();
        }

    }

    public void update(double mouseX, double mouseY, boolean mousePressed, int frameSpeed) {
        this.selBox.updateBound(this.buttons, mouseX, mouseY);
        int result = this.game.update(frameSpeed);
        switch(result) {
        case 0:
        default:
            break;
        case 1:
            GUIHandler.setSetup(new SetupLose(this.bg, this.game, this.bank, this.infos));
            this.level.stopMusic();
            break;
        case 2:
            GUIHandler.setSetup(new SetupWin(this.level, this.game.getTimeLeft(), this.game.getHintsLeft(), this.bg));
            this.level.stopMusic();
        }

        this.infos[0].setText("" + this.game.getTimeLeft());
    }

    public void onKeyPress(KeyCode key) {
        if (key.isLetterKey()) {
            this.bank.addLetter(key.getName().charAt(0));
        } else if (key.isDigitKey()) {
            this.game.useHint(key.getName().charAt(0) - 49);
            this.infos[1].setText("" + this.game.getHintsLeft());
        } else {
            switch(key) {
            case BACK_SPACE:
                this.bank.backspace();
                break;
            case SPACE:
                this.bank.shuffle();
                break;
            case ENTER:
                this.game.sendWord(this.bank.popInput());
                this.infos[2].setText("" + this.game.getWordsLeft());
            }
        }

    }
}
