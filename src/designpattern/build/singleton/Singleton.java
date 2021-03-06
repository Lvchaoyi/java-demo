package designpattern.build.singleton;

public class Singleton {
//    volatile 主要是为了防止另一个线程在getInstance时读取到上一个线程创建实例时由于指令重排序导致的instance未初始化（先引用指向空间，再初始化对象）的情况
    private volatile static Singleton instance;
    private Singleton() {}
    public Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
