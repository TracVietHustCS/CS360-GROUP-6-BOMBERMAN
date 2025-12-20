package Entity.animal;

import Control.Move;
import javafx.scene.image.Image;
import Graphics.Sprite;

import static GameRunner.RunBomberman.enemy;
import static GameRunner.RunBomberman.id_objects;

public class Kondoria extends Animal {

    private int deathFrame = 0;
    private boolean movingLeft = true;   // Kondoria starts by going left


    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        this.speed = 1;
        this.life = true;
    }

    public Kondoria(boolean life) {
        super(life);
    }

    @Override
    public void updateDeathAnimation() {
        deathFrame++;

        if (deathFrame < 10) {
            img = Sprite.kondoria_dead.getFxImage();
        }
        else if (deathFrame < 20) {
            img = Sprite.player_dead_3.getFxImage();
        }
        else {
            this.setLife(false);
            this.setRemoved(true);   // Mark for safe removal
        }
    }

    @Override
    public void update() {
        // Kondoria death is decided in Bomb, not in Kondoria
        // 1. If dead → play death animation
        if (!life) {
            playDeathAnimation();
            return;
        }
        // 2. When it is between tiles, it keeps moving, does not make new decision
        if (count > 0) { // count = how many small pixel-steps are left to finish the current tile movement
            Move.checkRun(this);
            return;
        }

        // 3. Move only when perfectly aligned to tile
        if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {

            // Get map width from id_objects[][]
            int mapWidthTiles = id_objects.length; // number of columns
            int tileX = x / Sprite.SCALED_SIZE;

            // Reverse direction when hitting boundaries
            if (tileX <= 1) movingLeft = false;
            if (tileX >= mapWidthTiles - 2) movingLeft = true;

            // Move horizontally
            if (movingLeft) Move.left(this);
            else Move.right(this);
        }
    }
}
