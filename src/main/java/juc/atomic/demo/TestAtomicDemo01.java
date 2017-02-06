/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc.atomic.demo;

/**
 *
 *   一、i++ 的原子性问题：i++ 的操作实际上分为三个步骤“读-改-写”
 * 		  int i = 10;
 * 		  i = i++; //10
 *
 * 		  int temp = i;
 * 		  i = i + 1;
 * 		  i = temp;
 *
 *  多次运行，会出现同样的值，如下
 *   2
 *   1
 *   4
 *   3
 *   0
 *   6
 *   8
 *   7
 *   5
 */
public class TestAtomicDemo01 {

    public static void main(String[] args) {

        AtomicDemo01 atomicDemo01 = new AtomicDemo01();

        for (int i = 0; i < 9; i++) {
            new Thread(atomicDemo01).start();
        }

    }
}

class AtomicDemo01 implements Runnable {

    private volatile int serialNumber = 0;

    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        System.out.println(getSerialNumber());
    }

    public int getSerialNumber() {
        return serialNumber++;
    }

}