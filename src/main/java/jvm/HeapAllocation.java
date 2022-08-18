package jvm;


public class HeapAllocation {

    private static final int _1MB = 1024 * 1024;

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728 -XX:+UseSerialGC
     *
     */

    public static void main(String[] args) {
        byte[] allocation = new byte[4 * _1MB];
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
