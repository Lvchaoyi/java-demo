package concurrency.question;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程轮流执行，一个线程打印奇数，一个线程打印偶数，10000以内
 *
 * @author lvcy
 * @since 2021/9/27
 */
public class RunInTurn {

  static final Object lock = new Object();
  static AtomicInteger atomicInteger = new AtomicInteger(0);
  static Integer count = 0;
  static Lock reentrantLock = new ReentrantLock();
  static Condition condition = reentrantLock.newCondition();
  static Semaphore semaphore = new Semaphore(1);

  static class Task3 implements Runnable {

    @Override
    public void run() {
      synchronized (lock) {
        for (int i = 0; i < 5; i ++) {
          System.out.println("线程1打印：" + (2 * i + 1));
        }
        lock.notify();
      }
    }
  }

  static class Task4 implements Runnable {

    @Override
    public void run() {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      synchronized (lock) {
        // 线程2先进入阻塞，等待线程1执行完再唤醒
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        for (int i = 0; i < 5; i ++) {
          System.out.println("线程2打印：" + 2 * i);
        }
      }
    }
  }

//  看起来task5每次执行完都会notify唤醒task6，但是这只是保证task6是就绪状态，并不能确保task6能够被优先执行
  static class Task5 implements Runnable {

    @Override
    public void run() {
      for (int i = 0; i < 5000; i ++) {
        synchronized (lock) {
          System.out.println("线程1打印：" + (2 * i + 1));
          lock.notify();
        }
      }
    }
  }

  static class Task6 implements Runnable {

    @Override
    public void run() {
      for (int i = 0; i < 5000; i++) {
        synchronized (lock) {
          try {
            lock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println("线程2打印：" + 2 * i);
        }
      }
    }
  }
  static class NumTask implements Runnable {

//    总计循环次数
    private int loopCount;
//    总计处理线程数
    private int taskCount;
//    被除数（第几个线程）
    private int dividend;

    NumTask(int loopCount, int taskCount, int dividend) {
      this.loopCount = loopCount;
      this.taskCount = taskCount;
      this.dividend = dividend;
    }

    @Override
    public void run() {}

    public int getLoopCount() {
      return loopCount;
    }

    public int getTaskCount() {
      return taskCount;
    }

    public int getDividend() {
      return dividend;
    }
  }


  static class LockNumTask extends NumTask {

    LockNumTask(int loopCount, int taskCount, int dividend) {
      super(loopCount, taskCount, dividend);
    }

    @Override
    public void run() {
      while (count < getLoopCount()) {
        reentrantLock.lock();
        if (count % getTaskCount() == getDividend()) {
          System.out.println("线程" + getDividend() + "打印：" + count ++);
        }
        reentrantLock.unlock();
      }
    }
  }

  static class SyncNumTask extends NumTask {

    SyncNumTask(int loopCount, int taskCount, int dividend) {
      super(loopCount, taskCount, dividend);
    }

    @Override
    public void run() {
      while (count < getLoopCount()) {
        synchronized (lock) {
          if (count % getTaskCount() == getDividend()) {
            System.out.println("线程" + getDividend() + "打印：" + count ++);
          }
        }
      }
    }
  }

  static class NotifyNumTask extends NumTask {

    NotifyNumTask(int loopCount, int taskCount, int dividend) {
      super(loopCount, taskCount, dividend);
    }

    @Override
    public void run() {
      synchronized (lock) {
        while (count < getLoopCount()) {
          if (count % getTaskCount() != getDividend()) {
            try {
              lock.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } else {
            System.out.println("线程" + getDividend() + "打印：" + count ++);
            lock.notifyAll();
          }
        }
      }
    }
  }

  static class ConditionNumTask extends NumTask {

    ConditionNumTask(int loopCount, int taskCount, int dividend) {
      super(loopCount, taskCount, dividend);
    }

