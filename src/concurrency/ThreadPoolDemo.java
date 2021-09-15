package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {

  public static void main(String[] args) throws InterruptedException {
//    FixedExecutorDemo.init();
    ScheduledExecutorDemo.init();
  }

}

class FixedExecutorDemo {

  // OOM演示
  static void init() throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    while (true) {
      executorService.submit(new CustomTask());
    }
  }

}

class CachedExecutorDemo {

  static void init() throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    while (true) {
      executorService.submit(new CustomTask());
    }
  }

}

class ScheduledExecutorDemo {

  static void init() throws InterruptedException {
    ExecutorService executorService = Executors.newScheduledThreadPool(10);
    for (int i = 0; i < 10; i ++) {
      executorService.submit(new CustomTask());
    }
  }

}
