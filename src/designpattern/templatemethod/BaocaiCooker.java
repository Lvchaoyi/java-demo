package designpattern.templatemethod;

public class BaocaiCooker extends AbstractCooker {

  @Override
  void pourVegetable() {
    System.out.println("下锅的蔬菜是包菜");
  }

  @Override
  void pourSauce() {
    System.out.println("下锅的酱料是辣椒");
  }
}
