package com.wgy.concurrent2.chapter18;

public final class ActiveObjectFactory {
    private ActiveObjectFactory() {

    }

    public static ActiveObject createActiveObject() {
        Servant servant = new Servant();
        ActivationQueue queue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(queue);
        schedulerThread.setName("SchedulerThread");
        ActiveObjectProxy proxy = new ActiveObjectProxy(schedulerThread, servant);
        schedulerThread.start();
        return proxy;
    }
}
