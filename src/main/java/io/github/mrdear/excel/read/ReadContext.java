package io.github.mrdear.excel.read;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 通用读取上下文
 *
 * @author Quding Ding
 * @since 2018/6/29
 */
@Getter
@Setter(AccessLevel.PACKAGE)
public abstract class ReadContext<T> {

  /**
   * header读取出来的类
   */
  private Class<T> clazz;

  /**
   * header所开始的第一行
   */
  private int headerStart = 0;

  /**
   * 表头与类属性之间的映射
   */
  private Map<String, ReadHeader> headers;

}
