package com.dmsd.framework.dispatcher;

import org.springframework.stereotype.Component;

/**
 * Created by CXL on 2019/11/1.
 */
@Component
public class DispatcherFactory {
    public <T> IDispatcher  createDispatcher(String id,Class<T> clazz){
        return new DefaultDispatcher<T>(id);
    }
}
