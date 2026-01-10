package Entity.animal;

import Entity.Entity;
import Graphics.Sprite;
import javafx.scene.image.Image;

import static GameRunner.RunBomberman.list_kill;

public abstract class Animal extends Entity {
    protected int isMove;        // jump with pixel
    protected int swap;           // swap image
    protected String direction;   // direction of player
    protected int count;          // count step of a jump
    protected int countToRun;   // run after count frame
    protected boolean life = true;       // life of enemy
    protected boolean removed = false;   // removed status


    public Animal(int x_unit, int y_unit, Image img) {
        super(x_unit, y_unit, img);
    }


    public Animal(boolean life) {
        this.life = life;
    }

    public boolean isLife() {
        return life;
    }

    public void setLife(boolean life) {
        this.life = life;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public int getIsMove() {
        return isMove;
    }

    public void setIsMove(int is_move) {
        this.isMove = is_move;
    }

    public int getSwap() {
        return swap;
    }

    public void setSwap(int swap) {
        this.swap = swap;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCountToRun() {
        return countToRun;
    }

    public void setCountToRun(int count_to_run) {
        this.countToRun = count_to_run;
    }

    public Animal() {

    }

    @Override
    public void update() {
        int cx = (x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
        int cy = (y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;

        if (life && list_kill[cx][cy] == 4) {
            life = false;
        }
    }
}
