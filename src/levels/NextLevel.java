package levels;

import javafx.scene.image.Image;

import static Entity.block.Portal.is_portal;
import static GameRunner.RunBomberman.author_view;
import static GameRunner.RunBomberman.level;

public class NextLevel {
    public static boolean wait;
    public static long waiting_time;

    public static void waitToLevelUp(){
        if(wait){
            Image wait_to_next = new Image("images/levelUp.png");
            author_view.setImage(wait_to_next);
            long now =  System.currentTimeMillis();
            if(now - waiting_time > 3000){
                switch(level){
                    case 1:
                        is_portal = false;
                        new Level2();
                        break;
                    case 2:
                        new Level3();
                        break;
                    case 3:
                        new Level1();
                        break;
                }
                wait = false;
            }
        }
    }
}
