package io.github.mrdear.excel.convert;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.sql.Date;

/**
 * @author rxliuli
 */
class ConverterFactoryTest {

  @Test
  void get() throws IllegalAccessException, InstantiationException {
    final IConverter<Boolean> converter = ConverterFactory.get(boolean.class);
    assertThat(converter.to(true))
        .isEqualTo("true");
    assertThat(converter.to(false))
        .isEqualTo("false");
    assertThat(converter.from("true"))
        .isTrue();
    assertThat(converter.from("false"))
        .isFalse();
  }

  @Test
  void register() {
    DemoConverter converter = new DemoConverter();
    ConverterFactory.register(Date.class, converter);

    IConverter<Date> clazz = ConverterFactory.get(Date.class);
    assertThat(clazz)
        .isEqualTo(converter);
  }

  static class DemoConverter implements IConverter<Date> {
    @Override
    public String to(Date date) {
      return date.toString();
    }

    @Override
    public Date from(String to) {
      return Date.valueOf(to);
    }
  }
}