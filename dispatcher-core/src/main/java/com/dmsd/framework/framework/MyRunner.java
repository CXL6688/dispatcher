package com.dmsd.framework.framework;

import com.dmsd.framework.framework.dispatcher.DispatcherFactory;
import com.dmsd.framework.framework.dispatcher.IDispatcher;
import com.dmsd.framework.framework.anno.EnableDispatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by CXL on 2019/10/31.
 */
@SpringBootApplication
@EnableDispatcher
public class MyRunner {
  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(MyRunner.class);
    DispatcherFactory dispatcherFactory = context.getBean(DispatcherFactory.class);
    IDispatcher<String> dispatcher = dispatcherFactory.createDispatcher("gender", String.class);
    Object result = dispatcher.doDispath("男");
    System.out.println(result);
  }
}
