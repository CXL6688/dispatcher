package com.dmsd.framework.framework.dispatcher;

import com.dmsd.framework.framework.handler.HandlerNode;
import com.dmsd.framework.framework.handler.HandlerOrganizer;
import lombok.extern.slf4j.Slf4j;

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
    public final Object doDispath(T t) {
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
