import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: 方便测试
 * @author: wczy9
 * @createTime: 2023-01-21  17:26
 */
public class NoSpring {
    public static void main(String[] args) throws InterruptedException {

        List<String> list = new ArrayList<>();

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add("AAA arraylist concurrent modify: " + i);
            }
        }, "threadA:::").start();

        new Thread(() -> {
            for (int i = 1000; i < 2000; i++) {
                list.add("BBB arraylist concurrent modify: " + i);
            }
        }, "threadB:::").start();

        Thread.sleep(5000L);

        for (int i = 0; i < list.size(); i++) {
            System.out.println("第" + (i + 1) + "个元素为：" + list.get(i));
        }

    }
}
