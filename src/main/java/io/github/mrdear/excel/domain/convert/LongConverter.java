package io.github.mrdear.excel.domain.convert;

/**
 * @author rxliuli
 */
public class LongConverter implements IConverter<Long> {
  @Override
  public String to(Long aLong) {
    return Long.toString(aLong);
  }

  @Override
  public Long from(String s) {
    if (s.endsWith(".0")) {
      return Double.valueOf(s).longValue();
    }
    return Long.valueOf(s);
  }
}
