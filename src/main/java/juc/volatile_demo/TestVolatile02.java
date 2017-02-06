/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc.volatile_demo;

/**
 *  使用synchronized也可以实现同样的功能，但是性能和volatile比起来，效率比较低
 * @author fangbz
 * @version $Id: TestVolatile02, v0.1 2017年02月06日 下午9:56 fangbz Exp $
 */
public class TestVolatile02 {

    public static void main(String[] args) {
        ThreadDemo03 threadDemo02 = new ThreadDemo03();
        new Thread(threadDemo02).start();

        while (true) {
            synchronized (threadDemo02) {
                if (threadDemo02.isFlag()) {
                    System.out.println("-----------------");
                    break;
                }
            }
        }
    }
}

class ThreadDemo02 implements Runnable {

    private boolean flag = false;

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