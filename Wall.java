package Entity.block;

import Graphics.Sprite;
import javafx.scene.image.Image;
import Entity.Entity;

public class Wall extends Entity
{

    public Wall(int x, int y, Image img)
    {
        super(x, y, img);
    }

    @Override
    public void update()
    {
        // Wall does not change
    }
}
