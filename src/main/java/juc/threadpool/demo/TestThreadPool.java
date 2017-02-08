/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc.threadpool.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author fangbz
 * @version $Id: TestThreadPool, v0.1 2017年02月08日 上午8:12 fangbz Exp $
 */
public class TestThreadPool {

    public static void main(String[] args) throws Exception {

        // 1.创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        List<Future<Integer>> list = new ArrayList<Future<Integer>>();

        for (int i = 0; i < 10; i++) {
            Future<Integer> future = pool.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    int sum = 0;
                    for (int i = 0; i <= 100; i++) {
                        sum += i;
                    }
                    return sum;
                }
            });

            list.add(future);
        }

        for (Future<Integer> future : list) {
            System.out.println(future.get());
        }

        pool.shutdown();
    }
}