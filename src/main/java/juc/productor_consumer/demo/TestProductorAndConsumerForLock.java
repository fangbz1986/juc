/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc.productor_consumer.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fangbz
 * @version $Id: TestProductorAndConsumerForLock, v0.1 2017年02月08日 上午9:15 fangbz Exp $
 */
public class TestProductorAndConsumerForLock {

    public static void main(String[] args) {
        ClerkForLock clerkForLock = new ClerkForLock();

        ProductorForLock pro = new ProductorForLock(clerkForLock);
        ConsumerForLock con = new ConsumerForLock(clerkForLock);

        new Thread(pro, "生产者 A").start();
        new Thread(con, "消费者 B").start();

    }
}

/**
 * 店员
 */
class ClerkForLock {

    private int       product   = 0;

    private Lock      lock      = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * 进货
     */
    public void get() {
        lock.lock();
        try {
            if (product >= 1) {
                System.out.println("库存已满！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 卖货
     */
    public void sale() {
        lock.lock();
        try {
            if (product <= 0) {
                System.out.println("缺货！");

                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

}

// 生产者
class ProductorForLock implements Runnable {

    private ClerkForLock clerk;

    public ProductorForLock(ClerkForLock clerk) {
        this.clerk = clerk;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clerk.get();
        }
    }
}

// 消费者
class ConsumerForLock implements Runnable {

    private ClerkForLock clerk;

    public ConsumerForLock(ClerkForLock clerk) {
        this.clerk = clerk;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }

}