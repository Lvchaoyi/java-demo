package designpattern.build.factory.factorymethod;

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
