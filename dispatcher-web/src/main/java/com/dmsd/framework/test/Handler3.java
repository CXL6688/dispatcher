package com.dmsd.framework.test;

import com.dmsd.framework.framework.anno.DispatcherHandler;
import com.dmsd.framework.framework.handler.IHandler;

/**
 * Created by CXL on 2019/11/1.
 */
@DispatcherHandler(value = "",groupId = "gender",order = 2)
public class Handler3 implements IHandler<String>{

    @Override
    public boolean canExecute(String string) {
        System.out.println("判断是否为男");
        return string.equals("男");
    }

    @Override
    public Object doExecute(String string) {
        return "去男厕所";
    }
}
