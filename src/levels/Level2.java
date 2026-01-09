package levels;

import Entity.animal.Animal;
import Entity.animal.Ballom;
import Entity.animal.Kondoria;
import Entity.animal.Oneal;
import Graphics.MapCreation;
import Graphics.Sprite;
import javafx.scene.image.Image;

import static Control.Menu.bomb_number;
import static Control.Menu.time_number;
import static Entity.animal.Bomber.swap_kill;
import static Entity.items.SpeedItem.speed;
import static Entity.block.Bomb.is_bomb;
import static GameRunner.RunBomberman.*;
import static Features.SoundManager.is_sound_died;
import static Features.SoundManager.is_sound_title;


public class Level2 {
    public Level2() {
        enemy.clear();
        block.clear();
        swap_kill = 1;
        new MapCreation("/levels/level2.txt");
        player.setLife(true);
        player.setX(32);
        player.setY(32);
        is_sound_died = false;
        is_sound_title = false;
        time_number = 120;
        bomb_number = 30;
        is_bomb = 0;
        speed =1;

        player.setImg(Sprite.control_right_2.getFxImage());
        Image transparent = new Image("images/transparent.png");
        author_view.setImage(transparent);

        Animal enemy1 = new Ballom(7, 3, Sprite.ballom_left_1.getFxImage());
        Animal enemy2 = new Ballom(1, 5, Sprite.ballom_left_2.getFxImage());

        Animal enemy3 = new Kondoria(3, 5, Sprite.kondoria_right_1.getFxImage());
        Animal enemy4 = new Kondoria(11, 5, Sprite.kondoria_right_1.getFxImage());
        Animal enemy5 = new Kondoria(22, 11, Sprite.kondoria_right_1.getFxImage());

        Animal enemy6 = new Oneal(9, 9, Sprite.oneal_right_1.getFxImage());
        Animal enemy7 = new Oneal(22, 13, Sprite.oneal_right_1.getFxImage());

        enemy.add(enemy1);
        enemy.add(enemy2);
        enemy.add(enemy3);
        enemy.add(enemy4);
        enemy.add(enemy5);
        enemy.add(enemy6);
        enemy.add(enemy7);

        for(Animal a: enemy){
            a.setLife(true);
        }

    }
}
