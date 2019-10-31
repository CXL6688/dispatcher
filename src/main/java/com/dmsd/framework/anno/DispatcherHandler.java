package com.dmsd.framework.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by CXL on 2019/10/31.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface DispatcherHandler {
    String value() default "";
}
