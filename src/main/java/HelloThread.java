import java.util.concurrent.ExecutionException;

public class HelloThread {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建线程
        Thread myThread;

        // 1. 继承/匿名类
        myThread = new Thread() {
            @Override
            public void run() {
                System.out.println("你好");
            }
        };
        myThread.start();

        // 2. Runnable
        myThread = new Thread(() -> System.out.println("你好"));
        myThread.start();

        // 如果结合起来会怎么样
        myThread = new Thread(() -> System.out.println("runnable")) {
            @Override
            public void run() {
                System.out.println("override");
            }
        };
        myThread.start();

    }

}
