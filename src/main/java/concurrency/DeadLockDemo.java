package concurrency;

/**
 * 死锁
 *
 * @author chaoyi.lv
 * @date 2023/3/2
 */
public class DeadLockDemo {

    public static Object resource1 = new Object();

    public static Object resource2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (resource1) {
                System.out.println("获得资源1");
                try {
                    System.out.println("休眠1s");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (resource2) {
                    System.out.println("获得资源2");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (resource2) {
                System.out.println("获得资源2");
                try {
                    System.out.println("休眠1s");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (resource1) {
                    System.out.println("获得资源1");
                }
            }
        }).start();
    }

}
