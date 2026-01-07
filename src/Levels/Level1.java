package Levels;

import Graphics.MapCreation;
import Graphics.Sprite;
import javafx.scene.image.Image;
import Entity.animal.Animal;
import Entity.animal.Oneal;
import Entity.animal.Ballom;

import static GameRunner.RunBomberman.*;

import static Entity.animal.Bomber.swap_kill;
import static Entity.block.Bomb.power_bomb;
import static Features.SoundManager.is_sound_died;
import static Features.SoundManager.is_sound_title;
import static Control.Menu.time_number;
import static Control.Menu.bomb_number;
import static Entity.block.Bomb.is_bomb;
import static Entity.items.SpeedItem.speed;

public class Level1 {
    public Level1(){
        enemy.clear();
        block.clear();
        swap_kill = 1;
        power_bomb = 0;
        new MapCreation("resources/levels/Level1.txt");
        player.setLife(true);
        player.setX(32);
        player.setY(32);
        is_sound_died = false;
        is_sound_title = false;
        time_number = 120;
        bomb_number = 20;
        is_bomb = 0;
        speed =1;

        //load authorView screen
        player.setImg(Sprite.control_right_2.getFxImage());
        Image transparent = new Image("images/transparent.png");
        author_view.setImage(transparent);

        Animal enemy1= new Ballom(3,3,Sprite.ballom_left_1.getFxImage());
        Animal enemy2= new Ballom(9,8,Sprite.ballom_left_1.getFxImage());
        Animal enemy3= new Ballom(23,6,Sprite.ballom_left_1.getFxImage());

        Animal enemy4 = new Oneal(4,5,Sprite.oneal_left_1.getFxImage());
        Animal enemy5= new Oneal (21,7,Sprite.oneal_left_1.getFxImage());

        enemy.add(enemy1);
        enemy.add(enemy2);
        enemy.add(enemy3);
        enemy.add(enemy4);
        enemy.add(enemy5);

        for(Animal a : enemy){
            a.setLife(true);
        }
    }
}
