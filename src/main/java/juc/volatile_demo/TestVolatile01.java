/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc.volatile_demo;

/**
 *  当前程序会阻塞
 * @author fangbz
 * @version $Id: TestVolatile01, v0.1 2017年02月06日 下午9:47 fangbz Exp $
 */
public class TestVolatile01 {

    public static void main(String[] args) {

        ThreadDemo01 threadDemo01 = new ThreadDemo01();
        new Thread(threadDemo01).start();

        while (true) {
            if (threadDemo01.isFlag()) {
                System.out.println("-----------------");
                break;
            }
        }

    }

}

class ThreadDemo01 implements Runnable {

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