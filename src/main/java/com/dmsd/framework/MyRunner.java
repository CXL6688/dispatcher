package com.dmsd.framework;

import com.dmsd.framework.dispatcher.DispatcherFactory;
import com.dmsd.framework.dispatcher.IDispatcher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by CXL on 2019/10/31.
 */
@SpringBootApplication
public class MyRunner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.dmsd");
        ctx.refresh();
        DispatcherFactory dispatcherFactory = ctx.getBean(DispatcherFactory.class);

        IDispatcher<String> dispatcher = dispatcherFactory.createDispatcher("gender", String.class);
        Object result = dispatcher.doDispath("男");
        System.out.println(result);
    }
}
