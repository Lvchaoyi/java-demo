package designpattern.structure.proxy;
/**
 * 静态代理，就是在代码编译之前定义好的代理类
 *
 * @author lvcy
 * @since 2021/9/16
 */
public class StaticProxyDemo implements Feature {

  private Feature target;

  // 代理类一般都会屏蔽对被代理对象的访问，外界根本不知道有被代理对象的存在，这也是和装饰器模式关键的区别之一
  StaticProxyDemo() {
    this.target = new Target();
  }

  @Override
  public void doSomething() {
    System.out.println("静态代理类提前做事。。。");
    this.target.doSomething();
    System.out.println("静态代理类之后做事。。。");
  }

  public static void main(String[] args) {
    new StaticProxyDemo().doSomething();
  }
}

interface Feature {

  void doSomething();

}

// 被代理的对象
class Target implements Feature {

  @Override
  public void doSomething() {
    System.out.println("被代理对象开始做事了。。。");
  }
}

