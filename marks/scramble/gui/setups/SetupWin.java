package marks.scramble.gui.setups;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import marks.scramble.game.Level;
import marks.scramble.gui.CanvasUtils;
import marks.scramble.gui.GUIHandler;
import marks.scramble.gui.resources.Images;
import marks.scramble.gui.resources.Music;
import marks.scramble.gui.resources.Sounds;
import marks.scramble.gui.sprites.SpriteAniBackground;
import marks.scramble.gui.sprites.SpriteGear;
import marks.scramble.gui.sprites.SpriteLine;
import marks.scramble.gui.sprites.SpritePoly;
import marks.scramble.gui.sprites.SpriteText;

public class SetupWin extends Setup {
    private static final Color[] rainbowColors = new Color[]{Color.rgb(236, 64, 52), Color.rgb(235, 174, 52), Color.rgb(242, 228, 29), Color.rgb(49, 209, 38), Color.rgb(38, 126, 209), Color.rgb(141, 46, 219), Color.rgb(219, 46, 133)};
    private int nextreveal;
    private int totalTime;
    private int flashTime;
    private SpriteText[] text;
    private SpriteLine[] lines;
    private double[] poly;
    private SpritePoly polySprite;
    private List<StarSprite> stars;

    public SetupWin(Level l, int timeLeft, int hintsLeft, SpriteAniBackground bg) {
        Music.WIN.play();
        this.flashTime = 0;
        this.stars = new ArrayList();

        for(int i = 0; i < 10; ++i) {
            this.stars.add(new StarSprite(true, 980.0D, (double)(320 + 64 * i), 128));
            this.stars.add(new StarSprite(false, 220.0D, (double)(320 + 64 * i), 128));
        }

        bg.setFrozen(true);
        this.nextreveal = 0;
        this.totalTime = 0;
        this.addSprite(bg);
        this.addSprite(new SpriteGear(3, 0.0D, 800.0D, 120.0D, -270.0D));
        this.addSprite(new SpriteGear(3, 1200.0D, 0.0D, 120.0D, 270.0D));
        this.addSprite(this.polySprite = new SpritePoly(3, this.poly = new double[]{0.0D, 0.0D, 800.0D, 0.0D, 1200.0D, 0.0D, 400.0D, 0.0D}, Color.rgb(255, 255, 255, 0.6D)));
        this.lines = new SpriteLine[2];
        this.addSprite(this.lines[0] = new SpriteLine(2, 0.0D, 0.0D, 400.0D, 800.0D, 10.0D, Color.BLACK));
        this.addSprite(this.lines[1] = new SpriteLine(2, 800.0D, 0.0D, 1200.0D, 800.0D, 10.0D, Color.BLACK));
        this.text = new SpriteText[7];
        this.text[0] = new SpriteText(1, "Level", this.x(1.4D), this.y(1.8D), this.y(1.1D) / 2.0D, Color.rgb(66, 135, 245));
        this.text[1] = new SpriteText(1, l.getTitle(), this.x(3.0D), this.y(3.0D), this.y(2.0D) / 2.0D, Color.rgb(19, 72, 158));
        this.text[2] = new SpriteText(1, "Complete!", this.x(4.6D), this.y(4.2D), this.y(1.1D) / 2.0D, Color.rgb(66, 135, 245));
        this.text[3] = new SpriteText(1, timeLeft + " Seconds Left", this.x(6.0D), this.y(6.0D), this.y(1.1D) / 2.0D, Color.rgb(45, 145, 33));
        this.text[4] = new SpriteText(1, hintsLeft + " Hints Left", this.x(8.0D), this.y(8.0D), this.y(1.1D) / 2.0D, Color.rgb(133, 94, 27));
        String dif = "";

        for(int i = 0; i < Level.DIFFICULTY + 1; ++i) {
            dif = dif + '✯';
        }

        dif = dif + " / ✯✯✯ Difficulty";
        this.text[5] = new SpriteText(1, dif, this.x(10.0D), this.y(10.0D), this.y(1.1D) / 2.0D, Color.rgb(166, 22, 15));
        this.text[6] = new SpriteText(1, this.getSpecialMessage(Level.DIFFICULTY), this.x(12.0D), this.y(12.0D), this.y(1.1D) / 2.0D, Color.HOTPINK);
    }

    public void onClick(double mouseX, double mouseY) {
        if (this.totalTime > 3000) {
            Music.WIN.stop();
            GUIHandler.setSetup(new SetupTitle());
        }

    }

