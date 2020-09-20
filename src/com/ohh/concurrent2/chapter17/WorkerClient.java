package com.ohh.concurrent2.chapter17;

public class WorkerClient {
    public static void main(String[] args) {
        final Channel channel = new Channel(5);
        channel.startWorker();
        TransportThread transportThread = new TransportThread("Gary", channel);
        TransportThread transportThread2 = new TransportThread("Alex", channel);
        transportThread.start();
        transportThread2.start();
    }
}
