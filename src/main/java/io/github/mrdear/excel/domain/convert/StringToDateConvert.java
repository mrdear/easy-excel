package io.github.mrdear.excel.domain.convert;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import io.github.mrdear.excel.ExcelException;

import java.text.ParseException;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Quding Ding
 * @since 2018/5/28
 */
public class StringToDateConvert implements Function<String,Date> {

  private static FastDateFormat YMDHMS_ = FastDateFormat.getInstance("yyyy-MM-dd hh:MM:ss");

  @Override
  public Date apply(String s) {
    if (StringUtils.isEmpty(s)) {
      return null;
    }
    try {
      return YMDHMS_.parse(s);
    } catch (ParseException e) {
      throw new ExcelException(e);
    }
  }
}
