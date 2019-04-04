package io.github.mrdear.excel.domain;

import io.github.mrdear.excel.domain.convert.DefaultConverter;
import io.github.mrdear.excel.domain.convert.IConverter;

/**
 * excel header的封装
 *
 * @author Quding Ding
 * @since 2018/6/27
 */
public class ExcelWriterHeader {
  /**
   * header展示名称
   */
  private String name;
  /**
   * 对应转换器
   */
  private IConverter<Object, String> convert;

  private ExcelWriterHeader(String name, IConverter<Object, String> convert) {
    this.name = name;
    this.convert = convert;
  }

  public static ExcelWriterHeader create(String name) {
    return new ExcelWriterHeader(name, new DefaultConverter());
  }

  public static ExcelWriterHeader create(String name, IConverter<Object, String> convert) {
    return new ExcelWriterHeader(name, convert);
  }

  public String getName() {
    return name;
  }

  public IConverter<Object, String> getConvert() {
    return convert;
  }

}
