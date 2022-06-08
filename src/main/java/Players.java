import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Players {

    private static final Random RANDOM = new Random();
    private static final int[] PLAYERS = new int[3];
    private static final int[] STEP = new int[]{1, 2, 5, 50};
    private static final CountDownLatch LATCH = new CountDownLatch(3);
    private static final List<Integer> RESULT = Collections.synchronizedList(new LinkedList<>());

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        // 展示板线程
        service.execute(Players::look);
        // 逻辑线程
        for (int i = 0; i < PLAYERS.length; i++) {
            service.execute(compute(i));
        }
        // 展示结果
        showResult();
        service.shutdown();
    }

    private static Runnable compute(int i) {
        return () -> {
            while (PLAYERS[i] < 100) {
                move(i);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 录入成绩
            RESULT.add(i);
            LATCH.countDown();
        };
    }

    private static void look() {
        try {
            while (!LATCH.await(100, TimeUnit.MILLISECONDS)) {
                // 打印轨迹
                print();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void showResult() throws InterruptedException, IOException {
        LATCH.await();
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.printf("排名为%s", RESULT);
    }

    private static void print() throws InterruptedException, IOException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        int[] ints = Players.PLAYERS;
        for (int i = 0; i < ints.length; i++) {
            System.out.printf("%d 号 %s%s%n", i, "=".repeat(ints[i]), ">");
        }
        TimeUnit.SECONDS.sleep(1);
    }

    private static void move(int i) {
        Players.PLAYERS[i] += STEP[RANDOM.nextInt(4)];
        Players.PLAYERS[i] = Math.min(100, Players.PLAYERS[i]);
    }

}
