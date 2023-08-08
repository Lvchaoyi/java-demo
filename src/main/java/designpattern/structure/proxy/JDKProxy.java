package designpattern.structure.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author chaoyi.lv
 * @date 2023/2/27
 */
public class JDKProxy implements InvocationHandler {

    private final Object target;

    public JDKProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDK代理类之前开始做事。。。");
        Object result = method.invoke(target, args);
        System.out.println("JDK代理类之后开始做事。。。");
        return result;
    }

    public static void main(String[] args) {
        Feature instance = new Target();
        Feature feature = (Feature) Proxy.newProxyInstance(
                instance.getClass().getClassLoader(),
                // 必须要实现接口，然后jdk代理通过反射获取对象的接口才能执行对应方法
                instance.getClass().getInterfaces(),
                new JDKProxy(instance)
        );
        feature.doSomething();
    }
}
