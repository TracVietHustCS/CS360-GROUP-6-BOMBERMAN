package Entity.animal;

import java.util.List;

import Control.Move;
import Entity.animal.intelligent.AStar;
import Entity.animal.intelligent.Node;
import Graphics.Sprite;
import javafx.scene.image.Image;

import static GameRunner.RunBomberman.*;

public class Doll extends Animal{
    private static int swap_kill = 1;
    private static int count_kill = 0;

    public Doll(int x_unit, int y_unit) {
        super(x_unit, y_unit, Sprite.doll_left_1.getFxImage());
        this.direction = "left";
        this.life = true;
    }

    private void killDoll(Animal animal) {
        if (count_kill % 16 == 0) {
            if (swap_kill == 1) {
                animal.setImg(Sprite.doll_dead.getFxImage());
                swap_kill = 2;
            } else if (swap_kill == 2) {
                animal.setImg(Sprite.player_dead_3.getFxImage());
                swap_kill = 3;
            } else {
                animal.setLife(false);
                enemy.remove(animal);
                swap_kill = 1;
            }
        }
    }

    @Override
    public void update() {
        count_kill++;
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
        // 3. Decide new direction when aligned to tile
        if (x % 32 == 0 && y % 32 == 0) {
        moveDoll();
    }
    private void moveDoll() {
    // Only compute path when aligned to grid
   if (this.x % 32 == 0 && this.y % 32 == 0) {
            Node initial_node = new Node(this.y / 32, this.x / 32);
            Node final_node = new Node(player.getY() / 32, player.getX() / 32);

            int rows = height;
            int cols = width;

            AStar a_star = new AStar(rows, cols, initial_node, final_node);

            int[][] blocks_in_array = new int[width * height][2];
            int count_block = 0;

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (id_objects[j][i] != 0) {
                        blocks_in_array[count_block][0] = i;
                        blocks_in_array[count_block][1] = j;
                        count_block++;
                    }
                }
            }
            a_star.setBlocks(blocks_in_array, count_block);
            List<Node> path = a_star.findPath();
            if (path.size() != 0) {
                int nextX = path.get(1).getCol();
                int nextY = path.get(1).getRow();

                int dx = nextX - this.x / 32;
                int dy = nextY - this.y / 32;

                if (dx == 0 && dy == -1)
                    Move.up(this);
                else if (dx == 0 && dy == 1)
                    Move.down(this);
                else if (dx == -1 && dy == 0)
                    Move.left(this);
                else if (dx == 1 && dy == 0)
                    Move.right(this);
