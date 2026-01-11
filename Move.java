package Control;

import Entity.animal.*;
import Graphics.Sprite;

import static Entity.items.SpeedItem.speed;

public class Move {

    /**
     * Kiểm tra và xử lý việc thực hiện một bước di chuyển tiếp theo cho các đối tượng mob.
     *
     * Trong đoạn code này, logic chỉ áp dụng cho đối tượng {@code Bomber}.
     * Nó sẽ thực hiện một bước di chuyển nhỏ nếu đối tượng còn số bước đếm cần hoàn thành.
     *
     * @param animal Đối tượng Animal (Bomber, Ballom, v.v.) cần kiểm tra trạng thái di chuyển.
     */
    public static void checkRun(Animal animal) {
        if (animal instanceof Bomber && animal.getCount() > 0) {
            setDirection(animal.getDirection(), animal, 8 * speed);
            animal.setCount(animal.getCount() - 1);
        }
        if ((animal instanceof Ballom || animal instanceof Oneal
                || animal instanceof Kondoria)
                && animal.getCount() > 0) {
            setDirection(animal.getDirection(), animal, 4);
            animal.setCount(animal.getCount() - 1);
        }
    }

    /**
     * Thực hiện việc di chuyển thực tế (cộng/trừ tọa độ) và cập nhật animation cho đối tượng
     * dựa trên hướng đã được thiết lập trước đó. Hàm này được gọi bởi {@code checkRun}.
     * * @param direction Hướng di chuyển (chuỗi: "up", "down", "left", "right").
     * @param animal Đối tượng Animal (Bomber/Mob) cần di chuyển.
     * @param isMove Số lượng pixel mà đối tượng di chuyển trong bước hiện tại (tốc độ).
     */
    private static void setDirection(String direction, Animal animal, int isMove) {
        switch (direction) {
            case "down":
                down_step(animal);
                animal.setY(animal.getY() + isMove);
                break;
            case "up":
                up_step(animal);
                animal.setY(animal.getY() - isMove);
                break;
            case "left":
                left_step(animal);
                animal.setX(animal.getX() - isMove);
                break;
            case "right":
                right_step(animal);
                animal.setX(animal.getX() + isMove);
                break;
            case "up_left":
                left_step(animal);
                animal.setX(animal.getX() - (isMove / Math.sqrt(2)));
                animal.setY(animal.getY() - (isMove / Math.sqrt(2)));
                break;
            case "up_right":
                right_step(animal);
                animal.setX(animal.getX() + (isMove / Math.sqrt(2)));
                animal.setY(animal.getY() - (isMove / Math.sqrt(2)));
                break;
            case "down_left":
                left_step(animal);
                animal.setX(animal.getX() - (isMove / Math.sqrt(2)));
                animal.setY(animal.getY() + (isMove / Math.sqrt(2)));
                break;
            case "down_right":
                right_step(animal);
                animal.setX(animal.getX() + (isMove / Math.sqrt(2)));
                animal.setY(animal.getY() + (isMove / Math.sqrt(2)));
                break;
        }
    }

    /**
     * Bắt đầu quá trình di chuyển xuống dưới cho đối tượng {@code Bomber}.
     *
     * Hàm chỉ cho phép nhận lệnh mới khi Bomber đang ở vị trí chính xác của một ô gạch (x%32=0 và y%32=0).
     * Nếu không bị chặn (Blocked), hàm sẽ thiết lập hướng và số bước (count) cần di chuyển, sau đó
     * gọi {@code checkRun} để bắt đầu thực thi bước đầu tiên.
     *
     * @param animal Đối tượng Animal (Bomber) cần di chuyển.
     */
    public static void down(Animal animal) {
        if (animal.getY() % 32 == 0 && animal.getX() % 32 == 0) {
            if (animal instanceof Bomber && Blocked.block_down(animal)) {
                animal.setDirection("down");
                animal.setCount(4 / speed);
                checkRun(animal);
            }
        }
        if ((animal instanceof Ballom || animal instanceof Oneal)
                && Blocked.block_down(animal)) {
            animal.setDirection("down");
            animal.setCount(8);
            checkRun(animal);
        }
    }

