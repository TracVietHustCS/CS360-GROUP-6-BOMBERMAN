package Control;

import Entity.Entity;
import static GameRunner.RunBomberman.*;

public class Blocked {

    /**
     * Kiểm tra vật cản khi đối tượng di chuyển xuống dưới.
     * <p>
     * Hàm này tính toán tọa độ ô gạch (tile) ngay phía dưới vị trí hiện tại của đối tượng
     * (X/32, Y/32 + 1) và kiểm tra ID của ô gạch đó trong mảng {@code id_objects}.
     * <p>
     * Trả về {@code true} nếu ID của ô gạch mục tiêu bằng 0 (ô trống/cỏ), cho phép di chuyển.
     * Trả về {@code false} nếu gặp vật cản cứng (ID khác 0, ví dụ tường).
     *
     * @param entity Đối tượng Entity (Animal) cần kiểm tra.
     * @return {@code true} nếu không bị chặn, {@code false} nếu bị chặn.
     */
    public static boolean block_down (Entity entity){
        return id_objects[entity.getX()/32][entity.getY()/32+1]==0;
    }

    public static boolean block_up (Entity entity){
        return id_objects[entity.getX()/32][entity.getY()/32-1]==0;
    }

    public static boolean block_left(Entity entity) {
        return id_objects[entity.getX() /32 -1][entity.getY() / 32] == 0;
    }

    public static boolean block_right(Entity entity) {
        return id_objects[entity.getX()/32+1][entity.getY() / 32] == 0;
    }

    /**
     * Kiểm tra vật cản chặn phạm vi nổ bom theo hướng xuống dưới.
     * <p>
     * Hàm này kiểm tra ID của ô gạch tại vị trí nổ xa nhất (Y/32 + 1 + power).
     * <p>
     * Trả về {@code true} nếu ô đó là ID được phép nổ qua hoặc phá hủy (0, 3, 6, 7, 8),
     * cho phép vụ nổ lan đến phạm vi này. Ngược lại (ví dụ gặp tường cứng), vụ nổ sẽ bị chặn lại.
     *
     * @param entity Vị trí ban đầu của vụ nổ (thường là Bom).
     * @param power Phạm vi nổ xa nhất cần kiểm tra.
     * @return {@code true} nếu phạm vi nổ không bị chặn đến vị trí đó, {@code false} nếu bị chặn.
     */
    public static boolean block_down_bomb(Entity entity, int power) {
        return id_objects[entity.getX() / 32][entity.getY() / 32 + 1 + power] == 0
                || id_objects[entity.getX() / 32][entity.getY() / 32 + 1 + power] == 3
                || id_objects[entity.getX() / 32][entity.getY() / 32 + 1 + power] == 6
                || id_objects[entity.getX() / 32][entity.getY() / 32 + 1 + power] == 7
                || id_objects[entity.getX() / 32][entity.getY() / 32 + 1 + power] == 8;
    }

    public static boolean block_up_bomb(Entity entity, int power) {
        return id_objects[entity.getX() / 32][entity.getY() / 32 - 1 - power] == 0
                || id_objects[entity.getX() / 32][entity.getY() / 32 - 1 - power] == 3
                || id_objects[entity.getX() / 32][entity.getY() / 32 - 1 - power] == 6
                || id_objects[entity.getX() / 32][entity.getY() / 32 - 1 - power] == 7
                || id_objects[entity.getX() / 32][entity.getY() / 32 - 1 - power] == 8;
    }

    public static boolean block_left_bomb(Entity entity, int power){
        return id_objects[entity.getX()/32-1-power][entity.getY()/32]==0
                ||id_objects[entity.getX()/32-1+power][entity.getY()/32]==3
                ||id_objects[entity.getX()/32-1+power][entity.getY()/32]==6
                ||id_objects[entity.getX()/32-1+power][entity.getY()/32]==7
                ||id_objects[entity.getX()/32-1+power][entity.getY()/32]==8;
    }

    public static boolean block_right_bomb(Entity entity, int power) {
        return id_objects[entity.getX() / 32 + 1 + power][entity.getY() / 32] == 0
                || id_objects[entity.getX() / 32 + 1 + power][entity.getY() / 32] == 3
                || id_objects[entity.getX() / 32 + 1 + power][entity.getY() / 32] == 6
                || id_objects[entity.getX() / 32 + 1 + power][entity.getY() / 32] == 7
                || id_objects[entity.getX() / 32 + 1 + power][entity.getY() / 32] == 8;
    }
}
