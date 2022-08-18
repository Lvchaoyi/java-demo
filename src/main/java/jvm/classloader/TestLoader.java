package jvm.classloader;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestLoader {


  public static void main(String[] args)
      throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    System.out.println(Object.class.getClassLoader());

    // 验证双亲委派机制能够避免同名类的加载
    Class<?> clazz = Class.forName("java.lang.Object");
    System.out.println(clazz.getClassLoader());
    Method method = clazz.getMethod("print");
    Object o = clazz.newInstance();
    method.invoke(o);

    // 通过自定义类加载器，1：加载同名类（失败，其实加载的是默认Object类）2：加载classpath下的类（由于双亲委派机制，其实是AppClassLoader进行加载的）
//    CustomClassLoader loader = new CustomClassLoader();
//    Class<?> clazz1 = loader.loadClass("java.lang.Object");
//    Class<?> clazz2 = loader.loadClass("jvm.classloader.BeLoader");
//    System.out.println((clazz1.getClassLoader()));
//    clazz1.getMethod("print").invoke(clazz1.newInstance());
  }

}
