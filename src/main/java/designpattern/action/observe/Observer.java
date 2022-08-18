package designpattern.action.observe;

import java.util.Vector;
// 总体来说就是一个事件触发回调的过程
// 观察者接口
public interface Observer {

  public void getInfo();

  public static void main(String[] args) {
    Subject sub = new ConcreteSubject();
    sub.addObserver(new ConcreteObserver1()); //添加观察者1
    sub.addObserver(new ConcreteObserver2()); //添加观察者2
    sub.doSomething();
  }

}

// 具体观察者
class ConcreteObserver1 implements Observer {

  @Override
  public void getInfo() {
    System.out.println("接受到信息1...");
  }
}

// 具体观察者2
class ConcreteObserver2 implements Observer {

  @Override
  public void getInfo() {
    System.out.println("接受到信息2...");
  }
}


// 被订阅主题抽象类
abstract class Subject {
  private Vector<Observer> obs = new Vector<>();

  public void addObserver(Observer obs){
    this.obs.add(obs);
  }
  public void delObserver(Observer obs){
    this.obs.remove(obs);
  }
  protected void notifyObserver(){
    for(Observer o: obs){
      o.getInfo();
    }
  }
  public abstract void doSomething();

}

class ConcreteSubject extends Subject {

  @Override
  public void doSomething() {
    System.out.println("事件触发...");
    this.notifyObserver();
  }
}
