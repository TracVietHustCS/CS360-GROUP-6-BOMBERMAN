package GameRunner;

import Entity.animal.Animal;
import Entity.Entity;
import Graphics.Sprite;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class RunBomberman extends Application {


    public static final List<Entity> block = new ArrayList<>();     // Contains fixed entities
    public static List<Animal> enemy = new ArrayList<>();
    public static int[][] id_objects;
    public static int[][] list_kill;
    public static Animal player;

    @Override
    public void start(Stage stage) {
        // Tạo một đối tượng Sprite để test
        Sprite testSprite = Sprite.bomb; // Ví dụ: Sprite bomb

        // Render hình ảnh từ Sprite
        ImageView imageView = new ImageView(testSprite.getFxImage());

        // Đặt vị trí cho ImageView
        imageView.setX(50);
        imageView.setY(50);

        // Tạo một Pane và thêm ImageView vào
        Pane root = new Pane(imageView);

        // Tạo Scene và hiển thị
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Sprite Render Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(RunBomberman.class);
    }
}