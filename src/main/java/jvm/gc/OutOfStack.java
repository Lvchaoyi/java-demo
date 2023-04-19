package jvm.gc;

/**
 * @author chaoyi.lv
 * @date 2023/4/19
 */

//Stack Space用来做方法的递归调用时压入Stack Frame(栈帧)。所以当递归调用太深的时候，就有可能耗尽Stack Space，爆出StackOverflow的错误。 -Xss128k：设置每个线程的堆栈大小。JDK 5以后每个线程堆栈大小为1M，以前每个线程堆栈大小为256K。根据应用的线 程所需内存大小进行调整。在相同物理内存下，减小这个值能生成更多的线程。但是操作系统对一个进程内的线程数还是有 限制的，不能无限生成，经验值在3000~5000左右。 线程栈的大小是个双刃剑，如果设置过小，可能会出现栈溢出，特别是在该线程内有递归、大的循环时出现溢出的可能性更 大，如果该值设置过大，就有影响到创建栈的数量，如果是多线程的应用，就会出现内存溢出的错误。
public class OutOfStack {

    public static long count = 0;

    public static void method(long i) {
        System.out.println(count++);
        method(i);
    }

    public static void main(String[] args) {
        method(1);
    }

}
