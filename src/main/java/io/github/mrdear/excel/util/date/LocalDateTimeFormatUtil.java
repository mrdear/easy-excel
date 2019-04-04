package io.github.mrdear.excel.util.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import static io.github.mrdear.excel.util.date.LocalDateTimeFormatInstance.*;
import static java.time.format.DateTimeFormatter.*;

/**
 * 时间日期格式化工具类
 *
 * @author rxliuli
 */
public class LocalDateTimeFormatUtil {
  /**
   * 解析字符串为日期时间
   *
   * @param str                   字符串
   * @param dateTimeFormatterList 解析
   * @param converter             转换方法
   * @param <T>                   转换后得到的时间类型，必须继承 {@link Temporal}
   * @return 解析得到的日期时间，或者为 {@code null}
   */
  public static <T extends Temporal> T parse(String str, List<DateTimeFormatter> dateTimeFormatterList, BiFunction<String, DateTimeFormatter, T> converter) {
    return dateTimeFormatterList.stream()
        .map(dateTimeFormatter -> {
          try {
            return converter.apply(str, dateTimeFormatter);
          } catch (DateTimeParseException e) {
            return null;
          }
        })
        .filter(Objects::nonNull)
        .findFirst()
        .orElse(null);
  }

  /**
   * 解析字符串为日期时间
   *
   * @param str                   字符串
   * @param dateTimeFormatterList 时间时间格式化列表
   * @return 解析得到的日期时间，或者为 {@code null}
   */
  public static LocalDateTime parseLocalDateTime(String str, List<DateTimeFormatter> dateTimeFormatterList) {
    return parse(str, dateTimeFormatterList, LocalDateTime::parse);
  }

  /**
   * 解析字符串为日期时间
   *
   * @param str 字符串
   * @return 解析得到的日期时间，或者为 {@code null}
   */
  public static LocalDateTime parseLocalDateTime(String str) {
    return parseLocalDateTime(str, Arrays.asList(ISO_DATE_TIME, ISO_LOCAL_DATE_TIME, LOCAL_DATE_TIME_FORMATTER_FOR_DATE, LOCAL_DATE_TIME_FORMATTER_FOR_SPACE_SEPARATED));
  }

  /**
   * 解析字符串为日期
   *
   * @param str                   字符串
   * @param dateTimeFormatterList 日期格式化列表
   * @return 解析得到的日期，或者为 {@code null}
   */
  public static LocalDate parseLocalDate(String str, List<DateTimeFormatter> dateTimeFormatterList) {
    return parse(str, dateTimeFormatterList, LocalDate::parse);
  }

  /**
   * 解析字符串为日期
   *
   * @param str 字符串
   * @return 解析得到的日期，或者为 {@code null}
   */
  public static LocalDate parseLocalDate(String str) {
    return parseLocalDate(str, Arrays.asList(ISO_DATE, ISO_LOCAL_DATE, LOCAL_DATE_FORMATTER_FOR_CHINA));
  }

  /**
   * 解析字符串为时间
   *
   * @param str                   字符串
   * @param dateTimeFormatterList 时间格式化列表
   * @return 解析得到的时间，或者为 {@code null}
   */
  public static LocalTime parseLocalTime(String str, List<DateTimeFormatter> dateTimeFormatterList) {
    return parse(str, dateTimeFormatterList, LocalTime::parse);
  }

  /**
   * 解析字符串为时间
   *
   * @param str 字符串
   * @return 解析得到的时间，或者为 {@code null}
   */
  public static LocalTime parseLocalTime(String str) {
    return parseLocalTime(str, Arrays.asList(ISO_TIME, ISO_LOCAL_TIME, LOCAL_TIME_FORMATTER_FOR_CHINA));
  }

  /**
   * 把时间毫秒转成LocalDateTime
   *
   * @param millis 毫秒数
   * @return 转换得到的 LocalDateTime 对象
   */
  public static LocalDateTime convertMillisecondToLocalDateTime(long millis) {
    Instant instant = Instant.ofEpochMilli(millis);
    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

}
