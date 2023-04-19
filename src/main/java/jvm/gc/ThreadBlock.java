package jvm.gc;

import java.util.concurrent.CountDownLatch;

/**
 * @author chaoyi.lv
 * @date 2023/4/19
 */

// step1.jps step2.jstack
//    根据线程栈信息得知，有线程处于WAITING (parking)状态，且能追踪到countDownLatch的await方法以及业务代码
//    结合代码分析可得发生的问题源于countDown没有减到0。因此一般处理线程阻塞时要注意异常情况，需将释放阻塞点的代码放入finally中：

public class ThreadBlock {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println("start: " + Thread.currentThread());
                    int j = 1 / 0;
                    //抛出异常后，就不会执行countDown（）
                    countDownLatch.countDown();
                } catch (Exception e) {
                    System.out.println("thread error");
                } finally {

                }

            }).start();
        }
        try {
            System.out.println("main await");
            countDownLatch.await();
            System.out.println("main continue");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
