package io.github.mrdear.excel.convert;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author rxliuli
 */
public class LocalTimeConverter implements IConverter<LocalTime> {
  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

  @Override
  public String to(LocalTime localDateTime) {
    return TIME_FORMATTER.format(localDateTime);
  }

  @Override
  public LocalTime from(String s) {
    return LocalTime.parse(s, TIME_FORMATTER);
  }
}
