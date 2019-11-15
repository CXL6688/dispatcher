package com.dmsd.framework.framework.handler;

import com.dmsd.framework.framework.anno.DispatcherHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

/**
 * Created by CXL on 2019/11/1.
 */
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
      asyncMetaData();
      sortEachGroupHandlers();
    }
  }

  public void reOrderByGroup(String groupId){
    if(!StringUtils.isEmpty(groupId)){
      List<HandlerNode> list=HANDLER_GROUPS.get(groupId);
      if(!CollectionUtils.isEmpty(list)){
        Collections.sort(list);
      }
    }
  }

  /**
   * 这里Map<String, HandlerMetaData> finalLoadedMetaDatas = loadedMetaDatas;
   * 由于作用域需要修改一下
   */
  private void asyncMetaData() {
    List<HandlerMetaData> refreshMetaData=new ArrayList<>();
    Map<String, HandlerMetaData> loadedMetaDatas= handlerMetaDataLoader.findAllToMap();
    if(loadedMetaDatas==null){
       loadedMetaDatas=new HashMap<>();
    }
    Map<String, HandlerMetaData> finalLoadedMetaDatas = loadedMetaDatas;
    HANDLER_GROUPS.entrySet().forEach(entry->{
      String groupId=entry.getKey();
      entry.getValue().forEach(handler->{
        HandlerMetaData handlerMetaData= finalLoadedMetaDatas.get(handler.getId());
        if(handlerMetaData!=null){
          applyMetaData(handler,handlerMetaData);
        }else{
          handlerMetaData=handlerToMetaData(groupId,handler);
        }
        refreshMetaData.add(handlerMetaData);
      });
    });
    handlerMetaDataLoader.overrideAllData(refreshMetaData);
  }

  private void applyMetaData(HandlerNode handlerNode,HandlerMetaData handlerMetaData){
    handlerNode.setOrder(handlerMetaData.getOrder());
  }

  private HandlerMetaData handlerToMetaData(String groupId,HandlerNode handlerNode){
    HandlerMetaData handlerMetaData=new HandlerMetaData();
    handlerMetaData.setHandlerId(handlerNode.getId());
    handlerMetaData.setGroupId(groupId);
    handlerMetaData.setOrder(handlerNode.getOrder());
    return handlerMetaData;
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
