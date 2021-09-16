package designpattern.proxy;

public class CglibProxyDemo {

  public static void main(String[] args) {
    Feature target = new Target();
    Feature proxy = (Feature) new CglibProxy(target).getProxyInstance();
    proxy.doSomething();
  }
}
