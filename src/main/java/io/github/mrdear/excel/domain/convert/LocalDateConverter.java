package io.github.mrdear.excel.domain.convert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author rxliuli
 */
public class LocalDateConverter implements IConverter<LocalDate, String> {
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public String to(LocalDate localDateTime) {
    return DATE_FORMAT.format(localDateTime);
  }

  @Override
  public LocalDate from(String s) {
    return LocalDate.parse(s, DATE_FORMAT);
  }
}
