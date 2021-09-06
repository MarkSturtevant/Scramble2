package marks.scramble.gui.sprites;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import marks.scramble.game.Game;
import marks.scramble.game.Level;
import marks.scramble.game.Word;
import marks.scramble.gui.CanvasUtils;
import marks.scramble.gui.resources.Sounds;

public class SpriteGame extends Sprite {
    public static final double CENTERX = 400.0D;
    public static final double CENTERY = 450.0D;
    public static final double MAX_WIDTH = 800.0D;
    public static final double MAX_HEIGHT = 700.0D;
    public static final double ICON_SIZE = 75.0D;
    public static final double MAP_WIDTH = 10.666666666666666D;
    public static final double MAP_HEIGHT = 9.333333333333334D;
    public static final double MOVE_VEL = 0.005D;
    public static final int ANI_DELAY = 83;
    private double actWidth;
    private double actHeight;
    private double focusX = -5.333333333333333D;
    private double focusY = -4.666666666666667D;
    private double aspectRatio = 1.1428571428571428D;
    private double dfocusX;
    private double dfocusY;
    private Game game;
    private Level l;
    private List<Letter> letters;
    private Word[] playWords;
    private SpriteBank bank;
    private int timeRemaining;
    private int timeToNextHint;
    private double timeBonusA;
    private boolean autohints;

    public SpriteGame(int depth, Level level, SpriteBank bank) {
        super(depth);
        this.dfocusX = this.dfocusY = 0.0D;
        this.timeRemaining = level.getTime() * 1000;
        this.bank = bank;
        this.l = level;
        this.autohints = level.getAutoHints() != 0;
        this.timeToNextHint = level.getAutoHints();
        this.timeBonusA = level.getTimeBonus();
        this.game = new Game(level);
        this.initStart();
    }

    public boolean overlaps(double x, double y) {
        return false;
    }

    public void render(int frameSpeed) {
        double winWidth = CanvasUtils.getWindowX(800.0D);
        double winHeight = CanvasUtils.getWindowY(700.0D);
        if (winWidth / winHeight > this.aspectRatio) {
            this.actHeight = 700.0D;
            this.actWidth = CanvasUtils.getReferenceX(winHeight * this.aspectRatio);
        } else {
            this.actWidth = 800.0D;
            this.actHeight = CanvasUtils.getReferenceY(winWidth / this.aspectRatio);
        }

        double dis = Math.sqrt(this.dfocusX * this.dfocusX + this.dfocusY * this.dfocusY);
        if (dis > 0.0D) {
            double delta = (double)frameSpeed * 0.005D;
            if (delta > dis) {
                delta = dis;
            }

            this.focusX += this.dfocusX / dis * delta;
            this.dfocusX -= this.dfocusX / dis * delta;
            this.focusY += this.dfocusY / dis * delta;
            this.dfocusY -= this.dfocusY / dis * delta;
        }

        for(int i = this.letters.size() - 1; i >= 0; --i) {
            if (!((Letter)this.letters.get(i)).display(this, this.l)) {
                this.letters.remove(i);
            }
        }

        if (this.autohints && (this.timeToNextHint -= frameSpeed) < 0) {
            this.useHint(-1);
            this.timeToNextHint = this.l.getAutoHints();
        }

    }

    public double xTransform(double oldX) {
        return (oldX - this.focusX) / 10.666666666666666D * this.actWidth + 400.0D - this.actWidth / 2.0D;
    }

    public double yTransform(double oldY) {
        return (oldY - this.focusY) / 9.333333333333334D * this.actHeight + 450.0D - this.actHeight / 2.0D;
    }

    public double wTransform(double oldW) {
        return oldW / 10.666666666666666D * this.actWidth;
    }

    public double hTransform(double oldH) {
        return oldH / 9.333333333333334D * this.actHeight;
    }

    public void translate(double x, double y) {
    }

    private void initStart() {
        this.letters = new ArrayList();
        this.playWords = new Word[2];
        this.playWords[0] = this.game.getNextWord(false);
        this.playWords[1] = this.game.getNextWord(true);
        this.addWord(this.playWords[0]);
        this.addWord(this.playWords[1]);
        this.bank.setNewList(this.game.getLetters());
    }

    public void sendWord(String sequence) {
        if (this.game.testWord(sequence)) {
            Sounds.CORRECT.play();
            Word toRemove = this.playWords[0];
            this.playWords[0] = this.playWords[1];
            this.playWords[1] = this.game.getNextWord(true);
            this.removeWord(toRemove);
            this.fillWord(this.playWords[0], sequence);
            if (this.game.ended()) {
                System.out.println("GAME ENDED!");
            } else {
                this.timeToNextHint = this.l.getAutoHints();
                this.timeRemaining = (int)((double)this.timeRemaining + this.timeBonusA * Math.pow((double)this.playWords[0].getWord().length(), 2.0D));
                this.addWord(this.playWords[1]);
                this.bank.setNewList(this.game.getLetters());
            }
        }
    }

