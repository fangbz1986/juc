/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package juc;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author fangbz
 * @version $Id: TestForkJoinPool, v0.1 2017年02月08日 下午10:21 fangbz Exp $
 */
public class TestForkJoinPool {

    public static void main(String[] args) {

        ///Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();

        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, 50000000000L);

        Long sum = pool.invoke(task);
        System.out.println(sum);

        // Instant end = Instant.now();
        //  System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());//166-1996-10590

    }
}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

    // 起始值
    private long              start;
    // 结束值
    private long              end;

    private static final long THURSHOLD = 10000L; //临界值

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    protected Long compute() {

        long length = end - start;

        /**
         * 小于阈值，进行计算
         * 大于阈值，继续拆分
         */
        if (length <= THURSHOLD) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;

            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork(); //进行拆分，同时压入线程队列

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork(); //

            return left.join() + right.join();
        }

    }
}