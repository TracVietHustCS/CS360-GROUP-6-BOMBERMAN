package Entity.block;

import javafx.scene.image.Image;
import Entity.Entity;

public class Portal extends Entity {
    private boolean active = false; // cannot active until the player kills all

    public Portal(int x, int y, Image img) {         // Create a contructor of the Portal class
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    public void activate() { active = true; }

    public boolean isActive() { return active; }


}