package com.ohh.concurrent2.chapter9;

public class SuspensionClient {
    public static void main(String[] args) throws InterruptedException {
        RequestQueue requestQueue = new RequestQueue();
        ClientThread clientThread = new ClientThread(requestQueue, "Gary");
        ServerThread serverThread = new ServerThread(requestQueue);
        clientThread.start();
        serverThread.start();

        clientThread.join();
        while (serverThread.getState().equals(Thread.State.RUNNABLE) || serverThread.getState().equals(Thread.State.TIMED_WAITING)) {
            Thread.sleep(1000);
        }
        serverThread.close();
    }
}
