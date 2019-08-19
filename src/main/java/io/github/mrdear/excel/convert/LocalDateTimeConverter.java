package io.github.mrdear.excel.convert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Java8 {@link LocalDateTime} 的转换器
 *
 * @author rxliuli
 */
public class LocalDateTimeConverter implements IConverter<LocalDateTime> {
  /**
   * {@link LocalDateTime} 解析格式对象
   * 能够解析类似于 {@code yyyy-MM-dd hh:mm:ss} 这种通过空格分隔格式的字符串
   */
  private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER_FOR_SPACE_SEPARATED =
      new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ [HH][:mm][:ss]]")
          .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
          .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
          .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
          .toFormatter();

  @Override
  public String to(LocalDateTime localDateTime) {
    return LOCAL_DATE_TIME_FORMATTER_FOR_SPACE_SEPARATED.format(localDateTime);
  }

  @Override
  public LocalDateTime from(String s) {
    return LocalDateTime.parse(s, LOCAL_DATE_TIME_FORMATTER_FOR_SPACE_SEPARATED);
  }
}
