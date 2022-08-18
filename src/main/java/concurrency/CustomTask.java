package concurrency;

import java.util.Date;

public class CustomTask implements Runnable {

  @Override
  public void run() {
    while (true) {
      System.out.println(Thread.currentThread().getName() + " is running..." + new Date());
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
