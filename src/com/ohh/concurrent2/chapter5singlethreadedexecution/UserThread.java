package com.ohh.concurrent2.chapter5singlethreadedexecution;

/**
 * 模拟一个人，这个人不停的通过一个门
 *
 * @author Gary
 */
public class UserThread extends Thread {

    private final String myName;
    private final String myAddress;
    private final Gate gate;

    public UserThread(String myName, String myAddress, Gate gate) {
        this.myName = myName;
        this.myAddress = myAddress;
        this.gate = gate;
    }

    /**
     * 模拟一个人不停地通过一个门的操作
     */
    @Override
    public void run() {
        while (true) {
            this.gate.pass(this.myName, this.myAddress);
        }
    }
}
