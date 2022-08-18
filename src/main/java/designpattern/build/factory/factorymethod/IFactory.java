package designpattern.build.factory.factorymethod;
/**
 * 工厂方法模式
 *
 * @author lvcy
 * @since 2021/11/22
 */
// 将逻辑下沉到实际子类中,新增工厂只需要额外添加子类去实现即可
public interface IFactory {
  Phone createPhone();
}

class MiFactory implements IFactory {

  @Override
  public Phone createPhone() {
    return new MiPhone();
  }
}

class AppleFactory implements IFactory {

  @Override
  public Phone createPhone() {
    return new Iphone();
  }
}
