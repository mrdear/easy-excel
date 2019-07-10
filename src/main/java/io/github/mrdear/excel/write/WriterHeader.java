package io.github.mrdear.excel.write;

import io.github.mrdear.excel.domain.convert.DefaultConverter;
import io.github.mrdear.excel.domain.convert.IConverter;

/**
 * excel写入时 header的封装
 *
 * @author Quding Ding
 * @since 2018/6/27
 */
public class WriterHeader {
  /**
   * header展示名称
   */
  private String name;
  /**
   * 对应转换器
   */
  private IConverter<?> convert;

  private WriterHeader(String name, IConverter<?> convert) {
    this.name = name;
    this.convert = convert;
  }

  public static WriterHeader create(String name) {
    return new WriterHeader(name, new DefaultConverter());
  }

  public static WriterHeader create(String name, IConverter<?> convert) {
    return new WriterHeader(name, convert);
  }

  public String getName() {
    return name;
  }

  public IConverter<?> getConvert() {
    return convert;
  }

}
