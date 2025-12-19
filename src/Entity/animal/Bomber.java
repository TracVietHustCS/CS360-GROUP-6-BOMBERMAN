package Entity.animal;


import Graphics.Sprite;
import javafx.scene.image.Image;

import static GameRunner.RunBomberman.*;

public class Bomber extends Animal {
    public static int swap_kill =1;
    private static int count_kill =0;

    public Bomber(int is_move, int swap, String direction, int count, int count_to_run){
        super(8,1,"down",0,0);
    }

    public Bomber(){};

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    private void killBomber(Animal animal){
        if(count_kill%16==0) {
            if (swap_kill == 1) {
                animal.setImg(Sprite.player_dead_1.getFxImage());
                swap_kill = 2;
            } else if (swap_kill == 2) {
                animal.setImg(Sprite.player_dead_2.getFxImage());
                swap_kill = 3;
            }else if (swap_kill == 3) {
                animal.setImg(Sprite.player_dead_3.getFxImage());
                swap_kill = 4;
            } else {
                animal.setImg(Sprite.transparent.getFxImage());
                running = false;
                Image gameOver = new Image("/Graphics/GameOver.png");
                author_view.setImage(gameOver);
            }
        }
    }

    /**
     * Kiểm tra va chạm giữa nhân vật Bomber và các vụ nổ bom đang diễn ra trên bản đồ.
     * <p>
     * Hàm này kiểm tra giá trị tại vị trí ô gạch (tọa độ pixel chia cho 32) của Bomber
     * trong mảng {@code list_kill}. Nếu giá trị tại ô gạch đó bằng 4 (thường là ký hiệu
     * của bom nổ đang hoạt động), nhân vật sẽ bị gán trạng thái chết (life = false).
     */
    private void checkBombs(){
        if(list_kill[player.getX()/32] [player.getY()/32]==4){
            player.setLife(false);
        }
    }

    /**
     * Kiểm tra va chạm theo đơn vị ô gạch (Tile-based Collision).
     * <p>
     * Va chạm được xác định nếu Bomber đứng cùng ô gạch (x/32, y/32) với kẻ thù
     * HOẶC đứng tại bất kỳ ô liền kề nào (trong phạm vi 1 ô).
     * <p>
     * Phương pháp này có độ chính xác thấp nhất (dễ va chạm) nhưng đơn giản về logic.
     */
    private void checkEnemy1(){
        int ax = player.getX() / 32;
        int ay = player.getY() / 32;
        for (Animal animal : enemy) {
            int bx = animal.getX() / 32;
            int by = animal.getY() / 32;
            if (
                ax == bx && ay == by
                || ax == bx && ay == by + 1
                || ax == bx && ay == by - 1
                || ay == by && ax == bx + 1
                || ay == by && ax == bx - 1
            )
            {
                player.life = false;
                break;
            }
        }
    }

    /**
     * Kiểm tra va chạm theo đơn vị Pixel (Pixel-based Collision - Rigid).
     * <p>
     * Va chạm được xác định nếu Bomber đứng tại chính xác tọa độ X, Y của kẻ thù,
     * HOẶC đứng cách kẻ thù đúng 32 pixel theo chiều ngang/dọc (vị trí rìa ô gạch).
     * <p>
     * Phương pháp này chính xác hơn checkEnemy1 nhưng lại rất cứng nhắc, chỉ phát hiện va chạm tại các điểm "tuyệt đối".
     */
    private void checkEnemy2() {    //easy level
        int ax = player.getX();
        int ay = player.getY();
        for (Animal animal : enemy)
            if (
                ax == animal.getX() && ay == animal.getY()
                || ax == animal.getX() && ay == animal.getY() - 32
                || ax == animal.getX() && ay == animal.getY() + 32
                || ay == animal.getY() && ax == animal.getX() - 32
                || ay == animal.getY() && ax == animal.getX() + 32
            )
            {
                player.life = false;
                break;
            }
    }

    /**
     * Kiểm tra va chạm theo đơn vị Pixel (Pixel-based Collision - Range).
     * <p>
     * Va chạm được xác định nếu Bomber nằm trong phạm vi 32 pixel theo chiều ngang VÀ/HOẶC chiều dọc so với kẻ thù.
     * <p>
     * Đây là phương pháp chính xác và linh hoạt nhất, thường được sử dụng trong Game Loop để tạo cảm giác va chạm mượt mà.
     */
    private void checkEnemy3() {
        int ax = player.getX();
        int ay = player.getY();
        for (Animal animal : enemy) {
            int bx = animal.getX();
            int by = animal.getY();
            if (
                ax == bx && by - 32 <= ay && by + 32 >= ay
                || ay == by && bx - 32 <= ax && bx + 32 >= ax
            )
            {
                player.life = false;
                break;
            }
        }
    }
    /**
     * Phương thức cập nhật trạng thái của Bomber trong mỗi khung hình (Frame) của trò chơi.
     * <p>
     * Đây là hàm ghi đè (Override) từ class cha, được vòng lặp game (Game Loop) gọi liên tục
     * để duy trì các logic kiểm tra sống còn và xử lý hoạt hình khi nhân vật bị tiêu diệt.
     * <p>
     * Các bước xử lý chính bao gồm:
     * <ul>
     * <li>Kiểm tra va chạm với các vụ nổ bom trên bản đồ.</li>
     * <li>Kiểm tra va chạm với tất cả quái vật hiện có bằng logic phạm vi pixel.</li>
     * <li>Tăng bộ đếm khung hình cho chuỗi hoạt hình chết.</li>
     * <li>Kích hoạt chuỗi hiệu ứng hình ảnh khi nhân vật hết mạng (life = false).</li>
     * </ul>
     */
    @Override
    public void update() {
        /* Kiểm tra xem vị trí hiện tại của người chơi có trùng với ô đang có nổ bom không */
        checkBombs();
        checkEnemy3();
        /* Duy trì bộ đếm khung hình liên tục để đảm bảo animation chết kích hoạt không độ trễ */
        count_kill++;
        if (!player.life)
            killBomber(player);
    }
}
