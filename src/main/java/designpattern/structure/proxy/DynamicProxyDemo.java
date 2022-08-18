package designpattern.structure.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 在代码编译之后动态生成字节码对目标对象进行代理增强
 *
 * @author lvcy
 * @since 2021/9/16
 */
public class DynamicProxyDemo {

  private Object target;

  public DynamicProxyDemo(Object target) {
    this.target = target;
  }

  public Object getProxyInstance() {
    return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
        new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("动态代理之前开始做事了。。。");
            method.invoke(target, args);
            System.out.println("动态代理之后开始做事了。。。");
            return null;
          }
        });
  }

  public static void main(String[] args) {
    Feature instance = (Feature) new DynamicProxyDemo(new Target()).getProxyInstance();
    instance.doSomething();
  }

}
