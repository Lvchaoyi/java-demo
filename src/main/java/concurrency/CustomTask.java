package concurrency;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

//public class CustomTask implements Runnable {
//
//  @Override
//  public void run() {
//    while (true) {
//      System.out.println(Thread.currentThread().getName() + " is running..." + new Date());
//      try {
//        Thread.sleep(1000);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    }
//  }
//}

public class CustomTask implements Callable {


  @Override
  public Object call() throws Exception {
    while (true) {
      System.out.println(Thread.currentThread().getName() + " is running..." + new Date());
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    new Thread(new FutureTask<>(new CustomTask())).start();
  }
}
