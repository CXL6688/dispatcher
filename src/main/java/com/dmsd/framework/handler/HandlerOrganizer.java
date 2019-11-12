package com.dmsd.framework.handler;

import com.dmsd.framework.anno.DispatcherHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * Created by CXL on 2019/11/1.
 */
@Component
@Slf4j
public class HandlerOrganizer implements ApplicationContextAware {
  private ApplicationContext APPLICATION_CONTEXT;
  public final static Map<String, List<HandlerNode>> HANDLER_GROUPS = new HashMap<>();
  @Autowired(required = false)
  private HandlerMetaDataLoader handlerMetaDataLoader;

  @PostConstruct
  private void initHandlers() {
    log.debug("start to init handlers");
    if (HANDLER_GROUPS.isEmpty()) {
      orgHandlersByGroup();
      if(handlerMetaDataLoader!=null){
        Map<String,HandlerMetaData> metaDatas= handlerMetaDataLoader.load();
        if(!CollectionUtils.isEmpty(metaDatas)){
          Iterator<Map.Entry<String,HandlerMetaData>> iterator= metaDatas.entrySet().iterator();
          while(iterator.hasNext()){

          }
        }
      }
      sortEachGroupHandlers();
    }
  }

  private void sortEachGroupHandlers() {
    Iterator<List<HandlerNode>> iterator = HANDLER_GROUPS.values().iterator();
    while (iterator.hasNext()) {
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
          String id=handlerEntry.getKey();
          String groupId = annotations[0].groupId();
          int order = annotations[0].order();
          if (!HANDLER_GROUPS.containsKey(groupId)) {
            HANDLER_GROUPS.put(groupId, new ArrayList<>());
          }
          HANDLER_GROUPS.get(groupId).add(new HandlerNode(id, (IHandler) handler, order));
        }
      }
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    APPLICATION_CONTEXT = applicationContext;
  }
}
