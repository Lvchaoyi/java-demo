package designpattern.proxy;

// 被代理的对象
public class Target implements Feature {

  @Override
  public void doSomething() {
    System.out.println("被代理对象开始做事了。。。");
  }
}
