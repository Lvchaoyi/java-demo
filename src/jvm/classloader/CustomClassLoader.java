package jvm.classloader;
/**
 * 自定义类加载器（用于打破双亲委派）
 *
 * @author lvcy
 * @since 2021/9/1
 */
public class CustomClassLoader extends ClassLoader {

  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    System.out.println("custom classloader start working...");
    return super.loadClass(name);
  }

}
