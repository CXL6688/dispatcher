package com.dmsd.framework.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HandlerNode implements Comparable<HandlerNode> {
  private String id;
  private IHandler handler;
  private int order;

  @Override
  public int compareTo(HandlerNode o) {
    if(this.getOrder()>o.getOrder()){
      return 1;
    }else if(this.getOrder()<o.getOrder()){
      return -1;
    }
    return this.getId().compareTo(o.getId());
  }
}
