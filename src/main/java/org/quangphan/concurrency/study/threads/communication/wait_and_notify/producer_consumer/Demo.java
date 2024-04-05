package org.quangphan.concurrency.study.threads.communication.wait_and_notify.producer_consumer;

import java.util.ArrayList;
import java.util.List;

class Processor {

    private List<Integer> list = new ArrayList<>();
    private static final int UPPER_LIMIT = 5;
    private static final int LOWER_LIMIT = 0;
    private final Object lock = new Object();
    private int value = 0;

    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == UPPER_LIMIT) {
                    System.out.println("Waiting for remove items...");
                    lock.wait();
                } else {
                    System.out.println("Adding: " + value);
                    list.add(value);
                    value++;
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == LOWER_LIMIT) {
                    System.out.println("Waiting to add new items...");
                    lock.wait();
                } else {
                    System.out.println("Remove item: " + list.remove(list.size() - 1));
                    value--;
                    lock.notify();
                    // do other operations
                }
                Thread.sleep(500);
            }
        }
    }
}

public class Demo {

    public static void main(String[] args) {

        Processor process = new Processor();

        Thread thread1 = new Thread(() -> {
                try {
                    process.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        });

        Thread thread2 = new Thread(() -> {
            try {
                process.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();
    }
}
