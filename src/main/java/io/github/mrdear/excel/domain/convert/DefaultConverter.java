package io.github.mrdear.excel.domain.convert;

import java.util.Objects;

/**
 * 默认的转换器
 *
 * @author rxliuli
 */
public class DefaultConverter implements IConverter<Object> {

    private static final DefaultConverter INSTANCE = new DefaultConverter();

    public static IConverter<?> getInstance() {
        return INSTANCE;
    }

    @Override
  public String to(Object o) {
    return Objects.toString(o);
  }

  @Override
  public Object from(String s) {
    return s;
  }
}
