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
    return Long.valueOf(s);
  }
}
