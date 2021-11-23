package designpattern.adapter;

// 适配类通过继承需要适配的对象并实现目标接口，相当于把两个不相关的组件和功能进行适配
public class Adapter extends Adaptee implements Target {

  @Override
  public void operation() {

  }
}

// 需要适配的对象，啥都干不了
class Adaptee {

}
// 需要进行适配的接口，可以具有某些功能
interface Target {
  public void operation();
}
