package com.dmsd.framework.dispatcher;

import com.dmsd.framework.anno.DispatcherHandler;
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
    protected List<IHandler<T>> handlerList=new ArrayList<>();

    protected AbsDispatcher(String id){
        this.id=id;
        List<IHandler> list= HandlerOrganizer.HANDLER_GROUPS.get(id);
        if(!CollectionUtils.isEmpty(list)){
            for (IHandler<T> iHandler:list){
                handlerList.add(iHandler);
            }
        }else{
            log.error("create dispather for id equals {},cause of none handler match this key",id);
        }
    }

    @Override
    public Object doDispath(T t) {
        for (IHandler<T> iHandler:this.handlerList){
            if(iHandler.canExecute(t)){
                return iHandler.doExecute(t);
            }
        }
        log.error("no handler match for param:{}",t);
        return null;
    }
}
