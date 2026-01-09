package Graphics;

// Import class

import Entity.Entity;
import Entity.block.Brick;
import Entity.block.Grass;
import Entity.block.Portal;
import Entity.block.Wall;
import Entity.items.FlameItem;
import Entity.items.SpeedItem;
import GameRunner.RunBomberman;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

import static GameRunner.RunBomberman.block;
import static GameRunner.RunBomberman.height;
import static GameRunner.RunBomberman.id_objects;
import static GameRunner.RunBomberman.list_kill;
import static GameRunner.RunBomberman.width;

public class MapCreation {
    // Constructor MapCreation with parameter "level" in string data type.
    public MapCreation(String level) {
        InputStream is = MapCreation.class.getResourceAsStream(level);   // Load map from resources

            Scanner ip = new Scanner(is);                    // Create object ip from class Scanner.
            String line = ip.nextLine();                            // Input variable line in string data type.

            StringTokenizer tokens = new StringTokenizer(line);     // Create object tokens from class StringTokenizer in library imported.

            // parseInt(): Method that parses the string argument and returns a primitive int.
            RunBomberman.level = Integer.parseInt(tokens.nextToken());   // To refer to variable level in main file.
            height = Integer.parseInt(tokens.nextToken());
            width = Integer.parseInt(tokens.nextToken());


                id_objects = new int[width][height];                 // Create new object id_object from main file.
                list_kill = new int[width][height];                  // Create new object lít_kill from main file.   Main file: RunBomberman.java
                for (int i = 0; i < height; ++i) {
                    String lineTile = ip.nextLine();                // Input variable lineTile in string data type.
                    StringTokenizer tokenTile = new StringTokenizer(lineTile);      // Create object tokenTile from class StringTokenizer in library imported.

                    for (int j = 0; j < width; j++) {
                        int token = Integer.parseInt(tokenTile.nextToken());
                        Entity entity;                              // Create object entity from class Entity.

                        // This switch statement running, and we got a full map for a game.
                        // Through the program, in the for-loop statement, we can get the map according to each loop it passed.
                        switch (token) {
                            case 1:
                                entity = new Portal(j, i, Sprite.grass.getFxImage());       // In case 1, set entity object equal to object portal with scaled size.
                                token = 0;
                                break;
                            case 2:
                                entity = new Wall(j, i, Sprite.wall.getFxImage());          // In case 2, set entity object equal to object wall with scaled size.
                                break;
                            case 3:
                                entity = new Brick(j, i, Sprite.brick.getFxImage());        // In case 3, set entity object equal to object brick with scaled size.
                                break;
                            case 6:
                                entity = new SpeedItem(j, i, Sprite.brick.getFxImage());
                                break;
                            case 7:
                                entity = new FlameItem(j, i, Sprite.brick.getFxImage());
                                break;
                            default:
                                entity = new Grass(j, i, Sprite.grass.getFxImage());
                        }
                        id_objects[j][i] = token;        //
                        block.add(entity);              //
                    }
                }
            
         ip.close();

        System.out.println("Map loaded: " + level);
        System.out.println("width=" + width + ", height=" + height);
        //Explicit close (even though Scanner on InputStream is GC-safe)
        //
        //Useful debug output
    }
}
