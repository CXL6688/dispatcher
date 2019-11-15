package com.dmsd.framework.vo;

import lombok.Data;

@Data
public class DmsdHttpResponse<T> {
  private static final int SUCCESS_CODE=0;
  private static final int FAILED_CODE=-1;

  private int code;
  private T data;
  private String msg;

  private DmsdHttpResponse(int code){
    this.code=code;
  }

  private DmsdHttpResponse(int code,T data){
    this.code=code;
    this.data=data;
  }

  private DmsdHttpResponse(int code,String msg){
    this.code=code;
    this.msg=msg;
  }

  public static <T> DmsdHttpResponse buildSuccess(T t){
    return new DmsdHttpResponse(SUCCESS_CODE,t);
  }

  public static  DmsdHttpResponse buildSuccess(){
    return new DmsdHttpResponse(SUCCESS_CODE);
  }

  public static DmsdHttpResponse buildFailed(String msg){
    return new DmsdHttpResponse(FAILED_CODE,msg);
  }
}
