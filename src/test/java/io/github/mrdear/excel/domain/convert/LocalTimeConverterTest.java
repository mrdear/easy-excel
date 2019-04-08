package io.github.mrdear.excel.domain.convert;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author rxliuli
 */
class LocalTimeConverterTest {
  private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
  private final IConverter<LocalTime> converter = new LocalTimeConverter();
  private final LocalTime now = LocalTime.now();

  @Test
  void to() {
    assertThat(converter.to(now))
        .isEqualTo(TIME_FORMATTER.format(now));
  }

  @Test
  void from() {
    assertThat(converter.from(TIME_FORMATTER.format(now)))
        .isEqualTo(TIME_FORMATTER.format(now));
  }
}