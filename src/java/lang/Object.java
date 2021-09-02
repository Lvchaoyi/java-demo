package java.lang;

/**
 * 用于测试双亲委派如何避免同名类的加载
 *
 * @author lvcy
 * @since 2021/9/1
 */
public class Object {

  void print() {
    System.out.println("It's a fake Object class.");
  }

}
