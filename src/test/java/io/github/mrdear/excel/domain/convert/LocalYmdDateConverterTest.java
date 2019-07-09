package io.github.mrdear.excel.domain.convert;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author rxliuli
 */
class LocalYmdDateConverterTest {
  private final IConverter<LocalDate> converter = new LocalDateConverter();
  private final LocalDate now = LocalDate.now();

  @Test
  void to() {
    assertThat(converter.to(now))
        .isEqualTo(now.toString());
  }

  @Test
  void from() {
    assertThat(converter.from(now.toString()))
        .isEqualTo(now);
  }
}