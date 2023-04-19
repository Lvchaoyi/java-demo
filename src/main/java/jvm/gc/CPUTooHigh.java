package jvm.gc;

/**
 * @author chaoyi.lv
 * @date 2023/4/19
 */
public class CPUTooHigh {

    public static void main(String[] args) {
        CPUThread thread1 = new CPUThread();
        CPUThread thread2 = new CPUThread();
        Thread t1 = new Thread(thread1);
        Thread t2 = new Thread(thread2);
        t1.start();
        t2.start();
    }

}

// 空线程循环
class CPUThread implements Runnable {

    @Override
    public void run() {
        while (true) {

        }
    }
}
//问题排查流程
//step1: jps查看Java服务列表
//step2: top -pid <processid> 查看上述进程CPU消耗，结果200%左右（因为有两个线程）
//step3: ps -M pid查看进程下的线程
//step4: jstack pid | grep -A 10 tid定位代码
