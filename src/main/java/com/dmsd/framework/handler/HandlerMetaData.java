package com.dmsd.framework.handler;

import lombok.Data;

@Data
public class HandlerMetaData {
  private String handlerId;
  private String description;
  private int order;
  private String handlerName;
  private String groupId;
}
