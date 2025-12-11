package Entity.animal;

import Control.Move;
import Graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

import static GameRunner.RunBomberman.enemy;
import static GameRunner.RunBomberman.list_kill;
import static GameRunner.RunBomberman.player;

public class Oneal extends Animal {

    private int deathFrame = 0;
    private static final Random rand = new Random();

    public Oneal(int x, int y) {
        super(x, y, Sprite.oneal_left_1.getFxImage());
        this.direction = "left";
        this.speed = 1;
        this.life = true;
    }

    @Override
    public void update() {

        // 1. Check explosion hit
        if (life && list_kill[x / 32][y / 32] == 4) {
            life = false;
        }

        // 2. Handle death animation
        if (!life) {
            playDeathAnimation();
            return;
        }

        // 3. AI movement: Oneal chases player (simplified BFS: move closer tile-by-tile)
        if (x % 32 == 0 && y % 32 == 0 && count == 0) {

            int px = player.getX();
            int py = player.getY();

            // choose dominant axis to avoid diagonal conflict
            if (Math.abs(px - x) > Math.abs(py - y)) {
                // move horizontally toward player
                if (px < x) Move.left(this);
                else Move.right(this);
            } else {
                // move vertically toward player
                if (py < y)  Move.up(this);
                else Move.down(this);
            }

            // move 32px at 8px steps
            count = 4;
        }
    }

    private void playDeathAnimation() {
        deathFrame++;

        if (deathFrame < 10)
            img = Sprite.oneal_dead.getFxImage();
        else if (deathFrame < 20)
            img = Sprite.mob_dead_2.getFxImage();
        else if (deathFrame < 30)
            img = Sprite.mob_dead_3.getFxImage();
        else
            enemy.remove(this);
    }
}
