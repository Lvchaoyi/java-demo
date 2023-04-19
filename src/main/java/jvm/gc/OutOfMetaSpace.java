package jvm.gc;

/**
 * @author chaoyi.lv
 * @date 2023/4/19
 */
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;
//元空间内存溢出有以下几种原因
//        加载的类太多
//        加载了重复的类
//        Large classes
//        类加载器泄漏
// 设置元空间大小 XX:MetaspaceSize=50M -XX:MaxMetaspaceSize=50M
// step1.
// step2.jmap -dump:format=b,file=meta2.hprof pid 导出堆栈信息
// step3.在mat（idea里也能看）里可以看到很多Classtest开头的类但是没有对象创建
public class OutOfMetaSpace extends ClassLoader {
    public static List<Class<?>> createClasses() {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (int i = 0; i < 100; ++i) {
            ClassWriter cw = new ClassWriter(0);
            cw.visit(Opcodes.V1_1, Opcodes.ACC_PUBLIC, "Classtest" + i, null, "java/lang/Object", null);
            MethodVisitor mw = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mw.visitVarInsn(Opcodes.ALOAD, 0);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
            mw.visitInsn(Opcodes.RETURN);
            mw.visitMaxs(1, 1);
            mw.visitEnd();
            OutOfMetaSpace test = new OutOfMetaSpace();
            byte[] code = cw.toByteArray();
            Class<?> exampleClass = test.defineClass("Classtest" + i, code, 0, code.length);
            classes.add(exampleClass);
        }
        return classes;
    }

    public static void main(String[] args) {
        List<Class<?>> list = new ArrayList<Class<?>>();
        while (true) {
            list.addAll(OutOfMetaSpace.createClasses());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}