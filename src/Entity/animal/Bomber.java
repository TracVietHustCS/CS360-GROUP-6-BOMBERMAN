package Entity.animal;

import Graphics.Sprite;
import javafx.scene.image.Image;

import static GameRunner.RunBomberman.author_view;
import static GameRunner.RunBomberman.enemy;
import static GameRunner.RunBomberman.list_kill;
import static GameRunner.RunBomberman.player;
import static GameRunner.RunBomberman.running;

public class Bomber extends Animal {
    public static int swap_kill = 1;
    private static int count_kill = 0;


    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    private void killBomber(Animal animal) {
        if (count_kill % 16 == 0) {
            if (swap_kill == 1) {
                animal.setImg(Sprite.player_dead_1.getFxImage());
                swap_kill = 2;
            } 
            else if (swap_kill == 2) {
                animal.setImg(Sprite.player_dead_2.getFxImage());
                swap_kill = 3;
            } 
            else if (swap_kill == 3) {
                animal.setImg(Sprite.player_dead_3.getFxImage());
                swap_kill = 4;
            } 
            else {
                animal.setImg(Sprite.transparent.getFxImage());
                running = false;
                Image gameOver = new Image("images/gameOver.png");
                author_view.setImage(gameOver);
            }
        }
    }

    private void checkBombs() {
        if (list_kill[player.getX() / 32][player.getY() / 32] == 4)
            player.setLife(false);
    }

    private void checkEnemy1() {
        int ax = player.getX() / 32;
        int ay = player.getY() / 32;
        for (Animal animal : enemy) {
            int bx = animal.getX() / 32;
            int by = animal.getY() / 32;
            if (
                ax == bx && ay == by
                || ax == bx && ay == by + 1
                || ax == bx && ay == by - 1
                || ay == by && ax == bx + 1
                || ay == by && ax == bx - 1
            ) 
            {
                player.life = false;
                break;
            }
        }
    }

    private void checkEnemy2() {    //easy level
        int ax = player.getX();
        int ay = player.getY();
        for (Animal animal : enemy)
            if (
                ax == animal.getX() && ay == animal.getY()
                || ax == animal.getX() && ay == animal.getY() - 32
                || ax == animal.getX() && ay == animal.getY() + 32
                || ay == animal.getY() && ax == animal.getX() - 32
                || ay == animal.getY() && ax == animal.getX() + 32
            )
            {
                player.life = false;
                break;
            }
    }

    private void checkEnemy3() {
        int ax = player.getX();
        int ay = player.getY();
        for (Animal animal : enemy) {
            int bx = animal.getX();
            int by = animal.getY();
            if (
                ax == bx && by - 32 <= ay && by + 32 >= ay
                || ay == by && bx - 32 <= ax && bx + 32 >= ax
            ) 
            {
                player.life = false;
                break;
            }
        }
    }

    @Override
    public void update() {
        if (player.life) {
            checkBombs();
            checkEnemy3();
        } else {
            count_kill++;
            killBomber(player);
        }
    }
}
