package com.dmsd.framework.test;

import com.dmsd.framework.anno.DispatcherHandler;
import com.dmsd.framework.handler.IHandler;

/**
 * Created by CXL on 2019/11/1.
 */
@DispatcherHandler(value = "",groupId = "order")
public class Handler1 implements IHandler<Integer>{

    @Override
    public boolean canExecute(Integer integer) {
        return integer.equals(1);
    }

    @Override
    public Object doExecute(Integer integer) {
        return "execute handler1";
    }
}