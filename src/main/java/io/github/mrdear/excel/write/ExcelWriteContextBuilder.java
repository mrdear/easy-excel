package io.github.mrdear.excel.write;

import io.github.mrdear.excel.domain.ExcelImportError;
import io.github.mrdear.excel.internal.util.Assert;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Quding Ding
 * @since 2018/6/28
 */
public class ExcelWriteContextBuilder {
  /**
   * 所有的build都是对其进行维护
   */
  private ExcelWriteContext context;

  public ExcelWriteContextBuilder() {
    this.context = new ExcelWriteContext();
  }

  public <T> ExcelWriteContextBuilder datasource(List<T> datasource) {
    this.context.setDatasource(datasource);
    // 此时可以确定header
    if (null == this.context.getHeaders()) {
      if (CollectionUtils.isNotEmpty(datasource)) {
        this.context.setHeaders(datasource.get(0));
      } else {
        this.context.setHeaders(Collections.emptyList());
      }
    }
    return this;
  }

  /**
   * 指定excel的header
   *
   * @param headers key field name  value WriterHeader
   * @return this
   */
  public ExcelWriteContextBuilder headers(LinkedHashMap<String, WriterHeader> headers) {
    this.context.setHeaders(headers);
    return this;
  }

  /**
   * 指定标记为错误的单元格的错误列表
   *
   * @param errors 错误列表
   * @return this
   */
  public ExcelWriteContextBuilder errors(List<ExcelImportError> errors) {
    this.context.setErrors(errors);
    return this;
  }

  public ExcelWriteContextBuilder createSheetHook(BiConsumer<Sheet, ExcelWriteContext> createSheetHook) {
    this.context.setCreateSheetHook(createSheetHook);
    return this;
  }


  public ExcelWriteContextBuilder startRow(int startRow) {
    this.context.setStartRow(startRow);
    return this;
  }

  public ExcelWriteContextBuilder sheetName(String sheetName) {
    this.context.setSheetName(sheetName);
    return this;
  }

  /**
   * 最终build方法
   *
   * @return ExcelWriteContext
   */
  public ExcelWriteContext build() {
    beforeBuildCheck();
    return context;
  }


  private void beforeBuildCheck() {
    Assert.notNull(context.getDatasource(), "datasource can't be null");
    Assert.notNull(context.getHeaders(), "headers can't be null");
  }
}
