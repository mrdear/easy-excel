package io.github.mrdear.excel.write;

import io.github.mrdear.excel.domain.ExcelImportError;
import io.github.mrdear.excel.internal.util.DocBeanUtils;
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
  private LinkedHashMap<String, WriterHeader> headers;
  /**
   * 创建工作目录后的hook
   */
  private BiConsumer<Sheet, ExcelWriteContext> createSheetHook = (w, v) -> {
  };
  /**
   * 起始行
   */
  private int startRow = 0;

  private String sheetName;
  /**
   * 错误列表
   */
  private List<ExcelImportError> errors = Collections.emptyList();

  public static ExcelWriteContextBuilder builder() {
    return new ExcelWriteContextBuilder();
  }

  // package set

  public List<Map<String, Object>> getDatasource() {
    return datasource;
  }

  <T> ExcelWriteContext setDatasource(List<T> datasource) {
    // 处理空情况
    if (null == datasource || datasource.isEmpty()) {
      this.datasource = Collections.emptyList();
      return this;
    }
    this.datasource = DocBeanUtils.beanToMap(datasource);
    return this;
  }

  public LinkedHashMap<String, WriterHeader> getHeaders() {
    return headers;
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
              (l, v) -> l.put(v, WriterHeader.create(v)),
              Map::putAll);
    } else {
      this.headers = DocBeanUtils.beanToWriterHeaders(bean);
    }
    return this;
  }

  ExcelWriteContext setHeaders(LinkedHashMap<String, WriterHeader> headers) {
    this.headers = headers;
    return this;
  }

  public BiConsumer<Sheet, ExcelWriteContext> getCreateSheetHook() {
    return createSheetHook;
  }

  // public get

  ExcelWriteContext setCreateSheetHook(BiConsumer<Sheet, ExcelWriteContext> createSheetHook) {
    this.createSheetHook = createSheetHook;
    return this;
  }

  public int getStartRow() {
    return startRow;
  }

  ExcelWriteContext setStartRow(int startRow) {
    this.startRow = startRow;
    return this;
  }

  public String getSheetName() {
    return sheetName;
  }

  ExcelWriteContext setSheetName(String sheetName) {
    this.sheetName = sheetName;
    return this;
  }

  public List<ExcelImportError> getErrors() {
    return errors;
  }

  public ExcelWriteContext setErrors(List<ExcelImportError> errors) {
    this.errors = errors;
    return this;
  }
}
