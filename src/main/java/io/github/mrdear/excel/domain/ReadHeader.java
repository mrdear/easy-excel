package io.github.mrdear.excel.domain;

import io.github.mrdear.excel.convert.DefaultConverter;
import io.github.mrdear.excel.convert.IConverter;

import java.lang.reflect.Field;

/**
 * 读取时对应header映射
 * @author Quding Ding
 * @since 2018/6/29
 */
public class ReadHeader {

  /**
   * 对应的属性字段
   */
  private Field field;

  /**
   * 对应转换器
   */
  private IConverter<?> convert;

  private ReadHeader(Field field, IConverter<?> convert) {
    this.field = field;
    this.convert = convert;
  }

  public static ReadHeader create(Field field, IConverter<?> convert) {
    return new ReadHeader(field, convert);
  }

  public static ReadHeader create(Field field) {
    return new ReadHeader(field, new DefaultConverter());
  }

  public Field getField() {
    return field;
  }

  public IConverter<?> getConvert() {
    return convert;
  }
}
