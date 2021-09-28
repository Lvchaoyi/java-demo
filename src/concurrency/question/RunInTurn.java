package concurrency.question;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程轮流执行，一个线程打印奇数，一个线程打印偶数，10000以内
 *
 * @author lvcy
 * @since 2021/9/27
 */
public class RunInTurn {

  static Object lock = new Object();
  static AtomicInteger atomicInteger = new AtomicInteger(0);
  static Integer count = 0;

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

  static class Task7 implements Runnable {

    @Override
    public void run() {
      while (count < 100) {
        synchronized (count) {
          if (count % 3 == 1) {
            System.out.println("线程1打印：" + count ++);
          }
        }
      }
    }
  }

  static class Task8 implements Runnable {

    @Override
    public void run() {
      while (count < 100) {
        synchronized (count) {
          if (count % 3 == 2) {
            System.out.println("线程2打印：" + count ++);
          }
        }
      }
    }
  }

  static class Task9 implements Runnable {

    @Override
    public void run() {
      while (count < 100) {
        synchronized (count) {
          if (count % 3 == 0) {
            System.out.println("线程3打印：" + count ++);
          }
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
    new Thread(new Task7()).start();
    new Thread(new Task8()).start();
    new Thread(new Task9()).start();

  }

}
