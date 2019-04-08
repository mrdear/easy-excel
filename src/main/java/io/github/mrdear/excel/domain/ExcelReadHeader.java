package io.github.mrdear.excel.domain;

import io.github.mrdear.excel.domain.convert.DefaultConverter;
import io.github.mrdear.excel.domain.convert.IConverter;

import java.lang.reflect.Field;

/**
 * @author Quding Ding
 * @since 2018/6/29
 */
public class ExcelReadHeader {

  /**
   * 对应的属性字段
   */
  private Field field;

  /**
   * 对应转换器
   */
  private IConverter<?> convert;

  private ExcelReadHeader(Field field, IConverter<?> convert) {
    this.field = field;
    this.convert = convert;
  }

  public static ExcelReadHeader create(Field field, IConverter<?> convert) {
    return new ExcelReadHeader(field, convert);
  }

  public static ExcelReadHeader create(Field field) {
    return new ExcelReadHeader(field, new DefaultConverter());
  }

  public Field getField() {
    return field;
  }

  public IConverter<?> getConvert() {
    return convert;
  }
}
