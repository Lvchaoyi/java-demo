package designpattern.structure.decorator;

public class Decorator implements Future {

  Future target;

  // 最重要的就是被装饰对象是由外界控制和传入的
  // 这点和代理模式（静态代理）不一样

  Decorator(Future target) {
    this.target = target;
  }

  @Override
  public void doSomething() {
    System.out.println("decorator do something...");
    target.doSomething();
  }

  public static void main(String[] args) {
    Future target = new Target();
    Future decorator = new Decorator(target);
    decorator.doSomething();
  }
}


interface Future {
  public void doSomething();
}

class Target implements Future {

  @Override
  public void doSomething() {
    System.out.println("Target do something...");
  }
}

