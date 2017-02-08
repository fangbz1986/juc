/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc.lock.demo;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 1. ReadWriteLock : 读写锁
 *
 * 写写/读写 需要“互斥”
 * 读读 不需要互斥
 *
 */
public class TestReadWriteLock {

    public static void main(String[] args) {

        final ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();

        new Thread(new Runnable() {
            public void run() {

                for (int i = 0; i < 200; i++) {
                    readWriteLockDemo.get();
                }
            }
        }, "read").start();

        new Thread(new Runnable() {
            public void run() {
                readWriteLockDemo.set(100);
            }
        }, "write").start();
    }
}

class ReadWriteLockDemo {

    private int           number = 0;
    private ReadWriteLock lock   = new ReentrantReadWriteLock();

    public void get() {
        // 上读锁
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " : " + number);
        } finally {
            lock.readLock().unlock(); //释放锁
        }
    }

    public void set(int number) {
        lock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        } finally {
            lock.writeLock().unlock();
        }
    }

}