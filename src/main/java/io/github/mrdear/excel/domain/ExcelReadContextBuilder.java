package io.github.mrdear.excel.domain;

import io.github.mrdear.excel.internal.util.Assert;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 对应的读取构造器
 *
 * @author Quding Ding
 * @since 2018/6/29
 */
public class ExcelReadContextBuilder<T> {

  private ExcelReadContext<T> context;


  public ExcelReadContextBuilder(ExcelReadContext<T> context) {
    this.context = context;
  }

  /**
   * 使用该方法默认会使用{@link io.github.mrdear.excel.annotation.ExcelField} 注解进行转换Header
   *
   * @param clazz 目标类
   * @return header构造器
   */
  public ExcelReadContextBuilder<T> clazz(Class<T> clazz) {
    context.setClazz(clazz);
    // 此时header可以确定
    if (null == this.context.getHeaders()) {
      this.context.setHeaders(clazz);
    }
    return this;
  }

  public ExcelReadContextBuilder<T> sheetIndex(int sheetIndex) {
    this.context.setSheetIndex(sheetIndex);
    return this;
  }

  public ExcelReadContextBuilder<T> headerStart(int headerStart) {
    this.context.setHeaderStart(headerStart);
    return this;
  }

  public ExcelReadContextBuilder<T> readSheetHook(BiConsumer<Sheet, ExcelReadContext> readSheetHook) {
    this.context.setReadSheetHook(readSheetHook);
    return this;
  }

  /**
   * 指定列表头
   *
   * @param headers key 该列名称  value 对应的field以及convert
   * @return this
   */
  public ExcelReadContextBuilder<T> headers(Map<String, ExcelReadHeader> headers) {
    this.context.setHeaders(headers);
    return this;
  }


  public ExcelReadContext<T> build() {
    beforeBuildCheck();
    return this.context;
  }

  private void beforeBuildCheck() {
    Assert.notNull(this.context.getClazz(), "未指定导入实体类型");

  }

}
