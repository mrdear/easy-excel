package io.github.mrdear.excel.domain.convert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类型转换器工厂
 *
 * @author rxliuli
 */
public class ConverterFactory {
  private static final Class<DefaultConverter> DEFAULT_CONVERTER = DefaultConverter.class;
  private static final Map<Class<?>, Class<? extends IConverter>> CONVERTER_MAP = new ConcurrentHashMap<>();

  static {
    CONVERTER_MAP.put(Date.class, DateConverter.class);
    CONVERTER_MAP.put(LocalDateTime.class, LocalDateTimeConverter.class);
    CONVERTER_MAP.put(LocalDate.class, LocalDateConverter.class);
    CONVERTER_MAP.put(LocalTime.class, LocalTimeConverter.class);
  }

  /**
   * 根据类型获取到默认的 converter 转换器
   *
   * @param clazz 类型
   * @return 默认的 converter 转换器，如果没有对应类型，则返回 {@link DefaultConverter}
   */
  public static Class<? extends IConverter> get(Class<?> clazz) {
    return CONVERTER_MAP.getOrDefault(clazz, DEFAULT_CONVERTER);
  }

  /**
   * 注册一个指定类型的转换器
   *
   * @param clazz     类型
   * @param converter 对应的转换器
   */
  public static void register(Class<?> clazz, Class<? extends IConverter> converter) {
    CONVERTER_MAP.put(clazz, converter);
  }
}
