package com.ohh.concurrent1.chapter1;

public class TryConcurrency {

    public static void main(String[] args) {
        new Thread("Read-Thread") {
            @Override
            public void run() {
                readFromDatabase();
            }
        }.start();

        new Thread("Write-Thread") {
            @Override
            public void run() {
                writeDataToFile();
            }
        }.start();
    }

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
