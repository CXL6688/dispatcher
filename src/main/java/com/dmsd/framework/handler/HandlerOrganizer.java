package com.dmsd.framework.handler;

import com.dmsd.framework.anno.DispatcherHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.*;

/**
 * Created by CXL on 2019/11/1.
 */
@Component
@Slf4j
public class HandlerOrganizer implements ApplicationContextAware {
  private ApplicationContext APPLICATION_CONTEXT;
  public final static Map<String, List<HandlerNode>> HANDLER_GROUPS = new HashMap<>();

  @PostConstruct
  private void initHandlers() {
    log.debug("start to init handlers");
    if (HANDLER_GROUPS.isEmpty()) {
        orgHandlersByGroup();
        sortEachGroupHandlers();
    }
  }

    private void sortEachGroupHandlers() {
        Iterator<List<HandlerNode>> iterator = HANDLER_GROUPS.values().iterator();
        while (iterator.hasNext()){
           Collections.sort(iterator.next());
        }
    }

    private void orgHandlersByGroup() {
    Map<String, Object> map = APPLICATION_CONTEXT.getBeansWithAnnotation(DispatcherHandler.class);
    if (map != null && map.size() > 0) {
      Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry<String, Object> handlerEntry = iterator.next();
        Object handler = handlerEntry.getValue();
        if (handler instanceof IHandler) {
          DispatcherHandler[] annotations = handler.getClass().getAnnotationsByType(DispatcherHandler.class);
          String groupId = annotations[0].groupId();
          int order = annotations[0].order();
          if (!HANDLER_GROUPS.containsKey(groupId)) {
            HANDLER_GROUPS.put(groupId, new ArrayList<>());
          }
          HANDLER_GROUPS.get(groupId).add(new HandlerNode(handlerEntry.getKey(), (IHandler) handler, order));
        }
      }
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    APPLICATION_CONTEXT = applicationContext;
  }
}
