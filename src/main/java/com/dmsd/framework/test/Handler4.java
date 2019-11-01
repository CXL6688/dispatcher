package com.dmsd.framework.test;

import com.dmsd.framework.anno.DispatcherHandler;
import com.dmsd.framework.handler.IHandler;

/**
 * Created by CXL on 2019/11/1.
 */
@DispatcherHandler(value = "",groupId = "gender")
public class Handler4 implements IHandler<String>{

    @Override
    public boolean canExecute(String string) {
        return string.equals("女");
    }

    @Override
    public Object doExecute(String string) {
        return "去女厕所";
    }
}
