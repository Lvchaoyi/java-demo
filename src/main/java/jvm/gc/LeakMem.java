package jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chaoyi.lv
 * @date 2023/4/19
 */
public class LeakMem {

    // 排查方式同内存溢出
    private static List<byte[]> lists;
    public static void main(String[] args) {
        lists = new ArrayList<>();
        lists.add(new byte[202400]);
        while (true) {
        }
    }

}
