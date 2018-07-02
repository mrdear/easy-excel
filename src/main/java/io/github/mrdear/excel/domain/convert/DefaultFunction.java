package io.github.mrdear.excel.domain.convert;

import java.util.function.Function;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
public class DefaultFunction implements Function {
  @Override
  public Object apply(Object o) {
    return o;
  }
}
