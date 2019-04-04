package io.github.mrdear.excel.domain.convert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rxliuli
 */
public class ConverterFactory {
  private static final DefaultConverter DEFAULT_CONVERTER = new DefaultConverter();
  private static final DateConverter DATE_CONVERTER = new DateConverter();
  private static final LocalDateTimeConverter LCOAL_DATE_TIME_CONVERTER = new LocalDateTimeConverter();
  private static final Map<Class<?>, IConverter> converterMap = new HashMap<>();

  static {
  }

  public static IConverter getInstance(Class<?> clazz) {
    return null;
  }
}
