/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc.lock.demo;

/**
 * @author fangbz
 * @version $Id: TestLock01, v0.1 2017年02月07日 上午7:59 fangbz Exp $
 */
public class TestLock01 {

    public static void main(String[] args) {
        Ticket01 ticket = new Ticket01();
        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();
    }
}

class Ticket01 implements Runnable {

    private int tick = 100;

    public void run() {
        while (true) {

            if (tick > 0) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }

                System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
            }
        }
    }

}