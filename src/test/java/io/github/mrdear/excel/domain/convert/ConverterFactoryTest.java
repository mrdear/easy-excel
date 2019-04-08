package io.github.mrdear.excel.domain.convert;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author rxliuli
 */
class ConverterFactoryTest {

  @Test
  void get() throws IllegalAccessException, InstantiationException {
    final IConverter<Boolean> converter = ConverterFactory.get(boolean.class).newInstance();
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
    ConverterFactory.register(Date.class, DemoConverter.class);
    final Class<? extends IConverter<Date>> clazz = ConverterFactory.get(Date.class);
    assertThat(clazz)
        .isEqualTo(DemoConverter.class);
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