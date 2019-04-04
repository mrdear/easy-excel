package io.github.mrdear.excel.domain.convert;

/**
 * 转换器常量
 *
 * @author rxliuli
 */
public interface ConverterConstant {
  DefaultConverter defaultConverter = new DefaultConverter();
  DateConverter dateConverter = new DateConverter();
  LocalDateTimeConverter lcoalDateTimeConverter = new LocalDateTimeConverter();
}
