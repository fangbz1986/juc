/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc.atomic.demo;

/**
 *
 * 模拟cas算法
 * @author fangbz
 * @version $Id: TestCompareAndSwap, v0.1 2017年02月06日 下午10:52 fangbz Exp $
 */
public class TestCompareAndSwap {

    public static void main(String[] args) {

        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 101));
                    System.out.println(b + "--" + expectedValue);
                }
            }).start();
        }

    }
}

class CompareAndSwap {

    private int value;

    //获取内存值
    public synchronized int get() {
        return value;
    }

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            this.value = newValue;
        }
        return oldValue;
    }

    //设置
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}