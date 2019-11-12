package com.dmsd.framework.dispatcher;

import com.dmsd.framework.anno.DispatcherHandler;
import com.dmsd.framework.handler.HandlerNode;
import com.dmsd.framework.handler.HandlerOrganizer;
import com.dmsd.framework.handler.IHandler;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by CXL on 2019/10/31.
 */
@Slf4j
public abstract class AbsDispatcher<T> implements IDispatcher<T>{
    private String id;

    protected AbsDispatcher(String id){
        this.id=id;
    }

    @Override
    public Object doDispath(T t) {
        List<HandlerNode> list=HandlerOrganizer.HANDLER_GROUPS.get(this.id);
        for (HandlerNode handlerNode:list){
            if(handlerNode.getHandler().canExecute(t)){
                return handlerNode.getHandler().doExecute(t);
            }
        }
        log.error("no handler match for param:{}",t);
        return null;
    }
}