    @Override
    public void run() {
      reentrantLock.lock();
      while (count < getLoopCount()) {
        if (count % getTaskCount() == getDividend()) {
          System.out.println("线程" + getDividend() + "打印：" + count ++);
          condition.signalAll();
        } else {
          try {
            condition.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
      reentrantLock.unlock();
    }
  }

  static class AtomicNumTask extends NumTask {

    AtomicNumTask(int loopCount, int taskCount, int dividend) {
      super(loopCount, taskCount, dividend);
    }

    @Override
    public void run() {
      int k;
      while ((k = atomicInteger.get()) < getLoopCount()) {
        if (k % getTaskCount() == getDividend()) {
          System.out.println("线程" + getDividend() + "打印：" + k);
//          TODO 为什么把上面一句去掉就会乱序？
          System.out.println("线程" + getDividend() + "额外打印：" + atomicInteger.getAndIncrement());
        }
      }
    }
  }

  static class SemaphoreNumTask extends NumTask {

    SemaphoreNumTask(int loopCount, int taskCount, int dividend) {
      super(loopCount, taskCount, dividend);
    }

    @Override
    public void run() {
      while (true) {
        try {
          semaphore.acquire();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (count >= getLoopCount()) {
          semaphore.release();
          break;
        }
        if (count % getTaskCount() == getDividend()) {
          System.out.println("线程" + getDividend() + "打印：" + count++);
        }
        semaphore.release();
      }
    }
  }

  static class QueueNumTask extends NumTask {

    private BlockingQueue<Integer> queue;
    private BlockingQueue<Integer> nextQueue;

    QueueNumTask(int loopCount, int taskCount, int dividend, BlockingQueue<Integer> queue, BlockingQueue<Integer> nextQueue) {
      super(loopCount, taskCount, dividend);
      this.queue = queue;
      this.nextQueue = nextQueue;
    }

    @Override
    public void run() {

      while (true) {
        try {
          // 从本地队列中取出元素
          int num = queue.take();
          System.out.println("线程" + getDividend() + "打印：" + num);
          if (num >= getLoopCount()) {
            break;
          }
          // 把新增的元素放入下一个队列中
          nextQueue.put(num + 1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }







  public static void main(String[] args) {
//    1.随机执行（直接两个Thread start就行了）
//    new Thread(new Task1()).start();
//    new Thread(new Task2()).start();
//    2.线程1执行完线程2再执行（wait/notify,需要设置共用的锁，哪个线程先start都没关系，反正线程2被阻塞了）
//    fix: 必须先wait再notify，否则wait之后没人唤醒一直阻塞，所以顺序还是有关系的
//    new Thread(new Task4()).start();
//    new Thread(new Task3()).start();
//    3.join方法（注意join是用线程Thread对象去调用的，谁调谁先执行）
//    Thread A = new Thread(new Runnable() {
//      @Override
//      public void run() {
//        for (int i = 0; i < 5000; i ++) {
//          System.out.println("线程1打印：" + (2 * i + 1));
//        }
//      }
//    });
//    Thread B = new Thread(new Runnable() {
//      @Override
//      public void run() {
//        try {
//          A.join();
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//        for (int i = 0; i < 5000; i ++) {
//          System.out.println("线程2打印：" + (2 * i));
//        }
//      }
//    });
//    A.start();
//    B.start();
//    4.交替执行，线程1打印奇数，线程2打印偶数，以此类推
//    简单的wait notify搭配并不靠谱，因为notify后不能保证线程2的优先级最高
//    new Thread(new Task5()).start();
//    new Thread(new Task6()).start();
//    只用synchronized修饰lock就可以，虽然每个线程循环的次数可能不尽相同，但是能够确保打印的数字是一步一步依次递增的
//    不能用count当锁，因为count没有用final修饰，synchronized同步代码段里会直接修改count指向的对象，然后多个线程同时进入同步代码段，但是final修饰了以后++会提示错误（不可修改引用地址）
//    所以只能用object当锁
//    new Thread(new SyncNumTask(100, 3, 0)).start();
//    new Thread(new SyncNumTask(100, 3, 1)).start();
//    new Thread(new SyncNumTask(100, 3, 2)).start();
//    5.通过wait和nofityAll（synchronized可以修饰在外侧，因为wait notify其实已经相同于多个线程同时进入同步区了，只是有的放在lock对象的等待队列中而已，不过java可能也会执行锁粗化吧？）
//    new Thread(new NotifyNumTask(100, 3, 0)).start();
//    new Thread(new NotifyNumTask(100, 3, 1)).start();
//    new Thread(new NotifyNumTask(100, 3, 2)).start();
//    6.通过lock
//    new Thread(new LockNumTask(100, 3, 0)).start();
//    new Thread(new LockNumTask(100, 3, 1)).start();
//    new Thread(new LockNumTask(100, 3, 2)).start();
//    7.通过lock的condition(注意，lock相当于synchronized，调用了lock()才能调用condition的await)
//    new Thread(new ConditionNumTask(100, 3, 0)).start();
//    new Thread(new ConditionNumTask(100, 3, 1)).start();
//    new Thread(new ConditionNumTask(100, 3, 2)).start();
//    8.通过atomic类替代synchronized
//    new Thread(new AtomicNumTask(100, 3, 0)).start();
//    new Thread(new AtomicNumTask(100, 3, 1)).start();
//    new Thread(new AtomicNumTask(100, 3, 2)).start();
//    9.通过信号量表明一次只有一个线程访问count，相当于是互斥量或者是锁，注意防止额外多输出，可以把break条件放while循环里
//    new Thread(new SemaphoreNumTask(100, 3, 0)).start();
//    new Thread(new SemaphoreNumTask(100, 3, 1)).start();
//    new Thread(new SemaphoreNumTask(100, 3, 2)).start();
//    10.通过阻塞队列，每个线程从阻塞队列中取出数，+1放入下一个队列中
    BlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();
    BlockingQueue<Integer> queue2 = new LinkedBlockingQueue<>();
    BlockingQueue<Integer> queue3 = new LinkedBlockingQueue<>();
    queue1.add(0);
    new Thread(new QueueNumTask(100, 3, 0, queue1, queue2)).start();
    new Thread(new QueueNumTask(100, 3, 1, queue2, queue3)).start();
    new Thread(new QueueNumTask(100, 3, 2, queue3, queue1)).start();

  }

}
