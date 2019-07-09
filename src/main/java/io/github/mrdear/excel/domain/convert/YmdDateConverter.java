package io.github.mrdear.excel.domain.convert;

import io.github.mrdear.excel.ExcelException;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;

/**
 * 普通 {@link Date} 转换器
 *
 * @author rxliuli
 */
public class YmdDateConverter implements IConverter<Date> {
  private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

  @Override
  public String to(Date date) {
    return FAST_DATE_FORMAT.format(date);
  }

  @Override
  public Date from(String s) {
    try {
      return FAST_DATE_FORMAT.parse(s);
    } catch (ParseException e) {
      throw new ExcelException(e);
    }
  }
}
