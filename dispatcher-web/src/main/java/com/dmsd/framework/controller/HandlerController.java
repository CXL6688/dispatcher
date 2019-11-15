package com.dmsd.framework.controller;

import com.dmsd.framework.framework.dispatcher.DispatcherFactory;
import com.dmsd.framework.framework.dispatcher.IDispatcher;
import com.dmsd.framework.framework.handler.HandlerMetaData;
import com.dmsd.framework.framework.handler.HandlerOrganizer;
import com.dmsd.framework.framework.handler.dao.IHandlerMetaDataDao;
import com.dmsd.framework.vo.DmsdHttpResponse;
import com.dmsd.framework.vo.HandlerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(value="节点管理", tags={"节点管理"})
@Controller

@RequestMapping("/dispatcher/v1/handler")
public class HandlerController {
  @Autowired
  private IHandlerMetaDataDao handlerMetaDataDao;

  private HandlerOrganizer handlerOrganizer;

  @ApiOperation(value="获取节点信息", notes="获取所有节点信息")
  @GetMapping("/")
  @ResponseBody
  public DmsdHttpResponse<List<HandlerVo>> index(){
    return DmsdHttpResponse.buildSuccess(handlerMetaDataDao.findAll());
  }

  @ApiOperation(value="更新节点信息", notes="更新节点信息")
  @PostMapping("/update")
  @ResponseBody
  public DmsdHttpResponse update(@RequestBody HandlerMetaData handlerMetaData){
    handlerMetaDataDao.update(handlerMetaData);
    return DmsdHttpResponse.buildSuccess();
  }

  @Autowired
  private DispatcherFactory dispatcherFactory;

  @ApiOperation(value="测试顺序", notes="测试顺序")
  @PostMapping("/test")
  @ResponseBody
  public void test(@RequestParam("groupId") String groupId,@RequestParam("param") String param){
    IDispatcher<String> dispatcher = dispatcherFactory.createDispatcher(groupId, String.class);
    Object result = dispatcher.doDispath(param);
    System.out.println(result);
  }
}