    /**
     * Cập nhật hoạt hình (animation) cho Bomber khi di chuyển xuống.
     *
     * Hàm này dùng biến {@code swap} để luân phiên đổi ảnh giữa {@code control_down},
     * {@code control_down_1} và {@code control_down_2}.
     * Animation chỉ được cập nhật khi Bomber đang ở các vị trí pixel chia hết cho 8 (y % 8 == 0).
     *
     * @param animal Đối tượng Animal (Bomber) cần cập nhật animation.
     */
    private static void down_step(Animal animal) {      //Show the animation of all mob that go down
        if (animal instanceof Bomber && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.control_down.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.control_down_1.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.control_down.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.control_down_2.getFxImage());
                animal.setSwap(1);
            }
        }
        if (animal instanceof Ballom && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.ballom_right_1.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.ballom_right_2.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.ballom_right_3.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.ballom_right_2.getFxImage());
                animal.setSwap(1);
            }
        }
        if (animal instanceof Oneal && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.oneal_right_1.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.oneal_right_2.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.oneal_right_3.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.oneal_right_2.getFxImage());
                animal.setSwap(1);
            }
        }
    }

    /**
     * Bắt đầu quá trình di chuyển lên trên cho đối tượng {@code Bomber}.
     *
     * Hàm chỉ cho phép nhận lệnh mới khi Bomber đang ở vị trí chính xác của một ô gạch.
     * Nếu không bị chặn, hàm sẽ thiết lập hướng và số bước đếm cần di chuyển, sau đó
     * gọi {@code checkRun} để bắt đầu thực thi bước đầu tiên.
     *
     * @param animal Đối tượng Animal (Bomber) cần di chuyển.
     */
    public static void up(Animal animal) {
        if (animal.getY() % 32 == 0 && animal.getX() % 32 == 0) {
            if (animal instanceof Bomber && Blocked.block_up(animal)) {
                animal.setDirection("up");
                animal.setCount(4 / speed);
                checkRun(animal);
            }
        }
        if ((animal instanceof Ballom || animal instanceof Oneal )
                && Blocked.block_up(animal)) {
            animal.setDirection("up");
            animal.setCount(8);
            checkRun(animal);
        }
    }

    /**
     * Cập nhật hoạt hình (animation) cho Bomber khi di chuyển lên.
     *
     * Hàm này dùng biến {@code swap} để luân phiên đổi ảnh giữa {@code control_up},
     * {@code control_up_1} và {@code control_up_2}.
     * Animation chỉ được cập nhật khi Bomber đang ở các vị trí pixel chia hết cho 8 (y % 8 == 0).
     *
     * @param animal Đối tượng Animal (Bomber) cần cập nhật animation.
     */
    private static void up_step(Animal animal) {
        if (animal instanceof Bomber && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.control_up.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.control_up_1.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.control_up.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.control_up_2.getFxImage());
                animal.setSwap(1);
            }
        }
        if (animal instanceof Ballom && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.ballom_left_1.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.ballom_left_2.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.ballom_left_3.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.ballom_left_2.getFxImage());
                animal.setSwap(1);
            }
        }
        if (animal instanceof Oneal && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.oneal_left_1.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.oneal_left_2.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.oneal_left_3.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.oneal_left_2.getFxImage());
                animal.setSwap(1);
            }
        }
    }

    /**
     * Bắt đầu quá trình di chuyển sang trái cho đối tượng {@code Bomber}.
     *
     * Hàm chỉ cho phép nhận lệnh mới khi Bomber đang ở vị trí chính xác của một ô gạch.
     * Nếu không bị chặn, hàm sẽ thiết lập hướng và số bước đếm cần di chuyển, sau đó
     * gọi {@code checkRun} để bắt đầu thực thi bước đầu tiên.
     *
     * @param animal Đối tượng Animal (Bomber) cần di chuyển.
     */
    public static void left(Animal animal) {
        if (animal.getX() % 32 == 0 && animal.getY() % 32 == 0) {
            if (animal instanceof Bomber && Blocked.block_left(animal)) {
                animal.setDirection("left");
                animal.setCount(4 / speed);
                checkRun(animal);
            }
        }
        if ((animal instanceof Ballom || animal instanceof Oneal
                || animal instanceof Kondoria)
                && Blocked.block_left(animal)) {
            animal.setDirection("left");
            animal.setCount(8);
            checkRun(animal);
        }
    }

    /**
     * Cập nhật hoạt hình (animation) cho Bomber khi di chuyển sang trái.
     *
     * Hàm này dùng biến {@code swap} để luân phiên đổi ảnh giữa {@code control_left},
     * {@code control_left_1} và {@code control_left_2}.
     * Animation chỉ được cập nhật khi Bomber đang ở các vị trí pixel chia hết cho 8 (x % 8 == 0).
     *
     * @param animal Đối tượng Animal (Bomber) cần cập nhật animation.
     */
    private static void left_step(Animal animal) {      //Show the animation of all mob that go left
        if (animal instanceof Bomber && animal.getX() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.control_left.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.control_left_1.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.control_left.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.control_left_2.getFxImage());
                animal.setSwap(1);
            }
        }
        if (animal instanceof Ballom && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.ballom_right_1.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.ballom_right_2.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.ballom_right_3.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.ballom_right_2.getFxImage());
                animal.setSwap(1);
            }
        }
        if (animal instanceof Oneal && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.oneal_right_1.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.oneal_right_2.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.oneal_right_3.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.oneal_right_2.getFxImage());
                animal.setSwap(1);
            }
        }
        if (animal instanceof Kondoria && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.kondoria_left_1.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.kondoria_left_2.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.kondoria_left_3.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.kondoria_left_2.getFxImage());
                animal.setSwap(1);
            }
        }

    }

    /**
     * Bắt đầu quá trình di chuyển sang phải cho đối tượng {@code Bomber}.
     *
     * Hàm chỉ cho phép nhận lệnh mới khi Bomber đang ở vị trí chính xác của một ô gạch.
     * Nếu không bị chặn, hàm sẽ thiết lập hướng và số bước đếm cần di chuyển, sau đó
     * gọi {@code checkRun} để bắt đầu thực thi bước đầu tiên.
     *
     * @param animal Đối tượng Animal (Bomber) cần di chuyển.
     */
    public static void right(Animal animal) {       //Control all mob to go right
        if (animal.getX() % 32 == 0 && animal.getY() % 32 == 0) {
            if (animal instanceof Bomber && Blocked.block_right(animal)) {
                animal.setDirection("right");
                animal.setCount(4 / speed);
                checkRun(animal);
            }
        }
        if ((animal instanceof Ballom || animal instanceof Oneal
                || animal instanceof Kondoria)
                && Blocked.block_right(animal)) {
            animal.setDirection("right");
            animal.setCount(8);
            checkRun(animal);
        }
    }

    /**
     * Cập nhật hoạt hình (animation) cho Bomber khi di chuyển sang phải.
     *
     * Hàm này dùng biến {@code swap} để luân phiên đổi ảnh giữa {@code control_right},
     * {@code control_right_1} và {@code control_right_2}.
     * Animation chỉ được cập nhật khi Bomber đang ở các vị trí pixel chia hết cho 8 (x % 8 == 0).
     *
     * @param animal Đối tượng Animal (Bomber) cần cập nhật animation.
     */
    public static void right_step(Animal animal) {
        if (animal instanceof Bomber && animal.getX() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.control_right.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.control_right_1.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.control_right.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.control_right_2.getFxImage());
                animal.setSwap(1);
            }
        }
        if (animal instanceof Ballom && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.ballom_left_1.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.ballom_left_2.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.ballom_left_3.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.ballom_left_2.getFxImage());
                animal.setSwap(1);
            }
        }
        if (animal instanceof Oneal && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.oneal_left_1.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.oneal_left_2.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.oneal_left_3.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.oneal_left_2.getFxImage());
                animal.setSwap(1);
            }
        }
        if (animal instanceof Kondoria && animal.getY() % 8 == 0) {
            if (animal.getSwap() == 1) {
                animal.setImg(Sprite.kondoria_right_1.getFxImage());
                animal.setSwap(2);
            } else if (animal.getSwap() == 2) {
                animal.setImg(Sprite.kondoria_right_2.getFxImage());
                animal.setSwap(3);
            } else if (animal.getSwap() == 3) {
                animal.setImg(Sprite.kondoria_right_3.getFxImage());
                animal.setSwap(4);
            } else {
                animal.setImg(Sprite.kondoria_right_2.getFxImage());
                animal.setSwap(1);
            }
        }
    }
    /**
        * Bắt đầu quá trình di chuyển sang chéo lên trên cho đối tượng {@code Doll}.
         *
         * Nếu bị chặn trên và chặn trái, hàm sẽ thiết lập hướng và số bước đếm cần di chuyển, sau đó
        * gọi {@code checkRun} để bắt đầu thực thi bước đầu tiên.
         *
         */
    public static void upLeft(Animal animal) {
        if ((animal instanceof Ballom || animal instanceof Oneal
                || animal instanceof Kondoria || animal instanceof Doll)
                && Blocked.block_up(animal) && Blocked.block_left(animal))
        {
            animal.setDirection("up_left");
            animal.setCount(8);
            checkRun(animal);
        }
    }

    public static void upRight(Animal animal) {
        if ((animal instanceof Ballom || animal instanceof Oneal
                || animal instanceof Kondoria || animal instanceof Doll)
                && Blocked.block_up(animal) && Blocked.block_right(animal))
        {
            animal.setDirection("up_right");
            animal.setCount(8);
            checkRun(animal);
        }
    }

    public static void downLeft(Animal animal) {
        if ((animal instanceof Ballom || animal instanceof Oneal
                || animal instanceof Kondoria || animal instanceof Doll)
                && Blocked.block_down(animal) && Blocked.block_left(animal))
        {
            animal.setDirection("down_left");
            animal.setCount(8);
            checkRun(animal);
        }
    }

    public static void downRight(Animal animal) {
        if ((animal instanceof Ballom || animal instanceof Oneal
                || animal instanceof Kondoria || animal instanceof Doll)
                && Blocked.block_down(animal) && Blocked.block_right(animal))
        {
            animal.setDirection("down_right");
            animal.setCount(8);
            checkRun(animal);
        }
    }
}