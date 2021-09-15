package concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 思路：
 * 先确定任务执行的流程
 * 查看线程池线程是否已满，已满则进入工作队列（无界队列）
 * 未满通过线程执行任务的run方法
 * 执行完成之后修改当前线程数
 *
 * 问题：线程如何复用？新建工作线程类，从任务队列中获取任务
 * 线程将任务执行完后，如何从队列中取得下一个任务 -> 创建工作线程
 *
 * @author lvcy
 * @since 2021/9/14
 */
public class CustomThreadPoolExecutor implements Executor {

  private final AtomicInteger ctl;

  private final int corePoolSize;

  private final BlockingQueue<Runnable> workQueue;

  CustomThreadPoolExecutor(int corePoolSize, BlockingQueue<Runnable> workQueue) {
    this.corePoolSize = corePoolSize;
    this.workQueue = workQueue;
    this.ctl = new AtomicInteger(0);
  }

  @Override
  public void execute(Runnable command) {
    if (ctl.get() >= corePoolSize) {
      if (!workQueue.offer(command)) {
        System.out.println("task reject...");
      }
    } else {
      addWorker(command);
    }
  }

  public void addWorker(Runnable command) {
//    新建任务执行线程
    new Thread(new Worker(command)).start();
  }

  private final class Worker implements Runnable {

    Runnable task;

    Worker(Runnable task) {
      this.task = task;
    }

    @Override
    public void run() {
//      开始前作为
      ctl.incrementAndGet();
//      不断从任务队列中获取任务执行任务
      while ((task = getTask()) != null) {
        task.run();
      }
      ctl.decrementAndGet();
    }

    // 从阻塞队列中获取方法
    public Runnable getTask() {
      try {
        return workQueue.take();
      } catch (InterruptedException e) {
        return null;
      }
    }
  }

  public static void main(String[] args) {
    CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(5, new LinkedBlockingQueue<>());
    for (int i = 0; i < 100; i ++) {
      executor.execute(new Runnable() {
        @Override
        public void run() {
          for (int i = 0; i < 10; i ++) {
            System.out.println(Thread.currentThread().getName() + "is running...");
          }
        }
      });
    }
  }


}


