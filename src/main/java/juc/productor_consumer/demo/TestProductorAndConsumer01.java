/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc.productor_consumer.demo;

/**
 * @author fangbz
 * @version $Id: TestProductorAndConsumer01, v0.1 2017年02月07日 下午10:06 fangbz Exp $
 */
public class TestProductorAndConsumer01 {
    public static void main(String[] args) {

        Clerk01 clerk = new Clerk01();

        Productor01 pro = new Productor01(clerk);
        Consumer01 cus = new Consumer01(clerk);

        new Thread(pro, "生产者 A").start();
        new Thread(cus, "消费者 B").start();

        new Thread(pro, "生产者 C").start();
        new Thread(cus, "消费者 D").start();
    }
}

/**
 * 店员
 */
class Clerk01 {
    private int product = 0;

    //进货
    public synchronized void get() {
        while (product >= 1) {
            System.out.println("产品已满");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + ":" + ++product);
        this.notifyAll();
    }

    //卖货
    public synchronized void sale() {
        while (product <= 0) {
            System.out.println("产品缺货");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + ":" + --product);
        this.notifyAll();
    }
}

//生产者
class Productor01 implements Runnable {
    private Clerk01 clerk;

    public Productor01(Clerk01 clerk) {
        this.clerk = clerk;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }

            clerk.get();
        }
    }
}

//消费者
class Consumer01 implements Runnable {
    private Clerk01 clerk;

    public Consumer01(Clerk01 clerk) {
        this.clerk = clerk;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}
