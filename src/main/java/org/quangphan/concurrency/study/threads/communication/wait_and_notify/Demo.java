package org.quangphan.concurrency.study.threads.communication.wait_and_notify;

class Process {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Running on produce method ...");
            wait();
            System.out.println("Back to produce method");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            System.out.println("Running on consume method");
            notify();
            Thread.sleep(5000);
        }
    }
}

public class Demo {

    public static void main(String[] args) {

        Process process = new Process();

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
