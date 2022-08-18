package designpattern.action.templatemethod;

public class CaixinCooker extends AbstractCooker {

  @Override
  public void  pourVegetable(){
    System.out.println("下锅的蔬菜是菜心");
  }
  @Override
  public void  pourSauce() {
    System.out.println("下锅的酱料是蒜蓉");
  }

  public static void main(String[] args) {
    new CaixinCooker().cookProcess();
  }
}
