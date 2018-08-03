package io.github.mrdear.excel.domain;

import io.github.mrdear.excel.internal.util.ExcelBeanHelper;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * excel上下文
 * 针对每一个sheet
 *
 * @author Quding Ding
 * @since 2018/6/27
 */
public class ExcelWriteContext {
  /**
   * 数据源
   */
  private List<Map<String, Object>> datasource;
  /**
   * excel头
   */
  private LinkedHashMap<String, ExcelWriterHeader> headers;
  /**
   * 创建工作目录后的hook
   */
  private BiConsumer<Sheet, ExcelWriteContext> createSheetHook = (w, v) -> { };
  /**
   * 起始行
   */
  private int startRow = 0;

  private String sheetName;

  public static ExcelWriteContextBuilder builder() {
    return new ExcelWriteContextBuilder();
  }

  // package set

  <T> ExcelWriteContext setDatasource(List<T> datasource) {
    // 处理空情况
    if (null == datasource || datasource.isEmpty()) {
      this.datasource = Collections.emptyList();
      return this;
    }
    this.datasource = ExcelBeanHelper.beanToMap(datasource);
    return this;
  }

  /**
   * 根据bean确定header
   *
   * @param bean bean
   */
  @SuppressWarnings("unchecked")
  <T> ExcelWriteContext setHeaders(T bean) {
    if (bean instanceof Map) {
      ((Map<String, ?>) bean)
          .keySet()
          .stream()
          .collect(LinkedHashMap::new,
              (l, v) -> l.put(v, ExcelWriterHeader.create(v)),
              Map::putAll);
    } else {
      this.headers = ExcelBeanHelper.beanToWriterHeaders(bean);
    }
    return this;
  }


  ExcelWriteContext setHeaders(LinkedHashMap<String, ExcelWriterHeader> headers) {
    this.headers = headers;
    return this;
  }

  ExcelWriteContext setCreateSheetHook(BiConsumer<Sheet, ExcelWriteContext> createSheetHook) {
    this.createSheetHook = createSheetHook;
    return this;
  }

  ExcelWriteContext setStartRow(int startRow) {
    this.startRow = startRow;
    return this;
  }

  ExcelWriteContext setSheetName(String sheetName) {
    this.sheetName = sheetName;
    return this;
  }

  // public get


  public List<Map<String, Object>> getDatasource() {
    return datasource;
  }


  public LinkedHashMap<String, ExcelWriterHeader> getHeaders() {
    return headers;
  }

  public BiConsumer<Sheet, ExcelWriteContext> getCreateSheetHook() {
    return createSheetHook;
  }

  public int getStartRow() {
    return startRow;
  }

  public String getSheetName() {
    return sheetName;
  }
}
