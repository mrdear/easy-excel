package io.github.mrdear.excel.domain;

import java.lang.reflect.Field;
import java.util.function.Function;

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
  private Function convert;

  private ExcelReadHeader(Field field, Function convert) {
    this.field = field;
    this.convert = convert;
  }

  public static ExcelReadHeader create(Field field, Function convert) {
    return new ExcelReadHeader(field, convert);
  }

  public static ExcelReadHeader create(Field field) {
    return new ExcelReadHeader(field, Function.identity());
  }

  public Field getField() {
    return field;
  }

  public Function getConvert() {
    return convert;
  }
}
