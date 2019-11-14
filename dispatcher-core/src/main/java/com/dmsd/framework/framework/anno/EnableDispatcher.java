package com.dmsd.framework.framework.anno;

import com.dmsd.framework.framework.handler.HandlerOrganizer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({HandlerOrganizer.class})
@Documented
public @interface EnableDispatcher {
}
