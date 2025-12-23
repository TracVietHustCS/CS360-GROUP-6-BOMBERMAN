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
    if (x % 32 != 0 || y % 32 != 0) 
        return;

    Node start = new Node(y / 32, x / 32);
    Node goal  = new Node(player.getY() / 32, player.getX() / 32);

    AStar aStar = new AStar(height, width, start, goal);

    int[][] blocks = new int[width * height][2];
    int blockCount = 0;

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if (id_objects[j][i] != 0) {
                blocks[blockCount][0] = i;
                blocks[blockCount][1] = j;
                blockCount++;
            }
        }
    }

    aStar.setBlocks(blocks, blockCount);

    List<Node> path = aStar.findPath();
    if (path == null || path.size() < 2) return;

    Node next = path.get(1);

    int dx = next.getCol() - start.getCol();
    int dy = next.getRow() - start.getRow();

    // 4 direction movement
    if (dx == 0 && dy == -1) {
        Move.up(this);
    } else if (dx == 0 && dy == 1) {
        Move.down(this);
    } else if (dx == -1 && dy == 0) {
        Move.left(this);
    } else if (dx == 1 && dy == 0) {
        Move.right(this);
    }
}
