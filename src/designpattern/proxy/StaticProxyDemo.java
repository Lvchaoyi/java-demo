package designpattern.proxy;
/**
 * 静态代理，就是在代码编译之前定义好的代理类
 *
 * @author lvcy
 * @since 2021/9/16
 */
public class StaticProxyDemo implements Feature {

  private Feature target;

  StaticProxyDemo(Feature target) {
    this.target = target;
  }

  @Override
  public void doSomething() {
    System.out.println("静态代理类提前做事。。。");
    this.target.doSomething();
    System.out.println("静态代理类之后做事。。。");
  }

  public static void main(String[] args) {
    new StaticProxyDemo(new Target()).doSomething();
  }
}
