package com.wgy.concurrent1.chapter1;

/**
 * 多线程的创建。
 */
public class TryConcurrency {

    /**
     * 通过多线程实现边从数据库读取数据，边写入磁盘的过程。
     */
    public static void main(String[] args) {
        //一个内部类，这个类重写了 Thread 类的 run 方法。
        //通过 start 方法，可以异步的使线程运行，实现 run 方法中的逻辑。
        new Thread("Read-Thread") {
            //run 方法里面就是线程执行的逻辑
            @Override
            public void run() {
                readFromDatabase();
            }
        }.start();

        //一个内部类，这个类重写了 Thread 类的 run 方法。
        new Thread("Write-Thread") {
            @Override
            public void run() {
                writeDataToFile();
            }
        }.start();
    }

    /**
     * 模拟读取文件的过程
     */
    private static void readFromDatabase() {
        //read data from database and handle it
        try {
            println("Begin read data from db.");
            Thread.sleep(1000 * 10L);
            println("Read data done and start handle it.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        println("The data handle finished and successfully.");
    }

    /**
     * 模拟写文件的过程
     */
    private static void writeDataToFile() {
        try {
            println("Begin write data to file.");
            Thread.sleep(1000 * 10L);
            println("Write data done and start handle it.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        println("The data handle finished and successfully.");
    }

    private static void println(String message) {
        System.out.println(message);
    }
}
