package io.github.mrdear.excel.domain.convert;

import io.github.mrdear.excel.ExcelException;

/**
 * 未指定的转换器
 *
 * @author rxliuli
 */
public class NotSpecifyConverter extends DefaultConverter {
  @Override
  public String to(Object o) {
    throw new ExcelException("你不能使用未指定的转换器");
  }

  @Override
  public Object from(String s) {
    throw new ExcelException("你不能使用未指定的转换器");
  }
}
