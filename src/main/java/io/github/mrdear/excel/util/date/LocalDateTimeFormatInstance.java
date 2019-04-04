package io.github.mrdear.excel.util.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * 一些日期时间的全局格式化对象
 *
 * @author rxliuli
 */
public interface LocalDateTimeFormatInstance {
  /**
   * {@link LocalDateTime} 解析格式对象
   * 能够解析类似于 {@code yyyy-MM-dd} 这种格式的字符串
   */
  DateTimeFormatter LOCAL_DATE_TIME_FORMATTER_FOR_DATE =
      new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd['T'[HH][:mm][:ss][.SSS]]")
          .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
          .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
          .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
          .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
          .toFormatter();
  /**
   * {@link LocalDateTime} 解析格式对象
   * 能够解析类似于 {@code yyyy-MM-dd hh:mm:ss} 这种通过空格分隔格式的字符串
   */
  DateTimeFormatter LOCAL_DATE_TIME_FORMATTER_FOR_SPACE_SEPARATED =
      new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ [HH][:mm][:ss][.SSS]]")
          .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
          .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
          .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
          .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
          .toFormatter();
  /**
   * {@link LocalDate} 解析格式对象
   * 能够解析类似于 {@code yyyy年MM月dd日} 这种中文环境的日期字符串
   */
  DateTimeFormatter LOCAL_DATE_FORMATTER_FOR_CHINA = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
  /**
   * {@link LocalTime} 解析格式对象
   * 能够解析类似于 {@code HH时mm分ss秒} 这种中文环境的时间字符串
   */
  DateTimeFormatter LOCAL_TIME_FORMATTER_FOR_CHINA = DateTimeFormatter.ofPattern("HH时mm分ss秒[SSS毫秒]");

}