    public void update(double mouseX, double mouseY, boolean mousePressed, int frameSpeed) {
        if (this.flashTime > 0) {
            this.flashTime = Math.max(0, this.flashTime - frameSpeed);
            this.polySprite.setColor(Color.rgb(255, 255, 255, 0.3D * (double)this.flashTime / 200.0D + 0.6D));
        }

        for(int i = this.stars.size() - 1; i >= 0; --i) {
            if (!((StarSprite)this.stars.get(i)).drawAndUpdate(frameSpeed)) {
                this.stars.remove(i);
            }
        }

        this.totalTime += frameSpeed;
        this.text[6].setHeight(this.y(0.8D + 0.1D * Math.sin((double)this.totalTime / 500.0D * 3.141592653589793D)) / 2.0D);
        if (Level.DIFFICULTY == 2) {
            this.lines[0].setColor(rainbowColors[this.totalTime / 50 % 7]);
            this.lines[1].setColor(rainbowColors[this.totalTime / 50 % 7]);
        }

        switch(this.nextreveal) {
        case 0:
            if (this.totalTime < 520) {
                this.poly[5] = this.poly[7] = (double)this.totalTime / 500.0D * 800.0D;
                this.poly[4] = (double)this.totalTime / 500.0D * 400.0D + 800.0D;
                this.poly[6] = (double)this.totalTime / 500.0D * 400.0D;
            }

            if (this.totalTime < 1500) {
                CanvasUtils.drawImage3(Images.FINISH.get(), 600.0D, 400.0D, 800.0D, 640.0D);
            }

            if (this.totalTime >= 2500) {
                Sounds.WIN2.play();
                this.flashTime = 200;
                this.addSprite(this.text[0]);
                this.addSprite(this.text[1]);
                this.addSprite(this.text[2]);
                this.stars.add(new StarSprite(false, this.x(0.0D), this.y(1.8D)));
                this.stars.add(new StarSprite(false, this.x(0.0D), this.y(4.2D)));
                this.stars.add(new StarSprite(true, this.x(8.0D), this.y(1.8D)));
                this.stars.add(new StarSprite(true, this.x(8.0D), this.y(4.2D)));
                ++this.nextreveal;
            }
            break;
        case 1:
            if (this.totalTime >= 3500) {
                Sounds.WIN3.play();
                this.flashTime = 200;
                this.addSprite(this.text[3]);
                this.stars.add(new StarSprite(true, this.x(14.0D), this.y(6.0D)));
                this.stars.add(new StarSprite(false, this.x(-2.0D), this.y(6.0D)));
                ++this.nextreveal;
            }
            break;
        case 2:
            if (this.totalTime >= 4500) {
                Sounds.WIN4.play();
                this.flashTime = 200;
                this.addSprite(this.text[4]);
                this.stars.add(new StarSprite(true, this.x(16.0D), this.y(8.0D)));
                this.stars.add(new StarSprite(false, this.x(0.0D), this.y(8.0D)));
                ++this.nextreveal;
            }
            break;
        case 3:
            if (this.totalTime >= 5500) {
                Sounds.WIN5.play();
                this.flashTime = 200;
                this.addSprite(this.text[5]);
                this.stars.add(new StarSprite(true, this.x(18.0D), this.y(10.0D)));
                this.stars.add(new StarSprite(false, this.x(2.0D), this.y(10.0D)));
                ++this.nextreveal;
            }
            break;
        case 4:
            if (this.totalTime >= 6500) {
                Sounds.WIN6.play();
                this.flashTime = 200;
                this.addSprite(this.text[6]);
                this.stars.add(new StarSprite(true, this.x(20.0D), this.y(12.0D)));
                this.stars.add(new StarSprite(false, this.x(4.0D), this.y(12.0D)));
                ++this.nextreveal;
            }
            break;
        default:
            if ((this.totalTime - 6500) / 250 > this.nextreveal - 4) {
                ++this.nextreveal;
                this.stars.add(new StarSprite(true, this.x(20.0D), this.y(12.0D)));
                this.stars.add(new StarSprite(false, this.x(4.0D), this.y(12.0D)));
                this.stars.add(new StarSprite(false, this.x(-5.0D), this.y(3.0D)));
                this.stars.add(new StarSprite(true, this.x(13.0D), this.y(3.0D)));
            }
        }

    }

    public void onKeyPress(KeyCode key) {
        if (this.totalTime > 3000 && key == KeyCode.ENTER) {
            GUIHandler.setSetup(new SetupTitle());
            Music.WIN.stop();
        }

    }

    private String getSpecialMessage(int diff) {
        Random rand = new Random();
        switch(diff) {
        case 0:
            return (new String[]{"Nice Work!", "Piece of Cake!", "You did it!", "Beautiful!", "Not Bad!", "Alright, good start!", "You got this!", "Warm-up?"})[rand.nextInt(8)];
        case 1:
            return (new String[]{"Working our way up!", "Nothing's stopping you.", "Clean!", "Alright, next attempt.", "Great Job!", "Snatched that Victory!", "Awesome!"})[rand.nextInt(7)];
        case 2:
            return (new String[]{"Difficulty 3?!", "That was absurd.", "Insane skills!", "Holy cow!!!", "You weren't supposed to beat that.", "Unbelievably good!", "Absolutely Perfect."})[rand.nextInt(7)];
        default:
            return "null";
        }
    }

    private double x(double t) {
        return 400.0D + 28.571428571428573D * t;
    }

    private double y(double t) {
        return 57.142857142857146D * t;
    }
}