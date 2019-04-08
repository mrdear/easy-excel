package io.github.mrdear.excel.domain.convert;

/**
 * @author rxliuli
 */
public class BooleanConverter implements IConverter<Boolean> {
  @Override
  public String to(Boolean aBoolean) {
    return Boolean.toString(aBoolean);
  }

  @Override
  public Boolean from(String to) {
    return Boolean.valueOf(to);
  }
}
