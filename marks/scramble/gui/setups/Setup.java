package marks.scramble.gui.setups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.input.KeyCode;
import marks.scramble.gui.CanvasUtils;
import marks.scramble.gui.sprites.Sprite;

public abstract class Setup {
    protected List<Sprite> sprites = new ArrayList();

    public Setup() {
    }

    public abstract void onClick(double var1, double var3);

    public abstract void update(double var1, double var3, boolean var5, int var6);

    public abstract void onKeyPress(KeyCode var1);

    private void renderSprites(int frameSpeed) {
        Iterator var2 = this.sprites.iterator();

        while(var2.hasNext()) {
            Sprite s = (Sprite)var2.next();
            s.render(frameSpeed);
        }

    }

    public void onUpdate(double mouseX, double mouseY, boolean mousePressed, int frameSpeed) {
        CanvasUtils.clear();
        this.renderSprites(frameSpeed);
        this.update(mouseX, mouseY, mousePressed, frameSpeed);
    }

    public void addSprite(Sprite s) {
        int size = this.sprites.size();
        int depth = s.getDepth();

        for(int i = 0; i < size; ++i) {
            if (depth > ((Sprite)this.sprites.get(i)).getDepth()) {
                this.sprites.add(i, s);
                return;
            }
        }

        this.sprites.add(s);
    }

    public void removeSprite(Sprite s) {
        if (this.sprites.contains(s)) {
            this.sprites.remove(s);
        }

    }
}
