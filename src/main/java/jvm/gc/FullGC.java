package jvm.gc;

/**
 * @author chaoyi.lv
 * @date 2023/4/19
 */

// jstat -gcutil pid 1000 10 查看gc信息
//     gc频繁会引发cpu使用率升高，因此cpu过高时，也需要考虑此种情况。
//    排查方式与内存溢出相同，主要排查大对象，并结合代码分析
public class FullGC {

    public static void main(String[] args) {
        while (true) {
            // 不断产生新的对象，导致老年代被挤满？
            byte[] bytes= new byte[102400];
        }
    }
}
