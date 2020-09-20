package com.ohh.concurrent2.chapter4;

public class ObserverClient {

    public static void main(String[] args) {
        final Subject subject = new Subject();
        new BinaryObserver(subject);
        new OctalObserver(subject);

        subject.setState(10);
        subject.setState(10);
        subject.setState(15);
    }

}
