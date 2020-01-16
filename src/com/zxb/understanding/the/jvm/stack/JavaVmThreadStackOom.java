package com.zxb.understanding.the.jvm.stack;

import java.util.concurrent.locks.LockSupport;

/**
 * {@link OutOfMemoryError} 创建线程过多导致栈内存溢出异常
 * VM Args: -Xss2m -Xmx5m -Xms5m
 *
 * @author Mr.zxb
 * @date 2020-01-16 15:16
 */
public class JavaVmThreadStackOom {

    private int i;

    private void dontStop() {
        // 阻塞线程
        LockSupport.park();
    }

    public void stackLeakByThread() {
        while (true) {
            i++;
            new Thread(() -> dontStop()).start();
        }
    }

    public static void main(String[] args) {
        JavaVmThreadStackOom overflowError = new JavaVmThreadStackOom();
        try {
            overflowError.stackLeakByThread();
        } catch (Throwable e) {
            System.out.printf("thread size = %s\n", overflowError.i);
            // java.lang.OutOfMemoryError: unable to create native thread:
            // possibly out of memory or process/resource limits reached
            throw e;
        }
    }
}
