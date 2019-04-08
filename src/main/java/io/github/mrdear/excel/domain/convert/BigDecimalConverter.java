package io.github.mrdear.excel.domain.convert;

import java.math.BigDecimal;

/**
 * @author rxliuli
 */
public class BigDecimalConverter implements IConverter<BigDecimal> {

  @Override
  public String to(BigDecimal bigDecimal) {
    return bigDecimal.toPlainString();
  }

  @Override
  public BigDecimal from(String to) {
    return new BigDecimal(to);
  }
}
