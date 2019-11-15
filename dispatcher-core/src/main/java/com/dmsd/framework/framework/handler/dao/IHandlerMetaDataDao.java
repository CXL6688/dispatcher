package com.dmsd.framework.framework.handler.dao;

import com.dmsd.framework.framework.handler.HandlerMetaData;
import com.dmsd.framework.framework.handler.HandlerMetaDataLoader;

import java.util.List;

public interface IHandlerMetaDataDao extends HandlerMetaDataLoader {
  List<HandlerMetaData> findAll();


  void update(HandlerMetaData handlerMetaData);

  HandlerMetaData findByHandlerId(String id);
}
