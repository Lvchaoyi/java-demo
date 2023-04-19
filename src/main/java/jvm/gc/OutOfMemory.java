package jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chaoyi.lv
 * @date 2023/4/19
 */
public class OutOfMemory {

// 设置堆内存参数
// -Xmx20M -Xms20M  -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=heap.hprof
// 初始堆最大堆为20M，且发生异常时，会自动导出heap.hprof文件
//    可将其使用jvisualvm解析(下载地址：https://visualvm.github.io/download.html)，可得知哪里发生了堆内存异常，并可得知此时java服务中有哪些大对象（如果是自定义的对象，需要特别注意）
//    亦可用mat分析，导入heap.hprof文件，点击Leak Suspects（涉嫌泄漏信息）
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        while (true) {
            list.add(new byte[1024]);
        }
    }

}
