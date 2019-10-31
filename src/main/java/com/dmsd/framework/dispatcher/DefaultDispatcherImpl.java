package com.dmsd.framework.dispatcher;

import com.dmsd.framework.anno.DispatcherHandler;
import com.dmsd.framework.handler.IHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * Created by CXL on 2019/10/31.
 */
@Component
public class DefaultDispatcherImpl implements IDispatcher,ApplicationContextAware{
    private ApplicationContext APPLICATIONCONTEXT;
    private Map<String,List<IHandler>> handlerGroups= new HashMap<>();

    @PostConstruct
    private void init(){
        Map<String, Object> map=APPLICATIONCONTEXT.getBeansWithAnnotation(DispatcherHandler.class);
       if(map!=null && map.size()>0){
           Iterator iterator= map.entrySet().iterator();
           while(iterator.hasNext()){
               Object handler = iterator.next();
               if(IHandler.class.isAssignableFrom(handler.getClass())){
                   System.out.println(handler.getClass().getName());
               }
           }
       }
    }
    @Override
    public Object doDispath() {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATIONCONTEXT=applicationContext;
    }
}