    public void useHint(int index) {
        if (this.game.useHint(index)) {
            int x = this.playWords[1].getX();
            int y = this.playWords[1].getY();

            for(int i = 0; i < this.playWords[1].getCharArray().length; ++i) {
                Iterator var5 = this.letters.iterator();

                while(var5.hasNext()) {
                    Letter l = (Letter)var5.next();
                    if (l.here(x, y)) {
                        l.setChar(this.playWords[1].getCharArray()[i]);
                    }
                }

                if (this.playWords[1].isHorizontal()) {
                    ++x;
                } else {
                    ++y;
                }
            }

        }
    }

    private void fillWord(Word w, String sequence) {
        int x = w.getX();
        int y = w.getY();

        for(int i = 0; i < w.getCharArray().length; ++i) {
            Iterator var6 = this.letters.iterator();

            while(var6.hasNext()) {
                Letter l = (Letter)var6.next();
                if (l.here(x, y)) {
                    l.setChar(sequence.charAt(i));
                }
            }

            if (w.isHorizontal()) {
                ++x;
            } else {
                ++y;
            }
        }

    }

    private void addWord(Word w) {
        int x = w.getX();
        int y = w.getY();
        char[] chars = w.getCharArray();

        for(int i = 0; i < chars.length; ++i) {
            boolean open = true;
            Iterator var7 = this.letters.iterator();

            while(var7.hasNext()) {
                Letter l = (Letter)var7.next();
                if (l.here(x, y)) {
                    open = false;
                    break;
                }
            }

            if (open) {
                this.letters.add(new Letter(x, y, chars[i], this.l.getBoxColor(), this.l.getLetterColor(), i * 83));
            }

            if (w.isHorizontal()) {
                ++x;
            } else {
                ++y;
            }
        }

        if (w.isHorizontal()) {
            this.dfocusX += -5.333333333333333D + (double)w.getX() + (double)chars.length / 2.0D - (this.focusX + this.dfocusX);
            this.dfocusY += -4.666666666666667D + (double)w.getY() + 0.5D - (this.focusY + this.dfocusY);
        } else {
            this.dfocusX += -5.333333333333333D + (double)w.getX() + 0.5D - (this.focusX + this.dfocusX);
            this.dfocusY += -4.666666666666667D + (double)w.getY() + (double)chars.length / 2.0D - (this.focusY + this.dfocusY);
        }

    }

    private void removeWord(Word w) {
        int x = w.getX();
        int y = w.getY();
        Word prev = this.playWords[0];

        for(int i = 0; i < w.getCharArray().length; ++i) {
            boolean used;
            if (w.isHorizontal()) {
                used = prev.getX() == x && y >= prev.getY() && y < prev.getY() + prev.getWord().length();
            } else {
                used = prev.getY() == y && x >= prev.getX() && x < prev.getX() + prev.getWord().length();
            }

            if (!used) {
                for(int j = this.letters.size() - 1; j >= 0; --j) {
                    if (((Letter)this.letters.get(j)).here(x, y)) {
                        ((Letter)this.letters.get(j)).setDieTime(83 * i);
                    }
                }
            }

            if (w.isHorizontal()) {
                ++x;
            } else {
                ++y;
            }
        }

    }

    public void setWord() {
        this.playWords[1].setWord(this.game.getLatestWord());
    }

    public void reveal(int numLetters) {
        int x = this.playWords[1].getX();
        int y = this.playWords[1].getY();

        for(int i = 0; i < Math.min(numLetters + 1, this.playWords[1].getCharArray().length); ++i) {
            Iterator var5 = this.letters.iterator();

            while(var5.hasNext()) {
                Letter l = (Letter)var5.next();
                if (l.here(x, y)) {
                    l.setChar(this.playWords[1].getWord().charAt(i));
                }
            }

            if (this.playWords[1].isHorizontal()) {
                ++x;
            } else {
                ++y;
            }
        }

    }

    public int update(int timePassed) {
        int copy = this.timeRemaining;
        if ((this.timeRemaining -= timePassed) < 0) {
            return 1;
        } else {
            if (copy < 4000 && copy / 1000 != this.timeRemaining / 1000) {
                Sounds.TICK.play();
            }

            return this.game.ended() ? 2 : 0;
        }
    }

    public int getWordsLeft() {
        return this.game.getWordsLeft();
    }

    public int getTimeLeft() {
        return this.timeRemaining / 1000;
    }

    public String getLatestWord() {
        return this.game.getLatestWord();
    }

    public int getHintsLeft() {
        int num = this.game.getNumHints();
        return Math.max(num, 0);
    }
}
