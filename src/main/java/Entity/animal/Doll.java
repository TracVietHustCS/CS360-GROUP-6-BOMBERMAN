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

    // Variables that decides should A* be re-calculated
    private int pathCooldown = 0;
    private List<Node> currentPath = null;
    private int lastPlayerTileX = -1;
    private int lastPlayerTileY = -1;

    public Doll(int x_unit, int y_unit, Image img) {
        super(x_unit, y_unit, img);
    }

    public Doll(boolean life) {
        super(life);
    }

    public Doll() {

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

    private void recomputePath() {
        Node initialNode = new Node(getY() / 32, getX() / 32);
        Node finalNode = new Node(player.getY() / 32, player.getX() / 32);
        int rows = height;
        int cols = width;
        AStar a_star = new AStar(rows, cols, initialNode, finalNode);
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
        currentPath = a_star.findPath();

        // Store player position for change detection
        lastPlayerTileX = player.getX() / 32;
        lastPlayerTileY = player.getY() / 32;

        // Reset cooldown to recompute after N tile movements
        pathCooldown = 32; // Adjust this value: higher = less frequent recomputation
    }
    private boolean shouldRecomputePath() {
        // Recompute if no path exists
        if (currentPath == null || currentPath.size() < 2) {
            return true;
        }

        // Recompute if cooldown expired
        if (pathCooldown <= 0) {
            return true;
        }

        // Recompute if player moved to a different tile
        int currentPlayerTileX = player.getX() / 32;
        int currentPlayerTileY = player.getY() / 32;
        return currentPlayerTileX != lastPlayerTileX || currentPlayerTileY != lastPlayerTileY;
    }


    private void moveDoll() {
        if (getX() % 32 == 0 && getY() % 32 == 0) {
            // Check if we need to recompute the path
            if (shouldRecomputePath()) {
                recomputePath();
            } else {
                pathCooldown--;
            }
            // Follow the cached path
            if (currentPath != null && currentPath.size() >= 2) {
                int nextX = currentPath.get(1).getCol();
                int nextY = currentPath.get(1).getRow();
                if (getY() / 32 > nextY)
                    Move.up(this);
                if (getY() / 32 < nextY)
                    Move.down(this);
                if (getX() / 32 > nextX)
                    Move.left(this);
                if (getX() / 32 < nextX)
                    Move.right(this);
            }
        }
    }

    @Override
    public void update() {
        count_kill++;
        for (Animal animal:enemy) {
            if (animal instanceof Doll && !animal.life)
                killDoll(animal);
        }
        moveDoll();
    }
}
