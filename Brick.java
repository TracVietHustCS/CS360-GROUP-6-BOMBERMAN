package Entity.block;

import Graphics.Sprite;
import javafx.scene.image.Image;
import Entity.Entity;

public class Brick extends Entity {

    private boolean destroyed = false;
    private int animationTimer = 10;
    private int frame = 0; // how many frames passed since Brick break
    public Brick(int x, int y, Image img) {     // Create a contructor of the Brick class
        super(x, y, img);
    }

    public void destroy()
    {
        if (destroyed) return;
        destroyed = true; // brick begins breaking
        frame = 0; // time reset, animation starts
    }

    private void checkHidden() {
                int tileX = brick.getX() / Sprite.SCALED_SIZE;
                int tileY = brick.getY() / Sprite.SCALED_SIZE;

                if (list_kill[tileY][tileX] == 4) { destroy(); }
            }

    @Override
    public void update() {
        checkHidden();
        if (destroyed) {
            frame++;
            if (frame >= animationTimer) {
                img = Sprite.grass.getFxImage();
            } //
        }
    }
}

