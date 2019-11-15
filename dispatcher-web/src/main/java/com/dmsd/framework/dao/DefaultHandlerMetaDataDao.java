package com.dmsd.framework.dao;

import com.alibaba.fastjson.JSONArray;
import com.dmsd.framework.framework.handler.HandlerMetaData;
import com.dmsd.framework.framework.handler.HandlerOrganizer;
import com.dmsd.framework.framework.handler.dao.IHandlerMetaDataDao;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DefaultHandlerMetaDataDao implements IHandlerMetaDataDao {
  @Autowired(required = false)
  @Lazy
  private HandlerOrganizer handlerOrganizer;

  @Override
  public List<HandlerMetaData> findAll() {
    try {
      File file =file = ResourceUtils.getFile(getDBJsonFilePath());
      InputStream inputStream = new FileInputStream(file);
      String json= IOUtils.toString(inputStream, Charset.forName("utf8"));
      return JSONArray.parseArray(json,HandlerMetaData.class);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 目前不支持groupId改变
   * @param handlerMetaData
   */
  @Override
  public void update(HandlerMetaData handlerMetaData) {
     if(handlerMetaData!=null){
       List<HandlerMetaData> list=this.findAll();
       for (int i=0,size=list.size();i<size;i++){
         if(list.get(i).getHandlerId().equals(handlerMetaData.getHandlerId())){
           list.set(i,handlerMetaData);
           handlerOrganizer.reOrderByGroup(handlerMetaData.getGroupId());
           break;
         }
       }
       this.saveToDisk(list);
     }
  }

  @Override
  public HandlerMetaData findByHandlerId(String id) {
    return this.findAllToMap().get(id);
  }

  @Override
  public Map<String, HandlerMetaData> findAllToMap() {
    Map<String, HandlerMetaData> map=new HashMap<>();
    List<HandlerMetaData> list=this.findAll();
    if(!CollectionUtils.isEmpty(list)){
      list.forEach(handlerMetaData -> {
        map.put(handlerMetaData.getHandlerId(),handlerMetaData);
      });
    }
    return map;
  }

  @Override
  public void overrideAllData(List<HandlerMetaData> handlerMetaDatas) {
    saveToDisk(handlerMetaDatas);
  }

  private void saveToDisk(List<HandlerMetaData> list){
    try {
      String json = JSONArray.toJSONString(list);
      File file =file = ResourceUtils.getFile(getDBJsonFilePath());
      IOUtils.write(json,new FileOutputStream(file),Charset.forName("utf8"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getDBJsonFilePath(){
    return "classpath:db.json";
  }
}
