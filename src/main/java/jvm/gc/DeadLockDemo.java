package jvm.gc;

/**
 * @author chaoyi.lv
 * @date 2023/4/19
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        DeadLock d1=new DeadLock(true);
        DeadLock d2=new DeadLock(false);
        Thread t1=new Thread(d1);
        Thread t2=new Thread(d2);
        t1.start();
        t2.start();
    }
}
// jstack分析java进程线程栈信息( 按照jps命令得知输入jstack 64768)，在线程栈信息的最后输出死锁结论
//定义锁对象
class  MyLock{
    public static Object obj1=new Object();
    public static Object obj2=new Object();
}
//死锁代码
class DeadLock implements Runnable{
    private boolean flag;
    DeadLock(boolean flag){
        this.flag=flag;
    }
    public void run() {
        if(flag) {
            while(true) {
                synchronized(MyLock.obj1) {
                    System.out.println(Thread.currentThread().getName()+"----if 获得obj1锁");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized(MyLock.obj2) {
                        System.out.println(Thread.currentThread().getName()+"--- -if获得obj2锁");
                    }
                }
            }
        }else {
            while(true){
                synchronized(MyLock.obj2) {
                    System.out.println(Thread.currentThread().getName()+"----否则 获得obj2锁");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized(MyLock.obj1) {
                        System.out.println(Thread.currentThread().getName()+"--- -否则获得obj1锁");
                    }
                }
            }
        }
    }
}