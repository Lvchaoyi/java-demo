package jvm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReferenceDemo {

    public static void main(String[] args) {
        Object o = new Object();
        ReferenceQueue queue = new ReferenceQueue();

        WeakReference weakObj = new WeakReference<Object>(o);

    }
}
