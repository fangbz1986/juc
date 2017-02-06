/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc.volatilekeyword;

/*
 * 一、volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 * 					  相较于 synchronized 是一种较为轻量级的同步策略。
 *
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. volatile 不能保证变量的“原子性”
 */
public class TestVolatile03 {

    public static void main(String[] args) {
        ThreadDemo02 threadDemo02 = new ThreadDemo02();
        new Thread(threadDemo02).start();

        while (true) {
            if (threadDemo02.isFlag()) {
                System.out.println("-----------------");
                break;
            }
        }
    }

}

class ThreadDemo03 implements Runnable {

    private volatile boolean flag = false;

    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag=" + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}