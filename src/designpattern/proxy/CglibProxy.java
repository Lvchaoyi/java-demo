package designpattern.proxy;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
/**
 * cglib的动态代理类
 * 
 * @author lvcy
 * @since 2021/9/16
 */
public class CglibProxy implements MethodInterceptor {

  private Object target;

  public CglibProxy(Object target) {
    this.target = target;
  }

  //为目标对象生成代理对象
  public Object getProxyInstance() {
    //工具类
    Enhancer en = new Enhancer();
    //设置父类
    en.setSuperclass(target.getClass());
    //设置回调函数
    en.setCallback(this);
    //创建子类对象代理
    return en.create();
  }
  
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
      throws Throwable {
    System.out.println("cglib代理类之前开始做事。。。");
    method.invoke(target, objects);
    System.out.println("cglib代理类之后开始做事。。。");
    return null;
  }


}
