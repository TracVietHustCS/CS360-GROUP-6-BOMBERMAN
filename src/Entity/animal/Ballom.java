package Entity.animal;

import Control.Move;
import javafx.scene.image.Image;
import Graphics.Sprite;

import java.util.Random;

import static GameRunner.RunBomberman.enemy;
import static GameRunner.RunBomberman.list_kill;

public class Ballom extends Animal {

    private static final Random rand = new Random();
    private int deathFrame = 0;

    public Ballom(int x_unit, int y_unit) {
        super(x_unit, y_unit, Sprite.ballom_left_1.getFxImage());
        this.direction = "left";   // default
        this.life = true;
    }

    @Override
    public void update() {

        // 1. Check if bomb explosion killed Ballom
        if (life && list_kill[x / 32][y / 32] == 4) {
            life = false;
        }

        // 2. If dead → play death animation
        if (!life) {
            playDeathAnimation();
            return;
        }
        // 3. When it is between tiles, it keeps moving, does not make new decision
        if (count > 0) { // count = how many small pixel-steps are left to finish the current tile movement
            Move.checkRun(this);
            return;
        }
        // 4. If aligned to tile → pick new random direction
        if (x % 32 == 0 && y % 32 == 0) {
            int Direction = rand.nextInt(4);
            switch (Direction) {
                case 0: Move.up(this);    break;
                case 1: Move.down(this);  break;
                case 2: Move.left(this);  break;
                case 3: Move.right(this); break;
            }
        }

    }

    private void playDeathAnimation() {
        deathFrame++;

        if (deathFrame < 10) img = Sprite.mob_dead_1.getFxImage();
        else if (deathFrame < 20) img = Sprite.mob_dead_2.getFxImage();
        else if (deathFrame < 30) img = Sprite.mob_dead_3.getFxImage();
        else Animal.remove = true; // finished → remove from game
    }
}
