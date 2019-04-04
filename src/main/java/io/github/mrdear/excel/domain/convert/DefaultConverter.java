package io.github.mrdear.excel.domain.convert;

import java.util.Objects;

/**
 * 默认的转换器
 *
 * @author rxliuli
 */
public class DefaultConverter implements IConverter<Object, String> {
  @Override
  public String to(Object o) {
    return Objects.toString(o);
  }

  @Override
  public Object from(String s) {
    return s;
  }
}
