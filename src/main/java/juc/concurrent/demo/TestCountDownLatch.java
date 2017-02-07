/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc.concurrent.demo;

import java.util.concurrent.CountDownLatch;

/**
 * @author fangbz
 * @version $Id: TestCountDownLatch, v0.1 2017年02月06日 下午11:19 fangbz Exp $
 */
public class TestCountDownLatch {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(5);
        LatchDemo ld = new LatchDemo(latch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            new Thread(ld).start();
        }

        try {
            // 等待子线程执行完毕，在执行主线程
            latch.await();
        } catch (InterruptedException e) {
        }

        long end = System.currentTimeMillis();

        System.out.println("耗费时间为：" + (end - start));
    }
}

class LatchDemo implements Runnable {

    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                if (i % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + "-->" + i);
                }
            }
        } finally {
            latch.countDown();
        }

    }
}