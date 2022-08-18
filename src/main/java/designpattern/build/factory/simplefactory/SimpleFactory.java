package designpattern.build.factory.simplefactory;
/**
 * 简单工厂
 *
 * @author lvcy
 * @since 2021/11/22
 */
public class SimpleFactory {

//  最关键的就是根据类型返回不同对象，一旦新增对象就需要修改工厂类代码
  public Phone getPhone(String type) {
    Phone phone = null;
    switch (type) {
      case "mi":
        phone = new MiPhone();
        break;
      case "apple":
        phone = new Iphone();
        break;
      default:
        throw new UnsupportedOperationException("不支持该操作");
    }
    return phone;
  }

}

abstract class Phone {
  abstract String getName();
}

class Iphone extends Phone {

  @Override
  String getName() {
    return "iphone";
  }
}

class MiPhone extends Phone {

  @Override
  String getName() {
    return "miphone";
  }
}
