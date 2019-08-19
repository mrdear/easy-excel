package io.github.mrdear.excel.convert;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class DateToStringConvertTest {

  @Test
  public void apply() throws ParseException {
    Date now = new Date();
    YmdDateConverter convert = new YmdDateConverter();

    String apply = convert.to(now);
    Date parse = convert.from(apply);
    Assert.assertEquals(now.getTime() / 1000, parse.getTime() / 1000);
  }

}