package io.github.mrdear.excel.read;

import io.github.mrdear.excel.annotation.DocField;
import io.github.mrdear.excel.domain.ReadHeader;
import io.github.mrdear.excel.internal.util.Assert;
import io.github.mrdear.excel.internal.util.DocBeanUtils;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * 对应的读取构造器
 *
 * @author Quding Ding
 * @since 2018/6/29
 */
public class ReadContextBuilder<T> {

  /**
   * 读取对应转换类类型
   */
  private Class<T> clazz;

  /**
   * 表头与类属性之间的映射
   */
  private Map<String, ReadHeader> headers;

  /**
   * 表头开始位置,用于跳过一些行
   */
  private int headerStart = 0;

  /**
   * excel读取hook
   */
  private BiConsumer<Sheet, ReadContext> excelReadSheetHook;

  private ReadContextBuilder() { }

  /**
   * 实例化入口
   */
  public static <T> ReadContextBuilder<T> builder() {
    return new ReadContextBuilder<>();
  }

  /**
   * 使用该方法默认会使用{@link DocField} 注解进行转换Header
   *
   * @param clazz 目标类
   * @return header构造器
   */
  public ReadContextBuilder<T> clazz(Class<T> clazz) {
    this.clazz = clazz;
    // 此时header可以确定
    if (null == this.headers) {
      this.headers = DocBeanUtils.beanToReaderHeaders(clazz);
    }
    return this;
  }

  public ReadContextBuilder<T> headerStart(int headerStart) {
    this.headerStart = headerStart;
    return this;
  }

  public ReadContextBuilder<T> excelReadSheetHook(BiConsumer<Sheet, ReadContext> readSheetHook) {
    this.excelReadSheetHook = readSheetHook;
    return this;
  }

  public ReadContext<T> buildForExcel() {
    beforeBuildCheck();

    ExcelReadContext<T> context = new ExcelReadContext<>();
    context.setClazz(this.clazz);
    context.setHeaders(this.headers);
    context.setHeaderStart(this.headerStart);
    context.setReadSheetHook(Optional
        .ofNullable(this.excelReadSheetHook).orElse((k,v) -> {}));
    return context;
  }

  public ReadContext<T> buildForExcel(int sheetIndex) {
    beforeBuildCheck();

    ExcelReadContext<T> context = (ExcelReadContext<T>) buildForExcel();
    context.setSheetIndex(sheetIndex);
    return context;
  }


  private void beforeBuildCheck() {
    Assert.notNull(this.clazz, "未指定导入实体类型");
  }

}
