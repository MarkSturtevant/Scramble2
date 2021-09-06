package marks.scramble.gui.setups;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import marks.scramble.gui.CanvasUtils;
import marks.scramble.gui.GUIHandler;
import marks.scramble.gui.resources.Images;
import marks.scramble.gui.resources.Sounds;
import marks.scramble.gui.sprites.SpriteAniBackground;
import marks.scramble.gui.sprites.SpriteBank;
import marks.scramble.gui.sprites.SpriteGame;
import marks.scramble.gui.sprites.SpriteImage;
import marks.scramble.gui.sprites.SpriteText;

public class SetupLose extends Setup {
    private static final double TIMERX = 875.0D;
    private static final double TIMERY = 0.0D;
    private int timePassed = 0;
    private boolean addedText = false;
    private SpriteGame game;

    public SetupLose(SpriteAniBackground bg, SpriteGame game, SpriteBank bank, SpriteText[] infos) {
        Sounds.LOST.play();
        game.setWord();
        infos[0].setText("0");
        bg.setFrozen(true);
        this.addSprite(bg);
        this.addSprite(this.game = game);
        this.addSprite(bank);
        SpriteText[] var5 = infos;
        int var6 = infos.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            SpriteText st = var5[var7];
            this.addSprite(st);
        }

        this.addSprite(new SpriteImage(Images.TIMERS.get(), 875.0D, 0.0D, 1175.0D, 400.0D, 4));
        this.addSprite(new SpriteText(3, "seconds\nleft", 971.0D, 196.0D, 15.0D, Color.BLACK));
        this.addSprite(new SpriteText(3, "hints", 1097.0D, 140.0D, 15.0D, Color.BLACK));
        this.addSprite(new SpriteText(3, "words\nleft", 1073.0D, 276.0D, 15.0D, Color.BLACK));
    }

    public void onClick(double mouseX, double mouseY) {
        if (this.timePassed > 3000) {
            GUIHandler.setSetup(new SetupTitle());
        }

    }

    public void update(double mouseX, double mouseY, boolean mousePressed, int frameSpeed) {
        this.timePassed += frameSpeed;
        CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(255, 0, 0, 0.33D));
        if (this.timePassed < 1000) {
            double ANG = 0.2617993877991494D;
            double TIME = 50.0D;
            boolean back = (double)this.timePassed / TIME % 2.0D == 1.0D;
            double angle = (double)this.timePassed % TIME * 2.0D * ANG / TIME * (double)(back ? -1 : 1) + (back ? ANG : -ANG);
            CanvasUtils.drawImageRotated(Images.TIMEUP.get(), 600.0D, 400.0D, 800.0D, 640.0D, angle);
        }

        if (this.timePassed > 2000) {
            this.game.reveal((this.timePassed - 2000) / 250);
        }

        if (this.timePassed > 4000 && !this.addedText) {
            this.addedText = true;
            this.addSprite(new SpriteText(1, "Click anywhere to continue", 600.0D, 750.0D, 30.0D, Color.DARKRED));
        }

    }

    public void onKeyPress(KeyCode key) {
        if (this.timePassed > 3000 && key == KeyCode.ENTER) {
            GUIHandler.setSetup(new SetupTitle());
        }

    }
}
