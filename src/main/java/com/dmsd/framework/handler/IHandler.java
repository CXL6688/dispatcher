package com.dmsd.framework.handler;

import org.springframework.stereotype.Component;

/**
 * Created by CXL on 2019/10/31.
 */
public interface IHandler<T> {
    boolean canExecute(T t);
    Object doExecute(T t);
}
