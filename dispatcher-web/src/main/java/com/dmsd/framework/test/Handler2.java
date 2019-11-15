package com.dmsd.framework.test;

import com.dmsd.framework.framework.handler.IHandler;
import com.dmsd.framework.framework.anno.DispatcherHandler;

/**
 * Created by CXL on 2019/11/1.
 */
@DispatcherHandler(value = "",groupId = "order")
public class Handler2 implements IHandler<Integer> {

    @Override
    public boolean canExecute(Integer integer) {
        return integer.equals(2);
    }

    @Override
    public Object doExecute(Integer integer) {
        return "execute handler2";
    }
}
