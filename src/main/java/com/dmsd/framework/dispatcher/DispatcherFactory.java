package com.dmsd.framework.dispatcher;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by CXL on 2019/11/1.
 */
@Component
public class DispatcherFactory {
    private static Map<String,IDispatcher> map = new ConcurrentHashMap<>();
    public <T> IDispatcher  createDispatcher(String id,Class<T> clazz){
        IDispatcher iDispatcher=map.get(id);
        if(iDispatcher==null){
            iDispatcher=new DefaultDispatcher<T>(id);
            map.put(id,iDispatcher);
        }
        return iDispatcher;
    }
}
