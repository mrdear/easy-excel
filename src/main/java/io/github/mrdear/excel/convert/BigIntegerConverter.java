package io.github.mrdear.excel.convert;

import java.math.BigInteger;

/**
 * @author rxliuli
 */
public class BigIntegerConverter implements IConverter<BigInteger> {
  @Override
  public String to(BigInteger bigInteger) {
    return bigInteger.toString();
  }

  @Override
  public BigInteger from(String to) {
    return new BigInteger(to);
  }
}
