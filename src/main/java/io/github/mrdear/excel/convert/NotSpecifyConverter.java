package io.github.mrdear.excel.convert;

import io.github.mrdear.excel.exception.DocumentException;

/**
 * 未指定的转换器
 *
 * @author rxliuli
 */
public class NotSpecifyConverter extends DefaultConverter {
  @Override
  public String to(Object o) {
    throw new DocumentException("你不能使用未指定的转换器");
  }

  @Override
  public Object from(String s) {
    throw new DocumentException("你不能使用未指定的转换器");
  }
}
