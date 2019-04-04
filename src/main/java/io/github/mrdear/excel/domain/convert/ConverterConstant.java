package io.github.mrdear.excel.domain.convert;

/**
 * 转换器常量
 *
 * @author rxliuli
 */
public interface ConverterConstant {
  DefaultConverter DEFAULT_CONVERTER = new DefaultConverter();
  DateConverter DATE_CONVERTER = new DateConverter();
  LocalDateTimeConverter LCOAL_DATE_TIME_CONVERTER = new LocalDateTimeConverter();
}
