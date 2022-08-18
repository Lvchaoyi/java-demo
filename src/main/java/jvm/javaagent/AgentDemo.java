package jvm.javaagent;

import java.lang.instrument.Instrumentation;

/**
 * java agent实例
 *
 * @author lvcy
 * @since 2021/9/2
 */
public class AgentDemo {

  /**
   * 该方法在main方法之前运行，与main方法运行在同一个JVM中
   */
  public static void premain(String agentArgs, Instrumentation inst) {
    System.out.println("------ premain方法 有两个入参 ------ agentArgs:" + agentArgs + " inst:" + inst.toString());
  }

  /**
   * 如果不存在 {@link AgentDemo#premain(String, Instrumentation)}, 则会执行本方法
   */
  public static void premain(String agentArgs) {
    System.out.println("------ premain方法，有一个入参 ------ agentArgs:" + agentArgs);
  }

}
