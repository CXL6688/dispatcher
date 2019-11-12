package com.dmsd.framework.handler;

import java.util.List;
import java.util.Map;

/**
 * 负责加载自定义Handler元数据
 */
public interface HandlerMetaDataLoader {
  /**
   * String代表HandlerID，HandlerMetaData代表Handler的元数据。
   * 使用map一方面保证一个处理节点的元数据只有一个，另一方面快速查找
   */
  Map<String,HandlerMetaData> load();
}
